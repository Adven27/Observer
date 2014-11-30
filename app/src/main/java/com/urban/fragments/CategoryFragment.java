package com.urban.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.tools.OrganizationAdapter;
import com.tools.PrototypeView;
import com.urban.activity.position.OrganizationActivity;
import com.urban.data.Category;
import com.urban.data.Organization;

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
        setUpOrganizationListView(categoryLayout);
        setUpBannerFragment();

        return categoryLayout;
    }

    private void setUpCategoryHeader(View categoryLayout) {
        TextView categoryHeader = (TextView) categoryLayout.findViewById(R.id.category_header);
        categoryHeader.setText(currentCategory.getName());
    }

    private void setUpOrganizationListView(View categoryLayout) {
        Collection<Organization> positions = getCategoryOrganizations();
        ListView positionList = (ListView) categoryLayout.findViewById(R.id.organization_list);

        positionList.setAdapter(new OrganizationAdapter(getActivity(), positions));
        positionList.setOnItemClickListener(new OnOrganizationClickListener());
    }

    private void setUpBannerFragment() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        int bannerContainerID = R.id.banner_container;

        PrototypeView.setUpContainer(fragmentManager, new BannerFragment(), bannerContainerID);
    }

    private Set<Organization> getCategoryOrganizations() {
        try {
            return currentCategory.getOrganizations();
        } catch (Exception e) {
            Log.e(LogHelper.TAG_DB_OPERATION, "Error during db access", e);
            return Collections.emptySet();
        }
    }

    private class OnOrganizationClickListener implements OnItemClickListener {
        public OnOrganizationClickListener() {
            super();
        }

        @Override
        public void onItemClick(AdapterView<?> adapter, View arg1, int position, long arg3) {
            Organization organization = getSelectedOrganization(adapter, position);

            Intent intent = new Intent(getActivity(), OrganizationActivity.class);
            intent.putExtra(OrganizationActivity.EXTRA_POSITION_ID, organization.getId());
            startActivity(intent);
        }

        private Organization getSelectedOrganization(AdapterView<?> adapter, int position) {
            OrganizationAdapter posAdapter = (OrganizationAdapter) adapter.getAdapter();
            return posAdapter.getItem(position);
        }
    }
}