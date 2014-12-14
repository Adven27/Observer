package com.urban.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.tools.PrototypeView;
import com.urban.appl.Settings;
import com.urban.data.User;
import com.urban.data.dao.DAO;

import java.util.List;

/**
 * Created by MetallFoX on 06.12.2014.
 */
public class UrbanActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PrototypeView.switchActivity(this);
        initUser();
    }

    @Override
    protected void onResume() {
        super.onResume();
        PrototypeView.switchActivity(this);
        initUser();
    }

    private void initUser() {
        if (Settings.getLoggedUser() == null) {
            List<User> users = (List<User>) DAO.getAll(User.class);
            if (!users.isEmpty()) {
                Settings.setLoggedUser(users.get(0));
            }
        }
    }
}
