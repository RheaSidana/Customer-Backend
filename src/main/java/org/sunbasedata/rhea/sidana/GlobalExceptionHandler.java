package org.sunbasedata.rhea.sidana;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.sunbasedata.rhea.sidana.customer.view.model.response.Error;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<Object> handleMissingHeaderException(MissingRequestHeaderException ex) {
        // Handle the MissingRequestHeaderException here
        Error error = new Error("Failure", ex.getMessage());

        return ResponseEntity.status(
                HttpStatus.BAD_REQUEST
        ).body(
                error
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        String message = ex.getMessage().substring(ex.getMessage().indexOf("field"));
        message = message.substring(0,message.indexOf(";"));
        Error error = new Error("Failure", message);

        return ResponseEntity.status(
                HttpStatus.BAD_REQUEST
        ).body(
                error
        );
    }
}
