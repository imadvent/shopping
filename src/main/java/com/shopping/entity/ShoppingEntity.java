package com.shopping.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ShoppingEntity {

    @Id
    private String shoppingId;
    private String productName;
    private String customerName;
    private String customerEmail;
    private int sellingPrice;
    private int buyingPrice;
    private int balanceAmount;
    private String purchaseTime;
    private String purchaseDate;
    private String purchaseModifyTime;
    private String purchaseModifyDate;
}
