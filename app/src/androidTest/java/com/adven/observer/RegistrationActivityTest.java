package com.adven.observer;

import android.app.Activity;

import com.urban.activity.registration.RegistrationActivity;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by MetallFoX on 2/2/15.
 */
@RunWith(MyRunner.class)
public class RegistrationActivityTest {

    @Test
    public void shouldBeATest() throws Exception {
        Activity activity = Robolectric.buildActivity(RegistrationActivity.class).create().get();
    }

    @Test
    public void loginShouldBeNotNull() throws Exception {
        Assert.assertFalse(RegistrationActivity.validateLogin(null));
    }

    @Test
    public void loginShouldBeNotEmpty() throws Exception {
        Assert.assertFalse(RegistrationActivity.validateLogin(""));
    }

    @Test
    public void loginShouldBeLongerThan5() throws Exception {
        Assert.assertFalse(RegistrationActivity.validateLogin("12345"));
    }

    @Test
    public void loginShouldBeLessThan21() throws Exception {
        Assert.assertFalse(RegistrationActivity.validateLogin("123456789012345678901"));
    }

    @Test
    public void validLoginExamples() throws Exception {
        StringBuilder login = new StringBuilder("12345");
        int i = 6;
        while (login.length() < 20) {
            login.append(i);
            i = ++i % 10;
            Assert.assertTrue(RegistrationActivity.validateLogin(login.toString()));
        }
    }

    @Test
    public void passwordShouldBeNotNull() throws Exception {
        Assert.assertFalse(RegistrationActivity.validatePassword(null));
    }

    @Test
    public void passwordShouldBeNotEmpty() throws Exception {
        Assert.assertFalse(RegistrationActivity.validatePassword(""));
    }

    @Test
    public void passwordShouldBeLongerThan7() throws Exception {
        Assert.assertFalse(RegistrationActivity.validatePassword("1234567"));
    }

    @Test
    public void passwordShouldBeLessThan21() throws Exception {
        Assert.assertFalse(RegistrationActivity.validatePassword("123456789012345678901"));
    }

    @Test
    public void validPasswordExamples() throws Exception {
        StringBuilder password = new StringBuilder("1234567");
        int i = 8;
        while (password.length() < 20) {
            password.append(i);
            i = ++i % 10;
            Assert.assertTrue(RegistrationActivity.validatePassword(password.toString()));
        }
    }


    @Test
    public void nameShouldBeNotNull() throws Exception {
        Assert.assertFalse(RegistrationActivity.validateName(null));
    }

    @Test
    public void nameShouldBeNotEmpty() throws Exception {
        Assert.assertFalse(RegistrationActivity.validateName(""));
    }

    @Test
    public void validNameExamples() throws Exception {
        Assert.assertTrue(RegistrationActivity.validateName("123"));
        Assert.assertTrue(RegistrationActivity.validateName("Aaa123"));
        Assert.assertTrue(RegistrationActivity.validateName("Aaa123Aaa"));
    }


    @Test
    public void surnameShouldBeNotNull() throws Exception {
        Assert.assertFalse(RegistrationActivity.validateSurname(null));
    }

    @Test
    public void surnameShouldBeNotEmpty() throws Exception {
        Assert.assertFalse(RegistrationActivity.validateSurname(""));
    }

    @Test
    public void validSurnameExamples() throws Exception {
        Assert.assertTrue(RegistrationActivity.validateSurname("123"));
        Assert.assertTrue(RegistrationActivity.validateSurname("Aaa123"));
        Assert.assertTrue(RegistrationActivity.validateSurname("Aaa123Aaa"));
    }

    @Test
    public void secondNameShouldBeNotNull() throws Exception {
        Assert.assertFalse(RegistrationActivity.validateSecondName(null));
    }

    @Test
    public void secondNameShouldBeNotEmpty() throws Exception {
        Assert.assertFalse(RegistrationActivity.validateSecondName(""));
    }

    @Test
    public void validSecondNameExamples() throws Exception {
        Assert.assertTrue(RegistrationActivity.validateSecondName("123"));
        Assert.assertTrue(RegistrationActivity.validateSecondName("Aaa123"));
        Assert.assertTrue(RegistrationActivity.validateSecondName("Aaa123Aaa"));
    }

    @Test
    public void phoneShouldBeNotNull() throws Exception {
        Assert.assertFalse(RegistrationActivity.validatePhone(null));
    }

    @Test
    public void phoneShouldBeNotEmpty() throws Exception {
        Assert.assertFalse(RegistrationActivity.validatePhone(""));
    }

    @Test
    public void phoneShouldStartFromDigitOrPlus() throws Exception {
        Assert.assertFalse(RegistrationActivity.validatePhone("z1234567890"));
        Assert.assertFalse(RegistrationActivity.validatePhone("-1234567890"));
        Assert.assertFalse(RegistrationActivity.validatePhone(" 1234567890"));
    }

    @Test
    public void phoneShouldContainOnlyNumbersExceptFirstChar() throws Exception {
        Assert.assertFalse(RegistrationActivity.validatePhone("8z234567890"));
        Assert.assertFalse(RegistrationActivity.validatePhone("+7z234567890"));
        Assert.assertFalse(RegistrationActivity.validatePhone("8@234567890"));
        Assert.assertFalse(RegistrationActivity.validatePhone("+7-234567890"));
    }

    @Test
    public void validPhoneExamples() throws Exception {
        Assert.assertTrue(RegistrationActivity.validatePhone("+71234567890"));
        Assert.assertTrue(RegistrationActivity.validatePhone("81234567890"));
    }

    @Test
    public void birthdayShouldBeNotNull() throws Exception {
        Assert.assertFalse(RegistrationActivity.validateBirthday(null));
    }

    @Test
    public void birthdayShouldBeNotAfterToday() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        Date tomorrow = calendar.getTime();
        Assert.assertFalse(RegistrationActivity.validateBirthday(tomorrow));
    }

    @Test
    public void validDateExamples() throws Exception {
        Assert.assertTrue(RegistrationActivity.validateBirthday(new Date()));
    }

}
