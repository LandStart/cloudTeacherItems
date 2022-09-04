package com.dong.infoback.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dong.infoback.entity.User;

import java.util.List;

/**
* @author 81921
* @description 针对表【user】的数据库操作Service
* @createDate 2022-09-04 23:43:52
*/
public interface UserService extends IService<User> {
    public List<User> selectUser(String acccount);
}
