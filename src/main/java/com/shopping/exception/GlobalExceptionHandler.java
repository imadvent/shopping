package com.shopping.exception;

import java.util.HashMap;
import java.util.Map;

import com.shopping.service.ShoppingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for procedures.
 * 
 * @author adhoke
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	private final static Logger logger = LogManager.getLogger(ShoppingService.class);

	@ExceptionHandler(ShoppingCustomException.class)
	public ResponseEntity<Map<String, String>> handleCustomException(ShoppingCustomException ex) {
		Map<String, String> errors = new HashMap<>();
		errors.put(ex.getErrorCode(), ex.getMessage());
		logger.error(ex.getMessage(), ex);
		return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, String>> handleException(Exception ex) {
		Map<String, String> errors = new HashMap<>();
		errors.put(HttpStatus.INTERNAL_SERVER_ERROR.toString(), ex.getMessage());
		logger.error(ex.getMessage(), ex);
		return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		logger.error(ex.getMessage(), ex);
		return ResponseEntity.badRequest().body(errors);
	}

}
