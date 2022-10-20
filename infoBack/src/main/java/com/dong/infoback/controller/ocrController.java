package com.dong.infoback.controller;

import com.dong.infoback.service.OcrService;
import com.dong.infoback.service.impl.OcrServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.annotation.Resource;

@RestController
public class ocrController {

    @Resource
    OcrServiceImpl ocrServiceImpl;

    @RequestMapping("/test")
    public String testnacos(){
        return ocrServiceImpl.getnacos();
    }
}
