package com.urban.fragments.pages;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.test.R;
import com.urban.data.Position;

public class InfoFragment extends Fragment {

    private static final int LAYOUT_ID = R.layout.position_info;

    private Position position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View view = inflater.inflate(LAYOUT_ID, container, false);
        TextView text = (TextView)view.findViewById(R.id.info_name);
        text.setText(position.getName());
        text = (TextView)view.findViewById(R.id.info_text);
        text.setText(position.getOrganization().getDescription());
        return view;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

}
