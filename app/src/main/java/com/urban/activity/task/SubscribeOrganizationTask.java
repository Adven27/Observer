package com.urban.activity.task;

import android.os.AsyncTask;
import android.view.View;

import com.tools.OrganizationAdapter;
import com.urban.data.Organization;
import com.urban.data.User;
import com.urban.service.urban.UrbanServiceFactory;
import com.urban.service.urban.exception.UrbanServiceException;

import java.lang.ref.WeakReference;

public class SubscribeOrganizationTask extends AsyncTask<Object, Void, String> {

    private WeakReference<OrganizationAdapter> ref;

    private String errorMsg;
    private User user;
    private Organization organization;
    private View view;

    public SubscribeOrganizationTask(OrganizationAdapter adapter, User user, View view, Organization organization) {
        super();
        this.ref = new WeakReference<OrganizationAdapter>(adapter);
        this.user = user;
        this.organization = organization;
        this.view = view;
    }

    @Override
    protected String doInBackground(Object... params) {
        try {
            UrbanServiceFactory.getInstance().subscribe(user, organization);
        } catch (UrbanServiceException e) {
            registerError(e.getMessage());
        }
        return null;
    }

    @Override
    public void onPostExecute(String result) {
        OrganizationAdapter adapter = ref.get();
        if (adapter != null) {
            if (errorMsg != null) {
                //TODO: Handle this situation!
            } else {
                adapter.subscribe(view);
            }
        }
    }

    public void registerError(String error) {
        this.errorMsg = error;
    }
}
