package com.urban.activity.task;

import android.os.AsyncTask;

import com.urban.activity.registration.RegistrationActivity;
import com.urban.data.User;
import com.urban.data.dao.DAO;
import com.urban.service.urban.UrbanServiceFactory;
import com.urban.service.urban.exception.UrbanServiceException;

import java.lang.ref.WeakReference;
import java.sql.SQLException;

public class RegistrationTask extends AsyncTask<User, Void, String> {

    private static final int NUMBER_OF_PARAMETERS = 8;

    private WeakReference<RegistrationActivity> parent;

    private String errorMsg;
    private User loggedUser;

    public RegistrationTask(RegistrationActivity parent) {
        super();
        this.parent = new WeakReference<RegistrationActivity>(parent);
    }

    @Override
    protected String doInBackground(User... users) {
        try {
            if (users.length == 1) {
                UrbanServiceFactory.getInstance().register(users[0]);
                loggedUser = users[0];
            } else {
                registerError("Wrong number of parameters");
            }
        } catch (UrbanServiceException e) {
            registerError(e.getMessage());
        }
        return null;
    }

    @Override
    public void onPostExecute(String result) {
        RegistrationActivity activity = parent.get();
        if (activity != null) {
            if (errorMsg != null) {
                activity.notify(errorMsg);
            } else {
                if (loggedUser != null) {
                    //TODO: надо что-то сделать с транзакциями единого DAO. Наверное, написать получение, закрытие и их стэк.
                    //Transaction trn = null;
                    try {
                        //trn = DAO.beginTransaction();
                        DAO.deleteAll(User.class);
                        DAO.save(loggedUser);
                        //trn.commit;
                        activity.logIn(loggedUser);
                    } catch (SQLException e) {
                        activity.notify("Problem with saving to DAO!");
                    } catch (Exception e) {
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
