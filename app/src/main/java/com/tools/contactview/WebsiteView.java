package com.tools.contactview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;

import com.tools.PrototypeView;
import com.tools.dialogs.SimpleDialog;

/**
 * Created by MetallFoX on 06.12.2014.
 */
public class WebsiteView extends ContactView {

    private final OnClickListener WEBSITE_CLICK_LISTENER = new OnClickListener() {

        @Override
        public void onClick(View view) {
            SimpleDialog dialog = SimpleDialog.getInstance(
                    WebsiteView.this,
                    "Открытие веб-сай",
                    "Открыть в браузере страницу " + getText() + "?");

            PrototypeView.showDialog(dialog);
        }
    };

    public WebsiteView(Context context) {
        super(context);
        setOnClickListener(WEBSITE_CLICK_LISTENER);
    }

    public WebsiteView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(WEBSITE_CLICK_LISTENER);
    }

    public WebsiteView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOnClickListener(WEBSITE_CLICK_LISTENER);
    }

    @Override
    public void onPositive() {
        String url = "http://" + getText();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse(url));
        getContext().startActivity(intent);
    }

    @Override
    public void onNegative() {

    }
}
