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

public class PositionMapFragment extends Fragment {


    private static final int LAYOUT_ID = R.layout.position_map;

    private Position position;

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

    public void setPosition(Position position) {
        this.position = position;
    }
}
