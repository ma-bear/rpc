package com.xq.core.server;

import io.vertx.core.Vertx;

/**
 * @author xq
 * @create 2024/8/5 11:01
 */
public class VertxHttpServer implements HttpServer {

    @Override
    public void doStart(int port) {

        // 获取 vertx 实例化对象
        Vertx vertx = Vertx.vertx();

        //创建 HTTP 服务器
        io.vertx.core.http.HttpServer httpServer = vertx.createHttpServer();

        // 监听端口并处理请求
        httpServer.requestHandler(new HttpServerHandler());

        // 启动 HTTP 服务器并监听指定端口
        httpServer.listen(port,result -> {
            if (result.succeeded()){
                System.out.println("Server is now listening on port " + port);
            }else {
                System.out.println("Failed to start server: " + result.cause());
            }
        });
    }
}
