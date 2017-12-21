package com.example.demo.exception;/**
 * Created by hasee on 2016/7/18.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 此异常默认返回通用错误码。加message信息
 *
 * @author yao dong
 * @date 2016/7/18
 */
public class BusinessMessageException extends Exception{
    private static final Logger logger = LoggerFactory.getLogger(BusinessMessageException.class);

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String code = "88888";//默认错误码
    private Object data;

    public BusinessMessageException(){}


    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public BusinessMessageException(String message, Throwable cause,
                             boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }


    /**
     * @param message
     * @param cause
     */
    public BusinessMessageException(String message, Throwable cause)
    {
        super(message, cause);
    }


    /**
     * @param message
     */
    public BusinessMessageException(String message)
    {
        super(message);
    }


    /**
     * @param cause
     */
    public BusinessMessageException(Throwable cause)
    {
        super(cause);
    }


    public BusinessMessageException(String code,String message)
    {
        this(code, message, null);
    }


    /**
     * @param code
     * @param msg
     * @param data
     */
    public BusinessMessageException(String code, String message, Object data)
    {
        super(message);
        this.code = code;
        this.data = data;
    }


    /**
     * @return Returns the code.
     */
    public String getCode()
    {
        return code;
    }


    /**
     * @param code The code to set.
     */
    public BusinessMessageException setCode(String code)
    {
        this.code = code;
        return this;
    }


    /**
     * @return Returns the data.
     */
    public Object getData()
    {
        return data;
    }


    /**
     * @param data The data to set.
     */
    public BusinessMessageException setData(Object data)
    {
        this.data = data;
        return this;
    }


    public static BusinessMessageException throwException(String code) throws BusinessMessageException
    {
        throw createException(code);
    }

    public static BusinessMessageException createException(String code)
    {
        return new BusinessMessageException().setCode(code);
    }

    public static BusinessMessageException createException(String code, String message)
    {
        return new BusinessMessageException(code, message);
    }

    public static BusinessMessageException createException(String code, String message, Object data)
    {
        return new BusinessMessageException(code, message, data);
    }


    /* (non-Javadoc)
     * @author fangsj
     * @see java.lang.Throwable#fillInStackTrace()
     */
    @Override
    public Throwable fillInStackTrace()
    {
        if (logger.isDebugEnabled()) return super.fillInStackTrace();
        return this;
    }
}
