//*****************************************************************************
//
// File Name       :  ToolUtils.java
// Date Created    :  2015年2月5日
// Last Changed By :  $Author: fangsj$
// Last Changed On :  $Date: 2015年2月5日$
// Revision        :  $Rev: 1.0.0$
// Description     :  通用工具类
//
// LeleYun Pte Ltd.  Copyright (c) 2015.  All Rights Reserved.
//
//*****************************************************************************

package com.example.demo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * 通用工具类
 *
 * @author fangsj
 */
public class ToolUtil {
    private static final Logger logger = LoggerFactory.getLogger(ToolUtil.class);

    /**
     * 取得request对象
     *
     * @return
     * @throws
     * @author fangsj
     */
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    /**
     * 格式化文件下载名称
     *
     * @return
     * @throws
     * @author fangsj
     */
    public static String formatDowanloadFileName(String fileName) throws Exception {
        try {
            if (isIe()) {
                fileName = new String(fileName.getBytes("GB2312"), "ISO8859-1");
            } else {
                fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
            }
        } catch (UnsupportedEncodingException e) {
            throw new Exception("格式化文件下载名称失败");
        }

        return fileName;
    }


    /**
     * 判断当前请求使用的浏览器，支持判断ie、safari、chrome、firefox、360se、opr、opera
     *
     * @return
     * @throws
     * @author fangsj
     */
    public static boolean isIe() {
        String lowerUserAgent = getRequest().getHeader("User-Agent").toLowerCase();
        return !(lowerUserAgent.contains("chrome") || lowerUserAgent.contains("safari") || lowerUserAgent.contains("firefox") || lowerUserAgent.contains("360se") || lowerUserAgent.contains("opera") || lowerUserAgent.contains("opr"));
    }


    /**
     * 判断文件是否是图片
     *
     * @param objName 图片名
     * @return
     * @throws
     * @author fangsj
     */
    public static boolean isImage(String objName) {
        String suffix = objName.substring(objName.lastIndexOf(".") + 1);
        //如果图片路径为空，则返回false
        if (CodeHelper.isNullOrEmpty(suffix)) return false;

        return "jpg,jpeg,gif,png".contains(suffix.toLowerCase());
    }
}
