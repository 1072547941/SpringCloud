package com.baizhi.service;

import com.baizhi.entity.User;
import com.baizhi.entity.UserFile;

import java.util.List;

public interface UserFileService {
    List<UserFile> findByUserId(Integer id);

    void save(UserFile userFile);

    UserFile findById(String id);

    void updateFilecounts(UserFile userFile);

    void delete(String id);
}
