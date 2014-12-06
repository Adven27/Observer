package com.urban.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.tools.PrototypeView;

/**
 * Created by MetallFoX on 06.12.2014.
 */
public class UrbanActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PrototypeView.switchActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        PrototypeView.switchActivity(this);
    }
}
