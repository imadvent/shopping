package com.shopping.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.shopping.constants.ShoppingConstants.*;

public class ShoppingUtil {

    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX.getCode(), Pattern.CASE_INSENSITIVE);
        Matcher match = pattern.matcher(email);
        return match.matches();
    }

    public static String prefixAppend(StringBuilder id, int count) {
        if (String.valueOf(count).length() == 1) {
            id.append(ID_PREFIX.getDescription()).append(count);
        } else if (String.valueOf(count).length() == 2) {
            id.append(ID_PREFIX.getCode()).append(count);
        } else {
            id.append(count);
        }
        return id.toString();
    }

    public static String dateToStringFormat(LocalDateTime localDateTime) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return formatter.format(localDateTime);
    }

    public static boolean isInvalidShoppingId(String shoppingId) {
        return shoppingId != null && !shoppingId.matches(SHOPPING_ID_REGEX.getCode());
    }

    public static String extractName(String email) {
        int atIndex = email.indexOf('@');
        if (atIndex != -1) {
            return email.substring(0, atIndex);
        } else {
            return "Invalid Email";
        }
    }
}
