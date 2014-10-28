package com.example.test;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class FooterFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.footer, container, true);
        Button button = (Button)v.findViewById(R.id.like);

        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast t = Toast.makeText(getActivity(), "Plus one, dude! =)", Toast.LENGTH_SHORT);
                t.show();
            }
        });
        return v;
    }
}
