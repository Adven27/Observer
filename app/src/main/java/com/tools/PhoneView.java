package com.tools;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * Created by MetallFoX on 06.12.2014.
 */
public class PhoneView extends TextView implements CustomDialogFragment.DialogListener {

    private final OnClickListener PHONE_CLICK_LISTENER = new OnClickListener() {

        @Override
        public void onClick(View view) {
            CustomDialogFragment dialog = new CustomDialogFragment();
            PrototypeView.showDialog(PhoneView.this, "Совершение вызова", "Совершить вызов номера " + getText() + "?");
        }
    };

    public PhoneView(Context context) {
        super(context);
        setOnClickListener(PHONE_CLICK_LISTENER);
    }

    public PhoneView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(PHONE_CLICK_LISTENER);
    }

    public PhoneView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOnClickListener(PHONE_CLICK_LISTENER);
    }


    @Override
    public void onPositive() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + getText()));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getContext().startActivity(intent);
    }

    @Override
    public void onNegative() {

    }
}
