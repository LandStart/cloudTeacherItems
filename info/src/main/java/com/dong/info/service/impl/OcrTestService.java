package com.dong.info.service.impl;

import com.dong.info.entity.Tickets;
import com.huaweicloud.sdk.core.auth.BasicCredentials;
import com.huaweicloud.sdk.core.exception.ConnectionException;
import com.huaweicloud.sdk.core.exception.RequestTimeoutException;
import com.huaweicloud.sdk.core.exception.ServiceResponseException;
import com.huaweicloud.sdk.core.http.HttpConfig;
import com.huaweicloud.sdk.ocr.v1.OcrClient;
import com.huaweicloud.sdk.ocr.v1.model.RecognizeTrainTicketRequest;
import com.huaweicloud.sdk.ocr.v1.model.RecognizeTrainTicketResponse;
import com.huaweicloud.sdk.ocr.v1.model.TrainTicketRequestBody;
import com.huaweicloud.sdk.ocr.v1.model.TrainTicketResult;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OcrTestService {

    @Value("${huawei.accessKeyId}")
    private String ak;
    @Value("${huawei.accessKey}")
    private String sk;
    @Value("${huawei.projectId}")
    private String projectId;
    @Value("${huawei.endpointOcr}")
    private String endpoint;



    public List<RecognizeTrainTicketResponse> ocrapis(String urls ){

        HttpConfig config = HttpConfig.getDefaultHttpConfig();
        config.withTimeout(60);
        config.withIgnoreSSLVerification(true);

        BasicCredentials basicCredentials = new BasicCredentials()
                .withAk(ak)
                .withSk(sk)
                .withProjectId(projectId);


        // 初始化指定云服务的客户端 {Service}Client ，以初始化 Region 级服务OCR的 OcrClient 为例
        OcrClient ocrClient = OcrClient.newBuilder()
                .withHttpConfig(config)
                .withCredential(basicCredentials)
                .withEndpoint(endpoint)
                .build();


        List<RecognizeTrainTicketResponse> responseList = new ArrayList<>();
        RecognizeTrainTicketResponse response = null;
        String[] split = urls.split("~");
        Integer size = split.length;

        try {
            for(int i = 0 ; i < size ; i++){
                RecognizeTrainTicketRequest request = new RecognizeTrainTicketRequest();
                TrainTicketRequestBody body = new TrainTicketRequestBody();
                body.setUrl(split[i] +"&fm=253&fmt=auto&app=138&f=JPEG?w=667&h=500");
                body.setReturnTextLocation(true);
                request.withBody(body);
                response = ocrClient.recognizeTrainTicket(request);
                responseList.add(response);
            }

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

        return responseList;
    }


    public RecognizeTrainTicketResponse ocrapi(String url){

        HttpConfig config = HttpConfig.getDefaultHttpConfig();
        config.withTimeout(60);
        config.withIgnoreSSLVerification(true);

        BasicCredentials basicCredentials = new BasicCredentials()
                .withAk(ak)
                .withSk(sk)
                .withProjectId(projectId);


        // 初始化指定云服务的客户端 {Service}Client ，以初始化 Region 级服务OCR的 OcrClient 为例
        OcrClient ocrClient = OcrClient.newBuilder()
                .withHttpConfig(config)
                .withCredential(basicCredentials)
                .withEndpoint(endpoint)
                .build();

        RecognizeTrainTicketRequest request = new RecognizeTrainTicketRequest();
        TrainTicketRequestBody body = new TrainTicketRequestBody();
        body.setUrl(url +"&fm=253&fmt=auto&app=138&f=JPEG?w=667&h=500");
        body.setReturnTextLocation(true);
        request.withBody(body);

        RecognizeTrainTicketResponse response = null;
        try {
            response = ocrClient.recognizeTrainTicket(request);
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

        return response;
    }
}
