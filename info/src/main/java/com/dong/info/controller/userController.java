package com.dong.info.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dong.info.entity.DynamicConfigEntity;
import com.dong.info.entity.Result;
import com.dong.info.service.impl.UserServiceImpl;
import feign.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class userController {


    @Autowired
    UserServiceImpl userService;

    @RequestMapping(value = "selectPage",method = RequestMethod.GET)
    public Result selectPage(@Param("currentPage") Long currentPage, @Param("size") Integer size) throws Exception {
        Page page = userService.selectPage(currentPage, size);
        Result result = null;
        if(page != null ){
            return result = new Result("ok",false,(Object) page);
        }else{
            return result = new Result("fail",true,null);
        }
    }
}
