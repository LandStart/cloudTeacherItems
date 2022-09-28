package com.dong.info.service.impl;

import com.alibaba.spring.util.ObjectUtils;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dong.info.entity.DynamicConfigEntity;
import com.dong.info.entity.User;
import com.dong.info.mapper.UserMapper;
import com.dong.info.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
* @author 81921
* @description 针对表【user】的数据库操作Service实现
* @createDate 2022-08-17 15:55:08
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    UserMapper userMapper;

    @Autowired
    DynamicConfigEntity dynamicConfigEntity;



    @Override
    @LoadBalanced
    public List<User> selectUser(String acccount) {
        System.out.println(dynamicConfigEntity.getVersion());
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", acccount);
        List<User> list = userMapper.selectList(queryWrapper );

        if(list == null && list.size() == 0 ){
            return list = new ArrayList<>();
        }

        return list;
    }



    public Page selectPage(Long currentPage,Integer size){

        Wrapper queryWrapper = new QueryWrapper();

        queryWrapper.getSqlSelect();
        Page page = new Page();
        page.setSize(size);
        page.setCurrent(currentPage);
        Page page1 = userMapper.selectPage(page, queryWrapper);


        return  page;
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

        List<User> list = userMapper.selectList(queryWrapper );
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

        List<User> list = userMapper.selectList(queryWrapper );
        if(list != null && list.size() > 0 ){
            System.out.println("用户已存在");
            return "redirect:error.html";
        }
        try {
            userMapper.insert(user);
        }catch (Exception e){
            throw new Exception("save error");
        }

        return "redirect:content.html";
    }

    public String deleteUser(String username ,String password) throws Exception {
        //根据用户名去查找这个账户，是否存在

        try {
            QueryWrapper queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", username);
            User user = userMapper.selectOne(queryWrapper);

            if (user == null) {
                System.out.println("delete  ok");
            }

            user.setIsdelete("1");
            userMapper.updateById(user);
        } catch (Exception e) {
            throw new Exception(e);
        }

        return "redirect:content.html";
    }


    public String getUserWithRedis(String key) throws Exception {

        if( key == " " && key == ""){
            return null;
        }
        try{
            //先去缓存中查找
            String cacheValue = stringRedisTemplate.opsForValue().get(key);

            //如果命中  null != ""
            if(  cacheValue != null ){
                return cacheValue;
            }else  {
                //没有命中缓存，直接去数据库查找
                QueryWrapper queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("username", key);
                User user = userMapper.selectOne(queryWrapper);
                if (user == null) {
                    //数据库中没有这个对象
                    return null;
                } else {
                    //查到这个对象
                    return user.getPassword();
                }
            }
        }catch(Exception e ){
            throw new Exception();
        }
    }

    public String UpdateUserWithRedis(User user) throws Exception {
        if(user == null || user.getUsername().equals("") || user.getUsername().equals(" ") ) {
            return null;
        }
        try{

            QueryWrapper queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", user.getUsername());
            User suser = userMapper.selectOne(queryWrapper);
            if (suser == null) {
                //数据库中没有这个对象
                return null;
            } else {
                //先去数据库更新对象
                userMapper.updateById(user);
                //去缓存中查找对象，有就更新，没有就插入
                stringRedisTemplate.opsForValue().set(user.getUsername(),user.getPassword());

            }
        }catch (Exception e){
            throw new Exception();
        }

        return null;

    }


    public String DeleteUserWithRedis(String key) throws Exception {
        if( key.equals("") || key.equals(" ") ) {
            return null;
        }
        try{
            //先去数据库删除对象
            //没有命中缓存，直接去数据库查找
            QueryWrapper queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username",key);
            userMapper.delete(queryWrapper);
            //去缓存中无效对象
            stringRedisTemplate.delete(key);

        }catch (Exception e){
            throw new Exception();
        }

        return null;

    }


    public String InsertUserWithRedis(User user) throws Exception {
        if(user == null || user.getUsername().equals("") || user.getUsername().equals(" ") ) {
            return null;
        }
        try{
            //先去数据库更新对象
            userMapper.insert(user);
            //去缓存中查找对象，如果存在，有可能是旧值，进行更新，没有就插入
            stringRedisTemplate.opsForValue().set(user.getUsername(),user.getPassword());

        }catch (Exception e){
            throw new Exception();
        }

        return null;

    }
}




