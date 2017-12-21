//*****************************************************************************
//
// File Name       :  DMLUpdateNoRecordEffectedException.java 
// Date Created    :  $ 5 Mar 2008
// Last Changed By :  $Author: huangfei $
// Last Changed On :  $Date: 5 Mar 2008 $
// Revision        :  $Rev: $
// Description     :  exception during operation database via DML
//
// PracBiz Pte Ltd.  Copyright (c) 2008.  All Rights Reserved.
//
//*****************************************************************************
package com.example.demo.exception;

public class DMLException extends Exception
{
    public DMLException()
    {
        super();
    }
    
    public DMLException(String message)
    {
        super(message);
    }
    
    public DMLException(Throwable cause)
    {
        super(cause);
    }
    
    public DMLException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
