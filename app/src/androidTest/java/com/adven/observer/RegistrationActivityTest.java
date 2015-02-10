package com.adven.observer;

import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.api.runners.ZohhakRunner;
import com.urban.validation.RegistrationPolicy;

import junit.framework.Assert;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by MetallFoX on 2/2/15.
 */
@RunWith(ZohhakRunner.class)
public class RegistrationActivityTest {

    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MAX_PASSWORD_LENGTH = 20;
    private static final int MAX_LOGIN_LENGTH = 20;

    private  static RegistrationPolicy registrationPolicy;
            
    @BeforeClass    
    public static void setUp() throws Exception {
        registrationPolicy = new RegistrationPolicy(MIN_LOGIN_LENGTH, MAX_LOGIN_LENGTH, 
                                                    MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH);
    }

    @Test
    public void loginShouldBeNotNull() throws Exception {
        Assert.assertFalse(registrationPolicy.validateLogin(null));
    }

    @Test
    public void loginShouldBeNotEmpty() throws Exception {
        Assert.assertFalse(registrationPolicy.validateLogin(""));
    }

    @Test(expected=IllegalArgumentException.class)
    public void loginMinLengthShouldNotBeGreaterThanMaxLength() throws Exception {
        int minLoginLength = 5;
        int maxLoginLength = 4;
        int someCorrectMinPasswordLength = 1;
        int someCorrectMaxPasswordLength = 1;

        new RegistrationPolicy(minLoginLength, maxLoginLength,
                someCorrectMinPasswordLength, someCorrectMaxPasswordLength);
    }

    @Test
    public void loginShouldNotBeLessThanMinAllowed() throws Exception {
        String minAllowed =
                RandomStringUtils.randomAlphanumeric(registrationPolicy.getMinLoginLength());
        String tooShort = minAllowed.substring(1);

        Assert.assertTrue(registrationPolicy.validateLogin(minAllowed));
        Assert.assertFalse(registrationPolicy.validateLogin(tooShort));
    }

    @Test
    public void loginShouldNotBeLongerThanMaxAllowed() throws Exception {
        String maxAllowed =
                RandomStringUtils.randomAlphanumeric(registrationPolicy.getMaxLoginLength());
        String tooLong = maxAllowed + "anyAlphanumeric";

        Assert.assertTrue(registrationPolicy.validateLogin(maxAllowed));
        Assert.assertFalse(registrationPolicy.validateLogin(tooLong));
    }

    @TestWith({"1, 4, 1", "1, 4, 2", "1, 4, 3", "1, 4, 4"})
    public void loginLengthValidExamples(int min, int max, int actual) throws Exception {
        int someCorrectMinPasswordLength = 1;
        int someCorrectMaxPasswordLength = 1;
        String login = RandomStringUtils.randomAlphanumeric(actual);
        RegistrationPolicy policy = new RegistrationPolicy(min, max,
                someCorrectMinPasswordLength, someCorrectMaxPasswordLength);

        Assert.assertTrue(policy.validateLogin(login));
    }

    @Test
    public void passwordShouldBeNotNull() throws Exception {
        Assert.assertFalse(registrationPolicy.validatePassword(null));
    }

    @Test
    public void passwordShouldBeNotEmpty() throws Exception {
        Assert.assertFalse(registrationPolicy.validatePassword(""));
    }

    @Ignore
    public void passwordShouldBeLongerThan7() throws Exception {
        Assert.assertFalse(registrationPolicy.validatePassword("1234567"));
    }

    @TestWith({"123,false", "1234567,false","123456789, true"})
    public void passwordShouldBeLongerThan7Chars(String pass, boolean expected) throws Exception {
        Assert.assertEquals(expected, registrationPolicy.validatePassword(pass));
    }

    @Test
    public void passwordShouldBeLessThan21() throws Exception {
        Assert.assertFalse(registrationPolicy.validatePassword("123456789012345678901"));
    }

    @TestWith({"12345678","123456789"})
    public void validPasswordExamples(String pass) throws Exception {
        Assert.assertTrue(registrationPolicy.validatePassword(pass.toString()));
    }


    @Test
    public void nameShouldBeNotNull() throws Exception {
        Assert.assertFalse(registrationPolicy.validateName(null));
    }

    @Test
    public void nameShouldBeNotEmpty() throws Exception {
        Assert.assertFalse(registrationPolicy.validateName(""));
    }

    @TestWith({"123", "Aaa123", "Aaa123Aaa"})
    public void validNameExamples(String name) throws Exception {
        Assert.assertTrue(registrationPolicy.validateName(name));
    }


    @Test
    public void surnameShouldBeNotNull() throws Exception {
        Assert.assertFalse(registrationPolicy.validateSurname(null));
    }

    @Test
    public void surnameShouldBeNotEmpty() throws Exception {
        Assert.assertFalse(registrationPolicy.validateSurname(""));
    }

    @TestWith({"123", "Aaa123", "Aaa123Aaa"})
    public void validSurnameExamples(String name) throws Exception {
        Assert.assertTrue(registrationPolicy.validateSurname(name));
    }

    @Test
    public void secondNameShouldBeNotNull() throws Exception {
        Assert.assertFalse(registrationPolicy.validateSecondName(null));
    }

    @Test
    public void secondNameShouldBeNotEmpty() throws Exception {
        Assert.assertFalse(registrationPolicy.validateSecondName(""));
    }

    @TestWith({"123", "Aaa123", "Aaa123Aaa"})
    public void validSecondNameExamples(String name) throws Exception {
        Assert.assertTrue(registrationPolicy.validateSecondName(name));
    }

    @Test
    public void phoneShouldBeNotNull() throws Exception {
        Assert.assertFalse(registrationPolicy.validatePhone(null));
    }

    @Test
    public void phoneShouldBeNotEmpty() throws Exception {
        Assert.assertFalse(registrationPolicy.validatePhone(""));
    }

    @TestWith(value = {"z1234567890", "-1234567890",
            "' 1234567890'"}, stringBoundary="'") //apostrophes are needed in order to keep leading white space
    public void phoneShouldStartFromDigitOrPlus(String phone) throws Exception {
        Assert.assertFalse(registrationPolicy.validatePhone(phone));
    }

    @TestWith({"8z234567890", "+7z234567890", "8@234567890", "+7-234567890"})
    public void phoneShouldContainOnlyNumbersExceptFirstChar(String phone) throws Exception {
        Assert.assertFalse(registrationPolicy.validatePhone(phone));
    }

    @TestWith({"+71234567890", "81234567890"})
    public void validPhoneExamples(String phone) throws Exception {
        Assert.assertTrue(registrationPolicy.validatePhone(phone));
    }

    @Test
    public void birthdayShouldBeNotNull() throws Exception {
        Assert.assertFalse(registrationPolicy.validateBirthday(null));
    }

    @Test
    public void birthdayShouldNotBeInFuture() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        Date tomorrow = calendar.getTime();
        Assert.assertFalse(registrationPolicy.validateBirthday(tomorrow));
    }

    @Test
    public void birthdayCanBeToday() throws Exception {
        Assert.assertTrue(registrationPolicy.validateBirthday(new Date()));
    }
}