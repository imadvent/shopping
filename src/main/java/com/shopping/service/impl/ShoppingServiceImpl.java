package com.shopping.service.impl;

import com.shopping.dao.ShoppingDao;
import com.shopping.dto.ShoppingRequest;
import com.shopping.dto.ShoppingResponse;
import com.shopping.entity.ShoppingEntity;
import com.shopping.exception.ShoppingCustomBadRequestException;
import com.shopping.exception.ShoppingCustomNotFoundException;
import com.shopping.service.ShoppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.shopping.constants.ShoppingConstants.SHOPPING_ID_PREFIX;
import static com.shopping.util.ShoppingUtil.*;
import static java.time.LocalDateTime.now;

@Service
public class ShoppingServiceImpl implements ShoppingService {

    @Autowired
    private ShoppingDao shoppingDao;

    @Override
    public String generateShoppingId() {

        String shoppingId;
        String shoppingIdPrefix = SHOPPING_ID_PREFIX.getCode();
        SecureRandom random = new SecureRandom();
        do {
            int randomNumber = random.nextInt(90000) + 10000;
            shoppingId = shoppingIdPrefix + randomNumber;
        } while (shoppingDao.existsById(shoppingId));

        return shoppingId;

    }

    @Override
    public ShoppingResponse insert(ShoppingRequest shoppingRequest) {

        ShoppingResponse shoppingResponse = new ShoppingResponse();
        ShoppingEntity shoppingEntity = new ShoppingEntity();

        shoppingRequestMapper(shoppingRequest, shoppingEntity);

        if (shoppingEntity.getShoppingId() != null && !shoppingEntity.getShoppingId().isEmpty()) {
            throw new ShoppingCustomBadRequestException("SHOPPING_ID_DETECTED", "Should not enter Shopping ID");
        }

        if (shoppingEntity.getProductName() == null || shoppingEntity.getProductName().isEmpty()) {
            throw new ShoppingCustomBadRequestException("EMPTY_PRODUCT_NAME", "Product name is mandatory");
        }

        if (!isValidEmail(shoppingEntity.getCustomerEmail())) {
            throw new ShoppingCustomBadRequestException("EMAIL_INVALID", "Invalid customer email");
        }

        if (shoppingEntity.getBuyingPrice() <= 0 || shoppingEntity.getSellingPrice() <= 0) {
            throw new ShoppingCustomBadRequestException("INVALID_COST", "Value should be non negative");
        }

        if (shoppingEntity.getBuyingPrice() < shoppingEntity.getSellingPrice()) {
            throw new ShoppingCustomBadRequestException("INSUFFICIENT_BALANCE", "Insufficient balance");
        }

        String shoppingId = generateShoppingId();
        shoppingEntity.setShoppingId(shoppingId);

        Optional<ShoppingEntity> existing = shoppingDao.findById(shoppingEntity.getShoppingId());

        if (existing.isPresent()) {
            throw new ShoppingCustomBadRequestException("DUPLICATE_SHOPPING_ID", "Shopping item with given id " +
                    shoppingEntity.getShoppingId() + " already exist");
        }

        shoppingEntity.setCustomerName(extractName(shoppingEntity.getCustomerEmail()));
        shoppingEntity.setPurchaseTime(extractTime(dateToStringFormat(now())));
        shoppingEntity.setPurchaseDate(extractDate(dateToStringFormat(now())));
        shoppingEntity.setPurchaseModifyTime("-");
        shoppingEntity.setPurchaseModifyDate("-");
        shoppingEntity.setBalanceAmount(shoppingEntity.getBuyingPrice() - shoppingEntity.getSellingPrice());

        shoppingDao.save(shoppingEntity);

        shoppingResponseMapper(shoppingEntity, shoppingResponse);

        return shoppingResponse;
    }

    @Override
    public ShoppingResponse view(String shoppingId) {

        ShoppingResponse shoppingResponse = new ShoppingResponse();
        Optional<ShoppingEntity> single = shoppingDao.findById(shoppingId);

        if (isInvalidShoppingId(shoppingId)) {
            throw new ShoppingCustomBadRequestException("INVALID_SHOPPING_ID", "Shopping item with ID " + shoppingId + " is invalid");
        }

        if (single.isEmpty()) {
            throw new ShoppingCustomNotFoundException("NO_SHOPPING_ID_EXIST",
                    "The shopping item with ID " + shoppingId + " is not present");
        }

        ShoppingEntity shoppingEntity = single.get();

        shoppingResponseMapper(shoppingEntity, shoppingResponse);

        return shoppingResponse;
    }

