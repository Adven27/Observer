package com.urban.activity.dashboard;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test.R;
import com.urban.data.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adven on 06.11.14.
 */
public class DashboardAdapter extends ArrayAdapter {
    private Context mContext;
    private ArrayList<Category> categories;

    public DashboardAdapter(Context context, List<Category> objects) {
        super(context, R.layout.dashboard_cell, R.id.label, objects);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = super.getView(position, convertView, parent);

        //TODO: брать иконки категорий из базы?
        //ImageView icon = (ImageView) row.findViewById(R.id.icon);
        //icon.setImageResource(R.drawable.dash_rest);

        TextView label = (TextView) row.findViewById(R.id.label);
        label.setText(((Category) getItem(position)).getName());
        return (row);
    }
}
