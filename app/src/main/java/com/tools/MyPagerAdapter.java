package com.tools;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;

public class MyPagerAdapter extends FragmentPagerAdapter {

    LayoutInflater inflater = null;

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
        /*inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public Fragment getItem(int position) {
        return ArrayListFragment.newInstance(position);
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        View v = layoutInflater.inflate(null);

        ((ViewPager) collection).addView(v,0);
        return v;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        ((ViewPager) collection).removeView((TextView) view);
    }*/
    }

    @Override
    public Fragment getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 0;
    }
}