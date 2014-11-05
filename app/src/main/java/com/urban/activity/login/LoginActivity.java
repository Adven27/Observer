package com.urban.activity.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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
    private Button signIn;
    private Button dismiss;
    private Button register;

    private SharedPreferences prefs;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        context = getApplicationContext();

        login = (EditText)findViewById(R.id.login);
        password = (EditText)findViewById(R.id.password);
        signIn = (Button)findViewById(R.id.signIn);
        dismiss = (Button)findViewById(R.id.dismiss);
        register = (Button)findViewById(R.id.register);

        //Dummy data for test.
        setDummy();

        signIn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                SignInTask task = new SignInTask(LoginActivity.this);
                HttpRequestTask httpTask = new HttpRequestTask(task);
                httpTask.execute(login.getText().toString(), password.getText().toString());

            }
        });

        dismiss.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                redirect();
            }
        });

        register.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                redirectToRegistration();
            }
        });

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

    private void redirectToRegistration() {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    private void setDummy() {
        login.setText("admin");
        password.setText("admin");
    }

}
