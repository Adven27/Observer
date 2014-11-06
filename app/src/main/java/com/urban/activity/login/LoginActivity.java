package com.urban.activity.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.test.R;
import com.tools.ViewServer;
import com.urban.activity.dashboard.DashboardActivity;
import com.urban.activity.login.task.SignInTask;
import com.urban.activity.registration.RegistrationActivity;
import com.urban.appl.Settings;
import com.urban.data.User;
import com.urban.task.HttpRequestTask;

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

        ViewServer.get(this).addWindow(this);
    }

   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Menu").setIcon(R.drawable.side_menu_button).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
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
        SignInTask task = new SignInTask(LoginActivity.this);
        HttpRequestTask httpTask = new HttpRequestTask(task);
        httpTask.execute(login.getText().toString(), password.getText().toString());
    }
}
