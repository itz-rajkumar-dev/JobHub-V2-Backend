package com.savedjob.exception;

public class AlreadySavedJobExistException extends RuntimeException{

    public AlreadySavedJobExistException(String message){

        super(message);
    }
}
