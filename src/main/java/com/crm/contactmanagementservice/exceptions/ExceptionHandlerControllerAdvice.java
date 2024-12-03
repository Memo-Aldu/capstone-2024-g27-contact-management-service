package com.crm.contactmanagementservice.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * This class is a controller advice that handles exceptions globally across the whole application.
 * It has three exception handlers: one for validation exceptions, one for domain exceptions, and one for runtime exceptions.
 * Each exception handler logs the exception, creates an error response, and returns it with the appropriate HTTP status.
 *
 * @author : memo-aldu
 * @mailto : maldu064@uOttawa.ca
 * @created : 2/23/2024, Friday
 */
@ControllerAdvice @Slf4j @Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionHandlerControllerAdvice {

    /**
     * This method is an exception handler for validation exceptions.
     * It logs the exception, creates a map of field errors, and returns an error response with a 400 Bad Request status.
     * @param ex the validation exception
     * @param request the HTTP request
     * @return ResponseEntity<Map<String, String>> the error response
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex, final HttpServletRequest request) {
        log.info("Handling Validation Exception: {}", ex.getMessage());
        Map<String, Object> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        Map<String, String> response = new HashMap<>();
        response.put("message", "Validation error");
        response.put("errors", errors.toString());
        response.put("timestamp", String.valueOf(System.currentTimeMillis()));
        response.put("path", request.getRequestURI());
        response.put("status", String.valueOf(HttpStatus.BAD_REQUEST.value()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * This method is an exception handler for domain exceptions.
     * It logs the exception and returns an error response with the status of the exception.
     * @param ex the validation exception
     * @param request the HTTP request
     * @return ResponseEntity<Map<String, String>>the error response
     */
    @ExceptionHandler(AppEntityAlreadyExistException.class)
    public ResponseEntity<Map<String, String>> handleAppEntityAlreadyExistException(AppEntityAlreadyExistException ex, final HttpServletRequest request) {
        log.warn("Advice DomainException: {}", ex.getMessage());
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        response.put("timestamp", String.valueOf(System.currentTimeMillis()));
        response.put("path", request.getRequestURI());
        response.put("status", String.valueOf(HttpStatus.CONFLICT.value()));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }


    /**
     * This method is an exception handler for domain exceptions.
     * It logs the exception and returns an error response with the status of the exception.
     * @param ex the validation exception
     * @param request the HTTP request
     * @return ResponseEntity<ErrorResponse> the error response
     */
    @ExceptionHandler(AppEntityNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleAppEntityNotFoundException(AppEntityNotFoundException ex, final HttpServletRequest request) {
        log.warn("Advice DomainException: {}", ex.getMessage());
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        response.put("timestamp", String.valueOf(System.currentTimeMillis()));
        response.put("path", request.getRequestURI());
        response.put("status", String.valueOf(HttpStatus.NOT_FOUND.value()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /**
     * This method is an exception handler for runtime exceptions.
     * It logs the exception and returns an error response with a 500 Internal Server Error status.
     * @param ex the validation exception
     * @param request the HTTP request
     * @return ResponseEntity<Map<String, String>> the error response
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleException(Exception ex, final HttpServletRequest request) {
        log.warn("Advice Exception: {}", ex.getMessage());
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        response.put("timestamp", String.valueOf(System.currentTimeMillis()));
        response.put("path", request.getRequestURI());
        response.put("status", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
