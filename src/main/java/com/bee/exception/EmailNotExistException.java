package com.bee.exception;

public class EmailNotExistException extends RuntimeException
{
    public  EmailNotExistException() {
        super("Email from user's input does not exist.");
    }
    public EmailNotExistException(String message){
        super(message);
    }
}
