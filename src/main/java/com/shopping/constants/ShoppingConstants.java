package com.shopping.constants;

public enum ShoppingConstants {

    EMAIL_REGEX("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", ""),
    SHOPPING_ID_PREFIX("SHOPID", ""), ID_PREFIX("0", "00");

    private final String code;
    private final String description;

    ShoppingConstants(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
