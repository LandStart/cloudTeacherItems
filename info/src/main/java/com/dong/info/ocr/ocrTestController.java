package com.dong.info.ocr;


import com.dong.info.entity.Tickets;
import com.dong.info.service.impl.OcrTestService;
import com.huaweicloud.sdk.ocr.v1.model.RecognizeTrainTicketResponse;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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

    @RequestMapping(value = "ocrs" )
    public List<RecognizeTrainTicketResponse> ocrApiss(@RequestParam("urls") String urls){
        return ocrTestService.ocrapis(urls);
    }

}
