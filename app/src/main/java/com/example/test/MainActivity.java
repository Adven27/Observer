package com.example.test;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import com.tools.PrototypeView;

public class MainActivity extends FragmentActivity {

    public static final BannerFragment bannerFragment = new BannerFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PrototypeView.createInstance(this, R.id.fragment_container);

        SlidingContentFragment fragment = new SlidingContentFragment();

        String categoryHeader = getIntent().getStringExtra(CategoryFragment.HEADER_ARGUMENT);
        if (categoryHeader != null) {
            Bundle bundle = new Bundle();
            bundle.putString(CategoryFragment.HEADER_ARGUMENT, categoryHeader);
            fragment.setArguments(bundle);
        }

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
    public boolean onMenuItemSelected(int featureId,MenuItem item) {
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
