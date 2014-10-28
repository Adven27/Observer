package com.tools;

import java.util.ArrayList;
import java.util.Collection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.R;
import com.urban.data.Position;

public class PositionAdapter extends ArrayAdapter<Position> {
    private final Context context;
    private ArrayList<Position> values;

    public PositionAdapter(Context context, int textViewResourceId, Collection<Position> values) {
        super(context, textViewResourceId, new ArrayList<Position>(values));
        this.context = context;
        this.values = new ArrayList<Position>(values);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        ViewHolder holder = null;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.category_item, parent, false);
        }

        holder = new ViewHolder();

        holder.text = (TextView) convertView.findViewById(R.id.txt);
        holder.text.setText(values.get(position).getName());

        holder.btn1View = (Button) convertView.findViewById(R.id.mapBtn);
        holder.btn2View = (Button) convertView.findViewById(R.id.likeBtn);

        holder.btn2View.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast t = Toast.makeText(context, "are you really like me?",
                        Toast.LENGTH_LONG);

                t.show();
            }
        });

        //convertView.setTag(holder);

        return convertView;
    }

    private static class ViewHolder {
        TextView text;
        Button btn1View;
        Button btn2View;
   }
}