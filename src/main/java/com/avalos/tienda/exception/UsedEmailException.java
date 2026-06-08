package com.avalos.tienda.exception;

public class UsedEmailException extends RuntimeException{
    public UsedEmailException(String message){
        super(message);
    }
}
