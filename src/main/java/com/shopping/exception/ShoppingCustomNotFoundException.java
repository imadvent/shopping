package com.shopping.exception;

import lombok.Getter;

@Getter
public class ShoppingCustomNotFoundException extends RuntimeException {

    private final String errorCode;

    public ShoppingCustomNotFoundException(String errorCode, String errorDescription) {
        super(errorDescription);
        this.errorCode = errorCode;
    }
}
