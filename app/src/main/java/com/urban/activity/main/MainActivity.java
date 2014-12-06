package com.urban.activity.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.example.test.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.tools.LogHelper;
import com.tools.PrototypeView;
import com.urban.activity.dashboard.DashboardActivity;
import com.urban.data.Category;
import com.urban.data.dao.DAO;
import com.urban.fragments.CategoryFragment;
import com.urban.fragments.SlidingContentFragment;

public class MainActivity extends FragmentActivity implements SearchView.OnQueryTextListener{

    private SearchView searchView;


    public boolean onQueryTextChange(String newText) {
        Fragment categoryFragment = getSupportFragmentManager().findFragmentById(R.id.main_container);
        ((CategoryFragment) categoryFragment).filter(newText);
        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PrototypeView.createInstance(this, R.id.fragment_container);
        addSlidingMenuFragment();
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
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        setUpSearchView(menu);
        return true;
    }

    private void setUpSearchView(Menu menu) {
        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setIconifiedByDefault(true);
        searchView.setSubmitButtonEnabled(false);
        searchView.setQueryHint("Search Here");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.ab_menu_side_menu_toggle) {
            View layout = findViewById(R.id.fragment_container);
            SlidingMenu menu = (SlidingMenu) layout.findViewById(R.id.slidingmenulayout);
            menu.toggle();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        PrototypeView.switchActivity(this);
    }

}
