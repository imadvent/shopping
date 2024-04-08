package com.shopping.controller;

import com.shopping.entity.ShoppingEntity;
import com.shopping.service.ShoppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.shopping.util.ShoppingUtil.isValidEmail;
import static com.shopping.util.ShoppingUtil.isValidShoppingId;

@RestController
public class ShoppingController {

    @Autowired
    private ShoppingService shoppingService;

    @PostMapping(value = "/shopping")
    public ResponseEntity<String> create(@RequestBody ShoppingEntity shopping) {

        if (shopping.getShoppingId() != null && !shopping.getShoppingId().isEmpty()) {
            return ResponseEntity.badRequest().body("Should not enter Shopping ID");
        }

        if (shopping.getProductName() == null || shopping.getProductName().isEmpty()) {
            return ResponseEntity.badRequest().body("Product name is mandatory");
        }

        if (!isValidEmail(shopping.getCustomerEmail())) {
            return ResponseEntity.badRequest().body("Invalid customer email");
        }

        if (shopping.getBuyingPrice() <= 0 || shopping.getSellingPrice() <= 0) {
            return ResponseEntity.badRequest().body("Value should be non negative");
        }

        if (shopping.getBuyingPrice() < shopping.getSellingPrice()) {
            return ResponseEntity.badRequest().body("Insufficient balance");
        }

        shoppingService.insert(shopping);
        return ResponseEntity.status(HttpStatus.CREATED).body("Shopping item saved successfully");
    }

    @GetMapping(value = "/shopping/{shoppingId}")
    public ResponseEntity<?> read(@PathVariable("shoppingId") String shoppingId) {

        if (!isValidShoppingId(shoppingId)) {
            return ResponseEntity.badRequest().body("Shopping item with ID " + shoppingId + " is invalid");
        }

        return new ResponseEntity<>(shoppingService.view(shoppingId), HttpStatus.OK);
    }

    @GetMapping(value = "/shopping")
    public List<ShoppingEntity> readAll() {
        ResponseEntity.ok();
        return shoppingService.viewAll();
    }

    @PutMapping(value = "/shopping/{shoppingId}")
    public ResponseEntity<?> update(@PathVariable("shoppingId") String shoppingId, @RequestBody ShoppingEntity shopping) {

        if (!isValidShoppingId(shoppingId)) {
            return ResponseEntity.badRequest().body("Shopping item with ID " + shoppingId + " is invalid");
        }

        return new ResponseEntity<>(shoppingService.change(shoppingId, shopping), HttpStatus.OK);
    }

    @DeleteMapping(value = "/shopping/{shoppingId}")
    public ResponseEntity<String> delete(@PathVariable("shoppingId") String shoppingId) {

        if (!isValidShoppingId(shoppingId)) {
            return ResponseEntity.badRequest().body("Shopping item with ID " + shoppingId + " is invalid");
        }

        shoppingService.remove(shoppingId);
        return new ResponseEntity<>("Shopping item with given ID is deleted", HttpStatus.OK);
    }
}
