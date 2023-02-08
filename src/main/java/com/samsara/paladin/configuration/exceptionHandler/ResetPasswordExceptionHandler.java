package com.samsara.paladin.configuration.exceptionHandler;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.samsara.paladin.exceptions.user.ResetPasswordFailedException;

@RestControllerAdvice
public class ResetPasswordExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ResetPasswordFailedException.class)
    public Map<String, String> handleResetPasswordFailedException(ResetPasswordFailedException ex) {
        return ErrorResponse.getErrorResponse(ex.getMessage());
    }
}
