package com.xq.example.provider;

import com.example.rpc.server.VertxHttpServer;

/**
 * @author xq
 * @create 2024/8/5 10:32
 */
public class EasyProviderExample {

    public static void main(String[] args) {

        // 启动 Web 服务器
        VertxHttpServer httpServer = new VertxHttpServer();

        httpServer.doStart(8080);
    }
}
