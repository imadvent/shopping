package com.shopping.dao;

import com.shopping.entity.ShoppingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingDao extends JpaRepository<ShoppingEntity, String> {
}
