package com.ibm.academy.ruletaapi.exceptions;

public class BadRequestException extends RuntimeException
{
    public BadRequestException(String message)
    {
        super(message);
    }
    private static final long serialVersionUID = -392544750917716098L;
}
