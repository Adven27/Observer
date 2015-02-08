package com.adven.observer;

import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.api.runners.ZohhakRunner;
import com.urban.activity.registration.RegistrationActivity;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by MetallFoX on 2/2/15.
 */
@RunWith(ZohhakRunner.class)
public class RegistrationActivityTest {

    /*@BeforeClass
    //TODO: похоже не нужно... да и падает с ним... =)
    public static void setUp() throws Exception {
        Activity activity = Robolectric.buildActivity(RegistrationActivity.class).create().get();
    }*/

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

    @TestWith({"123456", "123457", "123458"})
    public void validLoginExamples(String login) throws Exception {
        Assert.assertTrue(RegistrationActivity.validateLogin(login));
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

    @TestWith({"12345678","123456789"})
    public void validPasswordExamples(String pass) throws Exception {
        Assert.assertTrue(RegistrationActivity.validatePassword(pass.toString()));
    }


    @Test
    public void nameShouldBeNotNull() throws Exception {
        Assert.assertFalse(RegistrationActivity.validateName(null));
    }

    @Test
    public void nameShouldBeNotEmpty() throws Exception {
        Assert.assertFalse(RegistrationActivity.validateName(""));
    }

    @TestWith({"123", "Aaa123", "Aaa123Aaa"})
    public void validNameExamples(String name) throws Exception {
        Assert.assertTrue(RegistrationActivity.validateName(name));
    }


    @Test
    public void surnameShouldBeNotNull() throws Exception {
        Assert.assertFalse(RegistrationActivity.validateSurname(null));
    }

    @Test
    public void surnameShouldBeNotEmpty() throws Exception {
        Assert.assertFalse(RegistrationActivity.validateSurname(""));
    }

    @TestWith({"123", "Aaa123", "Aaa123Aaa"})
    public void validSurnameExamples(String name) throws Exception {
        Assert.assertTrue(RegistrationActivity.validateSurname(name));
    }

    @Test
    public void secondNameShouldBeNotNull() throws Exception {
        Assert.assertFalse(RegistrationActivity.validateSecondName(null));
    }

    @Test
    public void secondNameShouldBeNotEmpty() throws Exception {
        Assert.assertFalse(RegistrationActivity.validateSecondName(""));
    }

    @TestWith({"123", "Aaa123", "Aaa123Aaa"})
    public void validSecondNameExamples(String name) throws Exception {
        Assert.assertTrue(RegistrationActivity.validateSecondName(name));
    }

    @Test
    public void phoneShouldBeNotNull() throws Exception {
        Assert.assertFalse(RegistrationActivity.validatePhone(null));
    }

    @Test
    public void phoneShouldBeNotEmpty() throws Exception {
        Assert.assertFalse(RegistrationActivity.validatePhone(""));
    }

    @TestWith(value = {"z1234567890", "-1234567890",
            "' 1234567890'"}, stringBoundary="'") //apostrophes are needed in order to keep leading white space
    public void phoneShouldStartFromDigitOrPlus(String phone) throws Exception {
        Assert.assertFalse(RegistrationActivity.validatePhone(phone));
    }

    @TestWith({"8z234567890", "+7z234567890", "8@234567890", "+7-234567890"})
    public void phoneShouldContainOnlyNumbersExceptFirstChar(String phone) throws Exception {
        Assert.assertFalse(RegistrationActivity.validatePhone(phone));
    }

    @TestWith({"+71234567890", "81234567890"})
    public void validPhoneExamples(String phone) throws Exception {
        Assert.assertTrue(RegistrationActivity.validatePhone(phone));
    }

    @Test
    public void birthdayShouldBeNotNull() throws Exception {
        Assert.assertFalse(RegistrationActivity.validateBirthday(null));
    }

    @Test
    public void birthdayShouldNotBeInFuture() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        Date tomorrow = calendar.getTime();
        Assert.assertFalse(RegistrationActivity.validateBirthday(tomorrow));
    }

    @Test
    public void shouldNotBeInFuture() throws Exception {
        Assert.assertTrue(RegistrationActivity.validateBirthday(new Date()));
    }

}
