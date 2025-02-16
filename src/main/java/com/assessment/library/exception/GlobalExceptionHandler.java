package com.assessment.library.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.assessment.library.dto.ErrorResponse;

import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(
            new ErrorResponse(HttpStatus.BAD_REQUEST,"Validation Error",errors),
             HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRunTimeException(RuntimeException ex) {

        return new ResponseEntity<>(
            new ErrorResponse(ex.getMessage(),HttpStatus.NOT_FOUND),
             HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleUniqueConstraintViolation(DataIntegrityViolationException ex) {

        return new ResponseEntity<>(
            new ErrorResponse("Duplicate entry exists.",HttpStatus.CONFLICT),
             HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleUniqueConstraintViolation(AccessDeniedException ex) {

        return new ResponseEntity<>(
            new ErrorResponse("UnAuthorized.",HttpStatus.UNAUTHORIZED),
             HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {

        return new ResponseEntity<>(
            new ErrorResponse(ex.getMessage(),HttpStatus.BAD_REQUEST),
             HttpStatus.BAD_REQUEST);  
    }
}
