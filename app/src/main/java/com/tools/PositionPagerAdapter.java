package com.tools;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.urban.elements.pages.PageView;

public class PositionPagerAdapter extends PagerAdapter {

    private ArrayList<PageView> pages;

    public PositionPagerAdapter(ArrayList<PageView> pages) {
        this.pages = pages;
    }

    @Override
    public int getCount() {
        return pages != null ? pages.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return !(view == null) && view.equals(object);
    }

    @Override
    public Object instantiateItem(View collection, int position) {
        View v = pages.get(position);
        ((ViewPager) collection).addView(v, 0);
        return v;
    }

    @Override
    public void destroyItem(View collection, int position, Object view) {
        ((ViewPager) collection).removeView((View) view);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        PageView page = pages.get(position);
        return page != null ? page.getText() : null;
    }

}
