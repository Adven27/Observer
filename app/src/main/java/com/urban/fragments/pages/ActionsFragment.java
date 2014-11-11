package com.urban.fragments.pages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test.R;
import com.urban.data.Position;

public class ActionsFragment extends PositionTabFragment {

    private static final int LAYOUT_ID = R.layout.position_actions;


    public static ActionsFragment newInstance(Position position) {
        ActionsFragment fragment = new ActionsFragment();
        fragment.position = position;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(LAYOUT_ID, container, false);
        return view;
    }
}