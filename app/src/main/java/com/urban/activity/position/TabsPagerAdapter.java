package com.urban.activity.position;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.test.R;
import com.urban.data.Position;
import com.urban.fragments.pages.ActionsFragment;
import com.urban.fragments.pages.ContactsFragment;
import com.urban.fragments.pages.InfoFragment;
import com.urban.fragments.pages.PhotoFragment;
import com.urban.fragments.pages.PositionTabFragment;
import com.urban.fragments.pages.map.PositionMapFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter {
    private final Position categoryPosition;
    private final static int[] ICONS = {R.drawable.position_tab_desc,
            R.drawable.position_tab_contacts,
            R.drawable.position_tab_photo,
            R.drawable.position_tab_actions,
            R.drawable.position_tab_actions};

    public TabsPagerAdapter(FragmentManager fragmentManager, Position position) {
        super(fragmentManager);
        categoryPosition = position;
    }

    @Override
    public int getCount() {
        return ICONS.length;
    }

    // Returns the fragment to display for that page
    @Override
    public PositionTabFragment getItem(int position) {
        switch (position) {
            case 0:
                return InfoFragment.newInstance(categoryPosition);
            case 1:
                return ContactsFragment.newInstance(categoryPosition);
            case 2:
                return PhotoFragment.newInstance(categoryPosition);
            case 3:
                return ActionsFragment.newInstance(categoryPosition);
            case 4:
                return PositionMapFragment.newInstance(categoryPosition);
            default:
                return null;
        }
    }

    public int getIcon(int pos) {
        return ICONS[pos];
    }
}