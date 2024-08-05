package com.xq.example.rpc.server;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.CorsHandler;

/**
 * @author xq
 * @create 2024/8/5 11:01
 */
public class VertxHttpServer implements HttpServer{

    @Override
    public void doStart(int port) {

        // 获取 vertx 实例化对象
        Vertx vertx = Vertx.vertx();

        //创建 HTTP 服务器
        io.vertx.core.http.HttpServer httpServer = vertx.createHttpServer();

        Router router = Router.router(vertx);

        // 配置CORS处理器
        CorsHandler corsHandler = CorsHandler.create("*") // 允许所有来源访问
                .allowedMethod(io.vertx.core.http.HttpMethod.GET) // 允许GET方法
                .allowedMethod(io.vertx.core.http.HttpMethod.POST) // 允许POST方法
                .allowedHeader("Content-Type"); // 允许Content-Type头部

        // 将CORS处理器添加到路由中
        router.route().handler(corsHandler);

        router.get("/hello").handler(this::handleHello);

        // 监听端口并处理请求
        httpServer.requestHandler(request -> {
            // 处理 HTTP 请求
            System.out.println("Received request: " + request.method() + " " + request.uri());

            // 发送 HTTP 响应
            request.response()
                    .putHeader("content-type","text/plain")
                    .end("Hello from Vert.x HTTP server");
        });

        // 启动 HTTP 服务器并监听指定端口
        httpServer.listen(port,result -> {
            if (result.succeeded()){
                System.out.println("Server is now listening on port " + port);
            }else {
                System.out.println("Failed to start server: " + result.cause());
            }
        });



    }

    private void handleHello(RoutingContext context) {
        HttpServerResponse response = context.response();
        response.putHeader("content-type", "text/plain");
        response.end("Hello, World!");
    }
}
