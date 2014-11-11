package com.urban.activity.about.fragments;

import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test.R;
import com.tools.PrototypeView;

import java.util.ArrayList;

public class AboutFragment extends Fragment implements OnGesturePerformedListener {

    private GestureLibrary library;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        GestureOverlayView about = (GestureOverlayView)inflater.inflate(R.layout.about, container, false);
        about.setAlpha(0);
        library = GestureLibraries.fromRawResource(getActivity(), R.raw.gestures);

        about.addOnGesturePerformedListener(this);
        return about;
    }

    @Override
    public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
        ArrayList<Prediction> predictions = library.recognize(gesture);

        if (predictions.size() > 0) {
            Prediction maxPrediction = predictions.get(0);
            for (Prediction prediction : predictions) {
                if (prediction.score > maxPrediction.score) {
                    maxPrediction = prediction;
                }
            }
            if (maxPrediction.score > 3.0) {
                if ("Heart".equals(maxPrediction.name)) {
                    showEasternEgg();
                }
            }
        }
    }

    private static void showEasternEgg() {
        PrototypeView.setCurrentContainerId(R.id.about_frame);
        PrototypeView.doInTransaction(new PrototypeView.ShowEggAction());
    }
}
