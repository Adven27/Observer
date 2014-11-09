package com.urban.activity.task;

import android.os.AsyncTask;

import com.urban.activity.login.LoginActivity;
import com.urban.data.User;
import com.urban.data.dao.DAO;
import com.urban.service.urban.UrbanServiceFactory;
import com.urban.service.urban.exception.UrbanServiceException;

import java.lang.ref.WeakReference;
import java.sql.SQLException;

public class SignInTask extends AsyncTask<String, Void, User> {

    private static final int NUMBER_OF_PARAMETERS = 2;

    private WeakReference<LoginActivity> parent;

    private String errorMsg;

    public SignInTask(LoginActivity parent) {
        super();
        this.parent = new WeakReference<LoginActivity>(parent);
    }

    @Override
    protected User doInBackground(String... params) {
        try {
            if (params.length == NUMBER_OF_PARAMETERS) {
                return UrbanServiceFactory.getInstance().signIn(params[0], params[0]);
            } else {
                registerError("Wrong number of parameters");
            }
        } catch (UrbanServiceException e) {
            registerError(e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(User loggedUser) {
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

    public void registerError(String error) {
        this.errorMsg = error;
    }
}
