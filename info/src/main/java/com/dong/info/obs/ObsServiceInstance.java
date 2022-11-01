package com.dong.info.obs;

import com.dong.info.entity.Result;
import com.huaweicloud.sdk.core.utils.JsonUtils;
import com.obs.services.ObsClient;
import com.obs.services.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ObsServiceInstance {

    @Value("${huawei.accessKeyId}")
    private String accessKeyId;// 华为云的 Access Key Id
    @Value("${huawei.accessKey}")
    private String accessKeySecret;// 华为云的 Access Key Secret
    @Value("${huawei.endpoint}")
    private String endpoint; // 华为云连接的地址节点

    private ObsClient obsClient=null;

    public String getList() throws Throwable {


        obsClient =getObsClient(obsClient);

        // 列举桶
        ListBucketsRequest request = new ListBucketsRequest();
        request.setQueryLocation(true);
        List<ObsBucket> buckets = obsClient.listBuckets(request);
        List<List<String>> lists =new ArrayList<>();
        for(ObsBucket bucket : buckets){
            List<String> list = new ArrayList<>();
            list.add("bucket : "+ bucket.getBucketName());
            ObjectListing result = obsClient.listObjects(bucket.getBucketName());
            for(ObsObject obsObject : result.getObjects()){
                list.add("objectKey : "+ obsObject.getObjectKey());
            }
            lists.add(list);
        }
        return  JsonUtils.toJSON(lists);

    }

    public List<Map<String,Object>>  getbkList() throws Throwable {

        obsClient =getObsClient(obsClient);

        // 列举桶
        ListBucketsRequest request = new ListBucketsRequest();
        request.setQueryLocation(true);
        List<ObsBucket> buckets = obsClient.listBuckets(request);

        List<Map<String,Object>> bks = new ArrayList<>();

        for (ObsBucket obsBucket: buckets) {
            Map<String,Object> temp = new HashMap<>();
            System.out.println(obsBucket.getBucketName());
            temp.put(obsBucket.getBucketName(),obsBucket.getBucketName());
            bks.add(temp);
        }

        return  bks;

    }

    public Result upload(String filename,String bucketName) throws Throwable {
        Result result = new Result();

        String path="D:\\uploadHWY\\"+filename;
         FileInputStream fis=null;
        ObsClient obsClient =null;
        PutObjectResult putObjectResult = null;

        try {
            File file=new File(path);
            fis  = new FileInputStream(file);
            obsClient = getObsClient(obsClient);
            putObjectResult = obsClient.putObject(bucketName, filename, fis);
            int statusCode = putObjectResult.getStatusCode();
            result.setObject(putObjectResult);
            if(statusCode == 200){
                result.setStatus(true);
                result.setMessage("上传成功");
            }else{
                if(putObjectResult != null){
                    result.setMessage(String.valueOf(putObjectResult.getStatusCode()));
                    result.setStatus(false);
                }
            }

        }catch (Exception e){
           throw new Exception("error ");
        }finally {
            try {
                fis.close();
                obsClient.close();
            } catch (IOException e) {
                throw new Exception("error");
            }
        }
        return  result;
    }

    public Result download(String objectKey,String rename,String bucketName) throws Throwable {
        Result result = new Result();

        FileOutputStream outStream = null;
        InputStream objectContent = null;
        try{
            //创建ObsClient实例
           obsClient = getObsClient(obsClient);

            GetObjectRequest request = new GetObjectRequest(bucketName, objectKey);

            ObsObject obsObject = obsClient.getObject(request);

             objectContent = obsObject.getObjectContent();

            byte[] data=readInputStream(objectContent);

            File file=new File("D:\\downloadHYW\\"+rename);
            outStream=new FileOutputStream(file);
            outStream.write(data);

            result.setStatus(true);
            result.setMessage("下载成功");
        }catch (Exception e){
            throw new Throwable("下载出错");
        }finally{
            try {
                objectContent.close();
                outStream.close();
                obsClient.close();
            } catch (IOException e) {
                throw new Exception("error");
            }
        }

        return result;
    }

    public ObsClient getObsClient(ObsClient obsClient) {
        if(obsClient == null) {
            obsClient = new ObsClient(accessKeyId, accessKeySecret, endpoint);
        }
        return obsClient;
    }

    public  Result  putBytes(String content,String rename,  String bucketName) throws Throwable {
        Result result = new Result();

        try{
            // 创建ObsClient实例
            obsClient = getObsClient(obsClient);
            // 使用访问OBS
            PutObjectResult putObjectResult = obsClient.putObject(bucketName, rename, new ByteArrayInputStream(content.getBytes()));

            int statusCode = putObjectResult.getStatusCode();

            if(statusCode == 200){
                result.setStatus(true);
                result.setMessage("上传成功");
                result.setObject(content);
            }else{
                result.setStatus(false);
                result.setMessage("上传失败状态码"+statusCode);
                result.setObject(content);
            }

        }catch (Exception e){
            throw new Throwable("上传出错");
        }
        finally {
            try {
                obsClient.close();
            } catch (IOException e) {
                throw new Exception("error");
            }
        }

        return result;
    }

    public  Result getBytes(String objectKey) throws Throwable {
        Result result = new Result();
        obsClient = new ObsClient(accessKeyId, accessKeySecret, endpoint);
        ByteArrayOutputStream bos = null;
        InputStream input = null;
        try{
            ObsObject obsObject = obsClient.getObject("testliudong", objectKey);
            // 读取对象内容
            System.out.println("Object content:");
            input = obsObject.getObjectContent();
            byte[] b = new byte[1024];
            bos = new ByteArrayOutputStream();
            int len;
            while ((len=input.read(b)) != -1){
                bos.write(b, 0, len);
            }

            System.out.println(new String(bos.toByteArray()));
            result.setObject(new String(bos.toByteArray()));
            result.setStatus(true);
            result.setMessage("获取成功");
        }catch (Exception e ){
            throw e.getCause();
        }finally{
            bos.close();
            input.close();
        }

        return result;
    }

    private  byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream=new ByteArrayOutputStream();
        byte[] buffer=new byte[1024];//转换为二进制
        int len=0;
        while((len =inStream.read(buffer))!=-1){
            outStream.write(buffer,0,len);
        }
        return  outStream.toByteArray();
    }


}
