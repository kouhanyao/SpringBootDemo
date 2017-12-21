package com.example.demo.utils;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaog on 2017/8/4.
 * <p>
 * 调用webservice 工具类
 */
public final class WebServiceUtil {
    /**
     * 记录日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(WebServiceUtil.class);

    private WebServiceUtil() {
    }

    public static void main(String[] args) throws Exception {
        final String url = "http://127.0.0.1:52363/HisWebService.asmx?wsdl";
        long beginMillis;
        long endMillis;
        String result;
        String namespace = "http://tempuri.org/";
        String method = "GetPatientInfo";

        beginMillis = System.currentTimeMillis();
        Client client = WebServiceUtil.getCxfClient(url);
        result = cxfInvokeRemoteMethod(client, method, new Object[]{"111", "222"});
        endMillis = System.currentTimeMillis();
        System.out.println(result + ";用时：" + (endMillis - beginMillis));

        beginMillis = System.currentTimeMillis();
        result = cxfInvokeRemoteMethod(client, method, new Object[]{"111", "222"});
        endMillis = System.currentTimeMillis();
        System.out.println(result + ";用时：" + (endMillis - beginMillis));

        LinkedHashMap<String, Object> paramMap = new LinkedHashMap<>();
        paramMap.put("cardId", "111");
        paramMap.put("patientId", "222");
        beginMillis = System.currentTimeMillis();
        Call call = WebServiceUtil.getAxisCall(url);
        result = WebServiceUtil.axisInvokeRemoteMethod(call, method, namespace, paramMap);
        endMillis = System.currentTimeMillis();
        System.out.println(result + ";用时：" + (endMillis - beginMillis));

        /*beginMillis = System.currentTimeMillis();
        call = WebServiceUtil.getAxisCall(url);
        result = WebServiceUtil.axisInvokeRemoteMethod(call,method,namespace,paramMap);
        endMillis = System.currentTimeMillis();
        System.out.println(result + ";用时：" + (endMillis - beginMillis));*/
    }

    //常用
    public static String callRemoteMethod(String url, String methodName, String namespace, LinkedHashMap<String, Object> paramMap) {
        if (LOGGER.isInfoEnabled()) {
            String param = "";
            for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
                param += "key:" + entry.getKey() + ";value：" + entry.getValue();
            }
            LOGGER.info("callRemoteMethod 请求说明，url:{};namespace:{};methodName:{};参数:{}", url, namespace, methodName, param);
        }
        if (!url.endsWith("wsdl")) {
            url += "?wsdl";
        }
        Call call = WebServiceUtil.getAxisCall(url);
        String xml = WebServiceUtil.axisInvokeRemoteMethod(call, methodName, namespace, paramMap);
        LOGGER.info("callRemoteMethod 结果说明:" + xml);
        return xml;
    }

    private static Client getCxfClient(String url) {
        try {
            JaxWsDynamicClientFactory clientFactory = JaxWsDynamicClientFactory.newInstance();
            Client client = clientFactory.createClient(url);

            HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();    // 策略
            httpClientPolicy.setConnectionTimeout(60 * 1000);    // 连接超时(毫秒)
            httpClientPolicy.setAllowChunking(false);       // 取消块编码
            httpClientPolicy.setReceiveTimeout(10 * 1000);       // 响应超时(毫秒)
            HTTPConduit http = (HTTPConduit) client.getConduit();
            http.setClient(httpClientPolicy);
            return client;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    private static String cxfInvokeRemoteMethod(Client client, String method, Object[] params) {
        try {
            /*处理webService接口和实现类namespace不同的情况，CXF动态客户端在处理此问题时，
            会报No operation was found with the name的异常*/
            Endpoint endpoint = client.getEndpoint();
            QName opName = new QName(endpoint.getService().getName().getNamespaceURI(), method);
            BindingInfo bindingInfo = endpoint.getEndpointInfo().getBinding();
            if (bindingInfo.getOperation(opName) == null) {
                for (BindingOperationInfo operationInfo : bindingInfo.getOperations()) {
                    if (method.equals(operationInfo.getName().getLocalPart())) {
                        opName = operationInfo.getName();
                        break;
                    }
                }
            }
            Object[] objects = client.invoke(opName, params);
            String result = objects[0].toString();
            return result;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return "";
        }
    }

    private static Call getAxisCall(String url) {
        try {
            Service service = new Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(url);// 远程调用路径
            call.setTimeout(60 * 10000);//超时

            return call;
        } catch (ServiceException se) {
            LOGGER.error("getAxisCall create call error", se);
            return null;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    private static String axisInvokeRemoteMethod(Call call, String method, String namespaseURL, LinkedHashMap<String, Object> paramMap) {
        try {
            // 调用的命名空间和方法名
            call.setOperationName(new QName(namespaseURL, method));
            call.setUseSOAPAction(true);
            //可以在wsdl中找个这个地址
            call.setSOAPActionURI(namespaseURL + method);

            //命名空间和参数名，参数名不可以随便写,参数名可以在wsdl文件中找到
            Object[] params = new Object[paramMap.size()];
            Integer i = 0;
            for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
                call.addParameter(new QName(namespaseURL, entry.getKey()), XMLType.XSD_STRING, ParameterMode.IN);
                params[i] = entry.getValue().toString();
                i++;
            }
            call.setReturnType(XMLType.XSD_STRING);// 返回值类型：String
            //使用invok方法进行调用
            String result = (String) call.invoke(params);
            return result;
        } catch (RemoteException re) {
            LOGGER.error("axisInvokeRemoteMethod remote error", re);
            return "";
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return "";
        }
    }

    /**
     * 将请求参数转换为xml
     *
     * @param paramMap Map类型的参数
     * @return
     */
    protected String requestTransfer(Map<String, Object> paramMap) {
        if (paramMap == null || paramMap.isEmpty()) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        sb.append("<Request>");
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            String val = entry.getValue() == null ? "" : entry.getValue().toString();
            sb.append("<" + entry.getKey() + ">").append(val).append("</" + entry.getKey() + ">");
        }
        sb.append("</Request>");
        return sb.toString();
    }

    /**
     * 解析xml结果
     *
     * @param xml xml字符串
     * @return 返回Map类型的结果对象
     * @throws DocumentException xml解析异常
     */
    protected Map<String, Object> responseParse(String xml) throws DocumentException {
        Map<String, Object> resultMap = Maps.newHashMap();
        List<Map<String, Object>> itemMapList = Lists.newArrayList();
        // 解析交易配置文件
        SAXReader reader = new SAXReader();
        Document document = null;
        document = reader.read(new InputStreamReader(new ByteArrayInputStream(xml.getBytes())));
        // 根节点
        Element rootElement = document.getRootElement();
        //解析报文头数据
        List<Element> headList = rootElement.elements();
        for (Element field : headList) {
            String fieldName = field.getName();
            String fieldValue = field.getText();
            if ("Item".equals(fieldName)) {
                List<Element> fieldListRows = field.elements();

                Map<String, Object> itemMap = Maps.newHashMap();
                for (Element item : fieldListRows) {
                    String itemFieldName = item.getName();
                    String itemFieldValue = item.getText();
                    itemMap.put(itemFieldName, itemFieldValue);
                }
                itemMapList.add(itemMap);
            } else {
                resultMap.put(fieldName, fieldValue);
            }
        }
        resultMap.put("Item", itemMapList);
        return resultMap;
    }

    /**
     * 格式化结果为标准的json返回格式
     *
     * @param data 结果数据
     * @return 返回一个标准的json字符串
     */
    protected String resultFormat(Map<String, Object> data) {
        JSONObject jsonObject = new JSONObject();
        if (data != null) {
            jsonObject.putAll(data);
        }
        return jsonObject.toJSONString();
    }
}
