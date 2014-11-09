package com.urban.activity.task;

import android.os.AsyncTask;

import com.urban.activity.dashboard.DashboardActivity;
import com.urban.data.User;
import com.urban.service.urban.UrbanServiceFactory;
import com.urban.service.urban.exception.UrbanServiceException;

import java.lang.ref.WeakReference;

public class UpdateUserTask extends AsyncTask<User, Void, String> {

    private WeakReference<DashboardActivity> parent;
    private String errorMsg;

    public UpdateUserTask(DashboardActivity parent) {
        super();
        this.parent = new WeakReference<DashboardActivity>(parent);
    }

    @Override
    protected String doInBackground(User... users) {
        try {
            if (users.length == 1) {
                UrbanServiceFactory.getInstance().updateUser(users[0]);
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
        DashboardActivity activity = parent.get();
        if (activity != null) {
            if (errorMsg != null) {
                activity.notify(errorMsg);
            } else {
                //activity.storeRegistrationId();
            }
        }
    }

    public void registerError(String error) {
        this.errorMsg = error;
    }

}
