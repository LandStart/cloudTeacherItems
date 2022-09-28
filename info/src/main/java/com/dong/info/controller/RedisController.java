package com.dong.info.controller;

import com.dong.info.entity.User;
import com.dong.info.service.impl.UserServiceImpl;
import feign.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class RedisController {


    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private UserServiceImpl userService;


    @RequestMapping(value="/user/testGet",method= RequestMethod.GET)
    public String getname(@RequestParam("name") String name , @RequestParam("value") String value) throws Exception {
        String cacheName = stringRedisTemplate.opsForValue().get(name);

        try {
            if(name.equals("") && name.equals(" ")){
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

    /**
     * 用redis做高速缓存，提高查询效率
     * 1.查询： 先去缓存查找，如果找到数据，缓存命中，直接返回，如果没有命中，则去查询数据库
     * 2.更新： 先去更新数据库，然后去缓存中更新数据；
     * 3.删除：  删除数据库中的对象，然后去redis中删除；
     * 4.插入： 先将数据插入到数据库，然后再讲数据插入到缓存中；
     *
     * 假设我们将用户名和密码是常用的数据，将他放到缓存中，以提高访问速度；
     */

    //查询
    @RequestMapping("/getUserReids")
     public String getUserWithRedis(@Param("key") String key) throws Exception {
        return userService.getUserWithRedis(key);
     }
    //更新
    @PostMapping("/updateUser")
    public String UpdateUserWithRedis(@RequestBody User user) throws Exception {
        return userService.UpdateUserWithRedis(user);

    }
    //删除
    @RequestMapping("/delUser")
    public String DeleteUserWithRedis(@Param ("key") String key) throws Exception {
        return userService.DeleteUserWithRedis(key);
    }
    //插入
    @PostMapping("/insertUser")
    public String InsertUserWithRedis(@RequestBody User user) throws Exception {
        return userService.InsertUserWithRedis(user);
    }
}
