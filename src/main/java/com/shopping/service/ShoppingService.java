package com.shopping.service;

import com.shopping.entity.ShoppingEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.shopping.constants.ShoppingConstants.EMAIL_REGEX;

@Service
public interface ShoppingService {

    static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher match = pattern.matcher(email);
        return match.matches();
    }

    ShoppingEntity insert(ShoppingEntity shopping);

    ShoppingEntity view(String id);

    List<ShoppingEntity> viewAll();

    ShoppingEntity change(String id, ShoppingEntity entity);

    void remove(String id);

    String generateShoppingId();

}
