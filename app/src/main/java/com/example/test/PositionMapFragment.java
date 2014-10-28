package com.example.test;

import ru.yandex.yandexmapkit.MapController;
import ru.yandex.yandexmapkit.MapView;
import ru.yandex.yandexmapkit.OverlayManager;
import ru.yandex.yandexmapkit.overlay.Overlay;
import ru.yandex.yandexmapkit.overlay.OverlayItem;
import ru.yandex.yandexmapkit.utils.GeoPoint;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PositionMapFragment extends Fragment {

    MapController mMapController;
    OverlayManager mOverlayManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.position_map_fragment, container, false);

        final MapView mapView = (MapView) v.findViewById(R.id.map);

        mMapController = mapView.getMapController();

        mOverlayManager = mMapController.getOverlayManager();
        // Disable determining the user's location
        mOverlayManager.getMyLocation().setEnabled(false);

        // A simple implementation of map objects
        showObject();

        return v;

    }

    public void showObject(){
        // Load required resources
        Resources res = getResources();
        // Create a layer of objects for the map
        Overlay overlay = new Overlay(mMapController);
        mMapController.setZoomCurrent(17);
        GeoPoint point = new GeoPoint(59.221765,39.888093);
        mMapController.setPositionNoAnimationTo(point);

        // Create an object for the layer
        OverlayItem kremlin = new OverlayItem(point, res.getDrawable(R.drawable.shop));
        // Create a balloon model for the object
        /*ImageBalloonItem balloonKremlin = new ImageBalloonItem(this,kremlin.getGeoPoint());
//
        balloonKremlin.setOnViewClickListener();
//        // Add the balloon model to the object
        kremlin.setBalloonItem(balloonKremlin);*/
        // Add the object to the layer
        overlay.addOverlayItem(kremlin);


        // Add the layer to the map
        mOverlayManager.addOverlay(overlay);

    }

}
