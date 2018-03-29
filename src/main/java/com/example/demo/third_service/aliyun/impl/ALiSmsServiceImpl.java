package com.example.demo.third_service.aliyun.impl;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.example.demo.third_service.aliyun.ALiSmsService;
import com.example.demo.third_service.aliyun.config.ALiSmsCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @desciption:
 * @author: 姚东
 * @date: 2018/1/4
 */

@Service("aLiSmsService")
public class ALiSmsServiceImpl implements ALiSmsService {
    private Logger logger = LoggerFactory.getLogger(ALiSmsServiceImpl.class);

    final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
    final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
    final String signName = "医教云平台";//必填:短信签名-可在短信控制台中找到
    final String accessKeyId = "";//你的accessKeyId,参考本文档步骤2
    final String accessKeySecret = "";//你的accessKeySecret，参考本文档步骤2

    @Override
    public String sendSmsMsg(String phoneNumbers, String templateCode, Map<String, Object> templateParam) {
        //设置超时时间-可自行调整
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        // 初始化ascClient,暂时不支持多region（请勿修改）
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
                accessKeySecret);
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
            IAcsClient acsClient = new DefaultAcsClient(profile);
            //组装请求对象
            SendSmsRequest request = new SendSmsRequest();
            //使用post提交
            request.setMethod(MethodType.POST);
            //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
//            request.setPhoneNumbers("1500000000");
            request.setPhoneNumbers(phoneNumbers);
            //必填:短信签名-可在短信控制台中找到
//            request.setSignName("云通信");
            request.setSignName(signName);
            //必填:短信模板-可在短信控制台中找到
            request.setTemplateCode(templateCode);
            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
            //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
            request.setTemplateParam(JSON.toJSONString(templateParam));
            //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
            //request.setSmsUpExtendCode("90997");
            //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
            //            request.setOutId("yourOutId");
            //请求失败这里会抛ClientException异常
            SendSmsResponse sendSmsResponse = null;
            sendSmsResponse = acsClient.getAcsResponse(request);

            //保存短信数据


            if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
                return "success";
            } else {
                logger.debug("验证码发送失败,code={},message={}", sendSmsResponse.getCode(), sendSmsResponse.getMessage());
                return "error";
            }
        } catch (Exception e) {
            logger.error("调用阿里云发送短信失败", e);
        }
        return "error";
    }

    public static void main(String[] args) {
        //phoneNumber  必填:待发送手机号
        String phoneNumber = "13683422382";
        //templateParam 参数
        Map<String, Object> templateParam = new HashMap<>();
        templateParam.put("title", "测试短信");
        String returnData = new ALiSmsServiceImpl().sendSmsMsg(phoneNumber, ALiSmsCode.SMS__TEMPLATE_CODE, templateParam);
        System.out.println(returnData);
    }
}
