package com.adven.observer;

import android.app.Activity;
import android.widget.GridView;

import com.urban.activity.dashboard.DashboardActivity;
import com.urban.observer.R;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;

/**
 * Created by adven on 2/2/15.
 */
@RunWith(MyRunner.class)
public class DashboardActivityTest {

    @Test
    public void categoriesShouldBeVisible() throws Exception {
        Activity activity = Robolectric.buildActivity(DashboardActivity.class).create().get();
        GridView grid = (GridView)activity.findViewById(R.id.dashgridview);

        Assert.assertTrue(grid.getAdapter().getCount() > 0);
    }
}
