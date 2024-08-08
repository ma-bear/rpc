package com.xq.consumer;

import com.xq.common.mode.User;
import com.xq.common.service.UserService;
import com.xq.core.proxy.ServiceProxyFactory;



/**
 * @author xq
 * @create 2024/8/6 11:01
 */
public class ConsumerExample {

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 2; i++) {
            UserService userService = ServiceProxyFactory.getProxy(UserService.class);

            User user = new User();
            user.setName("yupi");
            // 调用
            User newUser = userService.getUser(user);
            if (newUser != null){
                System.out.println(newUser.getName());
            }else {
                System.out.println("user == null");
            }
        }

//        // 阻塞 1 分钟
//        Thread.sleep(60 * 1000L);
//
//        System.out.println("第3次调用，服务节点挂了");

//        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
//
//        User user = new User();
//        user.setName("yupi");
//        // 调用
//        User newUser = userService.getUser(user);
//        if (newUser != null){
//            System.out.println(newUser.getName());
//        }else {
//            System.out.println("user == null");
//        }
//        System.out.println(userService.getNumber());

//        RpcConfig rpcConfig = ConfigUtils.loadConfig(RpcConfig.class, "rpc");
//        System.out.println(rpcConfig);

    }
}
