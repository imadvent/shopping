package com.shopping.controller;

import com.shopping.dto.ShoppingRequest;
import com.shopping.dto.ShoppingResponse;
import com.shopping.service.ShoppingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ShoppingControllerTests {

    private static final String SHOPPING_ID = "SHOPID001";
    private static final String PRODUCT_NAME = "Product 1";
    private static final String CUSTOMER_EMAIL = "you@example.com";
    private static final int SELLING_PRICE = 250;
    private static final int BUYING_PRICE = 300;

    @Mock
    private ShoppingService shoppingService;

    @InjectMocks
    private ShoppingController shoppingController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateShoppingItem_Success() {
        ShoppingRequest shoppingEntity = new ShoppingRequest();
        shoppingEntity.setProductName(PRODUCT_NAME);
        shoppingEntity.setCustomerEmail(CUSTOMER_EMAIL);
        shoppingEntity.setBuyingPrice(BUYING_PRICE);
        shoppingEntity.setSellingPrice(SELLING_PRICE);

        ResponseEntity<String> response = shoppingController.create(shoppingEntity);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Shopping item saved successfully", response.getBody());
    }

//    @Test
//    public void testCreateShoppingItem_EmptyID() {
//        ShoppingRequest shoppingEntity = new ShoppingRequest();
//        shoppingEntity.setShoppingId(SHOPPING_ID);
//        shoppingEntity.setProductName(PRODUCT_NAME);
//
//        ResponseEntity<String> response = shoppingController.create(shoppingEntity);
//
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        assertEquals("Should not enter Shopping ID", response.getBody());
//    }

//    @Test
//    public void testCreateShoppingItem_InvalidEmail() {
//        ShoppingEntity shoppingEntity = new ShoppingEntity();
//        shoppingEntity.setProductName(PRODUCT_NAME);
//        shoppingEntity.setCustomerEmail("@test.com");
//
//        ResponseEntity<String> response = shoppingController.create(shoppingEntity);
//
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        assertEquals("Invalid customer email", response.getBody());
//    }

//    @Test
//    public void testCreateShoppingItem_InvalidProductName() {
//        ShoppingEntity shoppingEntity = new ShoppingEntity();
//        shoppingEntity.setProductName("");
//        shoppingEntity.setCustomerEmail(CUSTOMER_EMAIL);
//
//        ResponseEntity<String> response = shoppingController.create(shoppingEntity);
//
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        assertEquals("Product name is mandatory", response.getBody());
//    }

//    @Test
//    public void testCreateShoppingItem_NegativePricing() {
//        ShoppingEntity shoppingEntity = new ShoppingEntity();
//        shoppingEntity.setProductName(PRODUCT_NAME);
//        shoppingEntity.setCustomerEmail(CUSTOMER_EMAIL);
//        shoppingEntity.setSellingPrice(-50);
//        shoppingEntity.setBuyingPrice(0);
//
//        ResponseEntity<String> response = shoppingController.create(shoppingEntity);
//
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        assertEquals("Value should be non negative", response.getBody());
//    }

//    @Test
//    public void testCreateShoppingItem_InsufficientBalance() {
//        ShoppingEntity shoppingEntity = new ShoppingEntity();
//        shoppingEntity.setProductName(PRODUCT_NAME);
//        shoppingEntity.setCustomerEmail(CUSTOMER_EMAIL);
//        shoppingEntity.setSellingPrice(50);
//        shoppingEntity.setBuyingPrice(10);
//
//        ResponseEntity<String> response = shoppingController.create(shoppingEntity);
//
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        assertEquals("Insufficient balance", response.getBody());
//    }

    @Test
    public void testReadShoppingItem() {
        ShoppingResponse shoppingEntity = new ShoppingResponse();
        shoppingEntity.setShoppingId(SHOPPING_ID);

        when(shoppingService.view(SHOPPING_ID)).thenReturn(shoppingEntity);

        ResponseEntity<?> response = shoppingController.read(SHOPPING_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(shoppingEntity, response.getBody());
    }

    @Test
    public void testReadByProductName() {

        List<ShoppingResponse> shoppingEntity = Collections.singletonList(new ShoppingResponse());

        when(shoppingService.viewByProductName(PRODUCT_NAME)).thenReturn(shoppingEntity);

        ResponseEntity<List<ShoppingResponse>> response = shoppingController.readByProductName(PRODUCT_NAME);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(shoppingEntity, response.getBody());
    }

    @Test
    public void testReadByCustomerNameOrProductName() {

        ShoppingResponse shopping = createShoppingEntity();

        List<ShoppingResponse> shoppingEntity = Collections.singletonList(shopping);

        when(shoppingService.viewByCustomerNameOrProductName(shopping.getCustomerName(), PRODUCT_NAME)).thenReturn(shoppingEntity);

        ResponseEntity<List<ShoppingResponse>> response = shoppingController.readByCustomerNameOrProductName(shopping.getCustomerName(), PRODUCT_NAME);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(shoppingEntity, response.getBody());
    }

    @Test
    public void testReadAllShoppingItems() {
        List<ShoppingResponse> shoppingList = Collections.singletonList(new ShoppingResponse());

        when(shoppingService.viewAll()).thenReturn(shoppingList);

        List<ShoppingResponse> response = shoppingController.readAll();

        assertEquals(shoppingList, response);
    }

    @Test
    public void testUpdateShoppingItem() {
        ShoppingResponse updatedShoppingEntity = createShoppingEntity();
        updatedShoppingEntity.setProductName(PRODUCT_NAME);

        when(shoppingService.change(eq(SHOPPING_ID), any(ShoppingRequest.class))).thenReturn(updatedShoppingEntity);

        ResponseEntity<?> response = shoppingController.update(SHOPPING_ID, new ShoppingRequest());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedShoppingEntity, response.getBody());
    }

    @Test
    public void testUpdateWithQuery() {
        ShoppingResponse updatedShoppingEntity = createShoppingEntity();
        updatedShoppingEntity.setProductName(PRODUCT_NAME);

        when(shoppingService.change(eq(SHOPPING_ID), any(ShoppingRequest.class))).thenReturn(updatedShoppingEntity);

        ResponseEntity<?> response = shoppingController.update(SHOPPING_ID, new ShoppingRequest());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedShoppingEntity, response.getBody());
    }

    @Test
    public void testDeleteShoppingItem() {
        ResponseEntity<String> response = shoppingController.delete(SHOPPING_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Shopping item with given ID is deleted", response.getBody());
    }

    private ShoppingResponse createShoppingEntity() {
        ShoppingResponse shoppingEntity = new ShoppingResponse();
        shoppingEntity.setShoppingId(SHOPPING_ID);
        shoppingEntity.setProductName(PRODUCT_NAME);
        shoppingEntity.setCustomerEmail(CUSTOMER_EMAIL);
        shoppingEntity.setBuyingPrice(BUYING_PRICE);
        shoppingEntity.setSellingPrice(SELLING_PRICE);
        return shoppingEntity;
    }
}