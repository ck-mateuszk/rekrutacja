package com.lastminute.recruitment.rest;

import com.lastminute.recruitment.domain.error.ValidationException;
import com.lastminute.recruitment.domain.error.WikiPageNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class WikiScrapperResourceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = WikiPageNotFound.class)
    public ResponseEntity<Object> handleWikiPageNotFound() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ValidationException.class)
    public ResponseEntity<Object> handleValidationException() {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
