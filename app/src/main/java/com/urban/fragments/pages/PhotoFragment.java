package com.urban.fragments.pages;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.urban.data.Organization;
import com.urban.observer.R;

public class PhotoFragment extends OrganizationTabFragment {

    private static final int LAYOUT_ID = R.layout.organization_photo;

    public static PhotoFragment newInstance(Organization organization) {
        PhotoFragment fragment = new PhotoFragment();
        fragment.organization = organization;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(LAYOUT_ID, container, false);
        return view;
    }
}
