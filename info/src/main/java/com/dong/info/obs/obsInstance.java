package com.dong.info.obs;

import com.obs.services.ObsClient;
import com.obs.services.model.GetObjectRequest;
import com.obs.services.model.ObsObject;
import lombok.SneakyThrows;

import java.io.*;

public class obsInstance {

    private static final String endPoint = "https://obs.cn-north-4.myhuaweicloud.com";
    private static final String ak = "BWVVYADHDDE1SXMECZGS";
    private static final String sk = "CRfOmOYfdfnQ5flzxj5ELItnlvZMNxfa2D6s1VD0";

    public static void main(String[] args) throws IOException {
        obsInstance.getimage();
    }

    public static void  put(){

        // 创建ObsClient实例
        ObsClient obsClient = new ObsClient(ak, sk, endPoint);

        // 使用访问OBS
        obsClient.putObject("testliudong", ak, new ByteArrayInputStream("Hello OBS".getBytes()));

         // 关闭obsClient，全局使用一个ObsClient客户端的情况下，不建议主动关闭ObsClient客户端
        // obsClient.close();
    }

    public static void get() throws IOException {
        ObsClient obsClient = new ObsClient(ak, sk, endPoint);
        ObsObject obsObject = obsClient.getObject("testliudong", "BWVVYADHDDE1SXMECZGS");
        // 读取对象内容
        System.out.println("Object content:");
        InputStream input = obsObject.getObjectContent();
        byte[] b = new byte[1024];
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int len;
        while ((len=input.read(b)) != -1){
            bos.write(b, 0, len);
        }

        System.out.println(new String(bos.toByteArray()));
        bos.close();
        input.close();
    }

    @SneakyThrows
    public static void getimage() throws IOException {

//      创建ObsClient实例
        final ObsClient obsClient = new ObsClient(ak, sk, endPoint);
        GetObjectRequest request = new GetObjectRequest("testliudong", "image.webp");
//      设置图片处理参数，对图片依次进行缩放、旋转
        request.setImageProcess("image/resize,m_fixed,w_100,h_100/rotate,90");
        ObsObject obsObject = obsClient.getObject(request);
        InputStream objectContent = obsObject.getObjectContent();

        byte[] data=readInputStream(objectContent);

        File file=new File("D:\\ideawork\\springcloud_LD8\\info\\src\\main\\resources\\static\\images");
        FileOutputStream outStream=new FileOutputStream(file);
        outStream.write(data);
        outStream.close();

    }

    private static byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream=new ByteArrayOutputStream();
        byte[] buffer=new byte[1024];//转换为二进制
        int len=0;
        while((len =inStream.read(buffer))!=-1){
            outStream.write(buffer,0,len);
        }
        return  outStream.toByteArray();
    }

}
