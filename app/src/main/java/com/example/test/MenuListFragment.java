package com.example.test;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tools.PrototypeView;

public class MenuListFragment extends ListFragment {

    String[] categories = {"���", "�����", "�����������", "���������"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, categories));

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        PrototypeView.setCurrentContainerId(R.id.main_container);
//        PrototypeView.doInTransaction(new ShowCategoryListAction(categories[position]), true);

    }
}
