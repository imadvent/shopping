package com.shopping.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ShoppingEntity {

    @Id
    private String shoppingId;
    private String productName;
    private String customerEmail;
    private int sellingPrice;
    private int buyingPrice;
    private int balanceAmount;
    private String purchaseTime;
    private String purchaseModifyTime;
}
