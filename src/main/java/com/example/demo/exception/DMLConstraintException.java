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
public class DMLConstraintException extends Exception
{
    public DMLConstraintException()
    {
        super();
    }
    
    public DMLConstraintException(String message)
    {
        super(message);
    }
    
    public DMLConstraintException(Throwable cause)
    {
        super(cause);
    }
    
    public DMLConstraintException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
