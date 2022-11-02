package com.dong.info.smn;


import com.dong.info.service.impl.SmnImpService;
import com.huaweicloud.sdk.smn.v2.model.AddSubscriptionResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class SmnController {

    @Resource
    SmnImpService smnImpService;

    @RequestMapping("Subscription")
    public String sub(@RequestParam("protocol") String protocol, @RequestParam("smnEndpoint")  String smnEndpoint){

        return smnImpService.smn(protocol,smnEndpoint);
    }
}
