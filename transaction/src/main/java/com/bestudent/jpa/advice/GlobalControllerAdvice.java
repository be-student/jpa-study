package com.bestudent.jpa.advice;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler
    private String handleException(Exception e) {
        System.out.println(e.getMessage());
        return e.getMessage();
    }
}
