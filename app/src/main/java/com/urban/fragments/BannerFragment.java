package com.urban.fragments;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher.ViewFactory;

import com.example.test.R;
import com.urban.data.Advertising;
import com.urban.data.Image;
import com.urban.data.dao.DAO;

public class BannerFragment extends Fragment {

    private static ImageSwitcher switcher;
    private static Timer timer = new Timer();
    private int i;

    private static final BannerFragment bannerFragment = new BannerFragment();

    ArrayList<Advertising> advertisings = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        try {
            advertisings = (ArrayList<Advertising>)DAO.getAll(Advertising.class);
        } catch (Exception e) {
            Log.e("", "Error during db access", e);
        }

        if (switcher != null) {
            ViewGroup parent = (ViewGroup) switcher.getParent();
            if (parent != null)
                parent.removeView(switcher);
        }
        try {
            switcher = (ImageSwitcher)inflater.inflate(R.layout.banner_fragment, container, false);
            switcher.setFactory(new MyImageSwitcherFactory());
            switcher.setImageResource(R.drawable.shop);
        } catch (InflateException e) {
            return switcher;
        }

        return switcher;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        callAsynchronousTask();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }
        super.onDestroy();
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
        /** The system calls this to perform work in a worker thread and
          * delivers it the parameters given to AsyncTask.execute() */
        protected Bitmap doInBackground(String... urls) {
            return loadImageFromNetwork("qqq");
        }

        /** The system calls this to perform work in the UI thread and delivers
          * the result from doInBackground() */
        protected void onPostExecute(Bitmap result) {
            if (advertisings != null && !advertisings.isEmpty()) {
                Image img = advertisings.get(i).getImage();
                if (img != null) {
                    Drawable drawable = getDrawableFromImage(img);
                    if (drawable != null) {
                        switcher.setImageDrawable(drawable);
                    }
                }
                i = ++i % advertisings.size();
            }

        }
    }


    /**
     * Method for getting Drawable object by Image.
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

    private Bitmap loadImageFromNetwork(String url){
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
                            DownloadImageTask performBackgroundTask = new DownloadImageTask();
                            // PerformBackgroundTask this class is the class that extends AsynchTask
                            performBackgroundTask.execute();
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 5000); //execute in every 50000 ms
    }

    public static BannerFragment getInstance() {
        return bannerFragment;
    }
}
