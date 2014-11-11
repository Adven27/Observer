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

    private Category currentCategory = null;

    public void setCurrentCategory(Category category) {
        this.currentCategory = category;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View categoryLayout = inflater.inflate(R.layout.category, container, false);

        setUpCategoryHeader(categoryLayout);
        setUpPositionListView(categoryLayout);
        addOrReplaceBannerFragment();

        return categoryLayout;
    }

    private void setUpCategoryHeader(View categoryLayout) {
        TextView categoryHeader = (TextView) categoryLayout.findViewById(R.id.category_header);
        categoryHeader.setText(currentCategory.getName());
    }

    private void setUpPositionListView(View categoryLayout) {
        Collection<Position> positions = getCategoryPositions();
        ListView positionList = (ListView) categoryLayout.findViewById(R.id.position_list);

        positionList.setAdapter(new PositionAdapter(getActivity(), positions));
        positionList.setOnItemClickListener(new OnPositionClickListener());
    }

    private void addOrReplaceBannerFragment() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        int bannerContainerID = R.id.banner_container;

        if (isFragmentAlreadyAdded(fragmentManager, bannerContainerID)) {
            replaceFragment(fragmentManager, bannerContainerID);
        } else {
            addFragment(fragmentManager, bannerContainerID);
        }
    }

    private void replaceFragment(FragmentManager fragmentManager, int bannerContainerID) {
        FragmentTransaction t = fragmentManager.beginTransaction();
        t.replace(bannerContainerID, new BannerFragment());
        t.commit();
    }

    private void addFragment(FragmentManager fragmentManager, int bannerContainerID) {
        FragmentTransaction t = fragmentManager.beginTransaction();
        t.add(bannerContainerID, new BannerFragment());
        t.commit();
    }

    private boolean isFragmentAlreadyAdded(FragmentManager fragmentManager, int bannerContainerId) {
        return fragmentManager.findFragmentById(bannerContainerId) != null;
    }

    private Set<Position> getCategoryPositions() {
        try {
            return currentCategory.getPositions();
        } catch (Exception e) {
            Log.e(LogHelper.TAG_DB_OPERATION, "Error during db access", e);
            return Collections.emptySet();
        }
    }


    private class OnPositionClickListener implements OnItemClickListener {
        public OnPositionClickListener() {
            super();
        }

        @Override
        public void onItemClick(AdapterView<?> adapter, View arg1, int position, long arg3) {
            Position pos = getSelectedPosition(adapter, position);

            Intent intent = new Intent(getActivity(), PositionActivity.class);
            intent.putExtra(PositionActivity.EXTRA_POSITION_ID, pos.getId());
            startActivity(intent);
        }

        private Position getSelectedPosition(AdapterView<?> adapter, int position) {
            PositionAdapter posAdapter = (PositionAdapter) adapter.getAdapter();
            return posAdapter.getItem(position);
        }
    }
}