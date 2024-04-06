package com.shopping.exception;

import com.shopping.service.impl.ShoppingServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final static Logger logger = LogManager.getLogger(ShoppingServiceImpl.class);

    @ExceptionHandler(ShoppingCustomException.class)
    public ResponseEntity<Map<String, String>> handleCustomException(ShoppingCustomException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put(ex.getErrorCode(), ex.getMessage());
        logger.error(ex.getMessage(), ex);
        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

}
