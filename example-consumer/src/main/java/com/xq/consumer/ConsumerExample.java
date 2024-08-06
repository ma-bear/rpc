package com.xq.consumer;

import com.xq.core.config.RpcConfig;
import com.xq.core.utils.ConfigUtils;

/**
 * @author xq
 * @create 2024/8/6 11:01
 */
public class ConsumerExample {

    public static void main(String[] args) {
        RpcConfig rpcConfig = ConfigUtils.loadConfig(RpcConfig.class, "rpc");
        System.out.println(rpcConfig);
    }
}
