package com.shopping.service;

import com.shopping.dto.ShoppingRequest;
import com.shopping.dto.ShoppingResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ShoppingService {

    ShoppingResponse insert(ShoppingRequest shopping);

    ShoppingResponse view(String id);

    List<ShoppingResponse> viewByProductName(String productName);

    List<ShoppingResponse> viewByCustomerNameOrProductName(String customerName, String productName);

    @Transactional
    ShoppingResponse changeByQuery(String shoppingId, ShoppingRequest shopping);

    List<ShoppingResponse> viewAll();

    ShoppingResponse change(String id, ShoppingRequest entity);

    void remove(String id);

    String generateShoppingId();

}
