package com.urban.activity.task;

import android.os.AsyncTask;

import com.tools.dialogs.SignInDialog;
import com.urban.data.User;
import com.urban.service.urban.UrbanServiceFactory;
import com.urban.service.urban.exception.UrbanServiceException;

import java.lang.ref.WeakReference;

public class SignInTask extends AsyncTask<String, Void, User> {

    private static final int NUMBER_OF_PARAMETERS = 2;

    private WeakReference<SignInDialog> parent;

    private String errorMsg;

    public SignInTask(SignInDialog dialog) {
        super();
        this.parent = new WeakReference<SignInDialog>(dialog);
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
        SignInDialog dialog = parent.get();
        if (dialog != null) {
            if (errorMsg != null) {
                dialog.notify(errorMsg);
            } else {
                dialog.signIn(loggedUser);
            }
        }
    }

    public void registerError(String error) {
        this.errorMsg = error;
    }
}
