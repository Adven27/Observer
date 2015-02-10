package com.urban.activity;

import android.os.Bundle;

import com.tools.PromotionAdapter;
import com.urban.observer.R;

public class PromotionActivity extends UrbanActivity {

    private PromotionAdapter promoAdapter;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.promos);

        /*
        ListView promoList = (ListView) categoryLayout.findViewById(R.id.organization_list);

        promoAdapter = new PromoAdapter(this, positions);
        promoList.setAdapter(promoAdapter);
        promoList.setOnItemClickListener(new OnOrganizationClickListener());
        promoList.setTextFilterEnabled(true);
*/
    }

}