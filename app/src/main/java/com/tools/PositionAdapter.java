package com.tools;

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
import com.urban.appl.Settings;
import com.urban.data.Position;
import com.urban.data.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class PositionAdapter extends ArrayAdapter<Position> {
    private final Context context;
    private ArrayList<Position> values;

    public PositionAdapter(Context context, Collection<Position> values) {
        super(context, R.layout.category_item, new ArrayList<>(values));
        this.context = context;
        this.values = new ArrayList<>(values);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.category_item, parent, false);
        }

        holder = new ViewHolder();

        holder.text = (TextView) convertView.findViewById(R.id.txt);
        holder.text.setText(values.get(position).getName());

        holder.mapBtn = (Button) convertView.findViewById(R.id.mapBtn);
        holder.likeBtn = (Button) convertView.findViewById(R.id.likeBtn);

        //Change strategy of marking.
        User loggedUser = Settings.getLoggedUser();
        if (loggedUser != null) {
            Set<Position> subscribes = loggedUser.getSubscribes();
            if (subscribes != null && subscribes.contains(values.get(position))) {
                holder.likeBtn.setActivated(false);

                //TODO: Stub. just for test. remove this.
                holder.mapBtn.setVisibility(View.INVISIBLE);
            }
        } else {
            holder.mapBtn.setVisibility(View.INVISIBLE);
            holder.likeBtn.setVisibility(View.INVISIBLE);
        }

        holder.likeBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "are you really like me?",Toast.LENGTH_LONG).show();
            }
        });

        //convertView.setTag(holder);

        return convertView;
    }

    private static class ViewHolder {
        TextView text;
        Button mapBtn;
        Button likeBtn;
    }
}