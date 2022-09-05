package com.dong.info.controller;

import com.dong.info.entity.DynamicConfigEntity;
import com.dong.info.entity.User;
import com.dong.info.feign.BaseFeign;
import com.dong.info.service.impl.UserServiceImpl;
import feign.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

        System.out.println("into infov service 8673 ---");
        return userService.selectUser(account);
    }


    @RequestMapping("/getV")
    public String getV() {
        System.out.println("enter the info service getV method 8673");
        return "19";
    }

    @Value("${server.port}")
    private String port;

    @GetMapping("/ribbonTest")
    public String ribbonTest() {
        return "我是服务提供者 -- 我的端口是：" + port;
    }


}
