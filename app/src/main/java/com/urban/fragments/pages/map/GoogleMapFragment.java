package com.urban.fragments.pages.map;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.urban.data.Place;

import java.util.Collection;

public class GoogleMapFragment extends Fragment {

    private GoogleMap map;
    private Collection<Place> places;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.organization_google_map_fragment, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.google_map_fragment);
        map = mapFragment.getMap();
        if (map != null){
            GoogleMapOptions options = new GoogleMapOptions();
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

            LatLng point = null;
            for(Place place : places) {
                MarkerOptions opt = new MarkerOptions();
                point = new LatLng(place.getLat(), place.getAlt());
                opt.position(point);
                map.addMarker(opt);
            }

            CameraPosition cameraPosition = new CameraPosition(point != null ? point : new LatLng(59.221765, 39.888093), map.getMaxZoomLevel(), 30, 90);
            options.compassEnabled(false).camera(cameraPosition);
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
        //initGoogleMap();
        //final MapView mapView = (MapView) v.findViewById(R.id.google_map);
        return v;

    }

    public void initPlaces(Collection<Place> places) {
        this.places = places;
    }

    /*private void initGoogleMap(){
        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        //map = mapFragment.getMap();

        FragmentTransaction fragmentTransaction =
                getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.google_map_container, mapFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }*/

}
