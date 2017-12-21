//*****************************************************************************
//
// File Name       :  DMLNoRecordEffectedException.java 
// Date Created    :  $ 5 Mar 2008
// Last Changed By :  $Author: huangfei $
// Last Changed On :  $Date: 5 Mar 2008 $
// Revision        :  $Rev: $
// Description     :  TODO To fill in a brief description of the purpose of
//                    this class.
//
// PracBiz Pte Ltd.  Copyright (c) 2008.  All Rights Reserved.
//
//*****************************************************************************

package com.example.demo.exception;

/**
 * TODO To provide an overview of this class.
 * 
 * @author huangfei
 */
public class DMLNoRecordEffectedException extends DMLException
{
    public DMLNoRecordEffectedException()
    {
        super();
    }


    public DMLNoRecordEffectedException(Throwable cause)
    {
        super(cause);
    }


    public DMLNoRecordEffectedException(String message_)
    {
        super(message_);
    }


    public DMLNoRecordEffectedException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
