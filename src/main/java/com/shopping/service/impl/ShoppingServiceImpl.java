package com.shopping.service.impl;

import com.shopping.constants.ShoppingConstants;
import com.shopping.dao.ShoppingDao;
import com.shopping.entity.ShoppingEntity;
import com.shopping.exception.ShoppingCustomException;
import com.shopping.service.ShoppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static com.shopping.constants.ShoppingConstants.ID_PREFIX;
import static com.shopping.constants.ShoppingConstants.SHOPPING_ID_PREFIX;

@Service
public class ShoppingServiceImpl implements ShoppingService {

    private static Integer counter = 0;

    @Autowired
    private ShoppingDao shoppingDao;

    @Override
    public String generateShoppingId() {

        StringBuilder shoppingId = new StringBuilder(SHOPPING_ID_PREFIX.getCode());
        if (counter == 0) {
            try {
                String maxIdTill = shoppingDao.findAll().stream().map(ShoppingEntity::getShoppingId)
                        .max(Comparator.naturalOrder()).orElse(shoppingId + ID_PREFIX.getCode());
                counter = Integer.parseInt(maxIdTill.substring(3));
                counter++;
            } catch (Exception e) {
                ++counter;
            }
        } else {
            ++counter;
        }

        if (String.valueOf(counter).length() == 1) {
            shoppingId.append(ID_PREFIX.getDescription()).append(counter);
        } else if (String.valueOf(counter).length() == 2) {
            shoppingId.append(ID_PREFIX.getCode()).append(counter);
        } else {
            shoppingId.append(counter);
        }
        return shoppingId.toString();
    }

    @Override
    public ShoppingEntity insert(ShoppingEntity shopping) {

        String shoppingId = generateShoppingId();
        shopping.setShoppingId(shoppingId);

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
