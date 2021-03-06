package com.tools;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.urban.data.Category;

import java.util.ArrayList;
import java.util.List;

public class ActionAdapter extends ArrayAdapter<Category> {

    ArrayList<Category> categories = null;

    public ActionAdapter(Context context, int resource, List<Category> objects) {
        super(context, resource, objects);
        this.categories = (ArrayList<Category>)objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        TextView textView = (TextView)view.findViewById(android.R.id.text1);
        textView.setText(categories.get(position).getName());
        return view;
    }

}
