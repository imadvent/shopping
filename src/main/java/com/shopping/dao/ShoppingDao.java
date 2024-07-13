package com.shopping.dao;

import com.shopping.entity.ShoppingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ShoppingDao extends JpaRepository<ShoppingEntity, String> {

    @Query(value = "select t from ShoppingEntity t where t.productName = ?1")
    List<ShoppingEntity> findByProductName(String productName);

    @Query(value = "select s from ShoppingEntity s where s.customerName = :customerName or s.productName = :productName")
    List<ShoppingEntity> findbyCustomerNameOrProductName
            (@Param("customerName") String customerName, @Param("productName") String productName);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update ShoppingEntity e set e.productName = :productName, e.customerName = :customerName," +
            "e.customerEmail = :customerEmail, e.buyingPrice = :buyingPrice, e.sellingPrice = :sellingPrice," +
            "e.balanceAmount = :balanceAmount, e.purchaseModifyTime = :purchaseModifyTime, e.purchaseModifyDate = :purchaseModifyDate where e.shoppingId = :shoppingId")
    void updateWithQuery(@Param("shoppingId") String shoppingId, @Param("productName") String productName, @Param("customerName") String customerName, @Param("customerEmail") String customerEmail,
                         @Param("buyingPrice") int buyingPrice, @Param("sellingPrice") int sellingPrice, @Param("balanceAmount") int balanceAmount, @Param("purchaseModifyTime") String purchaseModifyTime,
                         @Param("purchaseModifyDate") String purchaseModifyDate);

    @Query(value = "select t from ShoppingEntity t where t.purchaseDate between :startDate and :endDate")
    List<ShoppingEntity> searchByDate(@Param("startDate") String startDate, @Param("endDate") String endDate);

}
