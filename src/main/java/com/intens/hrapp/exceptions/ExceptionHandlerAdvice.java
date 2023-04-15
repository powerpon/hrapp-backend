package com.intens.hrapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    private record ErroMessage(int statusCode, Instant timestamp, String message) {
    }

    @ExceptionHandler(CandidateNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErroMessage handleCandidateNotFoundException(CandidateNotFoundException exception){
        return new ErroMessage(
                HttpStatus.NOT_FOUND.value(),
                Instant.now(),
                exception.getMessage()
        );
    }

    @ExceptionHandler(SkillNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErroMessage handleSkillNotFoundException(SkillNotFoundException exception){
        return new ErroMessage(
                HttpStatus.NOT_FOUND.value(),
                Instant.now(),
                exception.getMessage()
        );
    }

}
