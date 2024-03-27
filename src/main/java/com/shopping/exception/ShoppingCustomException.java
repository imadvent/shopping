package com.shopping.exception;

public class ShoppingCustomException extends RuntimeException {

	private String errorCode;

	public ShoppingCustomException(String message) {
		super(message);
	}

	public ShoppingCustomException(String message, Throwable cause) {
		super(message, cause);
	}

	public ShoppingCustomException(String errorCode, String errorDescription) {
		super(errorDescription);
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}
}
