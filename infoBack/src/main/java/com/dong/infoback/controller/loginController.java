package com.dong.infoback.controller;


import com.dong.infoback.entity.DynamicConfigEntity;
import com.dong.infoback.entity.User;
import com.dong.infoback.service.impl.UserServiceImpl;
import feign.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class loginController {

    @Autowired
    UserServiceImpl userService;
    @Autowired
    DynamicConfigEntity dynamicConfigEntity;

    @RequestMapping("login")
    public String login(@Param("username") String username, @Param("password") String password) throws InterruptedException {
        return userService.login(username,password);

    }


    @RequestMapping("signUp")
    public String login( User user) throws Exception {
        System.out.println(user);
        return userService.signUp(user);
    }
}
