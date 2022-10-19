package com.dong.info.controller;

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
    public String load(@RequestParam("filename") String filename){
        return obsServiceInstance.putLocalFile( filename);
    }

    @RequestMapping("/obsDownload")
    public void getimage(@RequestParam("objectKey") String objectKey,@RequestParam("rename") String rename) throws Throwable {
        obsServiceInstance.getimage(objectKey,rename);
    }


    @RequestMapping("/getList")
    public List<List<String>> getList() throws Throwable {
        return obsServiceInstance.getList();
    }

    @RequestMapping("/obsPutBytes")
    public void putBytes(@RequestParam("content") String content) throws IOException {
         obsServiceInstance.putBytes( content);
    }

    @RequestMapping("/getBytes")
    public void getBytes(@RequestParam("objectKey") String objectKey) throws Throwable {
        obsServiceInstance.getBytes(objectKey);
    }




}
