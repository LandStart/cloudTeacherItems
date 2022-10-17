package com.dong.info.controller;

import com.dong.info.obs.ObsServiceInstance;
import feign.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class obsController {

    @Autowired
    private ObsServiceInstance obsServiceInstance;


    @RequestMapping("/obsUpload")
    public String load(@RequestParam("name") String name){
        return obsServiceInstance.putLocalFile( name);
    }

    @RequestMapping("/obsPutBytes")
    public void putBytes(@RequestParam("content") String content) throws IOException {
         obsServiceInstance.putBytes( content);
    }

    @RequestMapping("/getBytes")
    public void getBytes() throws Throwable {
        obsServiceInstance.getBytes();
    }

    @RequestMapping("/getimage")
    public void getimage(@RequestParam("bucketName") String bucketName,@RequestParam("objectKey") String objectKey,@RequestParam("rename") String rename) throws Throwable {
         obsServiceInstance.getimage(bucketName,objectKey,rename);
    }


}
