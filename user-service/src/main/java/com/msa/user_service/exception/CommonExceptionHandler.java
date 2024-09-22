package com.msa.user_service.exception;

import com.msa.user_service.dto.response.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler({
        RuntimeException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponse<?> handleCommonExceptions(Exception e) {
        return CommonResponse.badRequest(e.getMessage());
    }

}
