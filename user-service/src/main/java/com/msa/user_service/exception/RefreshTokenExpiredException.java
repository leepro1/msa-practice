package com.msa.user_service.exception;

public class RefreshTokenExpiredException extends RuntimeException {

    public RefreshTokenExpiredException(String msg) {
        super(msg);
    }
}