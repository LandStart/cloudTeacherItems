package com.dong.base.controller;

import com.dong.base.service.infoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class infoController {


    @Autowired
    infoService infoService;


    @RequestMapping("/getV")
    public String getInfo() throws InterruptedException {
        System.out.println("enter the base service getV method");
        return infoService.getInfo();

    }
}
