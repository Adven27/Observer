package com.urban.fragments;

import android.app.Activity;
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
import android.widget.ImageView;
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

    private static final BannerFragment bannerFragment = new BannerFragment();
    private static ImageSwitcher switcher;
    private static Timer timer = new Timer();

    ArrayList<Advertising> advertisings = null;
    private int advertIndexInArray;

    public static BannerFragment getInstance() {
        return bannerFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        callAsynchronousTask();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        advertisings = getAdvertisingFromDB();
        setUpSwitcher(inflater, container);

        return switcher;
    }

    private void setUpSwitcher(LayoutInflater inflater, ViewGroup container) {
        if (switcher != null) {
            ViewGroup parent = (ViewGroup) switcher.getParent();
            if (parent != null)
                parent.removeView(switcher);
        }
        //try {
            switcher = (ImageSwitcher) inflater.inflate(R.layout.banner_fragment, container, false);
            switcher.setFactory(new MyImageSwitcherFactory());
            switcher.setImageResource(R.drawable.shop);
        //} catch (InflateException e) {}
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

    /**
     * Method for getting Drawable object by Image.
     *
     * @param image - src Image for Drawable.
     * @return Drawable or null if can not create Drawable by src.
     */
    private Drawable getDrawableFromImage(Image image) {
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

    private Bitmap loadImageFromNetwork(String url) {
        return null;
    }

    public void callAsynchronousTask() {
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
                        } catch (Exception e) {}
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 5000); //execute in every 50000 ms
    }

    private class MyImageSwitcherFactory implements ViewFactory {
        public View makeView() {
            ViewGroup mQuestionImage = null;
            ImageView imageView = (ImageView) LayoutInflater.from(
                    getActivity().getApplicationContext()).inflate(
                    R.layout.banner_img, mQuestionImage, false);
            return imageView;
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        /**
         * The system calls this to perform work in a worker thread and
         * delivers it the parameters given to AsyncTask.execute()
         */
        protected Bitmap doInBackground(String... urls) {
            return loadImageFromNetwork("qqq");
        }

        /**
         * The system calls this to perform work in the UI thread and delivers
         * the result from doInBackground()
         */
        protected void onPostExecute(Bitmap result) {
            if (advertisings != null && !advertisings.isEmpty()) {
                Image img = advertisings.get(advertIndexInArray).getImage();
                if (img != null) {
                    Drawable drawable = getDrawableFromImage(img);
                    if (drawable != null) {
                        switcher.setImageDrawable(drawable);
                    }
                }
                advertIndexInArray = ++advertIndexInArray % advertisings.size();
            }
        }
    }
}
