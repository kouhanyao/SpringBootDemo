package com.example.demo.utils;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;


/**
 * 接口返回结果
 *
 * @author yaolg
 */
public class ResultBean implements Serializable {
    private static final String EMPTY_DATA = "{}";
    private String code = "";
    private String msg = "";
    private String resptime = "";
    private String sign = "";
    private String data = "{}";
    private String TOKEN = "";

    public ResultBean() {

    }

    public ResultBean(String code) {
        this(code, null);
    }

    public ResultBean(String code, String data) {
        this.code = code;
        this.data = data;
    }

    public ResultBean(String code, String data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public ResultBean setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return StringUtils.isBlank(msg) ? ResultCode.getMsg(this.code) : msg;
        //      return this.msg;
    }

    public ResultBean setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public String getResptime() {
        return resptime;
    }

    public ResultBean setResptime(String resptime) {
        this.resptime = resptime;
        return this;
    }

    public String getSign() {
        return sign;
    }

    public ResultBean setSign(String sign) {
        this.sign = sign;
        return this;
    }

    public String getData() {
        return StringUtils.isNotBlank(data) ? data : EMPTY_DATA;
    }

    public ResultBean setData(String data) {
        this.data = data;
        return this;
    }

    public String getTOKEN() {
        return TOKEN == null ? "" : TOKEN;
    }

    public ResultBean setTOKEN(String tOKEN) {
        TOKEN = tOKEN;
        return this;
    }

}
