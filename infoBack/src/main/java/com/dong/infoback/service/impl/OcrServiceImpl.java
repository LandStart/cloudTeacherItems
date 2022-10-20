package com.dong.infoback.service.impl;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.huaweicloud.sdk.core.auth.ICredential;
// 对用户身份进行认证
import com.huaweicloud.sdk.core.auth.BasicCredentials;
// 请求异常类
import com.huaweicloud.sdk.core.exception.ConnectionException;
import com.huaweicloud.sdk.core.exception.RequestTimeoutException;
import com.huaweicloud.sdk.core.exception.ServiceResponseException;
// 导入OCR客户端
import com.huaweicloud.sdk.core.http.HttpConfig;
import com.huaweicloud.sdk.ocr.v1.region.OcrRegion;
import com.huaweicloud.sdk.ocr.v1.*;
import com.huaweicloud.sdk.ocr.v1.model.*;

import com.dong.infoback.service.OcrService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class OcrServiceImpl implements OcrService {



    public void Test(){
        // 使用默认配置
        HttpConfig config = HttpConfig.getDefaultHttpConfig();
        // 根据需要配置是否跳过SSL证书验证
        config.withIgnoreSSLVerification(true);
        // 默认连接超时时间为60秒，可根据需要调整
        config.withTimeout(60);

        BasicCredentials basicCredentials = new BasicCredentials()
                .withAk("BY5VYWSKUKDVCLJWTLDX")
                .withSk("TIgR6UJxlC7Nh5jpfkBNgDptXJWOdaqLaFz7UVbh")
                .withProjectId("cf0a68733cc544be8530656fafe84c1c");

        // 初始化指定云服务的客户端 {Service}Client ，以初始化OCR服务的 OcrClient 为例
        OcrClient client = OcrClient.newBuilder()
                .withHttpConfig(config)
                .withCredential(basicCredentials)
                .withRegion(OcrRegion.valueOf("cn-north-4"))
                .build();

        // 以调用通用表格识别接口 RecognizeGeneralTable 为例
        RecognizeGeneralTableRequest request = new RecognizeGeneralTableRequest();
        GeneralTableRequestBody body = new GeneralTableRequestBody();
        body.withUrl("d:\\images\\xxx.excel");
        request.withBody(body);
        try {
            RecognizeGeneralTableResponse response = client.recognizeGeneralTable(request);
            System.out.println(response.toString());
        } catch (ConnectionException e) {
            e.printStackTrace();
        } catch (RequestTimeoutException e) {
            e.printStackTrace();
        } catch (ServiceResponseException e) {
            e.printStackTrace();
            System.out.println(e.getHttpStatusCode());
            System.out.println(e.getErrorCode());
            System.out.println(e.getErrorMsg());
        }





    }
}
