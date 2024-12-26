package com.simsimbookstore.frontserver.config.exception;

public class KeyMangerException extends RuntimeException{
    public KeyMangerException(String message, Exception e) {
        super(message);
    }
}
