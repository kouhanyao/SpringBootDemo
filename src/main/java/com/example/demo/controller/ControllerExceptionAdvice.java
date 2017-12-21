//*****************************************************************************
//
// File Name       :  ControllerAdvice.java
// Date Created    :  2015年3月16日
// Last Changed By :  $Author: fangsj$
// Last Changed On :  $Date: 2015年3月16日$
// Revision        :  $Rev: 1.0.0$
// Description     :  控制器增强类
//
// LeleYun Pte Ltd.  Copyright (c) 2015.  All Rights Reserved.
//
//*****************************************************************************

package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.exception.BusinessException;
import com.example.demo.exception.BusinessMessageException;
import com.example.demo.exception.DMLNoRecordEffectedException;
import com.example.demo.utils.DateHelper;
import com.example.demo.utils.ResultBean;
import com.example.demo.utils.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;


/**
 * 控制器增强类
 *
 * @author fangsj
 */
@ControllerAdvice
public class ControllerExceptionAdvice {
    private static final Logger log = LoggerFactory.getLogger(ControllerExceptionAdvice.class);


    /**
     * 统一异常处理
     *
     * @param e       发生的异常
     * @param request 请求
     * @return
     * @throws
     * @author fangsj
     */
    @ExceptionHandler(Exception.class)
    public ResultBean exceptionHandler(Exception e, WebRequest request) {
        // 如果是更新数据库的内容格式有异常,则返回数据异常
        if (e instanceof UncategorizedSQLException)
            return handleException(ResultCode.RETURN_CODE_EXCEPTION, request, e, "数据异常");
        ResultBean result = handleException(ResultCode.RETURN_CODE_EXCEPTION, request, e);
        log.error(JSON.toJSONString(result));
        return result;
    }


    /**
     * 统一异常处理
     *
     * @param e       发生的异常
     * @param request 请求
     * @return
     * @throws
     * @author fangsj
     */
    @ExceptionHandler(DMLNoRecordEffectedException.class)
    public ResultBean dmlNoRecordEffectedExceptionHandler(Exception e, WebRequest request) {
        return handleException(ResultCode.RETURN_CODE_EXCEPTION, request, e);
    }


    /**
     * 同一处理业务异常
     *
     * @param e       业务异常
     * @param request
     * @return
     * @throws
     * @author fangsj
     */
    @ExceptionHandler(BusinessException.class)
    public ResultBean businessExceptionHandler(BusinessException e, WebRequest request) {
        // 记录异常
        log.debug("^_^ 业务处理异常code：{},msg：{}", e.getCode(), e.getMessage());
        return handleException(e.getCode(), request, e);
    }

    /**
     * 统一处理此业务异常，只关注消息
     *
     * @param e       业务异常
     * @param request
     * @return
     * @throws
     * @author fangsj
     */
    @ExceptionHandler(BusinessMessageException.class)
    public ResultBean businessMessageExceptionHandler(BusinessMessageException e, WebRequest request) {
        // 记录异常
        log.debug("^_^ 业务处理异常code：{},msg：{}", e.getCode(), e.getMessage());
        return handleException(e.getCode(), request, e, e.getMessage());
    }


    /**
     * 处理异常返回结果
     *
     * @param code    异常code
     * @param request 请求
     * @param e       异常
     * @return
     * @throws
     * @author fangsj
     */
    private ResultBean handleException(String code, WebRequest request, Exception e) {
        log.error("处理异常返回结果", e);
        ResultBean ResultBean = new ResultBean();
        ResultBean.setCode(code).setResptime(DateHelper.convertDateToString(new Date(), DateHelper.DATE_FORMAT_YYYYMMDDHHMMSS))
                .setTOKEN(request.getParameter("TOKEN")).setMsg("服务器内部错误");
        return ResultBean;
    }

    /**
     * 处理异常返回结果
     *
     * @param code    异常code
     * @param request 请求
     * @param e       异常
     * @param message 异常自定义提示内容
     * @return
     * @author liub
     */
    private ResultBean handleException(String code, WebRequest request, Exception e, String message) {
        log.error("处理异常返回结果", e);
        ResultBean ResultBean = new ResultBean();
        ResultBean.setCode(code).setResptime(DateHelper.convertDateToString(new Date(), DateHelper.DATE_FORMAT_YYYYMMDDHHMMSS))
                .setTOKEN(request.getParameter("TOKEN")).setMsg(message);
        return ResultBean;
    }
}
