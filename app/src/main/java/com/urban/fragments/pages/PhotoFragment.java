package com.urban.fragments.pages;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test.R;
import com.urban.data.Position;

public class PhotoFragment extends PositionTabFragment {

    private static final int LAYOUT_ID = R.layout.position_photo;

    /**
     * newInstance constructor for creating fragment with arguments
     *
     * @param position
     */
    public static PhotoFragment newInstance(Position position) {
        PhotoFragment fragment = new PhotoFragment();
        fragment.position = position;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(LAYOUT_ID, container, false);
        /*TextView text = (TextView)page.findViewById(R.id.info_name);
        text.setText(position.getName());
        text = (TextView)page.findViewById(R.id.info_text);
        text.setText(position.getOrganization().getDescription());*/
        return view;

    }
}
