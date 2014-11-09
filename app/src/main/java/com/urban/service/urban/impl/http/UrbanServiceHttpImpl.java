package com.urban.service.urban.impl.http;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.urban.data.News;
import com.urban.data.Position;
import com.urban.data.ResponseError;
import com.urban.data.User;
import com.urban.service.urban.UrbanService;
import com.urban.service.urban.exception.UrbanServiceException;
import com.urban.service.urban.exception.UrbanServiceOfflineException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import flexjson.JSONDeserializer;
import src.com.urban.data.sqlite.pojo.UserPojo;

/**
 * Created by MetallFoX on 06.11.2014.
 */
public class UrbanServiceHttpImpl implements UrbanService {

    private static final String ATTR_ERROR_CODE = "errorCode";
    private static final String ATTR_ERROR_TEXT = "errorText";
    private static final String ATTR_ERROR_TYPE = "errorType";

    @Override
    public User signIn(String login, String password) throws UrbanServiceException {
        User user = new UserPojo();
        user.setLogin(login);
        user.setPassword(password);
        String request = JsonHelper.toJSON(user);

        String response = send(request, "signIn");

        User loggedUser = JsonHelper.fromJSON(User.class, response);
        return loggedUser;
    }

    @Override
    public void register(User user) throws UrbanServiceException {
        String request = JsonHelper.toJSON(user);
        send(request, "register");
    }

    @Override
    public void updateUser(User user) throws UrbanServiceException {
        String request = JsonHelper.toJSON(user);
        send(request, "updateUser");
    }

    @Override
    public void subscribe(User user, Position position) throws UrbanServiceException {
        String request = JsonHelper.toJSON(user);
        send(request, "subscribe");
    }

    @Override
    public Collection<News> getNews() throws UrbanServiceException {
        String request = JsonHelper.toJSON(null);
        send(request, "");
        return new ArrayList<News>();
    }

    private String send(String request, String operationURL) throws UrbanServiceException {
        String response = POST(request, UrbanServiceHttpSettings.getURLBase() + operationURL);

        Gson gson = new Gson();
        Type collectionType = new TypeToken<HashMap<String, Object>>(){}.getType();
        HashMap<String, Object> map = gson.fromJson(response, collectionType);
        if (map.containsKey(ATTR_ERROR_CODE)) {
            ResponseError error = new JSONDeserializer<ResponseError>().deserialize(response, ResponseError.class);
            throw new UrbanServiceException(error);
        }
        return response;
    }


    private String POST(String json, String url) throws UrbanServiceOfflineException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        StringEntity se = null;
        HttpResponse httpResponse = null;
        try {
            se = new StringEntity(json);
            httpPost.setEntity(se);
            httpResponse = httpClient.execute(httpPost);
        } catch (Throwable e) {
            /**
             * Catching of Throwable 'cause external interaction must not affect
             * on application stability.
             */
            throw new UrbanServiceOfflineException("Server is offline");
        }

        return readResponse(httpResponse);
    }

    private static String readResponse(HttpResponse httpResponse) {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
            String line = null;
            while((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            //TODO: log this.
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //TODO: log this.
                }
            }
        }
        return builder.toString();
    }
}
