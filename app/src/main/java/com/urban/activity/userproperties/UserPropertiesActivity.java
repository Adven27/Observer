package com.urban.activity.userproperties;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.R;
import com.tools.ViewServer;
import com.urban.activity.dashboard.DashboardActivity;
import com.urban.activity.userproperties.task.UserPropertiesTask;
import com.urban.data.User;
import com.urban.task.HttpRequestTask;

public class UserPropertiesActivity extends FragmentActivity {

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

        register.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (validateClient()) {
                    UserPropertiesTask task = new UserPropertiesTask(null);
                    HttpRequestTask httpTask = new HttpRequestTask(task);
                    httpTask.execute(
                            login.getText().toString(),
                            password.getText().toString(),
                            email.getText().toString(),
                            surname.getText().toString(),
                            name.getText().toString(),
                            secondName.getText().toString(),
                            null,
                            phone.getText().toString());
                } else {
                    Toast.makeText(UserPropertiesActivity.this, "Not all fields were filled", Toast.LENGTH_LONG).show();
                }
            }
        });

        ViewServer.get(this).addWindow(this);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        return super.onMenuItemSelected(featureId, item);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        ViewServer.get(this).removeWindow(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewServer.get(this).setFocusedWindow(this);
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

}
