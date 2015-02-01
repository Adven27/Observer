package com.urban.fragments.pages;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.urban.data.Organization;
import com.urban.observer.R;

public class EventsFragment extends Fragment {

    private static final int LAYOUT_ID = R.layout.organization_events;
    private Organization organization;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View view = inflater.inflate(LAYOUT_ID, container, false);
        /*TextView text = (TextView)view.findViewById(R.id.info_name);
        text.setText(organization.getName());
        text = (TextView)view.findViewById(R.id.info_text);
        text.setText(organization.getDescription());*/
        return view;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

}
