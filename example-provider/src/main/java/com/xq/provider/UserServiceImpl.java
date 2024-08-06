package com.xq.provider;

import com.xq.common.mode.User;
import com.xq.common.service.UserService;

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
