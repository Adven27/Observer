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

/**
 * Created by adven on 06.11.14.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {
    private final Position categoryPosition;
    private final int[] ICON = {R.drawable.ic_launcher,
            R.drawable.ic_launcher,
            R.drawable.ic_launcher,
            R.drawable.ic_launcher,
            R.drawable.ic_launcher};

    public TabsPagerAdapter(FragmentManager fragmentManager, Position position) {
        super(fragmentManager);
        categoryPosition = position;
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return ICON.length;
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
        return ICON[pos];
    }

}