package com.dong.info.controller;

import com.dong.info.entity.DynamicConfigEntity;
import com.dong.info.entity.User;
import com.dong.info.service.impl.UserServiceImpl;
import feign.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller
public class loginController {

    @Autowired
    UserServiceImpl userService;
    @Autowired
    DynamicConfigEntity dynamicConfigEntity;

    @RequestMapping(value = "login",method = RequestMethod.POST)
    public String login(@Param("username") String username, @Param("password") String password) throws InterruptedException {
        return userService.login(username,password);

    }

    @RequestMapping("deleteUser")
    public String deleteUser(@Param("username") String username, @Param("password") String password) throws Exception {
        return userService.deleteUser(username,password);
    }




    @RequestMapping("signUp")
    public String login( User user) throws Exception {
        System.out.println(user);
        return userService.signUp(user);
    }
}
