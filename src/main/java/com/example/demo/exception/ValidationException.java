//*****************************************************************************
//
// File Name       :  ValidationException.java
// Date Created    :  2012-7-10
// Last Changed By :  $Author: liyue $
// Last Changed On :  $Date: 2012-10-12 09:32:18 +0800 (周五, 12 十月 2012) $
// Revision        :  $Rev: 6810 $
// Description     :  TODO To fill in a brief description of the purpose of
//                    this class.
//
// Allinpay Pte Ltd.  Copyright (c) 2012.  All Rights Reserved.
//
//*****************************************************************************

package com.example.demo.exception;

import java.util.List;

/**
 * TODO To provide an overview of this class.
 * 
 * @author LiYue
 */
public class ValidationException extends Exception
{
    private String messageKey;

    private String[] messageParam;

    private List<String> messageKeyList;

    private List<String[]> messageParamList;


    public ValidationException()
    {
        super();
    }


    public ValidationException(String message)
    {
        super(message);
    }


    public ValidationException(Throwable cause)
    {
        super(cause);
    }


    public ValidationException(String message, Throwable cause)
    {
        super(message, cause);
    }


    public ValidationException(Exception exception)
    {
        super(exception);
    }
    
    public ValidationException(String messageKey, String[] messageParam)
    {
        this.messageKey = messageKey;
        this.messageParam = messageParam;
    }


    public ValidationException(List<String> messageKeyList,
            List<String[]> messageParamList)
    {
        this.messageKeyList = messageKeyList;
        this.messageParamList = messageParamList;
    }


    public List<String> getMessageKeyList()
    {
        return messageKeyList;
    }


    public void setMessageKeyList(List<String> messageKeyList)
    {
        this.messageKeyList = messageKeyList;
    }


    public List<String[]> getMessageParamList()
    {
        return messageParamList;
    }


    public void setMessageParamList(List<String[]> messageParamList)
    {
        this.messageParamList = messageParamList;
    }


    public String getMessageKey()
    {
        return messageKey;
    }


    public String[] getMessageParam()
    {
        return messageParam;
    }

}
