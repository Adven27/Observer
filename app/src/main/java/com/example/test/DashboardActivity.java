package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class DashboardActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dashboard);

        Button button = (Button)findViewById(R.id.btn_friends);
        button.setOnClickListener(onCathegodyClickListener);
        button = (Button)findViewById(R.id.btn_places);
        button.setOnClickListener(onCathegodyClickListener);
        button = (Button)findViewById(R.id.btn_photos);
        button.setOnClickListener(onCathegodyClickListener);
        button = (Button)findViewById(R.id.btn_messages);
        button.setOnClickListener(onCathegodyClickListener);

    }

    public void redirectToCathegory(String header){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(CategoryFragment.HEADER_ARGUMENT, header);
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
            if (v.getId() == R.id.btn_friends){
                redirectToCathegory("���");
            } else if (v.getId() == R.id.btn_places){
                redirectToCathegory("�����");
            } else if (v.getId() == R.id.btn_photos){
                redirectToCathegory("�����������");
            } else if (v.getId() == R.id.btn_messages){
                redirectToCathegory("���������");
            }

        }
    };

}
