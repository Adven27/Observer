package com.urban.activity.position;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.test.R;
import com.urban.data.Organization;
import com.urban.fragments.pages.ActionsFragment;
import com.urban.fragments.pages.InfoFragment;
import com.urban.fragments.pages.OrganizationTabFragment;
import com.urban.fragments.pages.PhotoFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter {
    private final Organization categoryOrganization;
    private final static int[] ICONS = {
            R.drawable.organization_tab_desc,
            R.drawable.organization_tab_photo,
            R.drawable.organization_tab_actions
    };

    public TabsPagerAdapter(FragmentManager fragmentManager, Organization organization) {
        super(fragmentManager);
        categoryOrganization = organization;
    }

    @Override
    public int getCount() {
        return ICONS.length;
    }

    // Returns the fragment to display for that page
    @Override
    public OrganizationTabFragment getItem(int position) {
        switch (position) {
            case 0:
                return InfoFragment.newInstance(categoryOrganization);
            case 1:
                return PhotoFragment.newInstance(categoryOrganization);
            case 2:
                return ActionsFragment.newInstance(categoryOrganization);
            default:
                return null;
        }
    }

    public int getIcon(int pos) {
        return ICONS[pos];
    }
}