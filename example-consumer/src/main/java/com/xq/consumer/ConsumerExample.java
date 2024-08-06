package com.xq.consumer;

import com.xq.common.mode.User;
import com.xq.common.service.UserService;
import com.xq.core.proxy.ServiceProxyFactory;


/**
 * @author xq
 * @create 2024/8/6 11:01
 */
public class ConsumerExample {

    public static void main(String[] args) {
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

        System.out.println(userService.getNumber());

    }
}
