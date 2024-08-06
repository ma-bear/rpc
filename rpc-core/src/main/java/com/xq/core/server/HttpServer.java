package com.xq.core.server;

/**
 * @author xq
 * @create 2024/8/5 10:56
 */

/**
 * HTTP 服务器接口
 */
public interface HttpServer {

    /**
     * 启动服务器
     *
     * @param port
     */
    void doStart(int port);


}
