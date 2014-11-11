package com.urban.activity.main;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.test.R;
import com.tools.LogHelper;
import com.tools.PrototypeView;
import com.tools.ViewServer;
import com.urban.activity.dashboard.DashboardActivity;
import com.urban.data.Category;
import com.urban.data.dao.DAO;
import com.urban.fragments.SlidingContentFragment;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PrototypeView.createInstance(this, R.id.fragment_container);
        addSlidingMenuFragment();

        ViewServer.get(this).addWindow(this);
    }

    private void addSlidingMenuFragment() {
        Category currentCategory = getCategory();
        SlidingContentFragment slidingMenuFragment = createSlidingMenuFragment(currentCategory);
        addFragment(slidingMenuFragment);
    }

    private void addFragment(SlidingContentFragment slidingMenuFragment) {
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.add(R.id.fragment_container, slidingMenuFragment);
        t.commit();
    }

    private Category getCategory() {
        int categoryId = getCategoryIdFromIntent();
        return getCategoryFromDB(categoryId);
    }

    private SlidingContentFragment createSlidingMenuFragment(Category currentCategory) {
        SlidingContentFragment slidingMenuFragment = new SlidingContentFragment();
        slidingMenuFragment.setCurrentCategory(currentCategory);
        return slidingMenuFragment;
    }

    private int getCategoryIdFromIntent() {
        return this.getIntent().getIntExtra(DashboardActivity.CATEGORY_ID_ARGUMENT, 0);
    }

    private Category getCategoryFromDB(int categoryId) {
        try {
            return DAO.get(Category.class, categoryId);
        } catch (Exception e) {
            Log.e(LogHelper.TAG_DB_OPERATION, "Can't find the category by id: " + categoryId);
            return null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ViewServer.get(this).removeWindow(this);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewServer.get(this).setFocusedWindow(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Menu").setIcon(R.drawable.side_menu_button).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

}
