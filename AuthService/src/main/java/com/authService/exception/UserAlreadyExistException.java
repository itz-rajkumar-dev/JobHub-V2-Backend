package com.authService.exception;

public class UserAlreadyExistException extends RuntimeException{

    public  UserAlreadyExistException(String message){

        super(message);
    }
}
