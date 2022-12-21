package com.dong.infoback.controller;


import com.dong.infoback.entity.DynamicConfigEntity;
import com.dong.infoback.entity.User;
import com.dong.infoback.feign.BaseFeign;
import com.dong.infoback.service.impl.UserServiceImpl;
import feign.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class InforController {



    @Autowired(required = true)
    UserServiceImpl userService;
    @Autowired
    DynamicConfigEntity dynamicConfigEntity;

    @Autowired
    BaseFeign baseFeign;
    @Autowired
    RestTemplate restTemplate;




    @RequestMapping("/getUser")
    public List<User> getUser(@Param("account") String account) throws InterruptedException {
       System.out.println("into infov service 8674" );
        return userService.selectUser(account);
    }



    @RequestMapping("/getV")
    public String getV(){
        System.out.println("enter the info service getV method 8674");
        return "19";
    }

    @Value("${server.port}")
    private String port;

    @GetMapping("/ribbonTest")
    public String ribbonTest() {
        return "我是服务提供者 -- 我的端口是：" + port;
    }




}
