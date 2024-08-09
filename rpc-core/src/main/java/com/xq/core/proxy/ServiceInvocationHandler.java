package com.xq.core.proxy;

import cn.hutool.core.collection.CollUtil;

import com.xq.core.RpcApplication;
import com.xq.core.config.RpcConfig;
import com.xq.core.constant.RpcConstant;
import com.xq.core.fault.retry.RetryStrategy;
import com.xq.core.fault.retry.RetryStrategyFactory;
import com.xq.core.fault.tolerant.TolerantStrategy;
import com.xq.core.fault.tolerant.TolerantStrategyFactory;
import com.xq.core.loadbalancer.LoadBalancer;
import com.xq.core.loadbalancer.LoadBalancerFactory;
import com.xq.core.mode.RpcRequest;
import com.xq.core.mode.RpcResponse;
import com.xq.core.mode.ServiceMetaInfo;
import com.xq.core.registry.Registry;
import com.xq.core.registry.RegistryFactory;
import com.xq.core.serializer.Serializer;
import com.xq.core.serializer.SerializerFactory;
import com.xq.core.server.tcp.VertxTcpClient;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

/**
 * 服务代理（JDK 动态代理）
 */
public class ServiceInvocationHandler implements InvocationHandler {

    /**
     * 调用代理
     *
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 指定序列化器
        final Serializer serializer = SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());

        // 构造请求
        String serviceName = method.getDeclaringClass().getName();
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(serviceName)
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();
        try {
            // 序列化
            byte[] bodyBytes = serializer.serialize(rpcRequest);
            // 从注册中心获取服务提供者请求地址
            RpcConfig rpcConfig = RpcApplication.getRpcConfig();
            Registry registry = RegistryFactory.getInstance(rpcConfig.getRegistryConfig().getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceVersion(RpcConstant.DEFAULT_SERVICE_VERSION);
            List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceMetaInfo.getServiceKey());
            if (CollUtil.isEmpty(serviceMetaInfoList)) {
                throw new RuntimeException("暂无服务地址");
            }

            // 负载均衡
            LoadBalancer loadBalancer = LoadBalancerFactory.getInstance(rpcConfig.getLoadBalancer());
            HashMap<String, Object> requestParams = new HashMap<>();
            requestParams.put("methodName", rpcRequest.getMethodName());
            ServiceMetaInfo selectedServiceMetaInfo = loadBalancer.select(requestParams, serviceMetaInfoList);

            try {
                // 使用重试机制
                RetryStrategy retryStrategy = RetryStrategyFactory.getInstance(rpcConfig.getRetryStrategy());
                RpcResponse rpcResponse = retryStrategy.doRetry(() ->
                        // 发送 rpc 请求
                        VertxTcpClient.doRequest(rpcRequest, selectedServiceMetaInfo)
                );
                return rpcResponse.getData();
            } catch (Exception e) {
                // 容错机制
                TolerantStrategy tolerantStrategy = TolerantStrategyFactory.getInstance(rpcConfig.getTolerantStrategy());
                tolerantStrategy.doTolerant(null, e);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
