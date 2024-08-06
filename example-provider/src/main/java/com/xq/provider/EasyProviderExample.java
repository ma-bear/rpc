package com.xq.provider;

import com.xq.common.service.UserService;
import com.xq.core.RpcApplication;
import com.xq.core.registry.LocalRegistry;
import com.xq.core.server.VertxHttpServer;


/**
 * @author xq
 * @create 2024/8/5 10:32
 */
public class EasyProviderExample {

    public static void main(String[] args) {

        // RPC 框架初始化
        RpcApplication.init();

        // 注册服务
        LocalRegistry.register(UserService.class.getName(),UserServiceImpl.class);

        // 启动 Web 服务器
        VertxHttpServer httpServer = new VertxHttpServer();

        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    }
}
