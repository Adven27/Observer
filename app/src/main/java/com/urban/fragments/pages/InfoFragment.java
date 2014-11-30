package com.urban.fragments.pages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.test.R;
import com.urban.data.Organization;

public class InfoFragment extends OrganizationTabFragment {

    private static final int LAYOUT_ID = R.layout.organization_info;

    public static InfoFragment newInstance(Organization organization) {
        InfoFragment fragment = new InfoFragment();
        fragment.organization = organization;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(LAYOUT_ID, container, false);
        fillPositionInfo(view);
        return view;
    }

    private void fillPositionInfo(View view) {
        TextView text = (TextView) view.findViewById(R.id.info_name);
        text.setText(organization.getName());
        text = (TextView) view.findViewById(R.id.info_text);
        text.setText(organization.getDescription());
    }
}