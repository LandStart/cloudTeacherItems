package com.dong.infoback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dong.infoback.entity.DynamicConfigEntity;
import com.dong.infoback.entity.User;
import com.dong.infoback.service.UserService;
import com.dong.infoback.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
* @author 81921
* @description 针对表【user】的数据库操作Service实现
* @createDate 2022-09-04 23:43:52
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{


    @Autowired(required = true)
    UserMapper userMappers;

    @Autowired
    DynamicConfigEntity dynamicConfigEntity;

    @Override
    @LoadBalanced
    public List<User> selectUser(String acccount) {
        System.out.println(dynamicConfigEntity.getVersion());
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", acccount);
        List<User> list = userMappers.selectList(queryWrapper );

        if(list == null && list.size() == 0 ){
            return list = new ArrayList<>();
        }

        return list;
    }

    public String login(String username,String password){

        if(username.equals("")
                || username.equals(" ")
        ){
            System.out.println("账号和密码不能为空");
            return "redirect:error.html";
        }


        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);

        List<User> list = userMappers.selectList(queryWrapper );
        if(list == null && list.size() == 0 ){
            System.out.println("用户不存在");
            return "redirect:error.html";
        }

        for (User u:
                list) {
            if(u.getPassword().equals(password)){
                return "redirect:content.html";

            }else{
                System.out.println("密码不正确");
                return "redirect:error.html";
            }
        }
        return "redirect:error.html";
    }


    public String signUp(User user) throws Exception {
        if(user == null){
            return "redirect:error.html";
        }

        if(user.getUsername().equals("")
                || user.getUsername().equals(" ")
                ||user.getPassword().equals("")
                ||user.getPassword().equals(" ")){
            System.out.println("账号和密码不能为空");
            return "redirect:error.html";
        }

        String username = user.getUsername();

        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);

        List<User> list = userMappers.selectList(queryWrapper );
        if(list != null && list.size() > 0 ){
            System.out.println("用户已存在");
            return "redirect:error.html";
        }
        try {
            userMappers.insert(user);
        }catch (Exception e){
            throw new Exception("save error");
        }

        return "redirect:content.html";
    }

}




