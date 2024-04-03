package com.shopping.service.impl;

import com.shopping.dao.ShoppingDao;
import com.shopping.entity.ShoppingEntity;
import com.shopping.exception.ShoppingCustomException;
import com.shopping.service.ShoppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static com.shopping.constants.ShoppingConstants.SHOPPING_ID_PREFIX;
import static com.shopping.util.ShoppingUtil.dateToStringFormat;
import static com.shopping.util.ShoppingUtil.prefixAppend;
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
        shopping.setPurchaseTime(dateToStringFormat(now()));
        shopping.setPurchaseModifyTime(dateToStringFormat(now()));

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
                    "The shopping item with given id " + shoppingId + " is not present");
        }

        return single.get();
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
        shop.setCustomerEmail(shopping.getCustomerEmail());
        shop.setSellingPrice(shopping.getSellingPrice());
        shop.setBuyingPrice(shopping.getBuyingPrice());
        shop.setPurchaseModifyTime(dateToStringFormat(now()));

        if (shop.getBuyingPrice() < shop.getSellingPrice()) {
            throw new ShoppingCustomException("INVALID_BALANCE", "buying balance is low");

        }

        shoppingDao.save(shop);
        return shop;
    }

    @Override
    public void remove(String shoppingId) {
        if (!shoppingDao.existsById(shoppingId)) {
            throw new ShoppingCustomException("SHOPPING_ITEM_NOT_FOUND",
                    "Shopping item with id " + shoppingId + " not found");
        }
        shoppingDao.deleteById(shoppingId);
    }
}
