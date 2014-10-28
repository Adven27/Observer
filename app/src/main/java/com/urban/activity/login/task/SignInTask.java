package com.urban.activity.login.task;

import java.lang.ref.WeakReference;

import src.com.urban.data.sqlite.pojo.UserPojo;

import com.google.gson.Gson;
import com.urban.activity.login.LoginActivity;
import com.urban.data.User;
import com.urban.task.HttpTask;

public class SignInTask implements HttpTask {

    WeakReference<LoginActivity> parent;

    public SignInTask(LoginActivity parent) {
        super();
        this.parent = new WeakReference<LoginActivity>(parent);
    }

    private User loggedUser;

    @Override
    public Object prepareRequestData(String... params) {
        if (params.length == 2) {
            return signIn(params[0], params[1]);
        }
        return null;
    }

    private User signIn(String login, String password) {
        User user = new UserPojo();
        user.setLogin(login);
        user.setPassword(password);
        return user;
    }

    @Override
    public void handleResponse(String response) {
        Gson gson = new Gson();
        User user = gson.fromJson(response, UserPojo.class);
        loggedUser = user;
    }

    @Override
    public void onPostExecute() {
        LoginActivity activity = parent.get();
        if (activity != null) {
            if (loggedUser != null) {
                activity.logIn(loggedUser);
            } else {
                activity.notify("ololo!");
            }
        }
    }

    @Override
    public String getURL() {
        return "ServicesTomcatWAR/signIn";
    }

    @Override
    public void registerError(String error) {
        LoginActivity activity = parent.get();
        if (activity != null) {
            activity.notify("Server is offline!");
        }

    }

}
