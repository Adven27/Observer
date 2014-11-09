package com.urban.service.urban.impl.stub;

import com.urban.data.News;
import com.urban.data.Position;
import com.urban.data.User;
import com.urban.service.urban.UrbanService;
import com.urban.service.urban.exception.UrbanServiceException;

import java.util.Collection;
import java.util.Date;

import src.com.urban.data.sqlite.pojo.PersonPojo;
import src.com.urban.data.sqlite.pojo.UserPojo;

/**
 * Created by MetallFoX on 06.11.2014.
 */
public class UrbanServiceStubImpl implements UrbanService {

    @Override
    public User signIn(String login, String password) {
        return getStubUser(login, password);
    }

    @Override
    public void register(User user) {

    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public void subscribe(User user, Position position) {

    }

    @Override
    public Collection<News> getNews() throws UrbanServiceException {
        return null;
    }

    private User getStubUser(String login, String password) {
        UserPojo user = new UserPojo();
        user.setLogin(login);
        user.setPassword(password);
        user.setRegDate(new Date());
        user.setRegId("00000");
        user.setIMEI(1111);
        user.setIsBlocked(0);

        PersonPojo person = new PersonPojo();
        person.setAge(25);
        person.setBirthday(new Date());
        person.setFirstName("x");
        person.setSurname("x");
        person.setSecondName("x");

        user.setPerson(person);
        return user;
    }

}