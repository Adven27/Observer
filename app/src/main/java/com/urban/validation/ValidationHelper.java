package com.urban.validation;

import android.widget.DatePicker;
import android.widget.TextView;

/**
 * Created by MetallFoX on 09.11.2014.
 */
public class ValidationHelper {

    public static boolean isEmpty(TextView view) {
        return "".equals(view.getText().toString());
    }

    public static boolean isEmpty(DatePicker view) {
        return false;
    }

}
