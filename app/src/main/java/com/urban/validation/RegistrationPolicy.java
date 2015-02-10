package com.urban.validation;

import java.util.Calendar;
import java.util.Date;

public class RegistrationPolicy {

    private final int minLoginLength;
    private final int minPasswordLength;
    private final int maxPasswordLength;
    private final int maxLoginLength;


    public int getMinLoginLength() {
        return minLoginLength;
    }

    public int getMinPasswordLength() {
        return minPasswordLength;
    }

    public int getMaxPasswordLength() {
        return maxPasswordLength;
    }

    public int getMaxLoginLength() {
        return maxLoginLength;
    }

    public RegistrationPolicy(int minLoginLength, int maxLoginLength, int minPasswordLength, int maxPasswordLength) {
        if (minLoginLength > maxLoginLength || minPasswordLength > maxPasswordLength ) {
            throw new IllegalArgumentException();
        }
        this.minLoginLength = minLoginLength;
        this.minPasswordLength = minPasswordLength;
        this.maxPasswordLength = maxPasswordLength;
        this.maxLoginLength = maxLoginLength;
    }

    public boolean validatePassword(String password) {
        return !ValidationHelper.isEmpty(password)
                && password.length() >= minPasswordLength && password.length() <= maxPasswordLength;
    }

    public boolean validatePhone(String phone) {
        return !ValidationHelper.isEmpty(phone) && phone.matches("^(\\+7||8)\\d{10}$");
    }

    public boolean validateEmail(String email) {
        return !ValidationHelper.isEmpty(email);
    }

    public boolean validateSurname(String surname) {
        return !ValidationHelper.isEmpty(surname);
    }

    public boolean validateName(String name) {
        return !ValidationHelper.isEmpty(name);
    }

    public boolean validateSecondName(String secondName) {
        return !ValidationHelper.isEmpty(secondName);
    }

    public boolean validateBirthday(Date birthday) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        Date tomorrow = calendar.getTime();
        return !ValidationHelper.isEmpty(birthday) && birthday.before(tomorrow);
    }

    public boolean validateLogin(String login) {
        return !ValidationHelper.isEmpty(login)
                && login.length() >= minLoginLength && login.length() <= maxLoginLength;
    }

    public boolean validateInput(String login, String password,
                                  String surname, String name, String secondName,
                                  String email, String phone, Date birthday) {
        return !validateLogin(login)
                && validatePassword(password)

                && validateSurname(surname)
                && validateName(name)
                && validateSecondName(secondName)

                && validateEmail(email)
                && validatePhone(phone)
                && validateBirthday(birthday);
    }
}
