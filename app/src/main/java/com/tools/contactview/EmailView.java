package com.tools.contactview;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;

import com.tools.CustomDialogFragment;
import com.tools.PrototypeView;
import com.urban.appl.Settings;
import com.urban.data.User;

/**
 * Created by MetallFoX on 06.12.2014.
 */
public class EmailView extends ContactView {

    private final OnClickListener PHONE_CLICK_LISTENER = new OnClickListener() {

        @Override
        public void onClick(View view) {
            CustomDialogFragment dialog = new CustomDialogFragment();
            PrototypeView.showDialog(EmailView.this, "Отправка электронного сообщения", "Отправить email на адрес " + getText() + "?");
        }
    };

    public EmailView(Context context) {
        super(context);
        setOnClickListener(PHONE_CLICK_LISTENER);
    }

    public EmailView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(PHONE_CLICK_LISTENER);
    }

    public EmailView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOnClickListener(PHONE_CLICK_LISTENER);
    }

    @Override
    public void onPositive() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        // TODO: добавить почтовый адрес в пользователя.
        User loggedUser = Settings.getLoggedUser();
        if (loggedUser != null) {
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{getText().toString()});
        }
        intent.putExtra(Intent.EXTRA_SUBJECT, "");
        intent.putExtra(Intent.EXTRA_TEXT, "");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //getContext().startActivity(Intent.createChooser(intent, ""));

        getContext().startActivity(intent);
    }

    @Override
    public void onNegative() {

    }
}
