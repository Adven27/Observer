package com.urban.fragments.pages.map;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test.R;
import com.tools.PrototypeView;
import com.urban.data.Place;

import java.util.Collection;

import ru.yandex.yandexmapkit.MapController;
import ru.yandex.yandexmapkit.MapView;
import ru.yandex.yandexmapkit.OverlayManager;
import ru.yandex.yandexmapkit.overlay.Overlay;
import ru.yandex.yandexmapkit.overlay.OverlayItem;
import ru.yandex.yandexmapkit.utils.GeoPoint;

public class YandexMapFragment extends Fragment {

       private static final YandexMapFragment mapFragment = new YandexMapFragment();

        private MapController mMapController;
        private OverlayManager mOverlayManager;
        private Overlay overlay;

        private  View v;
        private  MapView mapView;
        private Collection<Place> places;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                    Bundle savedInstanceState) {

            if (v != null) {
                showPlaces();
                return v;
            }
            v = inflater.inflate(R.layout.organization_map_fragment, null, false);
            mapView = (MapView)v.findViewById(R.id.map);
            mMapController = mapView.getMapController();
            mOverlayManager = mMapController.getOverlayManager();
            mOverlayManager.getMyLocation().setEnabled(false);
            overlay = new Overlay(mMapController);

            showPlaces();

            return v;

        }


        public void initPlaces(Collection<Place> places) {
            this.places = places;
        }

        public void showPlaces() {

            // Load required resources
            Resources res = PrototypeView.getActivity().getResources();
            // Create a layer of objects for the map
            overlay.clearOverlayItems();
            mMapController.setZoomCurrent(16);

            GeoPoint point = null;
            for (Place place : places) {
                point = new GeoPoint(place.getAlt(), place.getLat());
                OverlayItem item = new OverlayItem(point, res.getDrawable(R.drawable.shop));
                overlay.addOverlayItem(item);
            }

            if (point != null) {
                mMapController.setPositionNoAnimationTo(point);
            }

            mOverlayManager.addOverlay(overlay);
        }

        public static YandexMapFragment getInstance() {
            return mapFragment;
        }

}
