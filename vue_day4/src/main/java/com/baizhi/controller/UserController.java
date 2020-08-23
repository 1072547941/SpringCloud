package com.baizhi.controller;

import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin//跨域
public class UserController {

    @Autowired
    private UserService userService;

    //查询所有方法
    @GetMapping("/findall")
    public List<User> findAll(){
        System.out.println("123");
        return userService.findAll();
    }
    @PostMapping("save")
    public Map<String,Object> save(@RequestBody User user){
        Map<String,Object> map = new HashMap<>();
        try {
            if (StringUtils.isEmpty(user.getId())){
                user.setId(UUID.randomUUID().toString().replace("-",""));
                userService.save(user);
            }else {
                userService.update(user);
            }
            map.put("success",true);
        } catch (Exception e) {
            map.put("success",false);
            e.printStackTrace();
        }
        return map;
    }
    //删除用户信息
    @GetMapping("delete")
    public Map<String, Object> delete( String id){
        Map<String, Object> map = new HashMap<>();
        try {
            userService.delete(id);
            map.put("success","success");
        } catch (Exception e) {
            map.put("success","false");
            map.put("message","删除失败");
            e.printStackTrace();
        }
        return map;
    }
    /**'
     * 根据id查询用户
     */
    @GetMapping("findOne")
    public User findOne(String id){
        return userService.findOne(id);
    }
    /**
     * 模糊查询
     *
     */
    @GetMapping("findLike")
    public List<User> findNameOrPhoneCode(String name,String code){
        return userService.findNameOrPhoneCode(name,code);
    }
}
