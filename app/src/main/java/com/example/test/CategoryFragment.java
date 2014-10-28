package com.example.test;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.example.prototype.dao.DAO;
//import com.example.prototype.dao.DAO;
import com.tools.MyArrayAdapter;
import com.tools.PrototypeView;
import com.urban.entity.Position;

public class CategoryFragment extends Fragment {

    private String[] positions = {"������� ������", "���������", "��� �������", "��� ����", "���", "������� �����", "il Patio", "������ ���������", "Mock Duck", "���� �����", "������"};

    public static final String HEADER_ARGUMENT = "headerArgument";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View category = inflater.inflate(R.layout.category, container, false);

        String header = getArguments().getString(HEADER_ARGUMENT);
        if (header != null){
            TextView categoryHeader = (TextView)category.findViewById(R.id.category_header);
            categoryHeader.setText(header);
        }

        ListView positionList = (ListView)category.findViewById(R.id.position_list);

        FragmentTransaction t = getActivity().getSupportFragmentManager().beginTransaction();

        t.add(R.id.banner_container, MainActivity.bannerFragment);
        t.commit();

        try {
            int i = 1;          
            for (Position pos : DAO.getAll(Position.class)){
                positions[i] = pos.getName();
                i++;
            }
        } catch (Exception e) {
            Log.e("", "Error during db access", e);
        }
        positionList.setAdapter(
                new MyArrayAdapter<String>(getActivity(), R.layout.category_item, positions));

        positionList.setOnItemClickListener(new MyOnItemClickListener(getActivity().getSupportFragmentManager()));

        return category;
    }


    private class MyOnItemClickListener implements OnItemClickListener {

        private FragmentManager mgr;

        public MyOnItemClickListener(FragmentManager manager){
            super();
            this.mgr = manager;
        }

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                long arg3) {

            FragmentTransaction t = mgr.beginTransaction();
            t.remove(MainActivity.bannerFragment);
            t.commit();

            PrototypeView.setCurrentContainerId(R.id.main_container);
            //TODO: crutch!!!
            //PrototypeView.doInTransaction(new PrototypeView.ShowPositionAction(), true);

        }
    }
}
