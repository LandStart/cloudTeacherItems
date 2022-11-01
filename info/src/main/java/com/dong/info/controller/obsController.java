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
import java.util.Map;

@RestController
public class obsController {

    @Autowired
    private ObsServiceInstance obsServiceInstance;


    /**
     * 上传对象
     *
     * @param filename
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/obsUpload")
    public Result upload(@RequestParam(value = "filename") String filename ,@RequestParam(value="bucketName") String bucketName) throws Throwable {
        return obsServiceInstance.upload( filename,bucketName);
    }

    /**
     * 下载obs文件
     * @param objectKey
     * @param rename
     * @param bucketName
     * @return
     * @throws Throwable
     */
        @RequestMapping("/obsDownload")
    public Result download(@RequestParam("objectKey") String objectKey,@RequestParam("rename") String rename, @RequestParam(value="bucketName") String bucketName) throws Throwable {
        return obsServiceInstance.download(objectKey,rename,bucketName);
    }

    /**
     * 获取桶和对象信息
     * @return
     * @throws Throwable
     */
    @RequestMapping("/getList")
    public String getList() throws Throwable {
        return obsServiceInstance.getList();
    }

    /**
     * 获取桶信息
     * @return
     * @throws Throwable
     */
    @RequestMapping("/getbkList")
    public List<Map<String,Object>> getbkList() throws Throwable {
        return obsServiceInstance.getbkList();
    }

    @RequestMapping("/obsPutBytes")
    public Result putBytes(@RequestParam("content") String content,@RequestParam("rename") String rename, @RequestParam(value="bucketName") String bucketName) throws Throwable {
         return obsServiceInstance.putBytes(content,rename, bucketName);
    }

    @RequestMapping("/getBytes")
    public Result getBytes(@RequestParam("objectKey") String objectKey) throws Throwable {
        return  obsServiceInstance.getBytes(objectKey);
    }




}
