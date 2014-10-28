package com.urban.activity.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.test.R;
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
        PrototypeView.switchActivity(this);

        SlidingContentFragment fragment = new SlidingContentFragment();
        Intent intent = this.getIntent();
        long categoryId = intent.getLongExtra(DashboardActivity.CATEGORY_ID_ACRGUMENT, 0l);

        Category category = null;
        try {
            category = DAO.get(Category.class, categoryId);
        } catch (Exception e) {
            Log.e("", "Can't find the category by id: " + categoryId);
        }

        fragment.setCurrentCategory(category);


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container, fragment);
        transaction.commit();

        ViewServer.get(this).addWindow(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Menu").setIcon(R.drawable.side_menu_button).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {

        return super.onMenuItemSelected(featureId, item);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        ViewServer.get(this).removeWindow(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewServer.get(this).setFocusedWindow(this);
    }

}
