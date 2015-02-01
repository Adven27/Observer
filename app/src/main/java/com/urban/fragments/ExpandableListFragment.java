package com.urban.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.tools.ExpandableListAdapter;
import com.urban.observer.R;

import java.util.ArrayList;

public class ExpandableListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.expandable_list, container, false);

        // Находим наш list
        ExpandableListView listView = (ExpandableListView)v.findViewById(R.id.expandable_list);

        //Создаем набор данных для адаптера
        ArrayList<ArrayList<String>> groups = new ArrayList<ArrayList<String>>();
        ArrayList<String> children1 = new ArrayList<String>();
        ArrayList<String> children2 = new ArrayList<String>();
        children1.add("Child_1");
        groups.add(children1);
        //Создаем адаптер и передаем context и список с данными
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
