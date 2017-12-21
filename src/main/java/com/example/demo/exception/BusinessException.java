//*****************************************************************************
//
// File Name       :  BusinessException.java
// Date Created    :  2015年2月9日
// Last Changed By :  $Author: fangsj$
// Last Changed On :  $Date: 2015年2月9日$
// Revision        :  $Rev: 1.0.0$
// Description     :  业务异常
//
// LeleYun Pte Ltd.  Copyright (c) 2015.  All Rights Reserved.
//
//*****************************************************************************

package com.example.demo.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 业务异常
 *
 * @author fangsj
 */
public class BusinessException extends Exception
{
    private static final Logger logger = LoggerFactory.getLogger(BusinessException.class);
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String code;
    private Object data;

    public BusinessException(){}
    
    
    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public BusinessException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }


    /**
     * @param message
     * @param cause
     */
    public BusinessException(String message, Throwable cause)
    {
        super(message, cause);
    }


    /**
     * @param message
     */
    public BusinessException(String message)
    {
        super(message);
    }


    /**
     * @param cause
     */
    public BusinessException(Throwable cause)
    {
        super(cause);
    }


    public BusinessException(String code,String message)
    {
        this(code, message, null);
    }
    

    /**
     * @param code
     * @param msg
     * @param data
     */
    public BusinessException(String code, String message, Object data)
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
    public BusinessException setCode(String code)
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
    public BusinessException setData(Object data)
    {
        this.data = data;
        return this;
    }
    
    
    public static BusinessException throwException(String code) throws BusinessException
    {
        throw createException(code);
    }
    
    public static BusinessException createException(String code)
    {
        return new BusinessException().setCode(code);
    }
    
    public static BusinessException createException(String code, String message)
    {
        return new BusinessException(code, message);
    }
    
    public static BusinessException createException(String code, String message, Object data)
    {
        return new BusinessException(code, message, data);
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
