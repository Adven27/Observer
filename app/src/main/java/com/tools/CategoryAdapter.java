package com.tools;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test.R;
import com.urban.data.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends ArrayAdapter<Category> {

    ArrayList<Category> categories = null;

    public CategoryAdapter(Context context, int layout, int textView, List<Category> objects) {
        super(context, layout, textView, objects);
        this.categories = (ArrayList<Category>)objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        Category category = categories.get(position);

        TextView textView = (TextView)view.findViewById(R.id.sliding_menu_item_label);
        textView.setText(category.getName());

        ImageView imgView = (ImageView)view.findViewById(R.id.sliding_menu_item_icon);
        Drawable drawable = ImageHelper.getDrawableFromImage(category.getIcon());
        imgView.setImageDrawable(drawable);
        return view;
    }

}
