package com.company.exception;

public class CompanyPostNotFoundException extends RuntimeException{

    public CompanyPostNotFoundException(String message){

        super(message);
    }
}
