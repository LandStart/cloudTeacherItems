package com.dong.info.identify;


import com.dong.info.service.impl.IdentifyImpService;
import feign.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class identifyController {

    @Resource
    IdentifyImpService identifyImpService;

    @RequestMapping(value = "addusers")
    public String addusers(@Param("users") String users)   {
        return identifyImpService.addusers(users);
    }

}
