package com.baizhi.service;

import com.baizhi.entity.User;

import java.util.List;

public interface UserService {

//    次查询所有用户
    List<User> findAll();

    //保存用户
    void save(User user);

    void delete(String id);

    //根据id查询用户信息
    User findOne(String id);

    //修改用户信息
    void update(User user);

    //模糊搜索
    List<User> findNameOrPhoneCode(String name,String code);
}
