package com.dong.info.controller;

import com.dong.info.entity.Result;
import com.dong.info.obs.ObsServiceInstance;
import feign.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
public class obsController {

    @Autowired
    private ObsServiceInstance obsServiceInstance;


    @RequestMapping("/obsUpload")
    public Result load(@RequestParam("filename") String filename) throws Throwable {
        return obsServiceInstance.putLocalFile( filename);
    }

        @RequestMapping("/obsDownload")
    public Result getimage(@RequestParam("objectKey") String objectKey,@RequestParam("rename") String rename) throws Throwable {
        return obsServiceInstance.getimage(objectKey,rename);
    }


    @RequestMapping("/getList")
    public String getList() throws Throwable {
        return obsServiceInstance.getList();
    }

    @RequestMapping("/obsPutBytes")
    public Result putBytes(@RequestParam("content") String content) throws Throwable {
         return obsServiceInstance.putBytes( content);
    }

    @RequestMapping("/getBytes")
    public Result getBytes(@RequestParam("objectKey") String objectKey) throws Throwable {
        return  obsServiceInstance.getBytes(objectKey);
    }




}
