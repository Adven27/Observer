package com.urban.fragments.pages;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test.R;
import com.urban.data.Position;

public class ActionsFragment extends Fragment {

    private static final int LAYOUT_ID = R.layout.position_actions;
    private Position position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View view = inflater.inflate(LAYOUT_ID, container, false);
        return view;
    }

    public void setPosition(Position position) {
        this.position = position;
    }


}