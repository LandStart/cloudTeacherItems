package com.dong.info.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dong.info.entity.User;

import java.util.List;

/**
* @author 81921
* @description 针对表【user】的数据库操作Service
* @createDate 2022-08-17 15:55:08
*/
public interface UserService extends IService<User> {
    public List<User> selectUser(String acccount);
}