    public List<ShoppingResponse> viewByProductName(String productName) {

        List<ShoppingResponse> shoppingResponses = new ArrayList<>();

        if (productName == null || productName.isEmpty()) {
            throw new ShoppingCustomBadRequestException("INVALID_PRODUCT_NAME", "Product name is mandatory");
        }

        List<ShoppingEntity> singleProduct = shoppingDao.findByProductName(productName);

        if (singleProduct.isEmpty()) {
            throw new ShoppingCustomNotFoundException("NO_PRODUCT_NAME_EXIST",
                    "The product with name " + productName + " is not present");
        }

        singleProduct.forEach(single -> {
            ShoppingResponse shoppingResponse = new ShoppingResponse();
            shoppingResponseMapper(single, shoppingResponse);

            shoppingResponses.add(shoppingResponse);
        });
        return shoppingResponses;
    }

    public List<ShoppingResponse> viewByCustomerNameOrProductName(String customerName, String productName) {

        List<ShoppingResponse> shoppingResponseList = new ArrayList<>();

        if (customerName.isBlank() && customerName.isEmpty() && productName.isBlank() && productName.isEmpty()) {
            throw new ShoppingCustomBadRequestException("INVALID_PRODUCT_AND_CUSTOMER_NAME", "Product or customer name is mandatory");
        }

        List<ShoppingEntity> products = shoppingDao.findbyCustomerNameOrProductName(customerName, productName);

        if (products.isEmpty()) {
            throw new ShoppingCustomNotFoundException("NO_CUSTOMER_NAME_OR_PRODUCT_NAME_EXIST",
                    "The products with name " + customerName + " or product name " + productName + " are not present");
        }

        products.forEach(custProd -> {
            ShoppingResponse shoppingResponse = new ShoppingResponse();
            shoppingResponseMapper(custProd, shoppingResponse);

            shoppingResponseList.add(shoppingResponse);
        });

        return shoppingResponseList;
    }

    @Override
    public List<ShoppingResponse> viewAll() {

        List<ShoppingResponse> responses = new ArrayList<>();
        List<ShoppingEntity> every = shoppingDao.findAll();

        if (every.isEmpty()) {
            throw new ShoppingCustomNotFoundException("NO_SHOPPING_RECORDS", "Shopping list is empty");
        }

        every.forEach(everyList -> {
            ShoppingResponse shoppingResponse = new ShoppingResponse();
            shoppingResponseMapper(everyList, shoppingResponse);

            responses.add(shoppingResponse);
        });
        return responses;
    }

    @Override
    public List<ShoppingResponse> getFrom(String fromDate, String toDate) {
        List<ShoppingResponse> shoppingFromTo = new ArrayList<>();
        List<ShoppingEntity> shopping = shoppingDao.searchByDate(fromDate, toDate);

        if (!isValidDate(fromDate, toDate)) {
            throw new ShoppingCustomBadRequestException("INVALID_SHOPING_DATE", "The shopping date is invalid");
        }

        if (shopping.isEmpty()) {
            throw new ShoppingCustomNotFoundException("NO_SHOPPING_RECORDS", "There is no shopping records from this date");
        }

        shopping.forEach(dateRecord -> {
            ShoppingResponse shoppingResponse = new ShoppingResponse();
            shoppingResponseMapper(dateRecord, shoppingResponse);

            shoppingFromTo.add(shoppingResponse);
        });

        return shoppingFromTo;
    }

