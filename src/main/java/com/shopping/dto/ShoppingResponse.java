package com.shopping.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShoppingResponse {

    private String shoppingId;
    private String productName;
    private String customerName;
    private String customerEmail;
    private int sellingPrice;
    private int buyingPrice;
    private int balanceAmount;
    private String purchaseTime;
    private String purchaseModifyTime;
}
