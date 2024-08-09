package com.xq.core.bootstrap;

import com.xq.core.RpcApplication;
import com.xq.core.config.RegistryConfig;
import com.xq.core.config.RpcConfig;
import com.xq.core.mode.ServiceMetaInfo;
import com.xq.core.mode.ServiceRegisterInfo;
import com.xq.core.registry.LocalRegistry;
import com.xq.core.registry.Registry;
import com.xq.core.registry.RegistryFactory;
import com.xq.core.server.tcp.VertxTcpServer;

import java.util.List;

/**
 * @author xq
 * @create 2024/8/9 11:08
 */
public class ProviderBootstrap {

    /**
     * 初始化
     */
    public static void init(List<ServiceRegisterInfo> serviceRegisterInfoList) {
        // RPC 框架初始化 （配置和注册中心）
        RpcApplication.init();
        // 全局配置
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();

        // 注册服务
        for (ServiceRegisterInfo serviceRegisterInfo : serviceRegisterInfoList) {
            String serviceName = serviceRegisterInfo.getServiceName();
            // 本地注册服务
            LocalRegistry.register(serviceName, serviceRegisterInfo.getImplClass());

            // 注册服务到注册中心
            RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
            Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
            serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
            try {
                registry.register(serviceMetaInfo);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            // 启动 TCP 服务
            VertxTcpServer tcpServer = new VertxTcpServer();
            tcpServer.doStart(RpcApplication.getRpcConfig().getServerPort());

        }
    }
}