    @Override
    public ShoppingResponse change(String shoppingId, ShoppingRequest shoppingRequest) {

        ShoppingResponse shoppingResponse = new ShoppingResponse();

        if (isInvalidShoppingId(shoppingId)) {
            throw new ShoppingCustomBadRequestException("INVALID_SHOPPING_ID", "Shopping item with ID " + shoppingId + " is invalid");
        }

        Optional<ShoppingEntity> exist = shoppingDao.findById(shoppingId);

        if (exist.isEmpty()) {
            throw new ShoppingCustomNotFoundException("NO_SHOPPING_RECORDS", "Shopping item of ID " + shoppingId + " is empty");
        }

        ShoppingEntity shop = exist.get();

        shoppingRequestMapper(shoppingRequest, shop);

        shop.setCustomerName(extractName(shop.getCustomerEmail()));
        shop.setBalanceAmount(shoppingRequest.getBuyingPrice() - shoppingRequest.getSellingPrice());
        shop.setPurchaseModifyTime(extractTime(dateToStringFormat(now())));
        shop.setPurchaseModifyDate(extractDate(dateToStringFormat(now())));

        if (shop.getBalanceAmount() < 0 || shop.getBuyingPrice() < shop.getSellingPrice()) {
            throw new ShoppingCustomBadRequestException("INVALID_BALANCE", "buying balance is low");
        }

        if (shoppingRequest.getProductName() == null || shoppingRequest.getProductName().isEmpty()) {
            throw new ShoppingCustomBadRequestException("PRODUCT_NAME_MANDATORY", "Product name is mandatory");
        }

        if (!isValidEmail(shoppingRequest.getCustomerEmail())) {
            throw new ShoppingCustomBadRequestException("INVALID_EMAIL", "Entered email is invalid");
        }

        shoppingDao.save(shop);
        shoppingResponseMapper(shop, shoppingResponse);
        return shoppingResponse;
    }

    @Override
    @Transactional
    public ShoppingResponse changeByQuery(String shoppingId, ShoppingRequest shopping) {

        ShoppingResponse shoppingResponse = new ShoppingResponse();

        if (shoppingId.isBlank() && shoppingId.isEmpty()) {
            throw new ShoppingCustomBadRequestException("EMPTY_SHOPPING_ID", "Shopping ID should not be blank");
        }

        if (isInvalidShoppingId(shoppingId)) {
            throw new ShoppingCustomBadRequestException("INVALID_SHOPPING_ID", "Shopping item with ID " + shoppingId + " is invalid");
        }

        Optional<ShoppingEntity> exist = shoppingDao.findById(shoppingId);

        if (exist.isEmpty()) {
            throw new ShoppingCustomNotFoundException("NO_SHOPPING_RECORDS", "Shopping item of ID " + shoppingId + " is empty");
        }

        if (shopping.getProductName() == null || shopping.getProductName().isEmpty()) {
            throw new ShoppingCustomBadRequestException("PRODUCT_NAME_MANDATORY", "Product name is mandatory");
        }

        if (!isValidEmail(shopping.getCustomerEmail())) {
            throw new ShoppingCustomBadRequestException("INVALID_EMAIL", "Entered email is invalid");
        }

        ShoppingEntity shoppingEntity = exist.get();

        shoppingRequestMapper(shopping, shoppingEntity);

        shoppingEntity.setCustomerName(extractName(shopping.getCustomerEmail()));
        shoppingEntity.setBalanceAmount(shopping.getBuyingPrice() - shopping.getSellingPrice());
        shoppingEntity.setPurchaseModifyTime(extractTime(dateToStringFormat(now())));
        shoppingEntity.setPurchaseModifyDate(extractDate(dateToStringFormat(now())));
        shoppingDao.updateWithQuery(shoppingId, shopping.getProductName(), shoppingEntity.getCustomerName(), shopping.getCustomerEmail(),
                shopping.getSellingPrice(), shopping.getBuyingPrice(), shoppingEntity.getBalanceAmount(), shoppingEntity.getPurchaseModifyTime(),
                shoppingEntity.getPurchaseModifyDate());

        if (shopping.getBuyingPrice() < shopping.getSellingPrice()) {
            throw new ShoppingCustomBadRequestException("INVALID_BALANCE", "Buying price is less than selling price");
        }

        shoppingResponseMapper(shoppingEntity, shoppingResponse);
        return shoppingResponse;
    }

    @Override
    public void remove(String shoppingId) {

        if (isInvalidShoppingId(shoppingId)) {
            throw new ShoppingCustomBadRequestException("INVALID_SHOPPING_ID", "Shopping item with ID " + shoppingId + " is invalid");
        }

        if (!shoppingDao.existsById(shoppingId)) {
            throw new ShoppingCustomNotFoundException("SHOPPING_ITEM_NOT_FOUND",
                    "Shopping item with ID " + shoppingId + " not found");
        }
        shoppingDao.deleteById(shoppingId);
    }
}