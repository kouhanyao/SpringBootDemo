//*****************************************************************************
//
// File Name       :  NoTypeDefinitionException.java
// Date Created    :  2012-12-17
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
public class NoTypeDefinitionException extends Exception
{

    public NoTypeDefinitionException()
    {
        super();
    }


    public NoTypeDefinitionException(final String message)
    {
        super(message);
    }


    public NoTypeDefinitionException(final Throwable cause)
    {
        super(cause);
    }


    public NoTypeDefinitionException(final String message, final Throwable cause)
    {
        super(message, cause);
    }


    public NoTypeDefinitionException(Exception exception)
    {
        super(exception);
    }
}
