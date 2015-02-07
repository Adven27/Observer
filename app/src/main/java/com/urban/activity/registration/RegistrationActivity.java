package com.urban.activity.registration;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tools.dialogs.EditTextDatePicker;
import com.urban.activity.UrbanActivity;
import com.urban.activity.dashboard.DashboardActivity;
import com.urban.activity.task.RegistrationTask;
import com.urban.appl.Settings;
import com.urban.data.User;
import com.urban.observer.R;
import com.urban.validation.ValidationHelper;

import java.util.Calendar;
import java.util.Date;

import src.com.urban.data.sqlite.pojo.PersonPojo;
import src.com.urban.data.sqlite.pojo.UserPojo;

public class RegistrationActivity extends UrbanActivity {

    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MAX_PASSWORD_LENGTH = 20;
    private static final int MAX_LOGIN_LENGTH = 20;

    private EditText login;
    private EditText password;
    private EditText email;
    private EditText surname;
    private EditText name;
    private EditText secondName;
    private EditTextDatePicker birthday;
    private EditText phone;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        getActionBar().setTitle("Регистрация");

        login = (EditText)findViewById(R.id.anket_login);
        password = (EditText)findViewById(R.id.anket_password);
        email = (EditText)findViewById(R.id.anket_email);
        surname = (EditText)findViewById(R.id.anket_surname);
        name = (EditText)findViewById(R.id.anket_name);
        secondName = (EditText)findViewById(R.id.anket_second_name);
        birthday = (EditTextDatePicker)findViewById(R.id.anket_birthday);
        phone = (EditText)findViewById(R.id.anket_phone);
        register = (Button)findViewById(R.id.anket_register);

        password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        email.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        phone.setInputType(InputType.TYPE_TEXT_VARIATION_PHONETIC);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        return super.onMenuItemSelected(featureId, item);
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
        return !validateLogin(login.getText().toString())
                && validatePassword(password.getText().toString())

                && validateSurname(surname.getText().toString())
                && validateName(name.getText().toString())
                && validateSecondName(secondName.getText().toString())

                && validateEmail(email.getText().toString())
                && validatePhone(phone.getText().toString())
                && validateBirthday(birthday.getDate());
    }

    private User createUser(String login, String password, String email, String surname, String name, String secondName, Date birthday, String phone) {
        PersonPojo person = new PersonPojo();
        person.setSurname(surname);
        person.setFirstName(name);
        person.setSecondName(secondName);
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

    public static boolean validateLogin(String login) {
        return !ValidationHelper.isEmpty(login)
                && login.length() >= MIN_LOGIN_LENGTH && login.length() <= MAX_LOGIN_LENGTH;
    }

    public static boolean validatePassword(String password) {
        return !ValidationHelper.isEmpty(password)
                && password.length() >= MIN_PASSWORD_LENGTH && password.length() <= MAX_PASSWORD_LENGTH;
    }

    public static boolean validatePhone(String phone) {
        return !ValidationHelper.isEmpty(phone) && phone.matches("^(\\+7||8)\\d{10}$");
    }

    public static boolean validateEmail(String email) {
        return !ValidationHelper.isEmpty(email);
    }

    public static boolean validateSurname(String surname) {
        return !ValidationHelper.isEmpty(surname);
    }

    public static boolean validateName(String name) {
        return !ValidationHelper.isEmpty(name);
    }

    public static boolean validateSecondName(String secondName) {
        return !ValidationHelper.isEmpty(secondName);
    }

    public static boolean validateBirthday(Date birthday) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        Date tomorrow = calendar.getTime();
        return !ValidationHelper.isEmpty(birthday) && birthday.before(tomorrow);
    }

}
