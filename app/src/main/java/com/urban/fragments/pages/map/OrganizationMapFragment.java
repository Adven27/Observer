package com.urban.fragments.pages.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tools.PrototypeView;
import com.tools.PrototypeView.ShowMapAction;
import com.urban.data.Organization;
import com.urban.data.Place;
import com.urban.fragments.pages.OrganizationTabFragment;
import com.urban.observer.R;

import java.util.Collection;

public class OrganizationMapFragment extends OrganizationTabFragment {
    private static final int LAYOUT_ID = R.layout.organization_map;

    public static OrganizationMapFragment newInstance(Organization organization) {
        OrganizationMapFragment fragment = new OrganizationMapFragment();
        fragment.organization = organization;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View view = inflater.inflate(LAYOUT_ID, container, false);

        Collection<Place> places = organization.getPlaces();
        YandexMapFragment.setInstance(new YandexMapFragment());
        YandexMapFragment.getInstance().initPlaces(places);

        PrototypeView.setCurrentContainerId(R.id.map_container);
        PrototypeView.doInTransaction(new ShowMapAction());

        return view;
    }
}
