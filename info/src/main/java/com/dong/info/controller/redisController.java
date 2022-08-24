package com.dong.info.controller;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class redisController {


    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @RequestMapping(value="/user/testGet",method= RequestMethod.GET)
    public String getname(int age ){
        stringRedisTemplate.opsForValue().set("lzj","123");
        System.out.println(stringRedisTemplate.opsForValue().get("name"));
        return stringRedisTemplate.opsForValue().get("lzj");
    }
}
