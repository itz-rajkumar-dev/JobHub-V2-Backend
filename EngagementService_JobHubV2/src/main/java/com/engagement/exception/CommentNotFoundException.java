package com.engagement.exception;

public class CommentNotFoundException extends  RuntimeException{

    public CommentNotFoundException(String messaage){

        super(messaage);
    }
}
