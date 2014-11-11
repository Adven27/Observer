package com.urban.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.tools.PrototypeView;
import com.urban.data.Category;

public class SlidingContentFragment extends Fragment {

    private Category currentCategory = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.sliding_content, container, false);

        setUpSlidingMenu(layout);

        PrototypeView.setCurrentContainerId(R.id.main_container);
        PrototypeView.doInTransaction(new PrototypeView.ShowCategoryAction(currentCategory));

        return layout;
    }

    private void setUpSlidingMenu(View layout) {
        SlidingMenu menu = (SlidingMenu)layout.findViewById(R.id.slidingmenulayout);
        menu.setTouchmodeMarginThreshold(120);
    }

    public void setCurrentCategory(Category category) {
        this.currentCategory = category;
    }
}
