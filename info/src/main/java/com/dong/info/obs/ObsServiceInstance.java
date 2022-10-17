package com.dong.info.obs;

import com.obs.services.ObsClient;
import com.obs.services.model.GetObjectRequest;
import com.obs.services.model.ObsObject;
import com.obs.services.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.*;
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
    /**
     * 文件上传
     */
    public String putLocalFile(String name)  {
        String path="D:\\uploadHWY\\"+name;
        File file=new File(path);
        UUID uuid=UUID.randomUUID();
        String originalFileName = uuid.toString().replace("-","")+"_demo.jpg";
        FileInputStream fis=null;
        ObsClient obsClient =null;
        PutObjectResult putObjectResult = null;
        String requestId=null;
        try {
            fis  = new FileInputStream(file);
            obsClient = getObsClient(this.obsClient);
            putObjectResult = obsClient.putObject(obsBucketName, originalFileName, fis);
            requestId = putObjectResult.getRequestId();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                obsClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return  requestId;
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
    public  void  putBytes(String content) throws IOException {
        // 创建ObsClient实例
        obsClient = new ObsClient(accessKeyId, accessKeySecret, endpoint);
        // 使用访问OBS
        obsClient.putObject(obsBucketName, accessKeyId, new ByteArrayInputStream(content.getBytes()));
        // 关闭obsClient，全局使用一个ObsClient客户端的情况下，不建议主动关闭ObsClient客户端
        obsClient.close();
    }

    /**
     * 获取对象
     * @throws IOException
     */
    public  void getBytes() throws Throwable {
        obsClient = new ObsClient(accessKeyId, accessKeySecret, endpoint);
        ByteArrayOutputStream bos = null;
        InputStream input = null;
        try{
            ObsObject obsObject = obsClient.getObject("testliudong", "NULOVK6JBE1HY1YCPJ4V");
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
        }catch (Exception e ){
            throw e.getCause();
        }finally{
            bos.close();
            input.close();
        }
    }

    /**
     * 获取图片
     *
     * @throws IOException
     */
    public void getimage(String bucketName,String objectKey,String rename) throws Throwable {

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
        }catch (Exception e){
            throw e.getCause();
        }finally{
            outStream.close();
        }
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
