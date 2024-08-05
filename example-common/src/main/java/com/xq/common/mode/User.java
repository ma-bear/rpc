package com.xq.example.common.mode;

import java.io.Serializable;

/**
 * @author xq
 * @create 2024/8/5 10:11
 */
public class User implements Serializable {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
