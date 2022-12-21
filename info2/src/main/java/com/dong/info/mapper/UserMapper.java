package com.dong.info.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dong.info.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 81921
* @description 针对表【user】的数据库操作Mapper
* @createDate 2022-08-17 15:55:08
* @Entity .domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




