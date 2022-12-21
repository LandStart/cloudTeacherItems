package com.dong.info.service.impl;

import com.dong.info.service.identifyService;
import com.huaweicloud.sdk.core.auth.GlobalCredentials;
import com.huaweicloud.sdk.core.auth.ICredential;
import com.huaweicloud.sdk.core.exception.ConnectionException;
import com.huaweicloud.sdk.core.exception.RequestTimeoutException;
import com.huaweicloud.sdk.core.exception.ServiceResponseException;
import com.huaweicloud.sdk.iam.v3.IamClient;
import com.huaweicloud.sdk.iam.v3.model.CreateUserOption;
import com.huaweicloud.sdk.iam.v3.model.CreateUserRequest;
import com.huaweicloud.sdk.iam.v3.model.CreateUserRequestBody;
import com.huaweicloud.sdk.iam.v3.model.CreateUserResponse;
import com.huaweicloud.sdk.iam.v3.region.IamRegion;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class IdentifyImpService implements identifyService {

    @Value("${huawei.accessKeyId}")
    private String ak;
    @Value("${huawei.accessKey}")
    private String sk;
    @Value("${huawei.identifyRegion}")
    private String identifyRegion;



    public String addusers(String users){


        ICredential auth = new GlobalCredentials()
                .withAk(ak)
                .withSk(sk);

        IamClient client = IamClient.newBuilder()
                .withCredential(auth)
                .withRegion(IamRegion.valueOf(identifyRegion))
                .build();
        String[] split = users.split(",");
        if(split.length < 1){
            CreateUserRequest request = new CreateUserRequest();
            CreateUserRequestBody body = new CreateUserRequestBody();
            CreateUserOption createUserOption = new CreateUserOption();

            createUserOption.setName(users);
            createUserOption.setDomainId("e22ab154b2ab4b2a89d6ca9f5b9518d0");
            createUserOption.setPassword("17801230615ldnnm");
            createUserOption.setEnabled(true);
            body.setUser(createUserOption);
            request.withBody(body);
            try {
                CreateUserResponse response = client.createUser(request);
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
            for(int i = 0 ; i < split.length;i++){
                CreateUserRequest request = new CreateUserRequest();
                CreateUserRequestBody body = new CreateUserRequestBody();
                CreateUserOption createUserOption = new CreateUserOption();

                createUserOption.setName(split[i]);
                createUserOption.setDomainId("e22ab154b2ab4b2a89d6ca9f5b9518d0");
                createUserOption.setPassword("17801230615ldnnm");
                createUserOption.setEnabled(true);
                body.setUser(createUserOption);
                request.withBody(body);
                try {
                    System.out.println("创建第"+(i+1)+"个IAM用户==========================");
                    CreateUserResponse response = client.createUser(request);

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





        return "OK";
    }
}
