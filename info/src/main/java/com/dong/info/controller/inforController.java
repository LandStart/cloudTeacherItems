package com.dong.info.controller;

import com.dong.info.entity.DynamicConfigEntity;
import com.dong.info.entity.User;
import com.dong.info.service.impl.UserServiceImpl;
import feign.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class inforController {


    @Autowired
    UserServiceImpl userService;
    @Autowired
    DynamicConfigEntity dynamicConfigEntity;

    @RequestMapping("/getUser")
    public List<User> getUser(@Param("account") String account) throws InterruptedException {
        System.out.println("into infov service" );
        return userService.selectUser(account);
    }



}
