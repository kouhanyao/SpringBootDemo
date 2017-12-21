package com.example.demo.service.impl;

import com.example.demo.service.WebService;

/**
 * Created by 寇含尧 on 2017/10/26.
 */
@javax.jws.WebService(
        serviceName = "CommonService", // 与接口中指定的name一致
        targetNamespace = "http://webservice.leftso.com/", // 与接口中的命名空间一致,一般是接口的包名倒
        endpointInterface = "com.example.demo.service.WebService"// 接口地址
)
public class WebServiceImpl implements WebService {

    @Override
    public String reportNotify(String input) {
        return "success";
    }
}
