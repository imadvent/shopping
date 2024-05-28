package com.shopping.service.impl;

import com.shopping.dao.ShoppingDao;
import com.shopping.entity.ShoppingEntity;
import com.shopping.exception.ShoppingCustomBadRequestException;
import com.shopping.exception.ShoppingCustomNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ShoppingServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ShoppingServiceImplDiffblueTest {
    @MockBean
    private ShoppingDao shoppingDao;

    @Autowired
    private ShoppingServiceImpl shoppingServiceImpl;

    /**
     * Method under test: {@link ShoppingServiceImpl#generateShoppingId()}
     */
    @Test
    void testGenerateShoppingId() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Diffblue AI was unable to find a test

        // Arrange
        when(shoppingDao.findAll()).thenReturn(new ArrayList<>());

        // Act
        shoppingServiceImpl.generateShoppingId();
    }

    /**
     * Method under test: {@link ShoppingServiceImpl#generateShoppingId()}
     */
    @Test
//    @Disabled("TODO: Complete this test")
    void testGenerateShoppingId2() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: Method may be time-sensitive.
        //   Diffblue Cover was only able to write tests that are time-sensitive.
        //   The assertions don't pass when run at an alternate date, time, and
        //   timezone. Try refactoring the method to take a java.time.Clock instance so
        //   that the time can be parameterized during testing.
        //   See Working with code R031 (https://diff.blue/R031) for details.

        // Arrange
        when(shoppingDao.findAll()).thenThrow(new ShoppingCustomBadRequestException("An error occurred", ""));

        // Act
        shoppingServiceImpl.generateShoppingId();
    }

    /**
     * Method under test: {@link ShoppingServiceImpl#insert(ShoppingEntity)}
     */
    @Test
    void testInsert() {
        // Arrange
        ShoppingEntity shoppingEntity = new ShoppingEntity();
        shoppingEntity.setBalanceAmount(1);
        shoppingEntity.setBuyingPrice(1);
        shoppingEntity.setCustomerEmail("jane.doe@example.org");
        shoppingEntity.setCustomerName("Customer Name");
        shoppingEntity.setProductName("Product Name");
        shoppingEntity.setPurchaseModifyTime("Purchase Modify Time");
        shoppingEntity.setPurchaseTime("Purchase Time");
        shoppingEntity.setSellingPrice(1);
//        shoppingEntity.setShoppingId("42");
        Optional<ShoppingEntity> ofResult = Optional.of(shoppingEntity);
        when(shoppingDao.findById("SHOPID001")).thenReturn(ofResult);

//        ShoppingEntity shopping = new ShoppingEntity();
//        shopping.setBalanceAmount(1);
//        shopping.setBuyingPrice(1);
//        shopping.setCustomerEmail("jane.doe@example.org");
//        shopping.setCustomerName("Customer Name");
//        shopping.setProductName("Product Name");
//        shopping.setPurchaseModifyTime("Purchase Modify Time");
//        shopping.setPurchaseTime("Purchase Time");
//        shopping.setSellingPrice(1);
//        shopping.setShoppingId("42");

        // Act and Assert
        assertThrows(ShoppingCustomBadRequestException.class, () -> shoppingServiceImpl.insert(shoppingEntity));
//        verify(shoppingDao).findById(eq("SHOPID506"));
    }

    /**
     * Method under test: {@link ShoppingServiceImpl#insert(ShoppingEntity)}
     */
    @Test
    void testInsert2() {
        // Arrange
        ShoppingEntity shoppingEntity = new ShoppingEntity();
        shoppingEntity.setBalanceAmount(1);
        shoppingEntity.setBuyingPrice(1);
        shoppingEntity.setCustomerEmail("jane.doe@example.org");
        shoppingEntity.setCustomerName("Customer Name");
        shoppingEntity.setProductName("Product Name");
        shoppingEntity.setPurchaseModifyTime("Purchase Modify Time");
        shoppingEntity.setPurchaseTime("Purchase Time");
        shoppingEntity.setSellingPrice(1);
        shoppingEntity.setShoppingId("SHOPID001");
        when(shoppingDao.save(any(ShoppingEntity.class))).thenReturn(shoppingEntity);
//        Optional<ShoppingEntity> emptyResult = Optional.empty();
        when(shoppingDao.findById("SHOPID001")).thenReturn(Optional.empty());

//        ShoppingEntity shopping = new ShoppingEntity();
//        shopping.setBalanceAmount(1);
//        shopping.setBuyingPrice(1);
//        shopping.setCustomerEmail("jane.doe@example.org");
//        shopping.setCustomerName("Customer Name");
//        shopping.setProductName("Product Name");
//        shopping.setPurchaseModifyTime("Purchase Modify Time");
//        shopping.setPurchaseTime("Purchase Time");
//        shopping.setSellingPrice(1);
//        shopping.setShoppingId("42");

        // Act
        ShoppingEntity actualInsertResult = shoppingServiceImpl.insert(shoppingEntity);

        // Assert
//        verify(shoppingDao).findById(eq("SHOPID001"));
        verify(shoppingDao).save(isA(ShoppingEntity.class));
        assertEquals("-", shoppingEntity.getPurchaseModifyTime());
        assertEquals("jane.doe", shoppingEntity.getCustomerName());
        assertEquals(0, shoppingEntity.getBalanceAmount());
        assertSame(shoppingEntity, actualInsertResult);
    }

    /**
     * Method under test: {@link ShoppingServiceImpl#view(String)}
     */
    @Test
    void testView() {
        // Arrange
        ShoppingEntity shoppingEntity = new ShoppingEntity();
        shoppingEntity.setBalanceAmount(1);
        shoppingEntity.setBuyingPrice(1);
        shoppingEntity.setCustomerEmail("jane.doe@example.org");
        shoppingEntity.setCustomerName("Customer Name");
        shoppingEntity.setProductName("Product Name");
        shoppingEntity.setPurchaseModifyTime("Purchase Modify Time");
        shoppingEntity.setPurchaseTime("Purchase Time");
        shoppingEntity.setSellingPrice(1);
        shoppingEntity.setShoppingId("42");
        Optional<ShoppingEntity> ofResult = Optional.of(shoppingEntity);
        when(shoppingDao.findById(Mockito.any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(ShoppingCustomBadRequestException.class, () -> shoppingServiceImpl.view("42"));
        verify(shoppingDao).findById(eq("42"));
    }

    /**
     * Method under test: {@link ShoppingServiceImpl#view(String)}
     */
    @Test
    void testView2() {
        // Arrange
        ShoppingEntity shoppingEntity = new ShoppingEntity();
        shoppingEntity.setBalanceAmount(1);
        shoppingEntity.setBuyingPrice(1);
        shoppingEntity.setCustomerEmail("jane.doe@example.org");
        shoppingEntity.setCustomerName("Customer Name");
        shoppingEntity.setProductName("Product Name");
        shoppingEntity.setPurchaseModifyTime("Purchase Modify Time");
        shoppingEntity.setPurchaseTime("Purchase Time");
        shoppingEntity.setSellingPrice(1);
        shoppingEntity.setShoppingId("42");
        Optional<ShoppingEntity> ofResult = Optional.of(shoppingEntity);
        when(shoppingDao.findById(Mockito.any())).thenReturn(ofResult);

        // Act
        ShoppingEntity actualViewResult = shoppingServiceImpl.view("SHOPID999");

        // Assert
        verify(shoppingDao).findById(eq("SHOPID999"));
        assertSame(shoppingEntity, actualViewResult);
    }

    /**
     * Method under test: {@link ShoppingServiceImpl#view(String)}
     */
    @Test
    void testView3() {
        // Arrange
        when(shoppingDao.findById(Mockito.any()))
                .thenThrow(new ShoppingCustomBadRequestException("An error occurred", "An error occurred"));

        // Act and Assert
        assertThrows(ShoppingCustomBadRequestException.class, () -> shoppingServiceImpl.view("42"));
        verify(shoppingDao).findById(eq("42"));
    }

    /**
     * Method under test: {@link ShoppingServiceImpl#view(String)}
     */
    @Test
    void testView4() {
        // Arrange
        Optional<ShoppingEntity> emptyResult = Optional.empty();
        when(shoppingDao.findById(Mockito.any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(ShoppingCustomNotFoundException.class, () -> shoppingServiceImpl.view("SHOPID999"));
        verify(shoppingDao).findById(eq("SHOPID999"));
    }

    /**
     * Method under test: {@link ShoppingServiceImpl#viewByProductName(String)}
     */
    @Test
    void testViewByProductName() {
        // Arrange
        when(shoppingDao.findByProductName(Mockito.any())).thenReturn(new ArrayList<>());

        // Act and Assert
        assertThrows(ShoppingCustomNotFoundException.class, () -> shoppingServiceImpl.viewByProductName("Product Name"));
        verify(shoppingDao).findByProductName(eq("Product Name"));
    }

    /**
     * Method under test: {@link ShoppingServiceImpl#viewByProductName(String)}
     */
    @Test
    void testViewByProductName2() {
        // Arrange
        ShoppingEntity shoppingEntity = new ShoppingEntity();
        shoppingEntity.setBalanceAmount(1);
        shoppingEntity.setBuyingPrice(1);
        shoppingEntity.setCustomerEmail("jane.doe@example.org");
        shoppingEntity.setCustomerName("NO_PRODUCT_NAME_EXIST");
        shoppingEntity.setProductName("NO_PRODUCT_NAME_EXIST");
        shoppingEntity.setPurchaseModifyTime("NO_PRODUCT_NAME_EXIST");
        shoppingEntity.setPurchaseTime("NO_PRODUCT_NAME_EXIST");
        shoppingEntity.setSellingPrice(1);
        shoppingEntity.setShoppingId("42");

        ArrayList<ShoppingEntity> shoppingEntityList = new ArrayList<>();
        shoppingEntityList.add(shoppingEntity);
        when(shoppingDao.findByProductName(Mockito.any())).thenReturn(shoppingEntityList);

        // Act
        List<ShoppingEntity> actualViewByProductNameResult = shoppingServiceImpl.viewByProductName("Product Name");

        // Assert
        verify(shoppingDao).findByProductName(eq("Product Name"));
        assertEquals(1, actualViewByProductNameResult.size());
        assertSame(shoppingEntityList, actualViewByProductNameResult);
    }

    /**
     * Method under test: {@link ShoppingServiceImpl#viewByProductName(String)}
     */
    @Test
    void testViewByProductName3() {
        // Arrange, Act and Assert
        assertThrows(ShoppingCustomBadRequestException.class, () -> shoppingServiceImpl.viewByProductName(null));
    }

    /**
     * Method under test: {@link ShoppingServiceImpl#viewByProductName(String)}
     */
    @Test
    void testViewByProductName4() {
        // Arrange, Act and Assert
        assertThrows(ShoppingCustomBadRequestException.class, () -> shoppingServiceImpl.viewByProductName(""));
    }

    /**
     * Method under test: {@link ShoppingServiceImpl#viewByProductName(String)}
     */
    @Test
    void testViewByProductName5() {
        // Arrange
        when(shoppingDao.findByProductName(Mockito.any()))
                .thenThrow(new ShoppingCustomBadRequestException("An error occurred", "An error occurred"));

        // Act and Assert
        assertThrows(ShoppingCustomBadRequestException.class, () -> shoppingServiceImpl.viewByProductName("Product Name"));
        verify(shoppingDao).findByProductName(eq("Product Name"));
    }

    /**
     * Method under test:
     * {@link ShoppingServiceImpl#viewByCustomerNameOrProductName(String, String)}
     */
    @Test
    void testViewByCustomerNameOrProductName() {
        // Arrange
        when(shoppingDao.findbyCustomerNameOrProductName(Mockito.any(), Mockito.any()))
                .thenReturn(new ArrayList<>());

        // Act and Assert
        assertThrows(ShoppingCustomNotFoundException.class,
                () -> shoppingServiceImpl.viewByCustomerNameOrProductName("Customer Name", "Product Name"));
        verify(shoppingDao).findbyCustomerNameOrProductName(eq("Customer Name"), eq("Product Name"));
    }

    /**
     * Method under test:
     * {@link ShoppingServiceImpl#viewByCustomerNameOrProductName(String, String)}
     */
    @Test
    void testViewByCustomerNameOrProductName2() {
        // Arrange
        ShoppingEntity shoppingEntity = new ShoppingEntity();
        shoppingEntity.setBalanceAmount(1);
        shoppingEntity.setBuyingPrice(1);
        shoppingEntity.setCustomerEmail("jane.doe@example.org");
        shoppingEntity.setCustomerName("NO_CUSTOMER_NAME_OR_PRODUCT_NAME_EXIST");
        shoppingEntity.setProductName("NO_CUSTOMER_NAME_OR_PRODUCT_NAME_EXIST");
        shoppingEntity.setPurchaseModifyTime("NO_CUSTOMER_NAME_OR_PRODUCT_NAME_EXIST");
        shoppingEntity.setPurchaseTime("NO_CUSTOMER_NAME_OR_PRODUCT_NAME_EXIST");
        shoppingEntity.setSellingPrice(1);
        shoppingEntity.setShoppingId("42");

        ArrayList<ShoppingEntity> shoppingEntityList = new ArrayList<>();
        shoppingEntityList.add(shoppingEntity);
        when(shoppingDao.findbyCustomerNameOrProductName(Mockito.any(), Mockito.any()))
                .thenReturn(shoppingEntityList);

        // Act
        List<ShoppingEntity> actualViewByCustomerNameOrProductNameResult = shoppingServiceImpl
                .viewByCustomerNameOrProductName("Customer Name", "Product Name");

        // Assert
        verify(shoppingDao).findbyCustomerNameOrProductName(eq("Customer Name"), eq("Product Name"));
        assertEquals(1, actualViewByCustomerNameOrProductNameResult.size());
        assertSame(shoppingEntityList, actualViewByCustomerNameOrProductNameResult);
    }

    /**
     * Method under test:
     * {@link ShoppingServiceImpl#viewByCustomerNameOrProductName(String, String)}
     */
    @Test
    void testViewByCustomerNameOrProductName3() {
        // Arrange
        when(shoppingDao.findbyCustomerNameOrProductName(Mockito.any(), Mockito.any()))
                .thenReturn(new ArrayList<>());

        // Act and Assert
        assertThrows(ShoppingCustomNotFoundException.class,
                () -> shoppingServiceImpl.viewByCustomerNameOrProductName("", "Product Name"));
        verify(shoppingDao).findbyCustomerNameOrProductName(eq(""), eq("Product Name"));
    }

    /**
     * Method under test:
     * {@link ShoppingServiceImpl#viewByCustomerNameOrProductName(String, String)}
     */
    @Test
    void testViewByCustomerNameOrProductName4() {
        // Arrange
        when(shoppingDao.findbyCustomerNameOrProductName(Mockito.any(), Mockito.any()))
                .thenThrow(new ShoppingCustomBadRequestException("An error occurred", "An error occurred"));

        // Act and Assert
        assertThrows(ShoppingCustomBadRequestException.class,
                () -> shoppingServiceImpl.viewByCustomerNameOrProductName("Customer Name", "Product Name"));
        verify(shoppingDao).findbyCustomerNameOrProductName(eq("Customer Name"), eq("Product Name"));
    }

    /**
     * Method under test:
     * {@link ShoppingServiceImpl#viewByCustomerNameOrProductName(String, String)}
     */
    @Test
    void testViewByCustomerNameOrProductName5() {
        // Arrange, Act and Assert
        assertThrows(ShoppingCustomBadRequestException.class,
                () -> shoppingServiceImpl.viewByCustomerNameOrProductName("", ""));
    }

    /**
     * Method under test: {@link ShoppingServiceImpl#viewAll()}
     */
    @Test
    void testViewAll() {
        // Arrange
        when(shoppingDao.findAll()).thenReturn(new ArrayList<>());

        // Act and Assert
        assertThrows(ShoppingCustomNotFoundException.class, () -> shoppingServiceImpl.viewAll());
        verify(shoppingDao).findAll();
    }

    /**
     * Method under test: {@link ShoppingServiceImpl#viewAll()}
     */
    @Test
    void testViewAll2() {
        // Arrange
        ShoppingEntity shoppingEntity = new ShoppingEntity();
        shoppingEntity.setBalanceAmount(1);
        shoppingEntity.setBuyingPrice(1);
        shoppingEntity.setCustomerEmail("jane.doe@example.org");
        shoppingEntity.setCustomerName("NO_SHOPPING_RECORDS");
        shoppingEntity.setProductName("NO_SHOPPING_RECORDS");
        shoppingEntity.setPurchaseModifyTime("NO_SHOPPING_RECORDS");
        shoppingEntity.setPurchaseTime("NO_SHOPPING_RECORDS");
        shoppingEntity.setSellingPrice(1);
        shoppingEntity.setShoppingId("42");

        ArrayList<ShoppingEntity> shoppingEntityList = new ArrayList<>();
        shoppingEntityList.add(shoppingEntity);
        when(shoppingDao.findAll()).thenReturn(shoppingEntityList);

        // Act
        List<ShoppingEntity> actualViewAllResult = shoppingServiceImpl.viewAll();

        // Assert
        verify(shoppingDao).findAll();
        assertEquals(1, actualViewAllResult.size());
        assertSame(shoppingEntityList, actualViewAllResult);
    }

    /**
     * Method under test: {@link ShoppingServiceImpl#viewAll()}
     */
    @Test
    void testViewAll3() {
        // Arrange
        when(shoppingDao.findAll())
                .thenThrow(new ShoppingCustomBadRequestException("An error occurred", "An error occurred"));

        // Act and Assert
        assertThrows(ShoppingCustomBadRequestException.class, () -> shoppingServiceImpl.viewAll());
        verify(shoppingDao).findAll();
    }

    /**
     * Method under test: {@link ShoppingServiceImpl#change(String, ShoppingEntity)}
     */
    @Test
    void testChange() {
        // Arrange
        ShoppingEntity shopping = new ShoppingEntity();
        shopping.setBalanceAmount(1);
        shopping.setBuyingPrice(1);
        shopping.setCustomerEmail("jane.doe@example.org");
        shopping.setCustomerName("Customer Name");
        shopping.setProductName("Product Name");
        shopping.setPurchaseModifyTime("Purchase Modify Time");
        shopping.setPurchaseTime("Purchase Time");
        shopping.setSellingPrice(1);
        shopping.setShoppingId("42");

        // Act and Assert
        assertThrows(ShoppingCustomBadRequestException.class, () -> shoppingServiceImpl.change("42", shopping));
    }

    /**
     * Method under test: {@link ShoppingServiceImpl#change(String, ShoppingEntity)}
     */
    @Test
    void testChange2() {
        // Arrange
        ShoppingEntity shoppingEntity = new ShoppingEntity();
        shoppingEntity.setBalanceAmount(1);
        shoppingEntity.setBuyingPrice(1);
        shoppingEntity.setCustomerEmail("jane.doe@example.org");
        shoppingEntity.setCustomerName("Customer Name");
        shoppingEntity.setProductName("Product Name");
        shoppingEntity.setPurchaseModifyTime("Purchase Modify Time");
        shoppingEntity.setPurchaseTime("Purchase Time");
        shoppingEntity.setSellingPrice(1);
        shoppingEntity.setShoppingId("42");
        Optional<ShoppingEntity> ofResult = Optional.of(shoppingEntity);

        ShoppingEntity shoppingEntity2 = new ShoppingEntity();
        shoppingEntity2.setBalanceAmount(1);
        shoppingEntity2.setBuyingPrice(1);
        shoppingEntity2.setCustomerEmail("jane.doe@example.org");
        shoppingEntity2.setCustomerName("Customer Name");
        shoppingEntity2.setProductName("Product Name");
        shoppingEntity2.setPurchaseModifyTime("Purchase Modify Time");
        shoppingEntity2.setPurchaseTime("Purchase Time");
        shoppingEntity2.setSellingPrice(1);
        shoppingEntity2.setShoppingId("42");
        when(shoppingDao.save(Mockito.any())).thenReturn(shoppingEntity2);
        when(shoppingDao.findById(Mockito.any())).thenReturn(ofResult);

        ShoppingEntity shopping = new ShoppingEntity();
        shopping.setBalanceAmount(1);
        shopping.setBuyingPrice(1);
        shopping.setCustomerEmail("jane.doe@example.org");
        shopping.setCustomerName("Customer Name");
        shopping.setProductName("Product Name");
        shopping.setPurchaseModifyTime("Purchase Modify Time");
        shopping.setPurchaseTime("Purchase Time");
        shopping.setSellingPrice(1);
        shopping.setShoppingId("42");

        // Act
        ShoppingEntity actualChangeResult = shoppingServiceImpl.change("SHOPID999", shopping);

        // Assert
        verify(shoppingDao).findById(eq("SHOPID999"));
        verify(shoppingDao).save(isA(ShoppingEntity.class));
        assertEquals("Product Name", actualChangeResult.getProductName());
        assertEquals("jane.doe", actualChangeResult.getCustomerName());
        assertEquals("jane.doe@example.org", actualChangeResult.getCustomerEmail());
        assertEquals(0, actualChangeResult.getBalanceAmount());
        assertEquals(1, actualChangeResult.getBuyingPrice());
        assertEquals(1, actualChangeResult.getSellingPrice());
        assertSame(shoppingEntity, actualChangeResult);
    }

    /**
     * Method under test: {@link ShoppingServiceImpl#change(String, ShoppingEntity)}
     */
    @Test
    void testChange3() {
        // Arrange
        ShoppingEntity shoppingEntity = new ShoppingEntity();
        shoppingEntity.setBalanceAmount(1);
        shoppingEntity.setBuyingPrice(1);
        shoppingEntity.setCustomerEmail("jane.doe@example.org");
        shoppingEntity.setCustomerName("Customer Name");
        shoppingEntity.setProductName("Product Name");
        shoppingEntity.setPurchaseModifyTime("Purchase Modify Time");
        shoppingEntity.setPurchaseTime("Purchase Time");
        shoppingEntity.setSellingPrice(1);
        shoppingEntity.setShoppingId("42");
        Optional<ShoppingEntity> ofResult = Optional.of(shoppingEntity);
        when(shoppingDao.save(Mockito.any()))
                .thenThrow(new ShoppingCustomBadRequestException("An error occurred", "An error occurred"));
        when(shoppingDao.findById(Mockito.any())).thenReturn(ofResult);

        ShoppingEntity shopping = new ShoppingEntity();
        shopping.setBalanceAmount(1);
        shopping.setBuyingPrice(1);
        shopping.setCustomerEmail("jane.doe@example.org");
        shopping.setCustomerName("Customer Name");
        shopping.setProductName("Product Name");
        shopping.setPurchaseModifyTime("Purchase Modify Time");
        shopping.setPurchaseTime("Purchase Time");
        shopping.setSellingPrice(1);
        shopping.setShoppingId("42");

        // Act and Assert
        assertThrows(ShoppingCustomBadRequestException.class, () -> shoppingServiceImpl.change("SHOPID999", shopping));
        verify(shoppingDao).findById(eq("SHOPID999"));
        verify(shoppingDao).save(isA(ShoppingEntity.class));
    }

    /**
     * Method under test: {@link ShoppingServiceImpl#change(String, ShoppingEntity)}
     */
    @Test
    void testChange4() {
        // Arrange
        ShoppingEntity shoppingEntity = mock(ShoppingEntity.class);
        when(shoppingEntity.getBalanceAmount())
                .thenThrow(new ShoppingCustomNotFoundException("An error occurred", "An error occurred"));
        when(shoppingEntity.getCustomerEmail()).thenReturn("jane.doe@example.org");
        doNothing().when(shoppingEntity).setBalanceAmount(anyInt());
        doNothing().when(shoppingEntity).setBuyingPrice(anyInt());
        doNothing().when(shoppingEntity).setCustomerEmail(Mockito.any());
        doNothing().when(shoppingEntity).setCustomerName(Mockito.any());
        doNothing().when(shoppingEntity).setProductName(Mockito.any());
        doNothing().when(shoppingEntity).setPurchaseModifyTime(Mockito.any());
        doNothing().when(shoppingEntity).setPurchaseTime(Mockito.any());
        doNothing().when(shoppingEntity).setSellingPrice(anyInt());
        doNothing().when(shoppingEntity).setShoppingId(Mockito.any());
        shoppingEntity.setBalanceAmount(1);
        shoppingEntity.setBuyingPrice(1);
        shoppingEntity.setCustomerEmail("jane.doe@example.org");
        shoppingEntity.setCustomerName("Customer Name");
        shoppingEntity.setProductName("Product Name");
        shoppingEntity.setPurchaseModifyTime("Purchase Modify Time");
        shoppingEntity.setPurchaseTime("Purchase Time");
        shoppingEntity.setSellingPrice(1);
        shoppingEntity.setShoppingId("42");
        Optional<ShoppingEntity> ofResult = Optional.of(shoppingEntity);
        when(shoppingDao.findById(Mockito.any())).thenReturn(ofResult);

        ShoppingEntity shopping = new ShoppingEntity();
        shopping.setBalanceAmount(1);
        shopping.setBuyingPrice(1);
        shopping.setCustomerEmail("jane.doe@example.org");
        shopping.setCustomerName("Customer Name");
        shopping.setProductName("Product Name");
        shopping.setPurchaseModifyTime("Purchase Modify Time");
        shopping.setPurchaseTime("Purchase Time");
        shopping.setSellingPrice(1);
        shopping.setShoppingId("42");

        // Act and Assert
        assertThrows(ShoppingCustomNotFoundException.class, () -> shoppingServiceImpl.change("SHOPID999", shopping));
        verify(shoppingEntity).getBalanceAmount();
        verify(shoppingEntity).getCustomerEmail();
        verify(shoppingEntity, atLeast(1)).setBalanceAmount(anyInt());
        verify(shoppingEntity, atLeast(1)).setBuyingPrice(eq(1));
        verify(shoppingEntity, atLeast(1)).setCustomerEmail(eq("jane.doe@example.org"));
        verify(shoppingEntity, atLeast(1)).setCustomerName(Mockito.any());
        verify(shoppingEntity, atLeast(1)).setProductName(eq("Product Name"));
        verify(shoppingEntity, atLeast(1)).setPurchaseModifyTime(Mockito.any());
        verify(shoppingEntity).setPurchaseTime(eq("Purchase Time"));
        verify(shoppingEntity, atLeast(1)).setSellingPrice(eq(1));
        verify(shoppingEntity).setShoppingId(eq("42"));
        verify(shoppingDao).findById(eq("SHOPID999"));
    }

    /**
     * Method under test: {@link ShoppingServiceImpl#change(String, ShoppingEntity)}
     */
    @Test
    void testChange5() {
        // Arrange
        ShoppingEntity shoppingEntity = mock(ShoppingEntity.class);
        when(shoppingEntity.getBalanceAmount())
                .thenThrow(new ShoppingCustomNotFoundException("An error occurred", "An error occurred"));
        when(shoppingEntity.getCustomerEmail()).thenReturn("SHOPID999");
        doNothing().when(shoppingEntity).setBalanceAmount(anyInt());
        doNothing().when(shoppingEntity).setBuyingPrice(anyInt());
        doNothing().when(shoppingEntity).setCustomerEmail(Mockito.any());
        doNothing().when(shoppingEntity).setCustomerName(Mockito.any());
        doNothing().when(shoppingEntity).setProductName(Mockito.any());
        doNothing().when(shoppingEntity).setPurchaseModifyTime(Mockito.any());
        doNothing().when(shoppingEntity).setPurchaseTime(Mockito.any());
        doNothing().when(shoppingEntity).setSellingPrice(anyInt());
        doNothing().when(shoppingEntity).setShoppingId(Mockito.any());
        shoppingEntity.setBalanceAmount(1);
        shoppingEntity.setBuyingPrice(1);
        shoppingEntity.setCustomerEmail("jane.doe@example.org");
        shoppingEntity.setCustomerName("Customer Name");
        shoppingEntity.setProductName("Product Name");
        shoppingEntity.setPurchaseModifyTime("Purchase Modify Time");
        shoppingEntity.setPurchaseTime("Purchase Time");
        shoppingEntity.setSellingPrice(1);
        shoppingEntity.setShoppingId("42");
        Optional<ShoppingEntity> ofResult = Optional.of(shoppingEntity);
        when(shoppingDao.findById(Mockito.any())).thenReturn(ofResult);

        ShoppingEntity shopping = new ShoppingEntity();
        shopping.setBalanceAmount(1);
        shopping.setBuyingPrice(1);
        shopping.setCustomerEmail("jane.doe@example.org");
        shopping.setCustomerName("Customer Name");
        shopping.setProductName("Product Name");
        shopping.setPurchaseModifyTime("Purchase Modify Time");
        shopping.setPurchaseTime("Purchase Time");
        shopping.setSellingPrice(1);
        shopping.setShoppingId("42");

        // Act and Assert
        assertThrows(ShoppingCustomNotFoundException.class, () -> shoppingServiceImpl.change("SHOPID999", shopping));
        verify(shoppingEntity).getBalanceAmount();
        verify(shoppingEntity).getCustomerEmail();
        verify(shoppingEntity, atLeast(1)).setBalanceAmount(anyInt());
        verify(shoppingEntity, atLeast(1)).setBuyingPrice(eq(1));
        verify(shoppingEntity, atLeast(1)).setCustomerEmail(eq("jane.doe@example.org"));
        verify(shoppingEntity, atLeast(1)).setCustomerName(Mockito.any());
        verify(shoppingEntity, atLeast(1)).setProductName(eq("Product Name"));
        verify(shoppingEntity, atLeast(1)).setPurchaseModifyTime(Mockito.any());
        verify(shoppingEntity).setPurchaseTime(eq("Purchase Time"));
        verify(shoppingEntity, atLeast(1)).setSellingPrice(eq(1));
        verify(shoppingEntity).setShoppingId(eq("42"));
        verify(shoppingDao).findById(eq("SHOPID999"));
    }

    /**
     * Method under test: {@link ShoppingServiceImpl#change(String, ShoppingEntity)}
     */
    @Test
    void testChange6() {
        // Arrange
        Optional<ShoppingEntity> emptyResult = Optional.empty();
        when(shoppingDao.findById(Mockito.any())).thenReturn(emptyResult);
        new ShoppingCustomNotFoundException("An error occurred", "An error occurred");

        new ShoppingCustomNotFoundException("An error occurred", "An error occurred");

        new ShoppingCustomNotFoundException("An error occurred", "An error occurred");

        ShoppingEntity shopping = new ShoppingEntity();
        shopping.setBalanceAmount(1);
        shopping.setBuyingPrice(1);
        shopping.setCustomerEmail("jane.doe@example.org");
        shopping.setCustomerName("Customer Name");
        shopping.setProductName("Product Name");
        shopping.setPurchaseModifyTime("Purchase Modify Time");
        shopping.setPurchaseTime("Purchase Time");
        shopping.setSellingPrice(1);
        shopping.setShoppingId("42");

        // Act and Assert
        assertThrows(ShoppingCustomNotFoundException.class, () -> shoppingServiceImpl.change("SHOPID999", shopping));
        verify(shoppingDao).findById(eq("SHOPID999"));
    }

    /**
     * Method under test:
     * {@link ShoppingServiceImpl#changeByQuery(String, ShoppingEntity)}
     */
    @Test
    void testChangeByQuery() {
        // Arrange
        ShoppingEntity shopping = new ShoppingEntity();
        shopping.setBalanceAmount(1);
        shopping.setBuyingPrice(1);
        shopping.setCustomerEmail("jane.doe@example.org");
        shopping.setCustomerName("Customer Name");
        shopping.setProductName("Product Name");
        shopping.setPurchaseModifyTime("Purchase Modify Time");
        shopping.setPurchaseTime("Purchase Time");
        shopping.setSellingPrice(1);
        shopping.setShoppingId("42");

        // Act and Assert
        assertThrows(ShoppingCustomBadRequestException.class, () -> shoppingServiceImpl.changeByQuery("42", shopping));
    }

    /**
     * Method under test:
     * {@link ShoppingServiceImpl#changeByQuery(String, ShoppingEntity)}
     */
    @Test
    void testChangeByQuery2() {
        // Arrange
        ShoppingEntity shoppingEntity = new ShoppingEntity();
        shoppingEntity.setBalanceAmount(1);
        shoppingEntity.setBuyingPrice(1);
        shoppingEntity.setCustomerEmail("jane.doe@example.org");
        shoppingEntity.setCustomerName("jane.doe");
        shoppingEntity.setProductName("Product Name");
        shoppingEntity.setPurchaseModifyTime("1970-01-01T00:00:00");
        shoppingEntity.setPurchaseTime("Purchase Time");
        shoppingEntity.setSellingPrice(1);
        shoppingEntity.setShoppingId("SHOPID999");
        Optional<ShoppingEntity> ofResult = Optional.of(shoppingEntity);
//        doNothing().when(shoppingDao)
//                .updateWithQuery("SHOPID999", Mockito.any(), Mockito.any(), Mockito.any(),
//                        anyInt(), anyInt(), anyInt(), "1970-01-01T00:00:00");
        when(shoppingDao.findById(Mockito.any())).thenReturn(ofResult);

        ShoppingEntity shopping = new ShoppingEntity();
        shopping.setBalanceAmount(1);
        shopping.setBuyingPrice(1);
        shopping.setCustomerEmail("jane.doe@example.org");
        shopping.setCustomerName("jane.doe");
        shopping.setProductName("Product Name");
        shopping.setPurchaseModifyTime("date");
        shopping.setPurchaseTime("Purchase Time");
        shopping.setSellingPrice(1);
        shopping.setShoppingId("SHOPID999");

        // Act
        ShoppingEntity actualChangeByQueryResult = shoppingServiceImpl.changeByQuery("SHOPID999", shopping);

        // Assert
//        verify(shoppingDao).updateWithQuery(eq("SHOPID999"), eq("Product Name"), eq("jane.doe"), eq("jane.doe@example.org"),
//                eq(1), eq(1), eq(0), eq("date"));
        verify(shoppingDao, atLeast(1)).findById(eq("SHOPID999"));
        assertEquals("jane.doe", shopping.getCustomerName());
        assertEquals(0, shopping.getBalanceAmount());
        assertSame(shoppingEntity, actualChangeByQueryResult);
    }

    /**
     * Method under test:
     * {@link ShoppingServiceImpl#changeByQuery(String, ShoppingEntity)}
     */
    @Test
    void testChangeByQuery3() {
        // Arrange
        ShoppingEntity shoppingEntity = new ShoppingEntity();
        shoppingEntity.setBalanceAmount(1);
        shoppingEntity.setBuyingPrice(1);
        shoppingEntity.setCustomerEmail("jane.doe@example.org");
        shoppingEntity.setCustomerName("Customer Name");
        shoppingEntity.setProductName("Product Name");
        shoppingEntity.setPurchaseModifyTime("Purchase Modify Time");
        shoppingEntity.setPurchaseTime("Purchase Time");
        shoppingEntity.setSellingPrice(1);
        shoppingEntity.setShoppingId("42");
        Optional<ShoppingEntity> ofResult = Optional.of(shoppingEntity);
        doThrow(new ShoppingCustomBadRequestException("An error occurred", "An error occurred")).when(shoppingDao)
                .updateWithQuery(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
                        anyInt(), anyInt(), anyInt(), Mockito.any());
        when(shoppingDao.findById(Mockito.any())).thenReturn(ofResult);

        ShoppingEntity shopping = new ShoppingEntity();
        shopping.setBalanceAmount(1);
        shopping.setBuyingPrice(1);
        shopping.setCustomerEmail("jane.doe@example.org");
        shopping.setCustomerName("Customer Name");
        shopping.setProductName("Product Name");
        shopping.setPurchaseModifyTime("Purchase Modify Time");
        shopping.setPurchaseTime("Purchase Time");
        shopping.setSellingPrice(1);
        shopping.setShoppingId("SHOPID999");

        // Act and Assert
        assertThrows(ShoppingCustomBadRequestException.class,
                () -> shoppingServiceImpl.changeByQuery("SHOPID999", shopping));
//        verify(shoppingDao).updateWithQuery(eq("SHOPID999"), eq("Product Name"), eq("jane.doe"), eq("jane.doe@example.org"),
//                eq(1), eq(1), eq(0), eq("1970-01-01T00:00:00"));
//        verify(shoppingDao).findById(eq("SHOPID999"));
    }

    /**
     * Method under test:
     * {@link ShoppingServiceImpl#changeByQuery(String, ShoppingEntity)}
     */
    @Test
    void testChangeByQuery4() {
        // Arrange
        Optional<ShoppingEntity> emptyResult = Optional.empty();
        when(shoppingDao.findById(Mockito.any())).thenReturn(emptyResult);

        ShoppingEntity shopping = new ShoppingEntity();
        shopping.setBalanceAmount(1);
        shopping.setBuyingPrice(1);
        shopping.setCustomerEmail("jane.doe@example.org");
        shopping.setCustomerName("Customer Name");
        shopping.setProductName("Product Name");
        shopping.setPurchaseModifyTime("Purchase Modify Time");
        shopping.setPurchaseTime("Purchase Time");
        shopping.setSellingPrice(1);
        shopping.setShoppingId("42");

        // Act and Assert
        assertThrows(ShoppingCustomNotFoundException.class, () -> shoppingServiceImpl.changeByQuery("SHOPID999", shopping));
        verify(shoppingDao).findById(eq("SHOPID999"));
    }

    /**
     * Method under test:
     * {@link ShoppingServiceImpl#changeByQuery(String, ShoppingEntity)}
     */
    @Test
    void testChangeByQuery5() {
        // Arrange
        ShoppingEntity shopping = new ShoppingEntity();
        shopping.setBalanceAmount(1);
        shopping.setBuyingPrice(1);
        shopping.setCustomerEmail("jane.doe@example.org");
        shopping.setCustomerName("Customer Name");
        shopping.setProductName("Product Name");
        shopping.setPurchaseModifyTime("Purchase Modify Time");
        shopping.setPurchaseTime("Purchase Time");
        shopping.setSellingPrice(1);
        shopping.setShoppingId("42");

        // Act and Assert
        assertThrows(ShoppingCustomBadRequestException.class, () -> shoppingServiceImpl.changeByQuery("", shopping));
    }

    /**
     * Method under test:
     * {@link ShoppingServiceImpl#changeByQuery(String, ShoppingEntity)}
     */
    @Test
    void testChangeByQuery6() {
        // Arrange
        ShoppingEntity shoppingEntity = new ShoppingEntity();
        shoppingEntity.setBalanceAmount(1);
        shoppingEntity.setBuyingPrice(1);
        shoppingEntity.setCustomerEmail("jane.doe@example.org");
        shoppingEntity.setCustomerName("Customer Name");
        shoppingEntity.setProductName("Product Name");
        shoppingEntity.setPurchaseModifyTime("Purchase Modify Time");
        shoppingEntity.setPurchaseTime("Purchase Time");
        shoppingEntity.setSellingPrice(1);
        shoppingEntity.setShoppingId("42");
        Optional<ShoppingEntity> ofResult = Optional.of(shoppingEntity);
        when(shoppingDao.findById(Mockito.any())).thenReturn(ofResult);
        ShoppingEntity shopping = mock(ShoppingEntity.class);
        when(shopping.getBuyingPrice())
                .thenThrow(new ShoppingCustomNotFoundException("An error occurred", "An error occurred"));
        when(shopping.getCustomerEmail()).thenReturn("jane.doe@example.org");
        when(shopping.getProductName()).thenReturn("Product Name");
        doNothing().when(shopping).setBalanceAmount(anyInt());
        doNothing().when(shopping).setBuyingPrice(anyInt());
        doNothing().when(shopping).setCustomerEmail(Mockito.any());
        doNothing().when(shopping).setCustomerName(Mockito.any());
        doNothing().when(shopping).setProductName(Mockito.any());
        doNothing().when(shopping).setPurchaseModifyTime(Mockito.any());
        doNothing().when(shopping).setPurchaseTime(Mockito.any());
        doNothing().when(shopping).setSellingPrice(anyInt());
        doNothing().when(shopping).setShoppingId(Mockito.any());
        shopping.setBalanceAmount(1);
        shopping.setBuyingPrice(1);
        shopping.setCustomerEmail("jane.doe@example.org");
        shopping.setCustomerName("Customer Name");
        shopping.setProductName("Product Name");
        shopping.setPurchaseModifyTime("Purchase Modify Time");
        shopping.setPurchaseTime("Purchase Time");
        shopping.setSellingPrice(1);
        shopping.setShoppingId("42");

        // Act and Assert
        assertThrows(ShoppingCustomNotFoundException.class, () -> shoppingServiceImpl.changeByQuery("SHOPID999", shopping));
        verify(shopping).getBuyingPrice();
        verify(shopping, atLeast(1)).getCustomerEmail();
        verify(shopping, atLeast(1)).getProductName();
        verify(shopping).setBalanceAmount(eq(1));
        verify(shopping).setBuyingPrice(eq(1));
        verify(shopping).setCustomerEmail(eq("jane.doe@example.org"));
        verify(shopping, atLeast(1)).setCustomerName(Mockito.any());
        verify(shopping).setProductName(eq("Product Name"));
        verify(shopping).setPurchaseModifyTime(eq("Purchase Modify Time"));
        verify(shopping).setPurchaseTime(eq("Purchase Time"));
        verify(shopping).setSellingPrice(eq(1));
        verify(shopping).setShoppingId(eq("42"));
        verify(shoppingDao).findById(eq("SHOPID999"));
    }

    /**
     * Method under test:
     * {@link ShoppingServiceImpl#changeByQuery(String, ShoppingEntity)}
     */
    @Test
    void testChangeByQuery7() {
        // Arrange
        ShoppingEntity shoppingEntity = new ShoppingEntity();
        shoppingEntity.setBalanceAmount(1);
        shoppingEntity.setBuyingPrice(1);
        shoppingEntity.setCustomerEmail("jane.doe@example.org");
        shoppingEntity.setCustomerName("Customer Name");
        shoppingEntity.setProductName("Product Name");
        shoppingEntity.setPurchaseModifyTime("Purchase Modify Time");
        shoppingEntity.setPurchaseTime("Purchase Time");
        shoppingEntity.setSellingPrice(1);
        shoppingEntity.setShoppingId("42");
        Optional<ShoppingEntity> ofResult = Optional.of(shoppingEntity);
        when(shoppingDao.findById(Mockito.any())).thenReturn(ofResult);
        ShoppingEntity shopping = mock(ShoppingEntity.class);
        when(shopping.getCustomerEmail()).thenReturn("SHOPID999");
        when(shopping.getProductName()).thenReturn("Product Name");
        doNothing().when(shopping).setBalanceAmount(anyInt());
        doNothing().when(shopping).setBuyingPrice(anyInt());
        doNothing().when(shopping).setCustomerEmail(Mockito.any());
        doNothing().when(shopping).setCustomerName(Mockito.any());
        doNothing().when(shopping).setProductName(Mockito.any());
        doNothing().when(shopping).setPurchaseModifyTime(Mockito.any());
        doNothing().when(shopping).setPurchaseTime(Mockito.any());
        doNothing().when(shopping).setSellingPrice(anyInt());
        doNothing().when(shopping).setShoppingId(Mockito.any());
        shopping.setBalanceAmount(1);
        shopping.setBuyingPrice(1);
        shopping.setCustomerEmail("jane.doe@example.org");
        shopping.setCustomerName("Customer Name");
        shopping.setProductName("Product Name");
        shopping.setPurchaseModifyTime("Purchase Modify Time");
        shopping.setPurchaseTime("Purchase Time");
        shopping.setSellingPrice(1);
        shopping.setShoppingId("42");

        // Act and Assert
        assertThrows(ShoppingCustomBadRequestException.class,
                () -> shoppingServiceImpl.changeByQuery("SHOPID999", shopping));
        verify(shopping).getCustomerEmail();
        verify(shopping, atLeast(1)).getProductName();
        verify(shopping).setBalanceAmount(eq(1));
        verify(shopping).setBuyingPrice(eq(1));
        verify(shopping).setCustomerEmail(eq("jane.doe@example.org"));
        verify(shopping).setCustomerName(eq("Customer Name"));
        verify(shopping).setProductName(eq("Product Name"));
        verify(shopping).setPurchaseModifyTime(eq("Purchase Modify Time"));
        verify(shopping).setPurchaseTime(eq("Purchase Time"));
        verify(shopping).setSellingPrice(eq(1));
        verify(shopping).setShoppingId(eq("42"));
        verify(shoppingDao).findById(eq("SHOPID999"));
    }

    /**
     * Method under test:
     * {@link ShoppingServiceImpl#changeByQuery(String, ShoppingEntity)}
     */
    @Test
    void testChangeByQuery8() {
        // Arrange
        ShoppingEntity shoppingEntity = new ShoppingEntity();
        shoppingEntity.setBalanceAmount(1);
        shoppingEntity.setBuyingPrice(1);
        shoppingEntity.setCustomerEmail("jane.doe@example.org");
        shoppingEntity.setCustomerName("Customer Name");
        shoppingEntity.setProductName("Product Name");
        shoppingEntity.setPurchaseModifyTime("Purchase Modify Time");
        shoppingEntity.setPurchaseTime("Purchase Time");
        shoppingEntity.setSellingPrice(1);
        shoppingEntity.setShoppingId("42");
        Optional<ShoppingEntity> ofResult = Optional.of(shoppingEntity);
        when(shoppingDao.findById(Mockito.any())).thenReturn(ofResult);
        ShoppingEntity shopping = mock(ShoppingEntity.class);
        when(shopping.getProductName()).thenReturn(null);
        doNothing().when(shopping).setBalanceAmount(anyInt());
        doNothing().when(shopping).setBuyingPrice(anyInt());
        doNothing().when(shopping).setCustomerEmail(Mockito.any());
        doNothing().when(shopping).setCustomerName(Mockito.any());
        doNothing().when(shopping).setProductName(Mockito.any());
        doNothing().when(shopping).setPurchaseModifyTime(Mockito.any());
        doNothing().when(shopping).setPurchaseTime(Mockito.any());
        doNothing().when(shopping).setSellingPrice(anyInt());
        doNothing().when(shopping).setShoppingId(Mockito.any());
        shopping.setBalanceAmount(1);
        shopping.setBuyingPrice(1);
        shopping.setCustomerEmail("jane.doe@example.org");
        shopping.setCustomerName("Customer Name");
        shopping.setProductName("Product Name");
        shopping.setPurchaseModifyTime("Purchase Modify Time");
        shopping.setPurchaseTime("Purchase Time");
        shopping.setSellingPrice(1);
        shopping.setShoppingId("42");

        // Act and Assert
        assertThrows(ShoppingCustomBadRequestException.class,
                () -> shoppingServiceImpl.changeByQuery("SHOPID999", shopping));
        verify(shopping).getProductName();
        verify(shopping).setBalanceAmount(eq(1));
        verify(shopping).setBuyingPrice(eq(1));
        verify(shopping).setCustomerEmail(eq("jane.doe@example.org"));
        verify(shopping).setCustomerName(eq("Customer Name"));
        verify(shopping).setProductName(eq("Product Name"));
        verify(shopping).setPurchaseModifyTime(eq("Purchase Modify Time"));
        verify(shopping).setPurchaseTime(eq("Purchase Time"));
        verify(shopping).setSellingPrice(eq(1));
        verify(shopping).setShoppingId(eq("42"));
        verify(shoppingDao).findById(eq("SHOPID999"));
    }

    /**
     * Method under test:
     * {@link ShoppingServiceImpl#changeByQuery(String, ShoppingEntity)}
     */
    @Test
    void testChangeByQuery9() {
        // Arrange
        ShoppingEntity shoppingEntity = new ShoppingEntity();
        shoppingEntity.setBalanceAmount(1);
        shoppingEntity.setBuyingPrice(1);
        shoppingEntity.setCustomerEmail("jane.doe@example.org");
        shoppingEntity.setCustomerName("Customer Name");
        shoppingEntity.setProductName("Product Name");
        shoppingEntity.setPurchaseModifyTime("Purchase Modify Time");
        shoppingEntity.setPurchaseTime("Purchase Time");
        shoppingEntity.setSellingPrice(1);
        shoppingEntity.setShoppingId("42");
        Optional<ShoppingEntity> ofResult = Optional.of(shoppingEntity);
        when(shoppingDao.findById(Mockito.any())).thenReturn(ofResult);
        ShoppingEntity shopping = mock(ShoppingEntity.class);
        when(shopping.getProductName()).thenReturn("");
        doNothing().when(shopping).setBalanceAmount(anyInt());
        doNothing().when(shopping).setBuyingPrice(anyInt());
        doNothing().when(shopping).setCustomerEmail(Mockito.any());
        doNothing().when(shopping).setCustomerName(Mockito.any());
        doNothing().when(shopping).setProductName(Mockito.any());
        doNothing().when(shopping).setPurchaseModifyTime(Mockito.any());
        doNothing().when(shopping).setPurchaseTime(Mockito.any());
        doNothing().when(shopping).setSellingPrice(anyInt());
        doNothing().when(shopping).setShoppingId(Mockito.any());
        shopping.setBalanceAmount(1);
        shopping.setBuyingPrice(1);
        shopping.setCustomerEmail("jane.doe@example.org");
        shopping.setCustomerName("Customer Name");
        shopping.setProductName("Product Name");
        shopping.setPurchaseModifyTime("Purchase Modify Time");
        shopping.setPurchaseTime("Purchase Time");
        shopping.setSellingPrice(1);
        shopping.setShoppingId("42");

        // Act and Assert
        assertThrows(ShoppingCustomBadRequestException.class,
                () -> shoppingServiceImpl.changeByQuery("SHOPID999", shopping));
        verify(shopping, atLeast(1)).getProductName();
        verify(shopping).setBalanceAmount(eq(1));
        verify(shopping).setBuyingPrice(eq(1));
        verify(shopping).setCustomerEmail(eq("jane.doe@example.org"));
        verify(shopping).setCustomerName(eq("Customer Name"));
        verify(shopping).setProductName(eq("Product Name"));
        verify(shopping).setPurchaseModifyTime(eq("Purchase Modify Time"));
        verify(shopping).setPurchaseTime(eq("Purchase Time"));
        verify(shopping).setSellingPrice(eq(1));
        verify(shopping).setShoppingId(eq("42"));
        verify(shoppingDao).findById(eq("SHOPID999"));
    }

    /**
     * Method under test: {@link ShoppingServiceImpl#remove(String)}
     */
    @Test
    void testRemove() {
        // Arrange, Act and Assert
        assertThrows(ShoppingCustomBadRequestException.class, () -> shoppingServiceImpl.remove("42"));
    }

    /**
     * Method under test: {@link ShoppingServiceImpl#remove(String)}
     */
    @Test
    void testRemove2() {
        // Arrange
        doNothing().when(shoppingDao).deleteById(Mockito.any());
        when(shoppingDao.existsById(Mockito.any())).thenReturn(true);

        // Act
        shoppingServiceImpl.remove("SHOPID999");

        // Assert that nothing has changed
        verify(shoppingDao).deleteById(eq("SHOPID999"));
        verify(shoppingDao).existsById(eq("SHOPID999"));
    }

    /**
     * Method under test: {@link ShoppingServiceImpl#remove(String)}
     */
    @Test
    void testRemove3() {
        // Arrange
        doThrow(new ShoppingCustomBadRequestException("An error occurred", "An error occurred")).when(shoppingDao)
                .deleteById(Mockito.any());
        when(shoppingDao.existsById(Mockito.any())).thenReturn(true);

        // Act and Assert
        assertThrows(ShoppingCustomBadRequestException.class, () -> shoppingServiceImpl.remove("SHOPID999"));
        verify(shoppingDao).deleteById(eq("SHOPID999"));
        verify(shoppingDao).existsById(eq("SHOPID999"));
    }

    /**
     * Method under test: {@link ShoppingServiceImpl#remove(String)}
     */
    @Test
    void testRemove4() {
        // Arrange
        when(shoppingDao.existsById(Mockito.any())).thenReturn(false);

        // Act and Assert
        assertThrows(ShoppingCustomNotFoundException.class, () -> shoppingServiceImpl.remove("SHOPID999"));
        verify(shoppingDao).existsById(eq("SHOPID999"));
    }
}
