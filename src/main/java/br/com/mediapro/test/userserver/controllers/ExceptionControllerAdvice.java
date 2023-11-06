package br.com.mediapro.test.userserver.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.mediapro.test.userserver.exceptions.UserNotFoundException;

@ControllerAdvice
public class ExceptionControllerAdvice 
  extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { UserNotFoundException.class })
    protected ResponseEntity<Object> handleUserNotFound(
      Exception e, WebRequest request) {
        return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}
