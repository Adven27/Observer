package com.tools;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.urban.activity.about.fragments.AboutFragment;
import com.urban.activity.about.fragments.EggFragment;
import com.urban.data.Category;
import com.urban.data.Position;
import com.urban.fragments.CategoryFragment;
import com.urban.fragments.PositionFragment;
import com.urban.fragments.pages.map.YandexMapFragment;

public class PrototypeView {

    private static FragmentActivity activity;
    private static int containerId;

    private PrototypeView(){

    }

    public static PrototypeView createInstance(FragmentActivity activity, int containerId){
        PrototypeView.activity = activity;
        PrototypeView.containerId = containerId;
        return new PrototypeView();
    }

    public static void switchActivity(FragmentActivity activity) {
        PrototypeView.activity = activity;
    }

    public static class ShowCategoryAction implements Action {
        private Category category = null;

        public ShowCategoryAction(Category category) {
            this.category = category;
        }

        public void make(FragmentTransaction transaction){
            CategoryFragment category = new CategoryFragment();
            category.setCurrentCategory(this.category);
            transaction.replace(containerId, category);
        }
    }

    public static class ShowPositionAction implements Action {
        private Position position = null;

        public ShowPositionAction(Position position) {
            this.position = position;
        }

        public void make(FragmentTransaction transaction){
            PositionFragment position = new PositionFragment();
            position.setCurrentPosition(this.position);
            transaction.replace(containerId, position);
        }
    }

    public static class ShowMapAction implements Action {

        @Override
        public void make(FragmentTransaction transaction) {
            transaction.replace(containerId, YandexMapFragment.getInstance());
        }

    }

    public static class ShowAboutAction implements Action {

        public void make(FragmentTransaction transaction){
            AboutFragment about = new AboutFragment();
            transaction.replace(containerId, about);
        }
    }

    public static class ShowEggAction implements Action {

        public void make(FragmentTransaction transaction){
            EggFragment egg = new EggFragment();
            transaction.replace(containerId, egg);
        }
    }

    private interface Action {
        public void make(FragmentTransaction transaction);
    }

    public static void doInTransaction(Action action){
        FragmentTransaction t = activity.getSupportFragmentManager().beginTransaction();
        action.make(t);
        t.commit();
    }

    public static void setCurrentContainerId(int conteinerId){
        PrototypeView.containerId = conteinerId;
    }

    public static FragmentActivity getActivity() {
        return activity;
    }
}
