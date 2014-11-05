package com.urban.activity.dashboard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.example.test.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.tools.ViewServer;
import com.urban.activity.main.MainActivity;
import com.urban.activity.userproperties.task.UserPropertiesTask;
import com.urban.appl.Settings;
import com.urban.data.Category;
import com.urban.data.User;
import com.urban.data.dao.DAO;
import com.urban.task.HttpRequestTask;

import java.util.ArrayList;

public class DashboardActivity extends FragmentActivity {

    public static final String CATEGORY_ID_ARGUMENT = "category_id";

    private ArrayList<Category> categories = null;

    public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    /**
     * Tag used on log messages.
     */
    private static final String TAG = "DashBoardActivity";

    private SharedPreferences prefs;
    private Context context;

    private String regid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dashboard);

        context = getApplicationContext();
        //Remove this! Just for test.
        Settings.setLoggedUser(
                DAO.getUniqByCriterion(User.class, DAO.createCriterion(User.class).eq("login", "admin")));



        if (checkPlayServices()) {
            regid = getRegistrationId(context);
            if (regid == null) {
                sendRegistrationIdToBackend();
            }
        } else {
            Log.i(TAG, "No valid Google Play Services APK found.");
        }

        try {
            categories = (ArrayList<Category>)DAO.getAll(Category.class);
        } catch (Exception e) {
            Log.e("", "Can not load categories");
            return;
        }

        Button button = (Button)findViewById(R.id.dashboardPlate1);
        button.setText(categories.get(0).getName());
        button.setOnClickListener(onCategoryClickListener);
        button = (Button)findViewById(R.id.dashboardPlate2);
        button.setText(categories.get(1).getName());
        button.setOnClickListener(onCategoryClickListener);
        button = (Button)findViewById(R.id.dashboardPlate3);
        button.setText(categories.get(2).getName());
        button.setOnClickListener(onCategoryClickListener);
        /*button = (Button)findViewById(R.id.dashboardPlate4);
        button.setText(categories.get(3).getName());
        button.setOnClickListener(onCategoryClickListener);*/

    }

    public void redirectToCategory(Category category){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(CATEGORY_ID_ARGUMENT, category.getId());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ViewServer.get(this).removeWindow(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        checkPlayServices();
        ViewServer.get(this).setFocusedWindow(this);
    }

    private final OnClickListener onCategoryClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.dashboardPlate1){
                redirectToCategory(categories.get(0));
            } else if (v.getId() == R.id.dashboardPlate2){
                redirectToCategory(categories.get(1));
            } else if (v.getId() == R.id.dashboardPlate3){
                redirectToCategory(categories.get(2));
            } else if (v.getId() == R.id.dashboardPlate4){
                redirectToCategory(categories.get(3));
            }

        }
    };

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
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    /**
     * Gets the current registration ID for application on GCM service.
     * <p>
     * If result is empty, the app needs to register.
     *
     * @return registration ID, or empty string if there is no existing
     *         registration ID.
     */
    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, null);
        if (registrationId == null) {
            Log.i(TAG, "Registration not found.");
            return null;
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
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

    /**
     * Sends the registration ID to your server over HTTP, so it can use GCM/HTTP
     * or CCS to send messages to your app. Not needed for this demo since the
     * device sends upstream messages to a server that echoes back the message
     * using the 'from' address in the message.
     */
    private void sendRegistrationIdToBackend() {
        UserPropertiesTask upTask = new UserPropertiesTask(this);
        HttpRequestTask task = new HttpRequestTask(upTask);

        task.execute();
    }

    /**
     * Stores the registration ID and app versionCode in the application's
     * {@code SharedPreferences}.
     *
     * @param context application's context.
     * @param regId registration ID
     */
    public void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = getGCMPreferences(context);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }


    public void notify(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }


}
