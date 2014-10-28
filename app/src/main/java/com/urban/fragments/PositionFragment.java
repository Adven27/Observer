package com.urban.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.urban.data.Position;

public class PositionFragment extends Fragment {

    private Position currentPosition = null;

    public void setCurrentPosition(Position position){
        this.currentPosition = position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        /*View v = inflater.inflate(R.layout.position, container, false);
        ViewPager pager = (ViewPager)v.findViewById(R.id.view_pager);

        PageView infoPage = InfoPage.newInstance(inflater, currentPosition);
        PageView photoPage = PhotoPage.newInstance(inflater, currentPosition);
        PageView contactsPage = ContactsPage.newInstance(inflater, currentPosition);
        PageView actionsPage = ActionsPage.newInstance(inflater, currentPosition);


        PageView mapPage = YandexMapPage.newInstance(inflater, currentPosition);

        //PageView googleMapPage = (PageView)inflater.inflate(R.layout.position_google_map, null);

        ArrayList<PageView> pages = new ArrayList<PageView>();
        pages.add(infoPage);
        pages.add(photoPage);
        pages.add(contactsPage);
        pages.add(actionsPage);
        pages.add(mapPage);
        //pages.add(googleMapPage);

        PositionPagerAdapter pagerAdapter = new PositionPagerAdapter(pages);
        pager.setAdapter(pagerAdapter);


        TitlePageIndicator titleIndicator = (TitlePageIndicator)v.findViewById(R.id.indicator);
        titleIndicator.setViewPager(pager);*/

        return null;//v;
    }
}
