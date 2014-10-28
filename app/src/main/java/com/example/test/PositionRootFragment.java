package com.example.test;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tools.PositionPagerAdapter;
import com.urban.elements.pages.PageView;
import com.viewpagerindicator.TitlePageIndicator;

public class PositionRootFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.position, container, false);
        ViewPager pager = (ViewPager)v.findViewById(R.id.view_pager);

        PageView infoPage = (PageView)inflater.inflate(R.layout.position_info, null);

        PageView photoPage = (PageView)inflater.inflate(R.layout.position_photo, null);
        PageView contactsPage = (PageView)inflater.inflate(R.layout.position_contacts, null);
        PageView actionsPage = (PageView)inflater.inflate(R.layout.position_actions, null);
        //PageView mapPage = (PageView)inflater.inflate(R.layout.position_map, null);

        //PageView googleMapPage = (PageView)inflater.inflate(R.layout.position_google_map, null);

        ArrayList<com.urban.elements.pages.PageView> pages = new ArrayList<com.urban.elements.pages.PageView>();
        pages.add(infoPage);
        pages.add(photoPage);
        pages.add(contactsPage);
        pages.add(actionsPage);
        //pages.add(mapPage);
        //pages.add(googleMapPage);

        PositionPagerAdapter pagerAdapter = new PositionPagerAdapter(pages);
        pager.setAdapter(pagerAdapter);


        TitlePageIndicator titleIndicator = (TitlePageIndicator)v.findViewById(R.id.indicator);
        titleIndicator.setViewPager(pager);

        return v;
    }
}
