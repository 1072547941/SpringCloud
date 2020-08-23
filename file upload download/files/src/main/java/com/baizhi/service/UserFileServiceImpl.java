package com.baizhi.service;

import com.baizhi.dao.UserFileDao;
import com.baizhi.entity.UserFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class UserFileServiceImpl implements UserFileService {
    @Autowired
    @Resource
    private UserFileDao userFileDao;

    @Override
    public List<UserFile> findByUserId(Integer id) {
        return userFileDao.findByUserId(id);
    }

    @Override
    public void save(UserFile userFile) {
//        userFile.setIsImg();
        userFile.setDowncounts(0);
        userFile.setUploadTime(new Date());
        userFile.setIsImg(userFile.getType().startsWith("image")?"是":"否");
        System.out.println(userFile);
        userFileDao.save(userFile);
    }

    @Override
    public UserFile findById(String id) {
        return userFileDao.findById(id);
    }

    @Override
    public void updateFilecounts(UserFile userFile) {
        userFileDao.updateFilecounts(userFile);
    }

    @Override
    public void delete(String id) {
        userFileDao.delete(id);
    }
}
