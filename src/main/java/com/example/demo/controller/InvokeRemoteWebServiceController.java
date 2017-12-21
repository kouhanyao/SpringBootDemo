package com.example.demo.controller;

import com.example.demo.utils.WebServiceUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;

/**
 * Created by 寇含尧 on 2017/10/26.
 */
@RestController
@RequestMapping(value = "/InvokeRemoteWebServiceController")
public class InvokeRemoteWebServiceController {
    @RequestMapping(value = "/")
    public String invokeWebService() {
        LinkedHashMap<String, Object> paramMap = new LinkedHashMap<>();
        String reqXml = "<Request><StartDate>2016-04-30</StartDate><EndDate>2016-12-30</EndDate><Hisid>0000275378</Hisid><Outputid></Outputid><EXAMNO></EXAMNO></Request>";
        paramMap.put("input", reqXml);
        String url = "http://182.150.22.51:8001/pacs.asmx?WSDL";
        String methodName = "GetReport";
        String namespace = "http://tempuri.org/";
        String resultXml = WebServiceUtil.callRemoteMethod(url, methodName, namespace, paramMap);
        System.out.println(resultXml);
        return resultXml;
    }
}
