package com.example.demo.third_service.aliyun;

import java.util.Map;

/**
 * @desciption: 阿里短信验证码
 * @author: 姚东
 * @date: 2018/1/4
 */
public interface ALiSmsService {
    /**
     * 发送短信
     *
     * @param phoneNumber  必填:待发送手机号
     * @param templateCode 模板code
     * @param templateParam 参数
     * @return
     */
    String sendSmsMsg(String phoneNumber, String templateCode, Map<String, Object> templateParam);
}
