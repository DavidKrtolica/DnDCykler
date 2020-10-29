package com.example.demo.exception;

public class MethodNotAllowedException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public MethodNotAllowedException(String msg){
        super(msg);
    }
}
