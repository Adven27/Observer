package com.urban.activity.dashboard;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.tools.LogHelper;
import com.tools.PrototypeView;
import com.tools.dialogs.SignInDialog;
import com.tools.dialogs.SimpleDialog;
import com.urban.activity.UrbanActivity;
import com.urban.activity.main.CategoryActivity;
import com.urban.activity.task.UpdateUserTask;
import com.urban.appl.Settings;
import com.urban.dao.DBInitializer;
import com.urban.data.Category;
import com.urban.data.User;
import com.urban.data.dao.DAO;
import com.urban.observer.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import src.com.urban.data.sqlite.pojo.UserPojo;

public class DashboardActivity extends UrbanActivity implements SimpleDialog.DialogListener {
    public static final String CATEGORY_ID_ARGUMENT = "category_id";
    public static final String PROPERTY_REG_ID = "registration_id";

    /**
     * Substitute you own sender ID here. This is the project number you got
     * from the API Console, as described in "Getting Started."
     */
    private static final String SENDER_ID = "724387067630";

    private static final String PROPERTY_APP_VERSION = "appVersion";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    private List<Category> categories = null;
    private SharedPreferences prefs;
    private Context context;

    private String regId;
    private GoogleCloudMessaging gcm;

    private NotificationManager notificationManager;
    private NotificationCompat.Builder builder;


    /**
     * @return Application's version code from the {@code PackageManager}.
     */
    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        context = getApplicationContext();

        updateActionBar();
        if (Settings.getLoggedUser() != null) {
            registerBackground();
        }
        categories = getCategories();

        AdapterView gridview = (AdapterView) findViewById(R.id.dashgridview);
        gridview.setAdapter(new DashboardAdapter(this, categories));

        gridview.setOnItemSelectedListener(new MyOnItemClickListener());
    }

    private List<Category> getCategories() {
        try {
            return (ArrayList<Category>) DAO.getAll(Category.class);
        } catch (Exception e) {
            Log.e(LogHelper.TAG_DB_OPERATION, "Can not load categories", e);
            return Collections.<Category>emptyList();
        }
    }

    public void redirectToCategory(Category category) {
        Intent intent = new Intent(this, CategoryActivity.class);
        intent.putExtra(CATEGORY_ID_ARGUMENT, category.getId());
        startActivity(intent);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        return super.onMenuItemSelected(featureId, item);
    }


    private void registerBackground() {
        new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(context);
                    }
                    regId = gcm.register(SENDER_ID);
                    msg = "Device registered, registration id=" + regId;
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                }
                return msg;
            }
        }.execute(null, null, null);
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.w(LogHelper.TAG_PLAY_SERVICES, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    /**
     * Gets the current registration ID for application on GCM service.
     * <p/>
     * If result is empty, the app needs to register.
     *
     * @return registration ID, or empty string if there is no existing
     * registration ID.
     */
    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, null);
        if (registrationId == null) {
            Log.i(LogHelper.TAG_PLAY_SERVICES, "Registration not found.");
            return null;
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(LogHelper.TAG_PLAY_SERVICES, "App version changed.");
            return null;
        }
        return registrationId;
    }

    /**
     * @return Application's {@code SharedPreferences}.
     */
    private SharedPreferences getGCMPreferences(Context context) {
        // This sample app persists the registration ID in shared preferences, but
        // how you store the regID in your app is up to you.
        return getSharedPreferences(DashboardActivity.class.getSimpleName(), Context.MODE_PRIVATE);
    }

    /**
     * Sends the registration ID to your server over HTTP, so it can use GCM/HTTP
     * or CCS to send messages to your app. Not needed for this demo since the
     * device sends upstream messages to a server that echoes back the message
     * using the 'from' address in the message.
     */
    private void sendRegistrationIdToBackend(String regId) {
        User user = Settings.getLoggedUser();
        ((UserPojo)user).setRegId(regId); //TODO: Should not write direct to the logged user there.
        UpdateUserTask task = new UpdateUserTask(DashboardActivity.this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, user);
        } else {
            task.execute(user);
        }
    }

    /**
     * Stores the registration ID and app versionCode in the application's
     * {@code SharedPreferences}.
     *
     * @param regId   registration ID
     */
    public void storeRegistrationId(String regId) {
        final SharedPreferences prefs = getGCMPreferences(context);
        int appVersion = getAppVersion(context);
        Log.i(LogHelper.TAG_PLAY_SERVICES, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }

    public void notify(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dashboard_options_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.sign_in) {
            PrototypeView.doInTransaction(new PrototypeView.ShowDialogAction(SignInDialog.getInstance(this)));
        } else if (item.getItemId() == R.id.recreateDB) {
            recreateDB();
        } else if (item.getItemId() == R.id.send_notification) {
            Toast.makeText(this, "Do not push me again!", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Recreate DB button click
     */
    public void recreateDB() {
        try {
            DBInitializer.copyDataBase(context, Settings.getDBName());
            clearStoredData();
            Toast.makeText(context, "База пересоздана!", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(context, "Не удалось пересоздать базу", Toast.LENGTH_LONG).show();
            Log.e("DashboardActivity", "Не удалось пересоздать базу");
        }
    }

    private void updateActionBar() {
        User loggedUser = Settings.getLoggedUser();
        if (loggedUser != null) {
            //getActionBar().setIcon();
            getActionBar().setTitle(loggedUser.getLogin());
        } else {
            getActionBar().setTitle("Выполните вход");
        }
    }

    private void clearStoredData() {
        Settings.setLoggedUser(null);
        DAO.deleteAll(User.class);
        updateActionBar();
    }

    @Override
    public void onPositive() {
        updateActionBar();
    }

    @Override
    public void onNegative() {
        // Nothing to do.
    }

    private class MyOnItemClickListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
            redirectToCategory(categories.get(position));
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }
}
