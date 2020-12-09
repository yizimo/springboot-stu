package com.zimo.springbootstu.utils;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

public class AliyunSmsUtils {

    private final static String REGIONID = "cn-hangzhou";
    private final static String ACCESSKEYID = "LTAIbbR8XLxH1unJ";
    private final static String SECRET = "dS8h6cvDDDRnRk8nEMDaACuPOZt1yV";

    public static CommonResponse sendSms(String phone, Integer code)throws ClientException {
        DefaultProfile profile = DefaultProfile.getProfile(REGIONID, ACCESSKEYID, SECRET);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "zimo");
        request.putQueryParameter("TemplateCode", "SMS_166372664");
        request.putQueryParameter("TemplateParam", "{\"code\":\"" + code + "\"}");
        CommonResponse response = client.getCommonResponse(request);
        return response;
    }

    public static Integer getCode() {
        return (int)(Math.random()*999999)+100;
    }
}
