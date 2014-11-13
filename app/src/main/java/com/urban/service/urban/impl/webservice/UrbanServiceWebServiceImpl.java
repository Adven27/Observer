package com.urban.service.urban.impl.webservice;

import com.urban.data.News;
import com.urban.data.Position;
import com.urban.data.User;
import com.urban.service.urban.UrbanService;
import com.urban.service.urban.exception.UrbanServiceException;

import java.util.Collection;

/**
 * Created by MetallFoX on 06.11.2014.
 */
public class UrbanServiceWebServiceImpl implements UrbanService {

    @Override
    public User signIn(String login, String password) {
        return null;
    }

    @Override
    public void register(User user) {}

    @Override
    public void updateUser(User user) {}

    @Override
    public void subscribe(User user, Position position) {}

    @Override
    public void unsubscribe(User user, Position position) {}

    @Override
    public Collection<News> getNews() throws UrbanServiceException {
        return null;
    }

}
