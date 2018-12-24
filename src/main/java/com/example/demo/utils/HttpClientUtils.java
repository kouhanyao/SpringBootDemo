package com.example.demo.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;


/**
 *
 * 为了释放资源，我们必须手动消耗掉response或者取消连接（使用CloseableHttpResponse类的close方法），见：
 *          https://blog.csdn.net/zl834205311/article/details/79847746
 * Created by 寇含尧 on 2017/11/2.
 */
public class HttpClientUtils {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

    /**
     * 最大线程池
     */
    private int msMaxSize = 5;

    public interface HttpClientDownLoadProgress {
        public void onProgress(int progress);
    }

    private PoolingHttpClientConnectionManager cm;

    static volatile HttpClientUtils httpClientUtils = null;

    private HttpClientUtils() {
        //连接池管理器
        cm = new PoolingHttpClientConnectionManager();
        // 将最大连接数增加到msMaxSize
        cm.setMaxTotal(msMaxSize);
        // 将每个路由基础的连接增加到msMaxSize
        //DefaultMaxPerRout表示单路由的最大并发连接数，假设你的业务系统需要调用A和B这两个外部系统的http接口，那么如果DefaultMaxPerRout=100，那么调用A系统的http接口时，最大并发数就是100。代码如下：
        cm.setDefaultMaxPerRoute(msMaxSize);
    }

    public CloseableHttpClient getHttpClient() {
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .build();

        /*CloseableHttpClient httpClient = HttpClients.createDefault();//如果不采用连接池就是这种方式获取连接*/
        return httpClient;
    }

    public static HttpClientUtils getInstance() {
        if (httpClientUtils == null) {
            synchronized (HttpClientUtils.class) {
                if (httpClientUtils == null) {
                    httpClientUtils = new HttpClientUtils();
                }
            }
            return httpClientUtils;
        }
        return httpClientUtils;
    }

    /**
     * 下载文件
     *
     * @param url
     * @param filePath
     */
    public void download(final String url, final String filePath) {
        httpDownloadFile(url, filePath, null, null);
    }

    /**
     * 下载文件
     *
     * @param url
     * @param filePath
     * @param progress 进度回调
     */
    public void download(final String url, final String filePath,
                         final HttpClientDownLoadProgress progress) {
        httpDownloadFile(url, filePath, progress, null);
    }

