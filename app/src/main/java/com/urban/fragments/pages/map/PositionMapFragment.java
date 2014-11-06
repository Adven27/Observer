package com.urban.fragments.pages.map;

import java.util.Collection;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test.R;
import com.tools.PrototypeView;
import com.tools.PrototypeView.ShowMapAction;
import com.urban.data.Organization;
import com.urban.data.Place;
import com.urban.data.Position;
import com.urban.fragments.pages.PositionTabFragment;

public class PositionMapFragment extends PositionTabFragment {
    private static final int LAYOUT_ID = R.layout.position_map;

    /**
     * newInstance constructor for creating fragment with arguments
     *
     * @param position
     */
    public static PositionMapFragment newInstance(Position position) {
        PositionMapFragment fragment = new PositionMapFragment();
        fragment.position = position;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View view = inflater.inflate(LAYOUT_ID, container, false);

        Organization organization = position.getOrganization();
        Collection<Place> places = organization.getPlaces();
        YandexMapFragment.getInstance().initPlaces(places);

        PrototypeView.setCurrentContainerId(R.id.map_container);
        PrototypeView.doInTransaction(new ShowMapAction(), false);

        return view;
    }
}
