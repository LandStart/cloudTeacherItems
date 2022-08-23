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
           @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="1500")
   })
    public String getInfo() throws InterruptedException {
       Random random = new Random();
       int randI = random.nextInt(10);
       System.out.println(randI);
       if(randI % 2 == 0){
           Thread.sleep(1000000);
       }
        return infoClient.getV();
    }

    public String serviceFallback(){
       return "请求返回结果超过时间，请稍后";
    }
}
