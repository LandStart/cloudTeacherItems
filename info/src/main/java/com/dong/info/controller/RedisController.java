package com.dong.info.controller;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class RedisController {


    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @RequestMapping(value="/user/testGet",method= RequestMethod.GET)
    public String getname(@RequestParam("name") String name , @RequestParam("value") String value) throws Exception {
        String cacheName = stringRedisTemplate.opsForValue().get("name");

        try {
            if(name.equals("") && name.equals(" ") && name.equals("null")){
                System.out.println("缓存中不存在数据");
            }

            if(cacheName.equals(value)){
                return  value;
            }else{
                stringRedisTemplate.opsForValue().set( name, value);
            }

            stringRedisTemplate.opsForValue().set( name, value);
        }catch (Exception e ){
            throw new Exception(e.getMessage());
        }


        return value;
    }
}
