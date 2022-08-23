package com.dong.base.feignClient;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "info")
public interface infoClient {

    @RequestMapping("/getV")
    public String getV();
}
