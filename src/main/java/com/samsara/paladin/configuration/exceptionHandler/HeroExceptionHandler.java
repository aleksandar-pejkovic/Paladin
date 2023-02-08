package com.samsara.paladin.configuration.exceptionHandler;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.samsara.paladin.exceptions.hero.HeroExistsException;
import com.samsara.paladin.exceptions.hero.HeroNotFoundException;
import com.samsara.paladin.exceptions.hero.HeroTypeNotFoundException;

@RestControllerAdvice
public class HeroExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HeroExistsException.class)
    public Map<String, String> handleHeroExistsException(HeroExistsException ex) {
        return ErrorResponse.getErrorResponse(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(HeroNotFoundException.class)
    public Map<String, String> handleHeroNotFoundException(HeroNotFoundException ex) {
        return ErrorResponse.getErrorResponse(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(HeroTypeNotFoundException.class)
    public Map<String, String> handleHeroTypeNotFoundException(HeroTypeNotFoundException ex) {
        return ErrorResponse.getErrorResponse(ex.getMessage());
    }
}
