package com.lcwd.restaurant.exceptions;

public class BadApiRequestException extends  RuntimeException
{
    public BadApiRequestException(String message){
        super(message) ;
    }

    public BadApiRequestException(){
        super("Bad api exception are occur ") ;
    }
}
