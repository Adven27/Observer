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
    private Position position;
    private ViewPager tabsPager;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);

        setContentView(R.layout.position);
        PrototypeView.switchActivity(this);

        initCategoryPosition();
        initTabs();
    }

    private void initCategoryPosition() {
        long positionId = getIntent().getIntExtra(EXTRA_POSITION_ID, -1);
        position = getPosition(positionId);
    }

    private void initTabs() {
        initTabsPager();
        addTabsToActionBar();
    }

    private void initTabsPager() {
        tabsPager = (ViewPager) findViewById(R.id.tabpager);
        tabsPager.setAdapter(new TabsPagerAdapter(getSupportFragmentManager(), position));
        tabsPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                getActionBar().setSelectedNavigationItem(position);
            }
        });
    }

    private void addTabsToActionBar() {
        ActionBar.TabListener tabListener = createTabListener();
        final ActionBar actionBar = getActionBar();

        // Specify that tabs should be displayed in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        TabsPagerAdapter tabsPagerAdapter = (TabsPagerAdapter) tabsPager.getAdapter();
        for (int i = 0; i < tabsPagerAdapter.getCount(); i++) {
            actionBar.addTab(actionBar.newTab()
                    .setIcon(getResources().getDrawable(tabsPagerAdapter.getIcon(i)))
                    .setTabListener(tabListener));
        }
    }

    private ActionBar.TabListener createTabListener() {
        return new ActionBar.TabListener() {
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                tabsPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
            }
        };
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