package com.lcwd.restaurant.exceptions;


public class ResourceNotFoundException extends RuntimeException
{

    public ResourceNotFoundException(String message) {
        super(message );
    }

    public ResourceNotFoundException(){
        super("Sorry something went wrong this resource not found ") ;
    }
}
