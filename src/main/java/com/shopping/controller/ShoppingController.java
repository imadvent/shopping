package com.shopping.controller;

import com.shopping.entity.ShoppingEntity;
import com.shopping.service.ShoppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.shopping.util.ShoppingUtil.isValidEmail;

@RestController
@RequestMapping(value = "/shopping")
public class ShoppingController {

    @Autowired
    private ShoppingService shoppingService;

    @PostMapping(value = "/insert")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
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

    @GetMapping(value = "/check")
    public String unsafeRequest() {
        return "this request is not protected";
    }

    @GetMapping(value = "/{shoppingId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> read(@PathVariable("shoppingId") String shoppingId) {

        return new ResponseEntity<>(shoppingService.view(shoppingId), HttpStatus.OK);
    }

    @GetMapping(value = "/product")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<ShoppingEntity>> readByProductName(@RequestParam("productName") String productName) {
        return new ResponseEntity<>(shoppingService.viewByProductName(productName), HttpStatus.OK);
    }

    @GetMapping(value = "/custprod")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<ShoppingEntity>> readByCustomerNameOrProductName
            (@RequestParam("customerName") String customerName, @RequestParam("productName") String productName) {
        return new ResponseEntity<>(shoppingService.viewByCustomerNameOrProductName(customerName, productName), HttpStatus.OK);
    }

    @GetMapping(value = "/getall")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<ShoppingEntity> readAll() {
        ResponseEntity.ok();
        return shoppingService.viewAll();
    }

    @PutMapping(value = "/{shoppingId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> update(@PathVariable("shoppingId") String shoppingId, @RequestBody ShoppingEntity shopping) {

        return new ResponseEntity<>(shoppingService.change(shoppingId, shopping), HttpStatus.OK);
    }

    @PutMapping(value = "/queryupdate")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> updateWithQuery(@RequestParam("shoppingId") String shoppingId, @RequestBody ShoppingEntity shopping) {

        return new ResponseEntity<>(shoppingService.changeByQuery(shoppingId, shopping), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{shoppingId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> delete(@PathVariable("shoppingId") String shoppingId) {

        shoppingService.remove(shoppingId);
        return new ResponseEntity<>("Shopping item with given ID is deleted", HttpStatus.OK);
    }
}
