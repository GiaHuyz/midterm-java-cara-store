package com.ecommerce.cara.exception;

import com.ecommerce.cara.payload.ResponseData;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleInvalidInput(HttpMessageNotReadableException e) {
        ResponseData responseData = new ResponseData();
        responseData.setSuccess(false);
        responseData.setDescription("Invalid request body format.");
        responseData.setData(e.getMessage());
        return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> handleNullInput(NullPointerException e) {
        ResponseData responseData = new ResponseData();
        responseData.setSuccess(false);
        responseData.setDescription("Null Pointer Exception.");
        responseData.setData(e.getMessage());
        return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleRequestRejected(EntityNotFoundException e) {
        ResponseData responseData = new ResponseData();
        responseData.setSuccess(false);
        responseData.setDescription("Entity Not Found Exception.");
        responseData.setData(e.getMessage());
        return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        ResponseData responseData = new ResponseData();
        responseData.setSuccess(false);
        responseData.setDescription("Validation error");
        return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
    }
}
