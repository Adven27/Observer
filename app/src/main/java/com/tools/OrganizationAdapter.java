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
import com.urban.activity.task.SubscribeOrganizationTask;
import com.urban.activity.task.UnsubscribeOrganizationTask;
import com.urban.appl.Settings;
import com.urban.data.Organization;
import com.urban.data.User;
import com.urban.data.dao.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import src.com.urban.data.sqlite.pojo.UserPojo;

public class OrganizationAdapter extends ArrayAdapter<Organization> {
    private final Context context;
    private ArrayList<Organization> values;

    public OrganizationAdapter(Context context, Collection<Organization> values) {
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

        final Organization positionItem = values.get(position);
        holder.text.setText(positionItem.getName());

        //TODO: Change strategy of marking.
        User loggedUser = Settings.getLoggedUser();
        if (loggedUser != null) {
            Set<Organization> subscribes = loggedUser.getSubscribes();
            holder.likeBtn.setActivated(subscribes != null && subscribes.contains(positionItem));

            // Saving of organization in view to get if when click will appear.
            holder.likeBtn.setId(position);
            holder.likeBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!v.isActivated()) {
                        SubscribeOrganizationTask task = new SubscribeOrganizationTask(
                                OrganizationAdapter.this, Settings.getLoggedUser(), holder.likeBtn, positionItem);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        } else {
                            task.execute();
                        }
                    } else {
                        UnsubscribeOrganizationTask task = new UnsubscribeOrganizationTask(
                                OrganizationAdapter.this, Settings.getLoggedUser(), holder.likeBtn, positionItem);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        } else {
                            task.execute();
                        }
                    }
                }
            });
        } else {
            holder.likeBtn.setVisibility(View.GONE);
        }
        return convertView;
    }

    public void subscribe(View view) {
        UserPojo user = (UserPojo)Settings.getLoggedUser();

        Set<Organization> subscribes = user.getSubscribes();
        subscribes.add(values.get(view.getId()));
        user.setSubscribes(subscribes);
        try {
            DAO.save(user);
            view.setActivated(true);
            Toast.makeText(context, "You were subscribed on this organization!", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            //TODO: log this!
            Toast.makeText(context, "You were not subscribed!", Toast.LENGTH_SHORT).show();
        }
    }

    public void unsubscribe(View view) {
        UserPojo user = (UserPojo)Settings.getLoggedUser();

        Set<Organization> subscribes = user.getSubscribes();
        subscribes.remove(values.get(view.getId()));
        user.setSubscribes(subscribes);
        try {
            DAO.save(user);
            view.setActivated(false);
            Toast.makeText(context, "You were unsubscribed from this organization!", Toast.LENGTH_SHORT).show();
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