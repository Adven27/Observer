package com.urban.fragments;

import android.graphics.Bitmap;
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
import android.widget.ViewSwitcher.ViewFactory;

import com.example.test.R;
import com.tools.LogHelper;
import com.urban.data.Advertising;
import com.urban.data.Image;
import com.urban.data.dao.DAO;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class BannerFragment extends Fragment {
    private static ImageSwitcher switcher;
    private static Timer timer = new Timer();

    private ArrayList<Advertising> advertisements = null;
    private int advertIndexInArray;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        callAsynchronousTask();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        advertisements = getAdvertisingFromDB();
        setUpSwitcher(inflater, container);

        return switcher;
    }

    private void setUpSwitcher(LayoutInflater inflater, ViewGroup container) {
        if (switcher != null) {
            ViewGroup parent = (ViewGroup) switcher.getParent();
            if (parent != null)
                parent.removeView(switcher);
        }
        switcher = (ImageSwitcher) inflater.inflate(R.layout.banner_fragment, container, false);
        switcher.setFactory(new MyImageSwitcherFactory());
        switcher.setImageResource(R.drawable.shop);
    }

    private ArrayList<Advertising> getAdvertisingFromDB() {
        try {
            return (ArrayList<Advertising>) DAO.getAll(Advertising.class);
        } catch (Exception e) {
            Log.e(LogHelper.TAG_DB_OPERATION, "Error during db access", e);
        }
        return null;
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

    private Drawable getDrawableFromImage(Image image) {
        if (image == null) {
            return null;
        }
        Drawable drawable = null;
        InputStream is = image.getAsStream();

        try {
            drawable = Drawable.createFromStream(is, "name");
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    Log.e("BannerFragment", "An error during close() operation on InputStream");
                }
            }
        }
        return drawable;
    }

    void callAsynchronousTask() {
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
        timer.schedule(doAsynchronousTask, 0, 5000);
    }

    private class MyImageSwitcherFactory implements ViewFactory {
        public View makeView() {
            return LayoutInflater.from(getActivity().getApplicationContext())
                    .inflate(R.layout.banner_img, null, false);
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        protected Bitmap doInBackground(String... urls) {
            //TOD: load Image From Network?
            return null;
        }

        protected void onPostExecute(Bitmap result) {
            if (haveAdvertisements()) {
                setAdvertImgToSwitcher();
                //TODO: WTF?...
                advertIndexInArray = ++advertIndexInArray % advertisements.size();
            }
        }

        private boolean haveAdvertisements() {
            return advertisements != null && !advertisements.isEmpty();
        }

        private void setAdvertImgToSwitcher() {
            Image img = advertisements.get(advertIndexInArray).getImage();
            Drawable drawable = getDrawableFromImage(img);

            if (drawable != null) {
                switcher.setImageDrawable(drawable);
            }
        }
    }
}