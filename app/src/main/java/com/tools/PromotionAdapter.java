package com.tools;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.urban.data.Action;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adven on 2/9/15.
 */
public class PromotionAdapter extends ArrayAdapter<Action> {
    ArrayList<Action> promos = null;

    public PromotionAdapter(Context context, int resource, List<Action> objects) {
        super(context, resource, objects);
        this.promos = (ArrayList<Action>)objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        TextView textView = (TextView)view.findViewById(android.R.id.text1);
        textView.setText(promos.get(position).getSubject());
        return view;
    }

    private class Holder {
        TextView title;
        TextView organizationName;
        TextView startDate;
        TextView endDate;
    }
}
