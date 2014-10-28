package com.urban.activity.position;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.example.test.R;
import com.tools.PositionPagesFragmentsAdapter;
import com.tools.PrototypeView;
import com.urban.data.Position;
import com.urban.data.dao.DAO;
import com.urban.fragments.pages.ActionsFragment;
import com.urban.fragments.pages.ContactsFragment;
import com.urban.fragments.pages.InfoFragment;
import com.urban.fragments.pages.PhotoFragment;
import com.urban.fragments.pages.map.PositionMapFragment;
import com.viewpagerindicator.TitlePageIndicator;

public class PositionActivity extends FragmentActivity {


    public static final String EXTRA_POSITION_ID = "position_id";
    private static final String TAG = "PositionActivity";


    private Position position = null;

    private ViewPager pager;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.position);

        PrototypeView.switchActivity(this);

        long positionId = getIntent().getIntExtra(EXTRA_POSITION_ID, -1);

        try {
            position = DAO.get(Position.class, positionId);
        } catch (Exception e) {
            Log.e(TAG, "Can't find the position by id: " + positionId);
        }

        List<Fragment> fragments = new ArrayList<Fragment>();
        pager = (ViewPager)findViewById(R.id.view_pager);

        InfoFragment info = new InfoFragment();
        info.setPosition(position);

        ContactsFragment contacts = new ContactsFragment();
        contacts.setPosition(position);

        PhotoFragment photo = new PhotoFragment();
        photo.setPosition(position);

        PositionMapFragment map = new PositionMapFragment();
        map.setPosition(position);

        ActionsFragment actions = new ActionsFragment();
        actions.setPosition(position);

        fragments.add(info);
        fragments.add(contacts);
        fragments.add(photo);
        fragments.add(actions);
        fragments.add(map);

        FragmentPagerAdapter adapter = new PositionPagesFragmentsAdapter(getSupportFragmentManager(), fragments);

        /*PositionPagerAdapter pagerAdapter = new PositionPagerAdapter(pages);
        pager.setAdapter(pagerAdapter);
        */
        pager.setAdapter(adapter);


        /*PrototypeView.setCurrentContainerId(R.id.map_container);
        PrototypeView.doInTransaction(new ShowMapAction(), false);*/

        TitlePageIndicator titleIndicator = (TitlePageIndicator)findViewById(R.id.indicator);
        titleIndicator.setViewPager(pager);
    }
}
