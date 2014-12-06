package com.urban.activity.about;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.test.R;
import com.tools.PrototypeView;
import com.urban.activity.UrbanActivity;

public class AboutActivity extends UrbanActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_frame);

        PrototypeView.setCurrentContainerId(R.id.about_frame);
        PrototypeView.doInTransaction(new PrototypeView.ShowAboutAction());
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
}
