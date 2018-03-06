package com.example.demo.controller;

import com.example.demo.utils.ToolUtil;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by 寇含尧 on 2017/12/26.
 */
public class BaseController {
    /**
     * 获取上传文件
     *
     * @return
     * @throws
     * @author fangsj
     */
    protected Map<String, MultipartFile> getUploadFileMap() {
        return getMultipartRequest2().getFileMap();
    }


    /**
     * 获取上传文件
     *
     * @return
     * @throws
     * @author fangsj
     */
    protected List<MultipartFile> getUploadFileList(String fileName) {
        return getMultipartRequest2().getFiles(fileName);
    }

    /**
     * 获取MultipartHttpServletRequest方式一
     *
     * @return MultipartHttpServletRequest
     * @author fangsj
     */
    protected MultipartHttpServletRequest getMultipartRequest() {
        return (DefaultMultipartHttpServletRequest) getRequest();
    }

    /**
     * 获取MultipartHttpServletRequest方式二
     *
     * @return MultipartHttpServletRequest
     * @author fangsj
     */
    protected MultipartHttpServletRequest getMultipartRequest2() {
        MultipartResolver resolver = new CommonsMultipartResolver(getRequest().getSession().getServletContext());
        return resolver.resolveMultipart(getRequest());
    }

    /**
     * 取得request对象
     *
     * @return
     * @throws
     * @author fangsj
     */
    protected HttpServletRequest getRequest() {
        return ToolUtil.getRequest();
    }

    /**
     * 取得客户端ip
     *
     * @return
     * @throws
     * @author kangy
     */
    protected String getIp() {
        String ip = getRequest().getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = getRequest().getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = getRequest().getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = getRequest().getRemoteAddr();
        }
        return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
    }
}
