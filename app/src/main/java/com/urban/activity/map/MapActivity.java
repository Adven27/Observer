package com.urban.activity.map;

import android.os.Bundle;
import android.util.Log;

import com.tools.LogHelper;
import com.tools.PrototypeView;
import com.urban.activity.UrbanActivity;
import com.urban.activity.position.OrganizationActivity;
import com.urban.data.Organization;
import com.urban.data.Place;
import com.urban.data.dao.DAO;
import com.urban.fragments.pages.map.YandexMapFragment;
import com.urban.observer.R;

import java.util.Collection;

public class MapActivity extends UrbanActivity {


    private Organization organization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organization_map);
        initOrganization();

        Collection<Place> places = organization.getPlaces();
        YandexMapFragment.setInstance(new YandexMapFragment());
        YandexMapFragment.getInstance().initPlaces(places);

        PrototypeView.setCurrentContainerId(R.id.map_container);
        PrototypeView.doInTransaction(new PrototypeView.ShowMapAction());
    }

    private void initOrganization() {
        long organizationId = getIntent().getIntExtra(OrganizationActivity.EXTRA_ORGANIZATION_ID, -1);
        organization = getOrganization(organizationId);
    }

    private Organization getOrganization(long organizationId) {
        try {
            return DAO.get(Organization.class, organizationId);
        } catch (Exception e) {
            Log.e(LogHelper.TAG_DB_OPERATION, "Can't find the organization by id: " + organizationId, e);
            return null;
        }
    }
}
