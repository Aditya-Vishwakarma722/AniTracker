package com.codersHub.AniTracker.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.converter.HttpMessageNotReadableException;
import tools.jackson.databind.exc.InvalidFormatException;


import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {



    // Validation errors: Missing names, missing enum fields, or ratings outside the allowed range.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException exception){
        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(error.getField(), error.getDefaultMessage())
                );

        ErrorResponse response = new ErrorResponse(
                400,
                "Validation failed",
                errors
        );

        return ResponseEntity.badRequest().body(response);
    }



    // Resource not found: Requests for media IDs that don't exist return 404 Not Found.
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
            ResourceNotFoundException exception) {

        ErrorResponse response = new ErrorResponse(
                404,
                exception.getMessage(),
                null
        );

        return ResponseEntity.status(404).body(response);
    }




    // Invalid request values: Invalid enum values such as ANIMEE or WATCHING return 400 Bad Request with the offending field and value.
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRequestBody(
            HttpMessageNotReadableException exception) {

        Map<String, String> errors = new HashMap<>();

        Throwable cause = exception.getCause();

        while (cause != null && !(cause instanceof InvalidFormatException)) {
            cause = cause.getCause();
        }

        if (cause instanceof InvalidFormatException invalidFormatException) {

            String fieldName = invalidFormatException.getPath()
                    .get(invalidFormatException.getPath().size() - 1)
                    .getPropertyName();

            Object invalidValue = invalidFormatException.getValue();

            errors.put(
                    fieldName,
                    "Invalid value '" + invalidValue
                            + "'. Please provide a valid " + fieldName + "."
            );

        } else {
            errors.put(
                    "body",
                    "Invalid request body. Check the values and JSON format."
            );
        }

        ErrorResponse response = new ErrorResponse(
                400,
                "Invalid request value",
                errors
        );

        return ResponseEntity.badRequest().body(response);
    }


}
