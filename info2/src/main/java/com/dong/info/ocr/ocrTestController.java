package com.dong.info.ocr;


import com.dong.info.service.impl.OcrTestService;
import com.huaweicloud.sdk.ocr.v1.model.RecognizeTrainTicketResponse;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class ocrTestController {

    @Resource
    OcrTestService ocrTestService;

    @RequestMapping("ocr")
    public RecognizeTrainTicketResponse ocrApi(@RequestParam("url") String url){
        return ocrTestService.ocrapi(url);
    }

    @RequestMapping(value = "ocr" ,method = RequestMethod.POST)
    public RecognizeTrainTicketResponse ocrApis(@RequestParam("url") String url){
        return ocrTestService.ocrapi(url);
    }
}
