package com.shopping.controller;

import com.shopping.dto.ShoppingRequest;
import com.shopping.dto.ShoppingResponse;
import com.shopping.service.ShoppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/shopping")
public class ShoppingController {

    @Autowired
    private ShoppingService shoppingService;

    @PostMapping(value = "/insert")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> create(@RequestBody ShoppingRequest shopping) {

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

        ShoppingResponse shoppingResponse = shoppingService.view(shoppingId);

        return new ResponseEntity<>(shoppingResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/product")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<ShoppingResponse>> readByProductName(@RequestParam("productName") String productName) {

        List<ShoppingResponse> shoppingResponse = shoppingService.viewByProductName(productName);
        return new ResponseEntity<>(shoppingResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/custprod")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<ShoppingResponse>> readByCustomerNameOrProductName
            (@RequestParam("customerName") String customerName, @RequestParam("productName") String productName) {

        List<ShoppingResponse> shoppingResponses = shoppingService.viewByCustomerNameOrProductName(customerName, productName);
        return new ResponseEntity<>(shoppingResponses, HttpStatus.OK);
    }

    @GetMapping(value = "/getall")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<ShoppingResponse> readAll() {

        List<ShoppingResponse> shoppingResponseList = shoppingService.viewAll();
        ResponseEntity.ok();
        return shoppingResponseList;
    }

    @GetMapping(value = "/getFrom")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<ShoppingResponse> getDataFromAndTo(@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate) {

        List<ShoppingResponse> shoppingFromTo = shoppingService.getFrom(fromDate, toDate);
        ResponseEntity.ok();
        return shoppingFromTo;
    }

    @PutMapping(value = "/{shoppingId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> update(@PathVariable("shoppingId") String shoppingId, @RequestBody ShoppingRequest shoppingRequest) {

        ShoppingResponse shoppingResponse = shoppingService.change(shoppingId, shoppingRequest);

        return new ResponseEntity<>(shoppingResponse, HttpStatus.OK);
    }

    @PutMapping(value = "/queryupdate")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> updateWithQuery(@RequestParam("shoppingId") String shoppingId, @RequestBody ShoppingRequest shoppingRequest) {

        ShoppingResponse shoppingResponse = shoppingService.changeByQuery(shoppingId, shoppingRequest);

        return new ResponseEntity<>(shoppingResponse, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{shoppingId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> delete(@PathVariable("shoppingId") String shoppingId) {

        shoppingService.remove(shoppingId);
        return new ResponseEntity<>("Shopping item with given ID is deleted", HttpStatus.OK);
    }
}
