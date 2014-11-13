package com.urban.activity.registration;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.test.R;
import com.tools.ViewServer;
import com.urban.activity.dashboard.DashboardActivity;
import com.urban.activity.task.RegistrationTask;
import com.urban.appl.Settings;
import com.urban.data.User;
import com.urban.validation.ValidationHelper;

import java.util.Date;

import src.com.urban.data.sqlite.pojo.PersonPojo;
import src.com.urban.data.sqlite.pojo.UserPojo;

public class RegistrationActivity extends FragmentActivity {

    private EditText login;
    private EditText password;
    private EditText email;
    private EditText surname;
    private EditText name;
    private EditText secondName;
    private DatePicker birthday;
    private EditText phone;
    private Button register;

    private void addMock() {
        login.setText("admin");
        password.setText("admin");
        email.setText("admin@admin.com");
        surname.setText("x");
        name.setText("x");
        secondName.setText("x");
        phone.setText("921");
    }

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

        addMock();//REMOVE THIS!!! JUST FOR TEST!!

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

    private boolean validateInput() {
        return !(ValidationHelper.isEmpty(login) && ValidationHelper.isEmpty(password)
                && ValidationHelper.isEmpty(surname) && ValidationHelper.isEmpty(name)
                && ValidationHelper.isEmpty(secondName) && ValidationHelper.isEmpty(email)
                && ValidationHelper.isEmpty(phone) && ValidationHelper.isEmpty(birthday));
    }

    private User createUser(String login, String password, String email, String surname, String name, String secondName, Date birthday, String phone) {
        PersonPojo person = new PersonPojo();
        person.setSurname(surname);
        person.setFirstName(name);
        person.setSecondName(secondName);
        person.setAge(25); //TODO: Remove this!!! And remove property from the Pojo! To write calculating method!
        person.setPhone(phone);
        person.setBirthday(birthday);

        UserPojo user = new UserPojo();
        user.setLogin(login);
        user.setPassword(password);
        user.setRegDate(new Date());
        user.setPerson(person);

        return user;
    }

    /**
     * Register button click
     * @param button
     */
    public void onRegister(View button) {
        if (validateInput()) {
            RegistrationTask task = new RegistrationTask(RegistrationActivity.this);

            User user = createUser(
                    login.getText().toString(),
                    password.getText().toString(),
                    email.getText().toString(),
                    surname.getText().toString(),
                    name.getText().toString(),
                    secondName.getText().toString(),
                    null,
                    phone.getText().toString()
            );

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, user);
            } else {
                task.execute(user);
            }

        } else {
            Toast.makeText(RegistrationActivity.this, "Not all fields were filled", Toast.LENGTH_LONG).show();
        }
    }

}
