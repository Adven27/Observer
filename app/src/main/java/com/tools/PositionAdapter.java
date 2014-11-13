package com.tools;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.R;
import com.urban.activity.task.SubscribePositionTask;
import com.urban.activity.task.UnsubscribePositionTask;
import com.urban.appl.Settings;
import com.urban.data.Position;
import com.urban.data.User;
import com.urban.data.dao.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import src.com.urban.data.sqlite.pojo.UserPojo;

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

        final ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.category_item, parent, false);

            holder = new ViewHolder();

            holder.text = (TextView) convertView.findViewById(R.id.txt);
            holder.mapBtn = (Button) convertView.findViewById(R.id.mapBtn);
            holder.likeBtn = (Button) convertView.findViewById(R.id.likeBtn);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        final Position positionItem = values.get(position);
        holder.text.setText(positionItem.getName());

        //TODO: Change strategy of marking.
        User loggedUser = Settings.getLoggedUser();
        if (loggedUser != null) {
            Set<Position> subscribes = loggedUser.getSubscribes();
            holder.likeBtn.setActivated(subscribes != null && subscribes.contains(positionItem));
        }

        // Saving of position in view to get if when click will appear.
        holder.likeBtn.setId(position);
        holder.likeBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!v.isActivated()) {
                    SubscribePositionTask task = new SubscribePositionTask(
                            PositionAdapter.this, Settings.getLoggedUser(), holder.likeBtn, positionItem);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    } else {
                        task.execute();
                    }
                } else {
                    UnsubscribePositionTask task = new UnsubscribePositionTask(
                            PositionAdapter.this, Settings.getLoggedUser(), holder.likeBtn, positionItem);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    } else {
                        task.execute();
                    }
                }
            }
        });
        return convertView;
    }

    public void subscribe(View view) {
        UserPojo user = (UserPojo)Settings.getLoggedUser();

        user.getSubscribes().add(values.get(view.getId()));
        try {
            DAO.save(user);
            view.setActivated(true);
            Toast.makeText(context, "You were subscribed on this position!", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            //TODO: log this!
            Toast.makeText(context, "You were not subscribed!", Toast.LENGTH_SHORT).show();
        }
    }

    public void unsubscribe(View view) {
        UserPojo user = (UserPojo)Settings.getLoggedUser();

        user.getSubscribes().remove(values.get(view.getId()));
        try {
            DAO.save(user);
            view.setActivated(false);
            Toast.makeText(context, "You were unsubscribed from this position!", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            //TODO: log this!
            Toast.makeText(context, "You were not unsubscribed!", Toast.LENGTH_SHORT).show();
        }
    }


    private static class ViewHolder {
        TextView text;
        Button mapBtn;
        Button likeBtn;

    }
}