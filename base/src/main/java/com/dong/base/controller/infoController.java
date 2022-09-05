package com.dong.base.controller;

import com.dong.base.service.infoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class infoController {


    @Autowired
    infoService infoService;

    @Autowired
    private RestTemplate restTemplate;

    private final String  URL = "http://info";

    @RequestMapping("/getV")
    public String getInfo() throws InterruptedException {
        System.out.println("enter the base service getV method =====liyayi");
        return infoService.getInfo();

    }

    @RequestMapping("/restTemplate/testRibbon")
    public String testRibbon2() {
        System.out.println("enter the method");
        // 使用restTemplate发起请求
        String result = restTemplate.getForEntity(URL + "/ribbonTest", String.class).getBody();
        return result;
    }
}
