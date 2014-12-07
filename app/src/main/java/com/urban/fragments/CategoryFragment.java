package com.urban.fragments;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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

import com.example.test.R;
import com.tools.ImageHelper;
import com.tools.LogHelper;
import com.tools.OrganizationAdapter;
import com.tools.PrototypeView;
import com.urban.activity.position.OrganizationActivity;
import com.urban.data.Category;
import com.urban.data.Image;
import com.urban.data.Organization;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;


public class CategoryFragment extends Fragment {

    private Category currentCategory = null;
    private ListView positionList;
    private OrganizationAdapter organizationAdapter;

    public void setCurrentCategory(Category category) {
        this.currentCategory = category;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View categoryLayout = inflater.inflate(R.layout.category, container, false);

        setUpOrganizationListView(categoryLayout);
        setUpBannerFragment();
        setUpCategoryTitle();

        return categoryLayout;
    }

    private void setUpOrganizationListView(View categoryLayout) {
        Collection<Organization> positions = getCategoryOrganizations();
        positionList = (ListView) categoryLayout.findViewById(R.id.organization_list);

        organizationAdapter = new OrganizationAdapter(getActivity(), positions);
        positionList.setAdapter(organizationAdapter);
        positionList.setOnItemClickListener(new OnOrganizationClickListener());
        positionList.setTextFilterEnabled(true);
    }

    private void setUpBannerFragment() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        int bannerContainerID = R.id.banner_container;

        PrototypeView.setUpContainer(fragmentManager, new BannerFragment(), bannerContainerID);
    }

    private void setUpCategoryTitle() {
        ActionBar actionBar = getActivity().getActionBar();
        if (currentCategory != null) {
            actionBar.setTitle(currentCategory.getName());
            Image icon = currentCategory.getIcon();
            if (icon != null) {
                Drawable drawable = ImageHelper.getDrawableFromImage(icon);
                actionBar.setIcon(drawable);
            }
        }
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private Set<Organization> getCategoryOrganizations() {
        try {
            return currentCategory.getOrganizations();
        } catch (Exception e) {
            Log.e(LogHelper.TAG_DB_OPERATION, "Error during db access", e);
            return Collections.emptySet();
        }
    }

    public void filter(String newText) {
        organizationAdapter.getFilter().filter(newText.toString());
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
            return organizationAdapter.getItem(position);
        }
    }
}