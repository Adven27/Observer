package com.urban.activity.position;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.example.test.R;
import com.tools.LogHelper;
import com.tools.PrototypeView;
import com.urban.data.Position;
import com.urban.data.dao.DAO;

public class PositionActivity extends FragmentActivity {
    public static final String EXTRA_POSITION_ID = "position_id";
    private TabsPagerAdapter tabsPagerAdapter;
    private Position position;
    private ViewPager pager;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);

        setContentView(R.layout.position);
        PrototypeView.switchActivity(this);

        long positionId = getIntent().getIntExtra(EXTRA_POSITION_ID, -1);
        position = getPosition(positionId);

        pager = (ViewPager) findViewById(R.id.tabpager);

        tabsPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager(), position);
        pager.setAdapter(tabsPagerAdapter);

        final ActionBar actionBar = getActionBar();

        // Specify that tabs should be displayed in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create a tab listener that is called when the user changes tabs.
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                // When the tab is selected, switch to the corresponding page in the ViewPager.
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
            }
        };

        pager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        // When swiping between pages, select the corresponding tab.
                        getActionBar().setSelectedNavigationItem(position);
                    }
                });

        // Add tabs, specifying the tab's text and TabListener
        addTabs(actionBar, tabListener);
    }

    private void addTabs(ActionBar actionBar, ActionBar.TabListener tabListener) {
        for (int i = 0; i < tabsPagerAdapter.getCount(); i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            //.setText("Tab " + (i + 1))
                            .setIcon(getResources().getDrawable(tabsPagerAdapter.getIcon(i)))
                            .setTabListener(tabListener));
        }
    }

    private Position getPosition(long positionId) {
        try {
            return DAO.get(Position.class, positionId);
        } catch (Exception e) {
            Log.e(LogHelper.TAG_DB_OPERATION, "Can't find the position by id: " + positionId, e);
            return null;
        }
    }

}

