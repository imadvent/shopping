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
    private String customerEmail;
    private int sellingPrice;
    private int buyingPrice;
    private String purchaseTime;
    private String purchaseModifyTime;
}
