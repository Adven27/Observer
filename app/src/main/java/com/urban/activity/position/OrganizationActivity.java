package com.urban.activity.position;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.example.test.R;
import com.tools.LogHelper;
import com.urban.activity.UrbanActivity;
import com.urban.activity.map.MapActivity;
import com.urban.data.Organization;
import com.urban.data.dao.DAO;

public class OrganizationActivity extends UrbanActivity {
    public static final String EXTRA_POSITION_ID = "organization_id";
    private Organization organization;
    private ViewPager tabsPager;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.organization);

        initCategoryOrganization();
        getActionBar().setTitle(organization.getName());
        initTabs();
    }

    private void initCategoryOrganization() {
        long organizationId = getIntent().getIntExtra(EXTRA_POSITION_ID, -1);
        organization = getOrganization(organizationId);
    }

    private void initTabs() {
        initTabsPager();
        addTabsToActionBar();
    }

    private void initTabsPager() {
        tabsPager = (ViewPager) findViewById(R.id.tabpager);
        tabsPager.setAdapter(new TabsPagerAdapter(getSupportFragmentManager(), organization));
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

    private Organization getOrganization(long organizationId) {
        try {
            return DAO.get(Organization.class, organizationId);
        } catch (Exception e) {
            Log.e(LogHelper.TAG_DB_OPERATION, "Can't find the organization by id: " + organizationId, e);
            return null;
        }
    }


    public void onShowOnMap(View view) {
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra(EXTRA_POSITION_ID, organization.getId());
        startActivity(intent);
    }
}