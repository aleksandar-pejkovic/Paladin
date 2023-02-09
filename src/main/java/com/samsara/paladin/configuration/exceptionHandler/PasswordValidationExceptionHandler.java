package com.samsara.paladin.configuration.exceptionHandler;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.samsara.paladin.exceptions.passwordValidation.IllegalPasswordArgumentException;
import com.samsara.paladin.exceptions.passwordValidation.PasswordArgumentMissingException;
import com.samsara.paladin.exceptions.passwordValidation.ResetPasswordFailedException;

@RestControllerAdvice
public class PasswordValidationExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ResetPasswordFailedException.class)
    public Map<String, String> handleResetPasswordFailedException(ResetPasswordFailedException ex) {
        return ErrorResponse.getErrorResponse(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PasswordArgumentMissingException.class)
    public Map<String, String> handlePasswordArgumentMissingException(PasswordArgumentMissingException ex) {
        return ErrorResponse.getErrorResponse(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalPasswordArgumentException.class)
    public Map<String, String> handleIllegalPasswordArgumentException(IllegalPasswordArgumentException ex) {
        return ErrorResponse.getErrorResponse(ex.getMessage());
    }
}
