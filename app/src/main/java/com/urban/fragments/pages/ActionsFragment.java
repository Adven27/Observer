package com.urban.fragments.pages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.test.R;
import com.urban.data.Action;
import com.urban.data.Organization;

import java.util.Set;

public class ActionsFragment extends OrganizationTabFragment {

    private static final int LAYOUT_ID = R.layout.organization_actions;


    public static ActionsFragment newInstance(Organization organization) {
        ActionsFragment fragment = new ActionsFragment();
        fragment.organization = organization;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(LAYOUT_ID, container, false);
        Set<Action> actions = organization.getActions();
        LinearLayout actionLayout = (LinearLayout)view.findViewById(R.id.action_layout);
        for (Action action : actions) {
            TextView text = new TextView(getActivity().getApplicationContext());
            text.setText(action.getSubject());
            actionLayout.addView(text);
        }

        return view;
    }
}