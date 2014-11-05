package com.urban.activity.login.task;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.urban.activity.login.LoginActivity;
import com.urban.data.ResponseError;
import com.urban.data.User;
import com.urban.data.dao.DAO;
import com.urban.task.HttpTask;

import java.lang.ref.WeakReference;
import java.sql.SQLException;

import src.com.urban.data.sqlite.pojo.UserPojo;

public class SignInTask implements HttpTask {

    private WeakReference<LoginActivity> parent;

    private User loggedUser;
    private String errorMsg;

    public SignInTask(LoginActivity parent) {
        super();
        this.parent = new WeakReference<LoginActivity>(parent);
    }

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
        try {
            loggedUser = new Gson().fromJson(response, UserPojo.class);
        } catch (JsonSyntaxException e){
            ResponseError error = new Gson().fromJson(response, ResponseError.class);
            registerError(error.getErrorText());
        }
    }

    @Override
    public void onPostExecute() {
        LoginActivity activity = parent.get();
        if (activity != null) {
            if (errorMsg != null) {
                activity.notify(errorMsg);
            } else {
                if (loggedUser != null) {
                    //FIXME: надо что-то сделать с транзакциями единого DAO. Наверное, написать получение, закрытие и их стэк.
                    //Transaction trn = null;
                    try {
                        //trn = DAO.beginTransaction();
                        DAO.deleteAll(User.class);
                        DAO.save(loggedUser);
                        //trn.commit;
                        activity.logIn(loggedUser);
                    } catch (SQLException e) {
                        activity.notify("Problem with saving to DAO!");
                    } finally {
                        /*
                        if (trn != null) {
                            trn.close();
                        }
                        */
                    }
                } else {
                    activity.notify("ololo!");
                }
            }
        }
    }

    @Override
    public String getURL() {
        return "ServicesTomcatWAR/signIn";
    }

    @Override
    public void registerError(String error) {
        this.errorMsg = error;
    }

}
