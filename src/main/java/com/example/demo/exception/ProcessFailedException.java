//*****************************************************************************
//
// File Name       :  ProcessFailedException.java
// Date Created    :  2012-12-18
// Last Changed By :  $Author: $
// Last Changed On :  $Date: $
// Revision        :  $Rev: $
// Description     :  TODO To fill in a brief description of the purpose of
//                    this class.
//
// Allinpay Pte Ltd.  Copyright (c) 2012.  All Rights Reserved.
//
//*****************************************************************************

package com.example.demo.exception;

/**
 * TODO To provide an overview of this class.
 * 
 * @author liyur
 */
public class ProcessFailedException extends Exception
{
    private Object errorObj;


    public ProcessFailedException()
    {
        super();
    }


    public ProcessFailedException(final String message)
    {
        super(message);
    }


    public ProcessFailedException(final Object errorObj, final String message)
    {
        super(message);
        setErrorObj(errorObj);
    }


    public ProcessFailedException(final Throwable cause)
    {
        super(cause);
    }


    public ProcessFailedException(final String message, final Throwable cause)
    {
        super(message, cause);
    }


    public ProcessFailedException(Exception exception)
    {
        super(exception);
    }


    /**
     * @return Returns the errorObj.
     */
    public Object getErrorObj()
    {
        return errorObj;
    }


    /**
     * @param errorObj The errorObj to set.
     */
    public void setErrorObj(Object errorObj)
    {
        this.errorObj = errorObj;
    }

}
