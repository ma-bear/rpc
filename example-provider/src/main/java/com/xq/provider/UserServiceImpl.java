package com.xq.example.provider;

import com.example.common.mode.User;
import com.example.common.service.UserService;

/**
 * @author xq
 * @create 2024/8/5 10:28
 */
public class UserServiceImpl implements UserService {
    @Override
    public User getUser(User user) {

        System.out.println(user.getName());
        return user;
    }
}
