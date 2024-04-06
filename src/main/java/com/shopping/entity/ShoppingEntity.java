package com.shopping.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "Product name is mandatory")
    private String productName;
    private String customerEmail;
    private int sellingPrice;
    private int buyingPrice;
    private String purchaseTime;
    private String purchaseModifyTime;
}
