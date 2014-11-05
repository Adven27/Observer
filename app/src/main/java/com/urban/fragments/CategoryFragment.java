package com.urban.fragments;

import android.content.Intent;
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

import com.example.test.R;
import com.tools.PositionAdapter;
import com.urban.activity.position.PositionActivity;
import com.urban.data.Category;
import com.urban.data.Position;

import java.util.Collection;
//import com.example.prototype.dao.DAO;

public class CategoryFragment extends Fragment {

    private Collection<Position> positions = null;
    private Category currentCategory = null;


    public void setCurrentCategory(Category category){
        this.currentCategory = category;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View category = inflater.inflate(R.layout.category, container, false);

        if (currentCategory != null) {
            TextView categoryHeader = (TextView)category.findViewById(R.id.category_header);
            categoryHeader.setText(currentCategory.getName());

            ListView positionList = (ListView)category.findViewById(R.id.position_list);

            FragmentTransaction t = getActivity().getSupportFragmentManager().beginTransaction();

            t.add(R.id.banner_container, BannerFragment.getInstance());
            t.commit();

            try {
                positions = currentCategory.getPositions();
            } catch (Exception e) {
                Log.e("", "Error during db access", e);
            }

            positionList.setAdapter(
                    new PositionAdapter(getActivity(), R.layout.category_item, positions));

            positionList.setOnItemClickListener(
                    new MyOnItemClickListener(getActivity().getSupportFragmentManager()));

        }

        return category;
    }


    private class MyOnItemClickListener implements OnItemClickListener {

        private FragmentManager mgr;

        public MyOnItemClickListener(FragmentManager manager){
            super();
            this.mgr = manager;
        }

        @Override
        public void onItemClick(AdapterView<?> adapter, View arg1, int position, long arg3) {

            PositionAdapter posAdapter = null;
            if (adapter.getAdapter() instanceof PositionAdapter) {
                posAdapter = (PositionAdapter) adapter.getAdapter();
            }
            Position pos = posAdapter.getItem(position);

            Intent intent = new Intent(getActivity(), PositionActivity.class);
            intent.putExtra(PositionActivity.EXTRA_POSITION_ID, pos.getId());
            startActivity(intent);

        }
    }
}
