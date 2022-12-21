package com.dong.info.service.impl;

import com.dong.info.service.smnService;
import com.huaweicloud.sdk.core.auth.BasicCredentials;
import com.huaweicloud.sdk.core.auth.ICredential;
import com.huaweicloud.sdk.core.exception.ConnectionException;
import com.huaweicloud.sdk.core.exception.RequestTimeoutException;
import com.huaweicloud.sdk.core.exception.ServiceResponseException;
import com.huaweicloud.sdk.smn.v2.SmnClient;
import com.huaweicloud.sdk.smn.v2.model.AddSubscriptionRequest;
import com.huaweicloud.sdk.smn.v2.model.AddSubscriptionRequestBody;
import com.huaweicloud.sdk.smn.v2.model.AddSubscriptionResponse;
import com.huaweicloud.sdk.smn.v2.region.SmnRegion;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmnImpService implements smnService {


    @Value("${huawei.accessKeyId}")
    private String ak;
    @Value("${huawei.accessKey}")
    private String sk;
    @Value("${huawei.projectId}")
    private String projectId;
    @Value("${huawei.region}")
    private String region;

    public String smn(String protocol ,String smnEndpoint){

        ICredential auth = new BasicCredentials()
                .withAk(ak)
                .withSk(sk);

        SmnClient client = SmnClient.newBuilder()
                .withCredential(auth)
                .withRegion(SmnRegion.valueOf(region))
                .build();

        String[] split = smnEndpoint.split("/");
        if(split.length < 1 ){
            AddSubscriptionRequest request = new AddSubscriptionRequest();
            request.setTopicUrn("urn:smn:cn-north-4:cf0a68733cc544be8530656fafe84c1c:news");

            AddSubscriptionRequestBody body = new AddSubscriptionRequestBody();
            body.withEndpoint(smnEndpoint);

            body.withProtocol(protocol);
            request.withBody(body);
            try {
                System.out.println("终端用户"+ smnEndpoint+"订阅主题");
                AddSubscriptionResponse response = client.addSubscription(request);
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
        }else{
            for(int i = 0 ; i < split.length; i++){

                AddSubscriptionRequest request = new AddSubscriptionRequest();
                request.setTopicUrn("urn:smn:cn-north-4:cf0a68733cc544be8530656fafe84c1c:news");
                AddSubscriptionRequestBody body = new AddSubscriptionRequestBody();
                body.withEndpoint(split[i]);
                body.withProtocol(protocol);
                request.withBody(body);
                try {
                    System.out.println("第"+(i+1)+"个终端用户"+ split[i]+"订阅主题");
                    AddSubscriptionResponse response = client.addSubscription(request);



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
            return "ok";
    }


}
