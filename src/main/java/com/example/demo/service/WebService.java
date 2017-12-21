package com.example.demo.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;

/**
 * Created by 寇含尧 on 2017/10/26.
 */
/**
 *  提供webservice接口
 */
@javax.jws.WebService(name = "CommonService", // 暴露服务名称
        targetNamespace = "http://webservice.leftso.com/"// 命名空间,一般是接口的包名倒序
        )
public interface WebService {
    /**
     * 信息查询
     *
     * @param input 请求参数 XML格式，参考文档
     * @return
     */
    @WebMethod(operationName = "reportNotifyTest", exclude = false)//operationName:接口的方法名;exclude 用于阻止将某一继承方法公开为web服务，默认为false
    @WebResult//接口的返回值
    String reportNotify(
            @WebParam(name = "inputTest")//接口的参数
            String input
    );
}
