package com.authService.exception;

public class OtpAlreadyVerified extends  RuntimeException{

    public OtpAlreadyVerified(String message){
        super(message);
    }
}
