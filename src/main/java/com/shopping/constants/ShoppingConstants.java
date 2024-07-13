package com.shopping.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ShoppingConstants {

    EMAIL_REGEX("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", ""),
    SHOPPING_ID_PREFIX("SHOPID", ""), ID_PREFIX("0", "00"),
    DATE_REGEX("^([0-2][0-9]|3[0-1])-(0[1-9]|1[0-2])-(\\d{4})$", ""),
    SHOPPING_ID_REGEX("SHOPID\\d{3}", "");

    private final String code;
    private final String description;

}