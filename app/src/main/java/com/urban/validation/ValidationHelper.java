package com.urban.validation;

import java.util.Date;

/**
 * Created by MetallFoX on 09.11.2014.
 */
public class ValidationHelper {

    public static boolean isEmpty(String value) {
        return value == null || "".equals(value);
    }

    public static boolean isEmpty(Date value) {
        return value == null;
    }

}
