package com.dong.infoback.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;

@Service
@FeignClient(value = "base")
public interface BaseFeign {

}
