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
import com.tools.LogHelper;
import com.tools.PositionAdapter;
import com.urban.activity.position.PositionActivity;
import com.urban.data.Category;
import com.urban.data.Position;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;


public class CategoryFragment extends Fragment {

    private Collection<Position> positions = null;
    private Category currentCategory = null;


    public void setCurrentCategory(Category category) {
        this.currentCategory = category;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View category = inflater.inflate(R.layout.category, container, false);

        if (currentCategory != null) {
            TextView categoryHeader = (TextView) category.findViewById(R.id.category_header);
            categoryHeader.setText(currentCategory.getName());

            FragmentTransaction t = getActivity().getSupportFragmentManager().beginTransaction();
            t.add(R.id.banner_container, BannerFragment.getInstance());
            t.commit();

            positions = getCategoryPositions();

            ListView positionList = (ListView) category.findViewById(R.id.position_list);
            positionList.setAdapter(new PositionAdapter(getActivity(), positions));
            positionList.setOnItemClickListener(
                    new OnPositionClickListener(getActivity().getSupportFragmentManager()));
        }

        return category;
    }

    private Set<Position> getCategoryPositions() {
        try {
            return currentCategory.getPositions();
        } catch (Exception e) {
            Log.e(LogHelper.TAG_DB_OPERATION, "Error during db access", e);
            return Collections.<Position>emptySet();
        }
    }


    private class OnPositionClickListener implements OnItemClickListener {

        private FragmentManager mgr;

        public OnPositionClickListener(FragmentManager manager) {
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
