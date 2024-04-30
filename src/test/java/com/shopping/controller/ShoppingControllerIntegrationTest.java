//package com.shopping.controller;
//
//import com.shopping.entity.ShoppingEntity;
//import com.shopping.service.ShoppingService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
//
//import java.util.Collections;
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.when;
//
//@WebMvcTest(ShoppingController.class)
//public class ShoppingControllerIntegrationTest {
//
//    private static final String SHOPPING_ID = "SHOPID001";
//    private static final String PRODUCT_NAME = "Product 1";
//    private static final String CUSTOMER_EMAIL = "you@example.com";
//    private static final int SELLING_PRICE = 250;
//    private static final int BUYING_PRICE = 300;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private ShoppingService shoppingService;
//
//    @Test
//    public void testCreateShoppingItem_Success() throws Exception {
//        ShoppingEntity shoppingEntity = new ShoppingEntity();
//        shoppingEntity.setProductName(PRODUCT_NAME);
//        shoppingEntity.setCustomerEmail(CUSTOMER_EMAIL);
//        shoppingEntity.setBuyingPrice(BUYING_PRICE);
//        shoppingEntity.setSellingPrice(SELLING_PRICE);
//
//        when(shoppingService.insert(any(ShoppingEntity.class))).thenReturn(shoppingEntity);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/shopping")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(shoppingEntity)))
//                .andExpect(MockMvcResultMatchers.status().isCreated())
//                .andExpect(MockMvcResultMatchers.content().string("Shopping item saved successfully"));
//    }
//
//    @Test
//    public void testCreateShoppingItem_EmptyID() throws Exception {
//        ShoppingEntity shoppingEntity = new ShoppingEntity();
//        shoppingEntity.setShoppingId(SHOPPING_ID);
//        shoppingEntity.setProductName(PRODUCT_NAME);
//
//        when(shoppingService.insert(any(ShoppingEntity.class))).thenReturn(shoppingEntity);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/shopping")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(shoppingEntity)))
//                .andExpect(MockMvcResultMatchers.status().isBadRequest())
//                .andExpect(MockMvcResultMatchers.content().string("Should not enter Shopping ID"));
//    }
//
//    @Test
//    public void testCreateShoppingItem_InvalidEmail() throws Exception {
//        ShoppingEntity shoppingEntity = new ShoppingEntity();
//        shoppingEntity.setProductName(PRODUCT_NAME);
//        shoppingEntity.setCustomerEmail("@test.com");
//
//        when(shoppingService.insert(any(ShoppingEntity.class))).thenReturn(shoppingEntity);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/shopping")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(shoppingEntity)))
//                .andExpect(MockMvcResultMatchers.status().isBadRequest())
//                .andExpect(MockMvcResultMatchers.content().string("Invalid customer email"));
//    }
//
//    @Test
//    public void testCreateShoppingItem_InvalidProductName() throws Exception {
//        ShoppingEntity shoppingEntity = new ShoppingEntity();
//        shoppingEntity.setProductName(null);
//        shoppingEntity.setCustomerEmail(CUSTOMER_EMAIL);
//
//        when(shoppingService.insert(any(ShoppingEntity.class))).thenReturn(shoppingEntity);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/shopping")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(shoppingEntity)))
//                .andExpect(MockMvcResultMatchers.status().isBadRequest())
//                .andExpect(MockMvcResultMatchers.content().string("Product name is mandatory"));
//    }
//
//    @Test
//    public void testCreateShoppingItem_NegativePricing() throws Exception {
//        ShoppingEntity shoppingEntity = new ShoppingEntity();
//        shoppingEntity.setProductName(PRODUCT_NAME);
//        shoppingEntity.setCustomerEmail(CUSTOMER_EMAIL);
//        shoppingEntity.setSellingPrice(-50);
//        shoppingEntity.setBuyingPrice(0);
//
//        when(shoppingService.insert(any(ShoppingEntity.class))).thenReturn(shoppingEntity);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/shopping")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(shoppingEntity)))
//                .andExpect(MockMvcResultMatchers.status().isBadRequest())
//                .andExpect(MockMvcResultMatchers.content().string("Value should be non negative"));
//    }
//
//    @Test
//    public void testCreateShoppingItem_InsufficientBalance() throws Exception {
//        ShoppingEntity shoppingEntity = new ShoppingEntity();
//        shoppingEntity.setProductName(PRODUCT_NAME);
//        shoppingEntity.setCustomerEmail(CUSTOMER_EMAIL);
//        shoppingEntity.setSellingPrice(50);
//        shoppingEntity.setBuyingPrice(10);
//
//        when(shoppingService.insert(any(ShoppingEntity.class))).thenReturn(shoppingEntity);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/shopping")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(shoppingEntity)))
//                .andExpect(MockMvcResultMatchers.status().isBadRequest())
//                .andExpect(MockMvcResultMatchers.content().string("Insufficient balance"));
//    }
//
//    @Test
//    public void testReadShoppingItem() throws Exception {
//        ShoppingEntity shoppingEntity = createShoppingEntity();
//        when(shoppingService.view(SHOPPING_ID)).thenReturn(shoppingEntity);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/shopping/{SHOPPING_ID}", SHOPPING_ID)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(shoppingEntity)))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.shoppingId").value(SHOPPING_ID))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.productName").value(PRODUCT_NAME))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.customerEmail").value(CUSTOMER_EMAIL))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.sellingPrice").value(SELLING_PRICE))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.buyingPrice").value(BUYING_PRICE));
//    }
//
//    @Test
//    public void testReadAllShoppingItems() throws Exception {
//        List<ShoppingEntity> shoppingList = Collections.singletonList(createShoppingEntity());
//
//        when(shoppingService.viewAll()).thenReturn(shoppingList);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/shopping")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(shoppingList)))
//                .andExpect(MockMvcResultMatchers.status().isOk())
////                .andExpect(MockMvcResultMatchers.content()
////                        .json("[{\"shoppingId\":\"SHOPID001\",\"productName\":\"Product 1\",\"customerEmail\":\"you@example.com\",\"sellingPrice\":250,\"buyingPrice\":300}]"));
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].shoppingId").value(SHOPPING_ID))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].productName").value(PRODUCT_NAME))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].customerEmail").value(CUSTOMER_EMAIL))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].sellingPrice").value(SELLING_PRICE))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].buyingPrice").value(BUYING_PRICE));
//    }
//
//    @Test
//    public void testUpdateShoppingItem() throws Exception {
//        ShoppingEntity updatedEntity = createShoppingEntity();
//        updatedEntity.setProductName("Product 2");
//
//        when(shoppingService.change(eq(SHOPPING_ID), any(ShoppingEntity.class))).thenReturn(updatedEntity);
//        mockMvc.perform(MockMvcRequestBuilders.put("/shopping/{SHOPPING_ID}", SHOPPING_ID)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(updatedEntity)))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.shoppingId").value(SHOPPING_ID))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.productName").value("Product 2"));
//    }
//
//    @Test
//    public void testDeleteShoppingItem() throws Exception {
//
//        doNothing().when(shoppingService).remove(SHOPPING_ID);
//
//        mockMvc.perform(MockMvcRequestBuilders.delete("/shopping/{SHOPPING_ID}", SHOPPING_ID)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().string("Shopping item with given ID is deleted"));
//
//    }
//
//    private ShoppingEntity createShoppingEntity() {
//        ShoppingEntity shoppingEntity = new ShoppingEntity();
//        shoppingEntity.setShoppingId(SHOPPING_ID);
//        shoppingEntity.setProductName(PRODUCT_NAME);
//        shoppingEntity.setCustomerEmail(CUSTOMER_EMAIL);
//        shoppingEntity.setBuyingPrice(BUYING_PRICE);
//        shoppingEntity.setSellingPrice(SELLING_PRICE);
//        return shoppingEntity;
//    }
//
//    private String asJsonString(final Object obj) {
//        try {
//            return new ObjectMapper().writeValueAsString(obj);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//}
