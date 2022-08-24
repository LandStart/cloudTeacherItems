package com.dong.info.controller;

import com.dong.info.entity.DynamicConfigEntity;
import com.dong.info.entity.User;
import com.dong.info.feign.BaseFeign;
import com.dong.info.service.impl.UserServiceImpl;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import feign.Param;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InforController {



    @Autowired
    UserServiceImpl userService;
    @Autowired
    DynamicConfigEntity dynamicConfigEntity;

    @Autowired
    BaseFeign baseFeign;

    @RequestMapping("/getUser")
    public List<User> getUser(@Param("account") String account) throws InterruptedException {
        System.out.println("into infov service" );
        return userService.selectUser(account);
    }


    @RequestMapping("/getV")
    public String getV(){
        System.out.println("enter the info service getV method ");
        return "19";
    }






}
