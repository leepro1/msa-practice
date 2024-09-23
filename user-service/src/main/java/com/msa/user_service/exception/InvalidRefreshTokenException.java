package com.msa.user_service.exception;

public class InvalidRefreshTokenException extends RuntimeException {

    public InvalidRefreshTokenException(String msg) {
        super(msg);
    }
}