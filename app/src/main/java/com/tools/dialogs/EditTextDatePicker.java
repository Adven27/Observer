package com.tools.dialogs;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by MetallFoX on 14.12.2014.
 */
public class EditTextDatePicker extends EditText {

    private static final String DATE_FORMAT = "dd.MM.yy";

    private Calendar calendar = Calendar.getInstance();

    private final OnClickListener EDIT_FIELD_CLICK_LISTENER = new OnClickListener() {

        @Override
        public void onClick(View v) {
            new DatePickerDialog(
                    getContext(),
                    listener,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            ).show();
        }
    };

    public EditTextDatePicker(Context context) {
        super(context);
        setOnClickListener(EDIT_FIELD_CLICK_LISTENER);
    }

    public EditTextDatePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(EDIT_FIELD_CLICK_LISTENER);
    }

    public EditTextDatePicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOnClickListener(EDIT_FIELD_CLICK_LISTENER);
    }

    public void setDate(Date date) {
        calendar.setTime(date);
        updateText();
    }

    public Date getDate() {
        return calendar.getTime();
    }


    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateText();
        }

    };

    private void updateText() {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        setText(formatter.format(calendar.getTime()));
    }


}
