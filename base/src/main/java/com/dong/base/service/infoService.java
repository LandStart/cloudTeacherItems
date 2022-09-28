package com.dong.base.service;

import com.dong.base.feignClient.infoClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class infoService {

    @Autowired
    infoClient infoClient;


   @HystrixCommand(fallbackMethod = "serviceFallback",commandProperties = {
           @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="15000")
   })
    public String getInfo() throws InterruptedException {

        return infoClient.getV();
    }

    public String serviceFallback(){
        /**
         *
         * 备用逻辑
         */
       return "请求返回结果超过时间，请稍后";
    }
}
