package com.ibm.academy.ruletaapi.exceptions;

public class NotFoundException extends BadRequestException
{
    public NotFoundException(String message){
        super(message);
    }
    private static final long serialVersionUID = -6415856783110734393L;
}
