package org.demo.springsecurity.exceptions;

public class CustomDBException extends RuntimeException{

    public CustomDBException(String message){
        super(message);
    }
}
