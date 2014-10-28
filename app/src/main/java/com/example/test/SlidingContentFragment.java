package com.example.test;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.tools.PrototypeView;


public class SlidingContentFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.sliding_content, container, false);
        SlidingMenu menu = (SlidingMenu)layout.findViewById(R.id.slidingmenulayout);
        menu.setTouchmodeMarginThreshold(120);

        String header = getArguments().getString(CategoryFragment.HEADER_ARGUMENT);

        PrototypeView.setCurrentContainerId(R.id.main_container);
        //TODO: Crutch!!!
        //PrototypeView.doInTransaction(new PrototypeView.ShowCategoryListAction(header), false);

        return layout;
    }
}
