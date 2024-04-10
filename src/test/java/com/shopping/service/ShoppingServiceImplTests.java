package com.shopping.service;

import com.shopping.dao.ShoppingDao;
import com.shopping.entity.ShoppingEntity;
import com.shopping.exception.ShoppingCustomNotFoundException;
import com.shopping.service.impl.ShoppingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.shopping.util.ShoppingUtil.isValidEmail;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ShoppingServiceImplTests {

    private static final String SHOPPING_ID = "SHOPID001";
    private static final String PRODUCT_NAME = "Product 1";
    private static final String CUSTOMER_EMAIL = "you@example.com";
    private static final int SELLING_PRICE = 250;
    private static final int BUYING_PRICE = 300;
    @Mock
    private ShoppingDao shoppingDao;
    @InjectMocks
    private ShoppingServiceImpl shoppingServiceImpl;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testIsValidEmail_ValidEmail() {
        assertTrue(isValidEmail(CUSTOMER_EMAIL));
    }

    @Test
    public void testIsValidEmail_InvalidEmail() {
        assertFalse(isValidEmail("test.example.com"));
    }

    @Test
    public void testInsert_ShoppingItemInserted() {
        ShoppingEntity shoppingEntity = new ShoppingEntity();
        shoppingEntity.setProductName(PRODUCT_NAME);
        shoppingEntity.setCustomerEmail(CUSTOMER_EMAIL);
        shoppingEntity.setBuyingPrice(BUYING_PRICE);
        shoppingEntity.setSellingPrice(SELLING_PRICE);

        when(shoppingDao.findById(anyString())).thenReturn(Optional.empty());
        when(shoppingDao.save(any(ShoppingEntity.class))).thenReturn(shoppingEntity);

        ShoppingEntity result = shoppingServiceImpl.insert(shoppingEntity);

        assertNotNull(result);
        assertEquals("Product 1", result.getProductName());
        assertEquals("you@example.com", result.getCustomerEmail());
        assertEquals(250, result.getSellingPrice());
        assertEquals(300, result.getBuyingPrice());
    }

    @Test
    public void testInsert_DuplicateShoppingItemId() {
        ShoppingEntity shoppingEntity = new ShoppingEntity();
        shoppingEntity.setShoppingId(SHOPPING_ID);

        when(shoppingDao.findById(SHOPPING_ID)).thenReturn(Optional.of(shoppingEntity));

        assertThrows(ShoppingCustomNotFoundException.class, () -> shoppingServiceImpl.insert(shoppingEntity));
    }

    @Test
    public void testView_ShoppingItemFound() {
        ShoppingEntity shoppingEntity = new ShoppingEntity();
        shoppingEntity.setShoppingId(SHOPPING_ID);

        when(shoppingDao.findById(SHOPPING_ID)).thenReturn(Optional.of(shoppingEntity));

        ShoppingEntity result = shoppingServiceImpl.view(SHOPPING_ID);

        assertNotNull(result);
        assertEquals("SHOPID001", result.getShoppingId());
    }

    @Test
    public void testView_ShoppingItemNotFound() {
        when(shoppingDao.findById(SHOPPING_ID)).thenReturn(Optional.empty());

        assertThrows(ShoppingCustomNotFoundException.class, () -> shoppingServiceImpl.view(SHOPPING_ID));
    }

    @Test
    public void testViewAll_ShoppingItemsFound() {
        List<ShoppingEntity> shoppingList = new ArrayList<>();
        shoppingList.add(new ShoppingEntity());
        shoppingList.add(new ShoppingEntity());

        when(shoppingDao.findAll()).thenReturn(shoppingList);

        List<ShoppingEntity> result = shoppingServiceImpl.viewAll();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testViewAll_NoShoppingItemsFound() {
        when(shoppingDao.findAll()).thenReturn(new ArrayList<>());

        assertThrows(ShoppingCustomNotFoundException.class, () -> shoppingServiceImpl.viewAll());
    }

    @Test
    public void testChange_ShoppingItemChanged() {
        ShoppingEntity existingEntity = new ShoppingEntity();
        existingEntity.setShoppingId(SHOPPING_ID);
        existingEntity.setBuyingPrice(BUYING_PRICE);

        ShoppingEntity updatedEntity = new ShoppingEntity();
        updatedEntity.setBuyingPrice(500);
        when(shoppingDao.findById(SHOPPING_ID)).thenReturn(Optional.of(existingEntity));
        when(shoppingDao.save(any(ShoppingEntity.class))).thenReturn(updatedEntity);

        ShoppingEntity result = shoppingServiceImpl.change(SHOPPING_ID, updatedEntity);

        assertNotNull(result);
        assertEquals(500, result.getBuyingPrice());
    }

    @Test
    public void testChange_ShoppingItemNotFound() {
        when(shoppingDao.findById(SHOPPING_ID)).thenReturn(Optional.empty());

        assertThrows(ShoppingCustomNotFoundException.class, () -> shoppingServiceImpl.change(SHOPPING_ID, new ShoppingEntity()));
    }

    @Test
    public void testRemove_ShoppingItemRemoved() {

        when(shoppingDao.existsById(SHOPPING_ID)).thenReturn(true);

        shoppingServiceImpl.remove(SHOPPING_ID);

        verify(shoppingDao, times(1)).deleteById(SHOPPING_ID);
    }

    @Test
    public void testRemove_ShoppingItemNotFound() {
        String shoppingId = SHOPPING_ID;

        when(shoppingDao.existsById(shoppingId)).thenReturn(false);

        assertThrows(ShoppingCustomNotFoundException.class, () -> shoppingServiceImpl.remove(shoppingId));
    }
}