    /**
     * 下载文件
     *
     * @param url
     * @param filePath
     */
    private void httpDownloadFile(String url, String filePath,
                                  HttpClientDownLoadProgress progress, Map<String, String> headMap) {
        try {
            HttpGet httpGet = new HttpGet(url);
            setGetHead(httpGet, headMap);
            CloseableHttpResponse response1 = getHttpClient().execute(httpGet);
            try {
                System.out.println(response1.getStatusLine());
                HttpEntity httpEntity = response1.getEntity();
                long contentLength = httpEntity.getContentLength();
                InputStream is = httpEntity.getContent();
                // 根据InputStream 下载文件
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                byte[] buffer = new byte[4096];
                int r = 0;
                long totalRead = 0;
                while ((r = is.read(buffer)) > 0) {
                    output.write(buffer, 0, r);
                    totalRead += r;
                    if (progress != null) {// 回调进度
                        progress.onProgress((int) (totalRead * 100 / contentLength));
                    }
                }
                is.close();
                FileOutputStream fos = new FileOutputStream(filePath);
                output.writeTo(fos);
                output.flush();
                output.close();
                fos.close();
            } finally {
                if (response1 != null) {
                    response1.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * get请求
     *
     * @param url
     * @return
     */
    public String httpGet(String url) throws Exception {
        return httpGet(url, null);
    }

    /**
     * http get请求
     *
     * @param url
     * @return
     */
    public String httpGet(String url, Map<String, String> headMap) throws Exception {
        HttpGet httpGet = new HttpGet(url);
        setGetHead(httpGet, headMap);
        CloseableHttpResponse response1 = getHttpClient().execute(httpGet);
        HttpEntity entity = response1.getEntity();
        String responseContent = getRespString(entity);
        EntityUtils.consume(entity);
        if (response1 != null) {
            response1.close();
        }
        return responseContent;
    }

    public String httpPost(String url, Map<String, String> paramsMap) throws Exception {
        return httpPost(url, paramsMap, null);
    }

    /**
     * http的post请求
     *
     * @param url
     * @param paramsMap
     * @return
     */
    public String httpPost(String url, Map<String, String> paramsMap,
                           Map<String, String> headMap) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        setPostHead(httpPost, headMap);
        setPostParams(httpPost, paramsMap);
        CloseableHttpResponse response = getHttpClient().execute(httpPost);
        HttpEntity entity = response.getEntity();
        String responseContent = getRespString(entity);
        EntityUtils.consume(entity);
        if (response != null) {
            response.close();
        }
        return responseContent;
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

    /**
     * 设置http的HEAD
     *
     * @param httpGet
     * @param headMap
     */
    private void setGetHead(HttpGet httpGet, Map<String, String> headMap) {
        if (headMap != null && headMap.size() > 0) {
            Set<String> keySet = headMap.keySet();
            for (String key : keySet) {
                httpGet.addHeader(key, headMap.get(key));
            }
        }
    }

    /**
     * 上传文件
     *
     * @param serverUrl       服务器地址
     * @param localFilePath   本地文件路径
     * @param serverFieldName
     * @param params
     * @return
     * @throws Exception
     */
    public String uploadFileImpl(String serverUrl, String localFilePath,
                                 String serverFieldName, Map<String, String> params) {
        String respStr = null;
        try {
            HttpPost httppost = new HttpPost(serverUrl);
            FileBody binFileBody = new FileBody(new File(localFilePath));

            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder
                    .create();
            // add the file params
            multipartEntityBuilder.addPart(serverFieldName, binFileBody);
            // 设置上传的其他参数
            setUploadParams(multipartEntityBuilder, params);

            HttpEntity reqEntity = multipartEntityBuilder.build();
            httppost.setEntity(reqEntity);

            CloseableHttpResponse response = getHttpClient().execute(httppost);
            try {
                HttpEntity resEntity = response.getEntity();
                respStr = getRespString(resEntity);
                EntityUtils.consume(resEntity);
            } finally {
                if (response != null) {
                    response.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("resp=" + respStr);
        return respStr;
    }

    /**
     * 设置上传文件时所附带的其他参数
     *
     * @param multipartEntityBuilder
     * @param params
     */
    private void setUploadParams(MultipartEntityBuilder multipartEntityBuilder,
                                 Map<String, String> params) {
        if (params != null && params.size() > 0) {
            Set<String> keys = params.keySet();
            for (String key : keys) {
                multipartEntityBuilder
                        .addPart(key, new StringBody(params.get(key),
                                ContentType.TEXT_PLAIN));
            }
        }
    }

    /**
     * 将返回结果转化为String
     *
     * @param entity
     * @return
     * @throws Exception
     */
    private String getRespString(HttpEntity entity) throws Exception {
        if (entity == null) {
            return null;
        }
        InputStream is = entity.getContent();
        StringBuffer strBuf = new StringBuffer();
        byte[] buffer = new byte[4096];
        int r = 0;
        while ((r = is.read(buffer)) > 0) {
            strBuf.append(new String(buffer, 0, r, "UTF-8"));
        }
        return strBuf.toString();
    }

    /**
     * 将返回结果转化为String
     *
     * @param response
     * @return
     * @throws Exception
     */
    private String getContentFromResponse(CloseableHttpResponse response) throws IOException {
        String respMsg = null;
        if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
            respMsg = EntityUtils.toString(response.getEntity(), Charset.forName("utf-8"));
        } else {
            EntityUtils.consume(response.getEntity());
        }
        if (response != null) {
            response.close();
        }
        return respMsg;
    }

    public void close() throws IOException {
        if (httpClientUtils != null) {
            getHttpClient().close();
        }
    }

    public static void main(String[] args) throws Exception {
        /**
         * 测试下载文件 异步下载
         */
        HttpClientUtils.getInstance().download(
                "http://newbbs.qiniudn.com/phone.png", "F:\\test.png",
                new HttpClientDownLoadProgress() {
                    @Override
                    public void onProgress(int progress) {
                        System.out.println("download progress = " + progress);
                    }
                });

        // POST 同步方法
        Map<String, String> params = new HashMap<String, String>();
        params.put("username", "admin");
        params.put("password", "admin");
        HttpClientUtils.getInstance().httpPost(
                "http://192.168.31.183:8080/SSHMySql/register", params);

        // GET 同步方法
        HttpClientUtils.getInstance().httpGet(
                "http://wthrcdn.etouch.cn/weather_mini?city=北京");

        // 上传文件 POST 同步方法
        try {
            Map<String, String> uploadParams = new LinkedHashMap<String, String>();
            uploadParams.put("userImageContentType", "image");
            uploadParams.put("userImageFileName", "testaa.png");
            HttpClientUtils.getInstance().uploadFileImpl(
                    "http://192.168.31.183:8080/SSHMySql/upload", "android_bug_1.png",
                    "userImage", uploadParams);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 通过json方式发送post请求
     *
     * @param url
     * @param params
     * @throws IOException
     */
    public String postJson(String url, Map<String, Object> params) throws IOException {
        //HttpClient httpClient = new DefaultHttpClient();
        ObjectMapper mapper = new ObjectMapper();
        //http://47.93.200.179:90/wjw/third/upload/inter/uploadConsulting
        HttpPost request = new HttpPost(url);
        request.setHeader("Content-Type", "application/json;charset=UTF-8");
        request.setHeader("Accept", "application/json");
        StringEntity requestJson = new StringEntity(mapper.writeValueAsString(params), "utf-8");
        requestJson.setContentType("application/json");
        request.setEntity(requestJson);
        CloseableHttpResponse response = getHttpClient().execute(request);
        return getContentFromResponse(response);
    }

    /**
     * 通过json方式发送post请求
     *
     * @param url
     * @param json
     * @return
     * @throws IOException
     */
    public String postJson(String url, String json) throws IOException {
        logger.debug("URL-------------" + url);
        logger.debug("JSON-------------" + json);
        HttpPost httpPost = new HttpPost(url);
        StringEntity entity = new StringEntity(json, "utf-8");//解决中文乱码问题
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        CloseableHttpResponse response = getHttpClient().execute(httpPost);
        try {
            return EntityUtils.toString(response.getEntity(), Charset.forName("utf-8"));
        } catch (IOException e) {
            throw e;
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }
}
