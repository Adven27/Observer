package com.urban.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.test.R;
import com.example.test.R.id;
import com.example.test.R.layout;
import com.tools.ExpandableListAdapter;

public class ExpandableListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.expandable_list, container, false);


        ExpandableListView listView = (ExpandableListView)v.findViewById(R.id.expandable_list);


        ArrayList<ArrayList<String>> groups = new ArrayList<ArrayList<String>>();
        ArrayList<String> children1 = new ArrayList<String>();
        ArrayList<String> children2 = new ArrayList<String>();
        children1.add("Child_1");
        groups.add(children1);

        ExpandableListAdapter adapter = new ExpandableListAdapter(getActivity().getApplicationContext(), groups);
        listView.setAdapter(adapter);



        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ExpandableListFragment f = (ExpandableListFragment) getFragmentManager().findFragmentById(R.id.expandable_list);
        if (f != null)
            getFragmentManager().beginTransaction().remove(f).commit();

    }
}
