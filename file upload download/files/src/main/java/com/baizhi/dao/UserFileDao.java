package com.baizhi.dao;

import com.baizhi.entity.UserFile;

import java.util.List;

public interface UserFileDao {
    //根据用户登陆用户的id，获取用户的文件列表信息
    List<UserFile> findByUserId(Integer id);

    //保存用户的文件记录
    void save(UserFile userFile);
    //根据文件id获取文件信息
    UserFile findById(String id);

    void updateFilecounts( UserFile userFile);

    void delete(String id);
}
