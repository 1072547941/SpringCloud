package com.baizhi.controller;

import com.baizhi.dao.UserDao;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    UserService userService;

    /**
     * 登陆方法
     * @param user
     * @return
     */
    @PostMapping("login")
    public String login(User user, HttpSession session){
        User userDB = userService.login(user);
        if (userDB!=null){
            //登陆成功
            session.setAttribute("user",userDB);
            return "redirect:/file/showAll";
        }else {
            return "redirect:/index";
        }

    }

    @Autowired
    @Resource
    private UserDao userDao;


    @GetMapping("add")
    @ResponseBody
    public String add(){
        User user = new User(3,"admin1","admin1");
        userDao.add(user);
        return "";
    }
}
