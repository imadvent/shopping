package com.shopping.service;

import com.shopping.dao.ShoppingDao;
import com.shopping.entity.ShoppingEntity;
import com.shopping.exception.ShoppingCustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.shopping.constants.ShoppingConstants.EMAIL_REGEX;

@Service
public class ShoppingService {

    private static Integer counter = 0;

    @Autowired
    private ShoppingDao shoppingDao;

    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher match = pattern.matcher(email);
        return match.matches();
    }

    public String generateMovieId() {

        StringBuilder shoppingId = new StringBuilder("SHOPID");
        if (counter == 0) {
            try {
                String maxIdTill = shoppingDao.findAll().stream().map(ShoppingEntity::getShoppingId)
                        .max(Comparator.naturalOrder()).orElse(shoppingId + "0");
                counter = Integer.parseInt(maxIdTill.substring(3));
                counter++;
            } catch (Exception e) {
                ++counter;
            }
        } else {
            ++counter;
        }

        if (String.valueOf(counter).length() == 1) {
            shoppingId.append("00").append(counter);
        } else if (String.valueOf(counter).length() == 2) {
            shoppingId.append("0").append(counter);
        } else {
            shoppingId.append(counter);
        }
        return shoppingId.toString();
    }

    public ShoppingEntity insert(ShoppingEntity shopping) {

        String shoppingId = generateMovieId();
        shopping.setShoppingId(shoppingId);

        Optional<ShoppingEntity> existing = shoppingDao.findById(shopping.getShoppingId());

        if (existing.isPresent()) {
            throw new ShoppingCustomException("DUPLICATE_SHOPPING_ID", "Shopping item with given id " +
                    shopping.getShoppingId() + " already exist");
        }

        return shoppingDao.save(shopping);
    }

    public ShoppingEntity view(String shoppingId) {
        Optional<ShoppingEntity> single = shoppingDao.findById(shoppingId);

        if (single.isEmpty()) {
            throw new ShoppingCustomException("NO_SHOPPING_ID_EXIST",
                    "The shopping item with given id " + shoppingId + " is not present");
        }

        return single.get();
    }

    public List<ShoppingEntity> viewAll() {
        List<ShoppingEntity> every = shoppingDao.findAll();

        if (every.isEmpty()) {
            throw new ShoppingCustomException("NO_SHOPPING_RECORDS", "Shopping list is empty");
        }
        return every;
    }

    public ShoppingEntity change(String shoppingId, ShoppingEntity shopping) {
        Optional<ShoppingEntity> exist = shoppingDao.findById(shoppingId);

        if (exist.isEmpty()) {
            throw new ShoppingCustomException("NO_SHOPPING_RECORDS", "Shopping list is empty");
        }
        ShoppingEntity shop = exist.get();
        shop.setProductName(shopping.getProductName());
        shop.setCustomerEmail(shopping.getCustomerEmail());
        shop.setSellingPrice(shopping.getSellingPrice());

        if (shopping.getBuyingPrice() < shop.getBuyingPrice()) {
            shop.setBuyingPrice(shopping.getBuyingPrice());
        }

        shoppingDao.save(shop);
        return shop;
    }

    public void remove(String shoppingId) {
        if (!shoppingDao.existsById(shoppingId)) {
            throw new ShoppingCustomException("SHOPPING_ITEM_NOT_FOUND",
                    "Movie with id " + shoppingId + " not found");
        }
        shoppingDao.deleteById(shoppingId);
    }
}
