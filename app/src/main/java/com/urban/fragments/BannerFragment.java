package com.urban.fragments;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageSwitcher;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;

import com.example.test.R;
import com.tools.ImageHelper;
import com.tools.LogHelper;
import com.tools.PrototypeView;
import com.urban.activity.position.OrganizationActivity;
import com.urban.data.Advertising;
import com.urban.data.Image;
import com.urban.data.Organization;
import com.urban.data.dao.DAO;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class BannerFragment extends Fragment {
    public static final int DISPLAY_IMAGE_DELAY = 5000;
    private static ImageSwitcher switcher;
    private static Timer timer = new Timer();

    private ArrayList<Advertising> advertisements = null;
    private int advertImgIndexForShow;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        callDownloadImgAsyncTask();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initAdvertisementsFromDB();
        setUpSwitcher(inflater, container);


        return switcher;
    }

    private void initAdvertisementsFromDB() {
        try {
            advertisements = (ArrayList<Advertising>) DAO.getAll(Advertising.class);
        } catch (Exception e) {
            Log.e(LogHelper.TAG_DB_OPERATION, "Error during db access", e);
        }
    }

    private void setUpSwitcher(LayoutInflater inflater, ViewGroup container) {
        if (switcher != null) {
            ViewGroup parent = (ViewGroup) switcher.getParent();
            if (parent != null) {
                parent.removeView(switcher);
            }
        }
        switcher = (ImageSwitcher) inflater.inflate(R.layout.banner_fragment, container, false);
        switcher.setFactory(new ImageSwitcherFactory());
        switcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Не совсем правильно. Надо бы синхронизировать
                Organization organization = advertisements.get(advertImgIndexForShow).getOrganization();
                if (organization != null) {
                    redirectToOrganization(organization);
                } else {
                    Toast.makeText(
                            getActivity().getApplicationContext(),
                            "Данная реклама не связана с организацией",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public void onDestroy() {
        removeTimerAndTasks();
        super.onDestroy();
    }

    private void removeTimerAndTasks() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }
    }

    void callDownloadImgAsyncTask() {
        if (timer == null) {
            timer = new Timer();
        }
        final Handler handler = new Handler();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            DownloadImageTask downloadImageBackgroundTask = new DownloadImageTask();
                            downloadImageBackgroundTask.execute();
                        } catch (Exception e) {
                            Log.w(LogHelper.TAG_ASYNC_TASK_OPERATION,
                                    "Error on download Image Background Task", e);
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, DISPLAY_IMAGE_DELAY);
    }

    private class ImageSwitcherFactory implements ViewFactory {
        public View makeView() {
            return LayoutInflater.from(getActivity().getApplicationContext())
                    .inflate(R.layout.banner_img, null, false);
        }
    }

    private void redirectToOrganization(Organization organization) {
        Intent intent = new Intent(PrototypeView.getActivity(), OrganizationActivity.class);
        intent.putExtra(OrganizationActivity.EXTRA_POSITION_ID, organization.getId());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PrototypeView.getActivity().startActivity(intent);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Drawable> {

        protected Drawable doInBackground(String... urls) {
            if (haveAdvertisements()) {
                return getAdvertDrawable();
            }
            return getDefaultDrawable();
        }

        private boolean haveAdvertisements() {
            return advertisements != null && !advertisements.isEmpty();
        }

        private Drawable getDefaultDrawable() {
            return getResources().getDrawable(R.drawable.banner_default);
        }

        private Drawable getAdvertDrawable() {
            int i = calcNextAdvertImgIndexForShow();
            Image img = advertisements.get(i).getImage();
            return ImageHelper.getDrawableFromImage(img);
        }

        private int calcNextAdvertImgIndexForShow() {
            return advertImgIndexForShow = (++advertImgIndexForShow % advertisements.size());
        }

        protected void onPostExecute(Drawable drawable) {
            switcher.setImageDrawable(drawable);
        }
    }

}