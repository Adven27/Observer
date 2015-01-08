package com.urban.service.gcm;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.test.R;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.urban.activity.position.OrganizationActivity;
import com.urban.data.Action;
import com.urban.data.Organization;
import com.urban.data.dao.DAO;
import com.urban.receiver.UrbanGcmBroadcastReceiver;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import src.com.urban.data.sqlite.pojo.ActionPojo;
import src.com.urban.data.sqlite.pojo.OrganizationPojo;

/**
 * Created by MetallFoX on 01.11.2014.
 */
public class UrbanGcmIntentService extends IntentService {

    private static final String TAG = "UrbanGcmIntentService";

    public static final String EXTRA_ID = "id";
    public static final String EXTRA_START_DATE = "start_date";
    public static final String EXTRA_END_DATE = "end_date";
    public static final String EXTRA_SUBJECT = "subject";
    public static final String EXTRA_ORGANIZATION_ID = "organization_id";

    private NotificationManager notificationManager;

    public UrbanGcmIntentService() {
        super("UrbanGcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
            /*
             * Filter messages based on message type. Since it is likely that GCM
             * will be extended in the future with new message types, just ignore
             * any message types you're not interested in, or that you don't
             * recognize.
             */
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                Log.i(TAG, "Error msg received: " + extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
                Log.i(TAG, "Deleted message on server: " + extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                // If it's a regular GCM message, do some work.
                //TODO: add an uuid field and use with unique criterion search and organization filter.
                String actionUniqueId = extras.getString(EXTRA_ID);
                Action action = DAO.get(Action.class, Long.valueOf(actionUniqueId));
                if (action == null) {
                    action = createPromotion(extras);
                    try {
                        DAO.save(action);
                    } catch (SQLException e) {
                        Log.e(TAG, "Promotion wasn't saved: " + action.getSubject());
                        //TODO: handle this!
                    }
                }

                // if action were found or created, send a notification.
                if (action != null) {
                    Notification notification = createNotification("" + actionUniqueId, action);
                    sendNotification(action.getId(), notification);
                    Log.i(TAG, "Received: " + action.getSubject());
                }

                Log.i(TAG, "Completed work @ " + SystemClock.elapsedRealtime());
            }
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        UrbanGcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    // Put the message into a notification and post it.
    // This is just one simple example of what you might choose to do with a GCM message.
    private void sendNotification(int id, Notification notification) {
        notificationManager = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(id, notification);
    }

    private Action createPromotion(Bundle data) {
        ActionPojo action = new ActionPojo();
        try {
            Date startDate = DATE_FORMAT.parse(data.getString(EXTRA_START_DATE));
            action.setStartDate(startDate);
        } catch (ParseException e) {
            //TODO: throw own exception to handle upper.
            Log.i(TAG, e.getMessage());
        }

        String endDateStr = data.getString(EXTRA_END_DATE);
        // End of action may not be set.
        if (endDateStr != null) {
            try {
                Date endDate = DATE_FORMAT.parse(endDateStr);
                action.setEndDate(endDate);
            } catch (ParseException e) {
                //TODO: throw own exception to handle upper, 'cause date was not null in extras.
                Log.i(TAG, e.getMessage());
            }
        }

        action.setSubject(data.getString(EXTRA_SUBJECT));

        Organization organization = DAO.get(Organization.class, data.getLong(EXTRA_ORGANIZATION_ID));
        if (organization == null) {
            //TODO: handle this situation! May be we need to suck it in? :)
        }

        action.setOrganization((OrganizationPojo)organization);
        return action;
    }

    private Notification createNotification(String uuid, Action action) {
        Intent intent = new Intent(this, OrganizationActivity.class);
        intent.setAction(uuid);
        intent.putExtra(OrganizationActivity.EXTRA_ORGANIZATION_ID, action.getOrganization().getId());
        PendingIntent contentIntent = PendingIntent.getActivity(this, action.getId(), intent, 0);
        String text = action.getSubject();

        return new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(getString(R.string.notification_title_action))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(text))
                .setContentText(text)
                .setContentIntent(contentIntent)
                .build();
    }

    /**
     * Date format for parse dates from extras.
     * It is not thread safe there 'cause it likely uses only in single thread.
     */
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
}