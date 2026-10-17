package com.savedjob.exception;

public class SavedJobNotFoundException extends RuntimeException{

    public SavedJobNotFoundException(String message){

        super(message);
    }
}
