package com.example.demo.utils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

/**
 * 参考文档:https://www.cnblogs.com/zhi-leaf/p/8508071.html
 */
public class HttpClientNotThreadPoolUtils {
    public static void main(String[] args) {
        HttpClientNotThreadPoolUtils httpClientNotThreadPoolUtils = new HttpClientNotThreadPoolUtils();
        //发送json数据
        String result = null;
        /*result = httpClientNotThreadPoolUtils.postJson("http://127.0.0.1:1201/request/one", "{\"name\":\"kouhanyao\",\"age\":\"32\",\"id\":\"1\",\"love\":[{\"sex\":\"woman\",\"play\":\"soccer\"}]}");
        System.out.println(result);*/

        Map<String, String> params = new HashMap<>();
        params.put("name", "寇含尧");
        params.put("age", "年龄");
        params.put("id", "一个");
        params.put("love", "[{\"sex\":\"woman\",\"play\":\"soccer\"}]");
        result = httpClientNotThreadPoolUtils.httpPost("http://127.0.0.1:1201/request/one", params);
        System.out.println(result);
    }

    /**
     * Post发送json数据
     *
     * @param url
     * @param json
     * @return
     */
    public String postJson(String url, String json) {
        try {
            CloseableHttpClient httpClient = null;
            CloseableHttpResponse response = null;
            try {
                httpClient = HttpClients.createDefault();
                HttpPost httpPost = new HttpPost(url);
                StringEntity entity = new StringEntity(json, "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                httpPost.setEntity(entity);
                response = httpClient.execute(httpPost);
                return EntityUtils.toString(response.getEntity(), Charset.forName("utf-8"));
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (response != null) {
                    response.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String httpPost(String url, Map<String, String> paramsMap) {
        return httpPost(url, paramsMap, null);
    }

    /**
     * Post发送form表单数据
     *
     * @param url
     * @param paramsMap
     * @return
     */
    public String httpPost(String url, Map<String, String> paramsMap,
                           Map<String, String> headMap) {
        try {
            CloseableHttpClient client = null;
            CloseableHttpResponse response = null;
            try {
                HttpPost httpPost = new HttpPost(url);
                setPostHead(httpPost, headMap);
                setPostParams(httpPost, paramsMap);
                client = HttpClients.createDefault();
                response = client.execute(httpPost);
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity, Charset.forName("utf-8"));
                return result;
            } finally {
                if (response != null) {
                    response.close();
                }
                if (client != null) {
                    client.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 设置POST的参数
     *
     * @param httpPost
     * @param paramsMap
     * @throws Exception
     */
    private void setPostParams(HttpPost httpPost, Map<String, String> paramsMap)
            throws Exception {
        // 创建一个提交数据的容器
        if (paramsMap != null && paramsMap.size() > 0) {
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            Set<String> keySet = paramsMap.keySet();
            for (String key : keySet) {
                nvps.add(new BasicNameValuePair(key, paramsMap.get(key)));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
        }
    }

    /**
     * 设置http的HEAD
     *
     * @param httpPost
     * @param headMap
     */
    private void setPostHead(HttpPost httpPost, Map<String, String> headMap) {
        if (headMap != null && headMap.size() > 0) {
            Set<String> keySet = headMap.keySet();
            for (String key : keySet) {
                httpPost.addHeader(key, headMap.get(key));
            }
        }
    }
}
