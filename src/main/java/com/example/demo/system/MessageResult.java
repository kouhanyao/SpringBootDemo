package com.example.demo.system;

import com.example.demo.constant.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 消息响应
 *
 * @author marker
 * Created by ROOT on 2016/10/31.
 */
@Data
@AllArgsConstructor
public class MessageResult implements Serializable {


    /**
     * 状态码
     */
    private String code;

    /**
     * 消息内容
     */
    private String msg;

    /**
     * 数据
     */
    private Object data;


    /**
     * 消息枚举构造
     *
     * @param status 消息枚举
     * @param data
     */
    public MessageResult(ResultCode status, Object data) {
        this.code = status.getCode();
        this.msg = status.getMsg();
        this.data = data;
    }

    /**
     * 构造消息
     *
     * @param code 错误码
     * @param data 数据
     * @param msg  消息
     */
    public MessageResult(String code, Object data, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    /**
     * 操作成功
     *
     * @return
     */
    public static MessageResult success() {
        return new MessageResult(ResultCode.STATUS_SUCCESS, null);
    }

    /**
     * 操作成功
     * 附加数据
     *
     * @return
     */
    public static MessageResult success(Object data) {
        return new MessageResult(ResultCode.STATUS_SUCCESS, data);
    }


    /**
     * 操作失败
     *
     * @return
     */
    public static MessageResult error() {

        return new MessageResult(ResultCode.STATUS_ERROR, null);
    }

    /**
     * 操作失败
     *
     * @param msg 消息内容
     * @return
     */
    public static MessageResult error(String msg) {
        return new MessageResult(ResultCode.STATUS_ERROR.getCode(), (Object) null, msg);
    }

    /**
     * 包装消息枚举
     *
     * @param resultCode 消息枚举
     * @return
     */
    public static MessageResult warpper(ResultCode resultCode) {
        return new MessageResult(resultCode, null);
    }


    /**
     * 包装消息枚举(含有数据)
     *
     * @param resultCode 消息枚举
     * @return
     */
    public static MessageResult warpper(ResultCode resultCode, Object data) {
        return new MessageResult(resultCode, data);
    }


}
