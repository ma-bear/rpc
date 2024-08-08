package com.xq.provider;

import com.xq.common.service.UserService;
import com.xq.core.RpcApplication;
import com.xq.core.config.RegistryConfig;
import com.xq.core.config.RpcConfig;
import com.xq.core.mode.ServiceMetaInfo;
import com.xq.core.registry.Registry;
import com.xq.core.registry.RegistryFactory;
import com.xq.core.server.HttpServer;
import com.xq.core.server.VertxHttpServer;
import com.xq.core.server.tcp.VertxTcpServer;


/**
 * 服务提供者示例
 *
 */
public class ProviderExample {

    public static void main(String[] args) {
        // RPC 框架初始化
        RpcApplication.init();

//        // 本地注册服务
        String serviceName = UserService.class.getName();
//        LocalRegistry.register(serviceName, UserServiceImpl.class);

        // 注册服务到注册中心
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
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

//        // 启动 web 服务
//        HttpServer httpServer = new VertxHttpServer();
//        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());

        // 启动 TCP 服务
        VertxTcpServer tcpServer = new VertxTcpServer();
        tcpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    }
}

