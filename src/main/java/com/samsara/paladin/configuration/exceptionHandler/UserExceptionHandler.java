package com.samsara.paladin.configuration.exceptionHandler;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.samsara.paladin.exceptions.user.EmailExistsException;
import com.samsara.paladin.exceptions.user.EmailNotFoundException;
import com.samsara.paladin.exceptions.user.UserNotFoundException;
import com.samsara.paladin.exceptions.user.UsernameExistsException;
import com.samsara.paladin.exceptions.user.UsernameNotFoundException;

@RestControllerAdvice
public class UserExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UsernameExistsException.class)
    public Map<String, String> handleUsernameExistsException(UsernameExistsException ex) {
        return ErrorResponse.getErrorResponse(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UsernameNotFoundException.class)
    public Map<String, String> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        return ErrorResponse.getErrorResponse(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public Map<String, String> handleUserNotFoundException(UserNotFoundException ex) {
        return ErrorResponse.getErrorResponse(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmailExistsException.class)
    public Map<String, String> handleEmailExistsException(EmailExistsException ex) {
        return ErrorResponse.getErrorResponse(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EmailNotFoundException.class)
    public Map<String, String> handleEmailNotFoundException(EmailNotFoundException ex) {
        return ErrorResponse.getErrorResponse(ex.getMessage());
    }
}
