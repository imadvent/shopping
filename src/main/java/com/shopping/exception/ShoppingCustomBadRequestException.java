package com.shopping.exception;

import lombok.Getter;

@Getter
public class ShoppingCustomBadRequestException extends RuntimeException {

    private final String errorCode;

    public ShoppingCustomBadRequestException(String errorCode, String errorDescription) {
        super(errorDescription);
        this.errorCode = errorCode;
    }
}
