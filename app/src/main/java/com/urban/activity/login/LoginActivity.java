package com.urban.activity.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.test.R;
import com.urban.activity.dashboard.DashboardActivity;
import com.urban.activity.registration.RegistrationActivity;
import com.urban.activity.task.SignInTask;
import com.urban.appl.Settings;
import com.urban.data.User;
import com.urban.validation.ValidationHelper;

public class LoginActivity extends FragmentActivity {

    private EditText login;
    private EditText password;

    private SharedPreferences prefs;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        context = getApplicationContext();
        login = (EditText)findViewById(R.id.login);
        password = (EditText)findViewById(R.id.password);

        //TODO: Stub
        setDummy();
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        return super.onMenuItemSelected(featureId, item);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void logIn(User user) {
        Settings.setLoggedUser(user);
        notify("You were entered!");
        redirect();
    }

    public void notify(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    private void redirect() {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }

    private void setDummy() {
        login.setText("admin");
        password.setText("admin");
    }

    /**
     * Register button click
     * @param button
     */
    public void onRegister(View button) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    /**
     * Dismiss button click
     * @param button
     */
    public void onDismiss(View button) {
        redirect();
    }

    /**
     * SignIn button click
     * @param button
     */
    public void onSignIn(View button) {
        if (validateInput()) {
            SignInTask task = new SignInTask(LoginActivity.this);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, login.getText().toString(), password.getText().toString());
            } else {
                task.execute(login.getText().toString(), password.getText().toString());
            }
        } else {
            Toast.makeText(LoginActivity.this, "Not all fields were filled", Toast.LENGTH_LONG).show();
        }
    }

    private boolean validateInput() {
        return !(ValidationHelper.isEmpty(login) && ValidationHelper.isEmpty(password));
    }

}
