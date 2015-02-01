package com.urban.activity.userproperties;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.urban.activity.UrbanActivity;
import com.urban.activity.dashboard.DashboardActivity;
import com.urban.activity.task.UpdateUserTask;
import com.urban.appl.Settings;
import com.urban.data.User;
import com.urban.observer.R;

public class UserPropertiesActivity extends UrbanActivity {

    private EditText login;
    private EditText password;
    private EditText email;
    private EditText surname;
    private EditText name;
    private EditText secondName;
    private DatePicker birthday;
    private EditText phone;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        login = (EditText)findViewById(R.id.anket_login);
        password = (EditText)findViewById(R.id.anket_password);
        email = (EditText)findViewById(R.id.anket_email);
        surname = (EditText)findViewById(R.id.anket_surname);
        name = (EditText)findViewById(R.id.anket_name);
        secondName = (EditText)findViewById(R.id.anket_second_name);
        birthday = (DatePicker)findViewById(R.id.anket_birthday);
        phone = (EditText)findViewById(R.id.anket_phone);
        register = (Button)findViewById(R.id.anket_register);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        return super.onMenuItemSelected(featureId, item);
    }

    public void logIn(User user) {
        notify("You was entered!");
        redirect();
    }

    public void notify(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    private void redirect() {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }

    private boolean validateClient() {
        return !(isEmpty(login) && isEmpty(password) && isEmpty(surname) && isEmpty(name)
                && isEmpty(secondName) && isEmpty(email) && isEmpty(phone) && isEmpty(birthday));
    }

    private boolean isEmpty(TextView view) {
        return "".equals(view.getText().toString());
    }

    private boolean isEmpty(DatePicker view) {
        return false;
    }

    /**
     * Register button click
     * @param button
     */
    public void onSave(View button) {
        if (validateInput()) {
            //TODO: fix this null! Write parent class or interface for all Activities!
            UpdateUserTask task = new UpdateUserTask(null);
            //TODO: Implement updating of user fields!
            User user = Settings.getLoggedUser();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, user);
            } else {
                task.execute(user);
            }

            task.execute(Settings.getLoggedUser());

        } else {
            Toast.makeText(UserPropertiesActivity.this, "Not all fields were filled", Toast.LENGTH_LONG).show();
        }
    }

    private boolean validateInput() {
        /*return !(ValidationHelper.isEmpty(login) && ValidationHelper.isEmpty(password)
                && ValidationHelper.isEmpty(surname) && ValidationHelper.isEmpty(name)
                && ValidationHelper.isEmpty(secondName) && ValidationHelper.isEmpty(email)
                && ValidationHelper.isEmpty(phone) && ValidationHelper.isEmpty(birthday));*/

        return true;
    }
}
