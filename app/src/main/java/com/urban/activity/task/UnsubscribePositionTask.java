package com.urban.activity.task;

import android.os.AsyncTask;
import android.view.View;

import com.tools.PositionAdapter;
import com.urban.data.Position;
import com.urban.data.User;
import com.urban.service.urban.UrbanServiceFactory;
import com.urban.service.urban.exception.UrbanServiceException;

import java.lang.ref.WeakReference;

public class UnsubscribePositionTask extends AsyncTask<Object, Void, String> {

    private WeakReference<PositionAdapter> ref;

    private String errorMsg;
    private User user;
    private Position position;
    private View view;

    public UnsubscribePositionTask(PositionAdapter adapter, User user, View view, Position position) {
        super();
        this.ref = new WeakReference<PositionAdapter>(adapter);
        this.user = user;
        this.position = position;
        this.view = view;
    }

    @Override
    protected String doInBackground(Object... params) {
        try {
            UrbanServiceFactory.getInstance().unsubscribe(user, position);
        } catch (UrbanServiceException e) {
            registerError(e.getMessage());
        }
        return null;
    }

    @Override
    public void onPostExecute(String result) {
        PositionAdapter adapter = ref.get();
        if (adapter != null) {
            if (errorMsg != null) {
                //TODO: Handle this situation!
            } else {
                adapter.unsubscribe(view);
            }
        }
    }

    public void registerError(String error) {
        this.errorMsg = error;
    }
}
