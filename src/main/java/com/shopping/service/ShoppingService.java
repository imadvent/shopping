package com.shopping.service;

import com.shopping.entity.ShoppingEntity;

import java.util.List;

public interface ShoppingService {

    ShoppingEntity insert(ShoppingEntity shopping);

    ShoppingEntity view(String id);

    List<ShoppingEntity> viewAll();

    ShoppingEntity change(String id, ShoppingEntity entity);

    void remove(String id);

    String generateShoppingId();

}
