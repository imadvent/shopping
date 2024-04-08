package com.shopping.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
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

    public static String dateToStringFormat(Instant instant) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME.withZone(ZoneId.from(ZoneOffset.UTC));
        return formatter.format(instant);
    }

    public static boolean isValidShoppingId(String shoppingId) {
        return shoppingId != null && shoppingId.matches(SHOPPING_ID_REGEX.getCode());
    }
}
