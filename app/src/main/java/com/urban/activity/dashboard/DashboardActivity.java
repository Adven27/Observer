package com.urban.activity.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.example.test.R;
import com.tools.ViewServer;
import com.urban.activity.main.MainActivity;
import com.urban.data.Category;
import com.urban.data.dao.DAO;

import java.util.ArrayList;

public class DashboardActivity extends FragmentActivity {

    public static final String CATEGORY_ID_ACRGUMENT = "category_id";

    private ArrayList<Category> categories = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dashboard);

        try {
            categories = (ArrayList<Category>)DAO.getAll(Category.class);
        } catch (Exception e) {
            Log.e("", "Can not load categories");
            return;
        }

        Button button = (Button)findViewById(R.id.dashboardPlate1);
        button.setText(categories.get(0).getName());
        button.setOnClickListener(onCathegodyClickListener);
        button = (Button)findViewById(R.id.dashboardPlate2);
        button.setText(categories.get(1).getName());
        button.setOnClickListener(onCathegodyClickListener);
        button = (Button)findViewById(R.id.dashboardPlate3);
        button.setText(categories.get(2).getName());
        button.setOnClickListener(onCathegodyClickListener);
        /*button = (Button)findViewById(R.id.dashboardPlate4);
        button.setText(categories.get(3).getName());
        button.setOnClickListener(onCathegodyClickListener);*/

    }

    public void redirectToCathegory(Category category){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(CATEGORY_ID_ACRGUMENT, category.getId());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
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

    private final OnClickListener onCathegodyClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.dashboardPlate1){
                redirectToCathegory(categories.get(0));
            } else if (v.getId() == R.id.dashboardPlate2){
                redirectToCathegory(categories.get(1));
            } else if (v.getId() == R.id.dashboardPlate3){
                redirectToCathegory(categories.get(2));
            } else if (v.getId() == R.id.dashboardPlate4){
                redirectToCathegory(categories.get(3));
            }

        }
    };

}
