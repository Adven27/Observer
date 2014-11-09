package com.urban.service.urban;

import com.urban.data.News;
import com.urban.data.Position;
import com.urban.data.User;
import com.urban.service.urban.exception.UrbanServiceException;

import java.util.Collection;

/**
 * Created by MetallFoX on 06.11.2014.
 */
public interface UrbanService {

    public User signIn(String login, String password) throws UrbanServiceException;

    public void register(User user) throws UrbanServiceException;

    public void updateUser(User user) throws UrbanServiceException;

    public void subscribe(User user, Position position) throws UrbanServiceException;

    public Collection<News> getNews() throws UrbanServiceException;
}
