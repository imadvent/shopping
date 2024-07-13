package com.shopping.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShoppingRequest {

    private String productName;
    private String customerEmail;
    private int sellingPrice;
    private int buyingPrice;
}
