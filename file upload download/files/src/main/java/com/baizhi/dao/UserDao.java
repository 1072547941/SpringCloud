package com.baizhi.dao;

import com.baizhi.entity.User;

public interface UserDao {

    //登陆功能
    User login(User user);
    void add(User user);

}
