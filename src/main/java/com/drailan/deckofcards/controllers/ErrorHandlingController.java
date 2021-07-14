package com.drailan.deckofcards.controllers;

import com.drailan.deckofcards.exceptions.EntityNotFoundException;
import com.drailan.deckofcards.models.errors.RestApiError;
import com.drailan.deckofcards.models.errors.RestApiErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;

@ControllerAdvice
public class ErrorHandlingController {
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    RestApiErrors onNotfoundException(EntityNotFoundException e) {
        var items = new ArrayList<RestApiError>();
        var error = new RestApiError(e.getClass().getName(), null, e.getMessage());
        items.add(error);

        return new RestApiErrors(items);
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    RestApiErrors onIllegalStateException(IllegalStateException e) {
        var items = new ArrayList<RestApiError>();
        var error = new RestApiError(e.getClass().getName(), null, e.getMessage());
        items.add(error);

        return new RestApiErrors(items);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    RestApiErrors onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        var items = new ArrayList<RestApiError>();
        var error = new RestApiError(e.getClass().getName(), null, e.getMessage());
        items.add(error);

        return new RestApiErrors(items);
    }
}
