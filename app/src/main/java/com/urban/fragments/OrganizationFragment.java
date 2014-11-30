package com.urban.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.urban.data.Organization;

public class OrganizationFragment extends Fragment {

    private Organization currentOrganization = null;

    public void setCurrentOrganization(Organization organization){
        this.currentOrganization = organization;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        /*View v = inflater.inflate(R.layout.organization, container, false);
        ViewPager pager = (ViewPager)v.findViewById(R.id.view_pager);

        PageView infoPage = InfoPage.newInstance(inflater, currentOrganization);
        PageView photoPage = PhotoPage.newInstance(inflater, currentOrganization);
        PageView contactsPage = ContactsPage.newInstance(inflater, currentOrganization);
        PageView actionsPage = ActionsPage.newInstance(inflater, currentOrganization);


        PageView mapPage = YandexMapPage.newInstance(inflater, currentOrganization);

        //PageView googleMapPage = (PageView)inflater.inflate(R.layout.organization_google_map, null);

        ArrayList<PageView> pages = new ArrayList<PageView>();
        pages.add(infoPage);
        pages.add(photoPage);
        pages.add(contactsPage);
        pages.add(actionsPage);
        pages.add(mapPage);
        //pages.add(googleMapPage);

        OrganizationPagerAdapter pagerAdapter = new OrganizationPagerAdapter(pages);
        pager.setAdapter(pagerAdapter);


        TitlePageIndicator titleIndicator = (TitlePageIndicator)v.findViewById(R.id.indicator);
        titleIndicator.setViewPager(pager);*/

        return null;//v;
    }
}
