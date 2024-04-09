package com.shopping.service.impl;

import com.shopping.dao.ShoppingDao;
import com.shopping.entity.ShoppingEntity;
import com.shopping.exception.ShoppingCustomException;
import com.shopping.service.ShoppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static com.shopping.constants.ShoppingConstants.SHOPPING_ID_PREFIX;
import static com.shopping.util.ShoppingUtil.*;
import static java.time.Instant.now;

@Service
public class ShoppingServiceImpl implements ShoppingService {

    public static Integer counter = 0;

    @Autowired
    private ShoppingDao shoppingDao;

    @Override
    public String generateShoppingId() {

        StringBuilder shoppingId = new StringBuilder(SHOPPING_ID_PREFIX.getCode());
        if (counter == 0) {
            try {
                String maxIdTill = shoppingDao.findAll().stream().map(ShoppingEntity::getShoppingId)
                        .max(Comparator.naturalOrder()).orElse(shoppingId + "0");
                counter = Integer.parseInt(maxIdTill.substring(SHOPPING_ID_PREFIX.getCode().length()));
                counter++;
            } catch (Exception e) {
                ++counter;
            }
        } else {
            ++counter;
        }

        return prefixAppend(shoppingId, counter);

    }

    @Override
    public ShoppingEntity insert(ShoppingEntity shopping) {

        String shoppingId = generateShoppingId();
        shopping.setShoppingId(shoppingId);
        shopping.setCustomerName(extractName(shopping.getCustomerEmail()));
        shopping.setPurchaseTime(dateToStringFormat(now()));
        shopping.setPurchaseModifyTime("-");
        shopping.setBalanceAmount(shopping.getBuyingPrice() - shopping.getSellingPrice());

        Optional<ShoppingEntity> existing = shoppingDao.findById(shopping.getShoppingId());

        if (existing.isPresent()) {
            throw new ShoppingCustomException("DUPLICATE_SHOPPING_ID", "Shopping item with given id " +
                    shopping.getShoppingId() + " already exist");
        }

        return shoppingDao.save(shopping);
    }

    @Override
    public ShoppingEntity view(String shoppingId) {

        Optional<ShoppingEntity> single = shoppingDao.findById(shoppingId);

        if (single.isEmpty()) {
            throw new ShoppingCustomException("NO_SHOPPING_ID_EXIST",
                    "The shopping item with ID " + shoppingId + " is not present");
        }

        return single.get();
    }

    public List<ShoppingEntity> viewByProductName(String productName) {
        List<ShoppingEntity> singleProduct = shoppingDao.findByProductName(productName);

        if (singleProduct.isEmpty()) {
            throw new ShoppingCustomException("NO_PRODUCT_NAME_EXIST",
                    "The product with name " + productName + " is not present");
        }

        return singleProduct;
    }

    public List<ShoppingEntity> viewByCustomerNameOrProductName(String customerName, String productName) {
        List<ShoppingEntity> products = shoppingDao.findbyCustomerNameOrProductName(customerName, productName);

        if (products.isEmpty()) {
            throw new ShoppingCustomException("NO_CUSTOMER_NAME_OR_PRODUCT_NAME_EXIST",
                    "The products with name " + customerName + " or product name " + productName + " are not present");
        }

        return products;
    }

    @Override
    public List<ShoppingEntity> viewAll() {
        List<ShoppingEntity> every = shoppingDao.findAll();

        if (every.isEmpty()) {
            throw new ShoppingCustomException("NO_SHOPPING_RECORDS", "Shopping list is empty");
        }
        return every;
    }

    @Override
    public ShoppingEntity change(String shoppingId, ShoppingEntity shopping) {
        Optional<ShoppingEntity> exist = shoppingDao.findById(shoppingId);

        if (exist.isEmpty()) {
            throw new ShoppingCustomException("NO_SHOPPING_RECORDS", "Shopping item of ID " + shoppingId + " is empty");
        }

        ShoppingEntity shop = exist.get();
        shop.setProductName(shopping.getProductName());
        shop.setCustomerName(extractName(shop.getCustomerEmail()));
        shop.setCustomerEmail(shopping.getCustomerEmail());
        shop.setSellingPrice(shopping.getSellingPrice());
        shop.setBuyingPrice(shopping.getBuyingPrice());
        shop.setBalanceAmount(shopping.getBuyingPrice() - shopping.getSellingPrice());
        shop.setPurchaseModifyTime(dateToStringFormat(now()));

        if (shop.getBalanceAmount() < 0 || shop.getBuyingPrice() < shop.getSellingPrice()) {
            throw new ShoppingCustomException("INVALID_BALANCE", "buying balance is low");
        }

        if (shopping.getProductName() == null || shopping.getProductName().isEmpty()) {
            throw new ShoppingCustomException("PRODUCT_NAME_MANDATORY", "Product name is mandatory");
        }

        if (!isValidEmail(shopping.getCustomerEmail())) {
            throw new ShoppingCustomException("INVALID_EMAIL", "Entered email is invalid");
        }

        shoppingDao.save(shop);
        return shop;
    }

    @Override
    @Transactional
    public ShoppingEntity changeByQuery(String shoppingId, ShoppingEntity shopping) {

        Optional<ShoppingEntity> exist = shoppingDao.findById(shoppingId);

        if (exist.isEmpty()) {
            throw new ShoppingCustomException("NO_SHOPPING_RECORDS", "Shopping item of ID " + shoppingId + " is empty");
        }

        if (shopping.getProductName() == null || shopping.getProductName().isEmpty()) {
            throw new ShoppingCustomException("PRODUCT_NAME_MANDATORY", "Product name is mandatory");
        }

        if (!isValidEmail(shopping.getCustomerEmail())) {
            throw new ShoppingCustomException("INVALID_EMAIL", "Entered email is invalid");
        }

        shopping.setCustomerName(extractName(shopping.getCustomerEmail()));

        shoppingDao.updateWithQuery(shoppingId, shopping.getProductName(), shopping.getCustomerName(), shopping.getCustomerEmail(),
                shopping.getSellingPrice(), shopping.getBuyingPrice(), shopping.getBuyingPrice() - shopping.getSellingPrice(),
                dateToStringFormat(now()));

        if (shopping.getBuyingPrice() < shopping.getSellingPrice()) {
            throw new ShoppingCustomException("INVALID_BALANCE", "Buying price is less than selling price");
        }

        return shoppingDao.findById(shoppingId)
                .orElseThrow(() -> new ShoppingCustomException("NO_SHOPPING_RECORDS", "Shopping item of ID " + shoppingId + " is empty"));
    }

    @Override
    public void remove(String shoppingId) {
        if (!shoppingDao.existsById(shoppingId)) {
            throw new ShoppingCustomException("SHOPPING_ITEM_NOT_FOUND",
                    "Shopping item with ID " + shoppingId + " not found");
        }
        shoppingDao.deleteById(shoppingId);
    }
}
