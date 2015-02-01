package com.urban.appl;

import android.app.Application;
import android.util.Log;

import com.urban.dao.DBInitializer;
import com.urban.service.urban.UrbanServiceSettings;

public class ObserverApplication extends Application {
	
    //sensible place to declare a log tag for the application
    public static final String LOG_TAG = "ObserverApp";

    //instance 
    private static ObserverApplication instance = null;

    //keep references to our global resources
  // static SomeResource someResource = null;
  //  private static AnotherResource anotherResource = null;

    /**
     * Convenient accessor, saves having to call and cast getApplicationContext() 
     */
    public static ObserverApplication getInstance() {
        checkInstance();
        return instance;
    }

    /**
     * Accessor for some resource that depends on a context
     */
  /*  public static SomeResource getSomeResource1() {
        if (someResource == null) {
            checkInstance();
            someResource = new SomeResource(instance);
        }
        return someResource;
    }*/

    /**
     * Accessor for another resource that depends on a context
     */
  /*  public static AnotherResource getSomeResource2() {
        if (anotherResource == null) {
            checkInstance();
            anotherResource = new AnotherResource(instance);
        }
        return anotherResource;
    }*/

    private static void checkInstance() {
        if (instance == null)
            throw new IllegalStateException("Application not created yet!");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        try {
			DBInitializer.initDB(this);
	        DBInitializer.initDAO(this);
            UrbanServiceSettings.setServiceType(UrbanServiceSettings.UrbanServiceType.STUB);
        } catch (Exception e) {
			//if we have error here it means we can't work.
			//TODO: show something sad to user and terminate
			Log.e(LOG_TAG, "Error during initialization.", e);
		}


        //provide an instance for our static accessors
        instance = this;
        Log.i(LOG_TAG, "Application was initialized.");
    }

}
