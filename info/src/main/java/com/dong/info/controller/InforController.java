package com.dong.info.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.dong.info.entity.DynamicConfigEntity;
import com.dong.info.entity.User;
import com.dong.info.feign.BaseFeign;
import com.dong.info.service.impl.UserServiceImpl;
import feign.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RefreshScope
public class InforController {



    @Autowired
    UserServiceImpl userService;
    @Autowired
    DynamicConfigEntity dynamicConfigEntity;

    @Autowired
    BaseFeign baseFeign;

    @Value(value = "${huawei.endpoint}")
     String version;


    @RequestMapping(value = "getnacosconfig")
    public String getnacosconfig() {
        System.out.println(version);
        return version;
    }

    @RequestMapping(value = "/getUser",method = RequestMethod.GET)
    public List<User> getUser(@Param("account") String account) throws InterruptedException {

        System.out.println("into infov service 8673" );
        return userService.selectUser(account);
    }

    @RequestMapping("/getCurrentDate")
    public String getTime() {
        System.out.println(Thread.currentThread().getName());
        String name = Thread.currentThread().getName();
        return name;
    }


    @RequestMapping("/getV")
    public String getV(){
        System.out.println("enter the info service getV method 8673");
        return "19";
    }

    @Value("${server.port}")
    private String port;

    @GetMapping("/ribbonTest")
    public String ribbonTest() {
        return "我是服务提供者 windows -- 我的端口是：" + port;
    }






}
