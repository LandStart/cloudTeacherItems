package com.dong.infoback.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dong.infoback.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 81921
* @description 针对表【user】的数据库操作Mapper
* @createDate 2022-09-04 23:43:52
* @Entity com.dong.infoback.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




