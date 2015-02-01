package com.adven.observer;

import android.app.Activity;

import com.urban.activity.dashboard.DashboardActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertTrue;

@Config(manifest = "./src/main/AndroidManifest.xml")
@RunWith(MyRunner.class)
public class ActivityRobolectricTest {

    @Test
    public void testSomething() throws Exception {
        Activity activity = Robolectric.buildActivity(DashboardActivity.class).create().get();
        assertTrue(activity != null);
    }
}