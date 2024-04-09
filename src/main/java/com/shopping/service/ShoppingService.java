package com.shopping.service;

import com.shopping.entity.ShoppingEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ShoppingService {

    ShoppingEntity insert(ShoppingEntity shopping);

    ShoppingEntity view(String id);

    List<ShoppingEntity> viewByProductName(String productName);

    List<ShoppingEntity> viewByCustomerNameOrProductName(String customerName, String productName);

    @Transactional
    ShoppingEntity changeByQuery(String shoppingId, ShoppingEntity shopping);

    List<ShoppingEntity> viewAll();

    ShoppingEntity change(String id, ShoppingEntity entity);

    void remove(String id);

    String generateShoppingId();

}
