package com.dong.info.obs;

import com.dong.info.entity.Result;
import com.huaweicloud.sdk.core.utils.JsonUtils;
import com.obs.services.ObsClient;
import com.obs.services.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ObsServiceInstance {

    @Value("${huawei.accessKeyId}")
    private String accessKeyId;// 华为云的 Access Key Id
    @Value("${huawei.accessKey}")
    private String accessKeySecret;// 华为云的 Access Key Secret
    @Value("${huawei.endpoint}")
    private String endpoint; // 华为云连接的地址节点
    @Value("${huawei.obsBucketName}")
    private String obsBucketName; // 创建的桶的名称
    @Value("${huawei.url}")
    private String url; // 访问OBS文件的url
    private ObsClient obsClient=null;


    public String getList() throws Throwable {


        obsClient = new ObsClient(accessKeyId, accessKeySecret, endpoint);

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
                System.out.println("\t" + obsObject.getObjectKey());
                System.out.println("\t" + obsObject.getOwner());
            }
            lists.add(list);
        }

        return  JsonUtils.toJSON(lists);


    }

        /**
         * 文件上传
         */
    public Result putLocalFile(String filename) throws Throwable {
        Result result = new Result();

        String path="D:\\uploadHWY\\"+filename;
         FileInputStream fis=null;
        ObsClient obsClient =null;
        PutObjectResult putObjectResult = null;

        try {
            File file=new File(path);
            fis  = new FileInputStream(file);
            obsClient = getObsClient(this.obsClient);
            putObjectResult = obsClient.putObject(obsBucketName, filename, fis);
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
           throw new Throwable("上传出错");
        }finally {
            try {
                obsClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return  result;
    }

    /**
     * 获取obsclient
     * @param obsClient
     * @return
     */
    public ObsClient getObsClient(ObsClient obsClient) {
        if(obsClient == null) {
            obsClient = new ObsClient(accessKeyId, accessKeySecret, endpoint);
        }
        return obsClient;
    }

    /**
     * 上传文本内容
     */
    public  Result  putBytes(String content) throws Throwable {
        Result result = new Result();

        try{
            // 创建ObsClient实例
            obsClient = new ObsClient(accessKeyId, accessKeySecret, endpoint);
            // 使用访问OBS
            PutObjectResult putObjectResult = obsClient.putObject(obsBucketName, accessKeyId, new ByteArrayInputStream(content.getBytes()));

            result.setStatus(true);
            result.setMessage("上传成功");
            result.setObject(content);

        }catch (Exception e){
            throw new Throwable("上传出错");
        }
        finally {
            // 关闭obsClient，全局使用一个ObsClient客户端的情况下，不建议主动关闭ObsClient客户端
            obsClient.close();
        }

        return result;
    }

    /**
     * 获取对象
     * @throws IOException
     */
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

    /**
     * 获取图片
     *
     * @throws IOException
     */
    public Result getimage(String objectKey,String rename) throws Throwable {
        Result result = new Result();
//      创建ObsClient实例
        obsClient = new ObsClient(accessKeyId, accessKeySecret, endpoint);
        FileOutputStream outStream = null;
        try{
            GetObjectRequest request = new GetObjectRequest("testliudong", objectKey);
//      设置图片处理参数，对图片依次进行缩放、旋转
            request.setImageProcess("image/resize,m_fixed,w_100,h_100/rotate,90");
            ObsObject obsObject = obsClient.getObject(request);

            InputStream objectContent = obsObject.getObjectContent();

            byte[] data=readInputStream(objectContent);

            File file=new File("D:\\downloadHYW\\"+rename);
            outStream=new FileOutputStream(file);
            outStream.write(data);

            result.setStatus(true);
            result.setMessage("下载成功");
        }catch (Exception e){
            throw new Throwable("下载出错");
        }finally{
            outStream.close();
        }

        return result;
    }

    /**
     *
     * 读取字节流
     * @param inStream
     * @return
     * @throws Exception
     */
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
