package com.baizhi.dao;

import com.baizhi.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {

    /*查询所有*/
    List<User> findAll();

    //保存用户的方法
    void save(User user);


    void delete(String id);

    //根据id查询信息
    User findOne(String id);
    //修改用户
    void update(User user);

    //根据姓名或者电话进行模糊搜索
    List<User> findNameOrPhoneCode(@Param("name") String name,@Param("code") String phoneCode);
}
