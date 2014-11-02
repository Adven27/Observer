package com.urban.activity.userproperties.task;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.urban.activity.dashboard.DashboardActivity;
import com.urban.appl.Settings;
import com.urban.data.ResponseError;
import com.urban.data.User;
import com.urban.task.HttpTask;

import java.io.IOException;
import java.lang.ref.WeakReference;

import src.com.urban.data.sqlite.pojo.UserPojo;

public class UserPropertiesTask implements HttpTask {

    private WeakReference<DashboardActivity> parent;

    /**
     * Substitute you own sender ID here. This is the project number you got
     * from the API Console, as described in "Getting Started."
     */
    String SENDER_ID = "724387067630";

    private String errorMsg;
    private String regId;

    public UserPropertiesTask(DashboardActivity parent) {
        super();
        this.parent = new WeakReference<DashboardActivity>(parent);
    }



    private User loggedUser;
    private GoogleCloudMessaging gcm;

    @Override
    public Object prepareRequestData(String... params) {

        try {
            if (gcm == null) {
                gcm = GoogleCloudMessaging.getInstance(parent.get().getApplicationContext());
            }
            regId = gcm.register(SENDER_ID);
            User user = Settings.getLoggedUser();
            ((UserPojo)user).setRegId(regId);

            return user;
        } catch (IOException e) {
            return null;
        }
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
        DashboardActivity activity = parent.get();
        if (activity != null) {
            if (errorMsg != null) {
                activity.notify(errorMsg);
            } else {
                if (loggedUser != null) {
                    activity.storeRegistrationId(activity.getApplicationContext(), regId);
                } else {
                    activity.notify("ololo!");
                }
            }
        }
    }

    @Override
    public String getURL() {
        return "ServicesTomcatWAR/updateUser";
    }

    @Override
    public void registerError(String error) {
        this.errorMsg = error;
    }

}
