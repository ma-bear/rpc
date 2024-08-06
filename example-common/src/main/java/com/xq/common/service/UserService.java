package com.xq.common.service;

import com.xq.common.mode.User;

/**
 * @author xq
 * @create 2024/8/5 10:11
 */
public interface UserService {

    /**
     * 获取用户
     *
     * @param user
     * @return
     */
    User getUser(User user);

    default long getNumber(){
        return 1;
    }
}
