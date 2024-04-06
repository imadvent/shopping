package com.shopping.exception;

import lombok.Getter;

@Getter
public class ShoppingCustomException extends RuntimeException {

    private final String errorCode;

    public ShoppingCustomException(String errorCode, String errorDescription) {
        super(errorDescription);
        this.errorCode = errorCode;
    }

}
