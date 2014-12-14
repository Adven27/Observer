package com.tools;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.tools.dialogs.SimpleDialog;
import com.urban.activity.about.fragments.AboutFragment;
import com.urban.activity.about.fragments.EggFragment;
import com.urban.data.Category;
import com.urban.fragments.CategoryFragment;
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

    public static void setCurrentContainerId(int containerId){
        PrototypeView.containerId = containerId;
    }

    public static FragmentActivity getActivity() {
        return activity;
    }


    public static void setUpContainer(FragmentManager fragmentManager, Fragment fragment, int containerId) {
        if (PrototypeView.isContainerEmpty(fragmentManager, containerId)) {
            addFragment(fragmentManager, fragment, containerId);
        } else {
            replaceFragment(fragmentManager, fragment, containerId);
        }
    }

    private static boolean isContainerEmpty(FragmentManager fragmentManager, int containerId) {
        return fragmentManager.findFragmentById(containerId) == null;
    }

    private static void addFragment(FragmentManager fragmentManager, Fragment fragment, int containerId) {
        FragmentTransaction t = fragmentManager.beginTransaction();
        t.add(containerId, fragment);
        t.commit();
    }

    private static void replaceFragment(FragmentManager fragmentManager, Fragment fragment, int containerId) {
        FragmentTransaction t = fragmentManager.beginTransaction();
        t.replace(containerId, fragment);
        t.commit();
    }


    public static class ShowDialogAction implements Action {

        SimpleDialog dialog;

        public ShowDialogAction(SimpleDialog dialog) {
            this.dialog = dialog;
        }

        public void make(FragmentTransaction transaction){
            dialog.show(activity.getFragmentManager(), null);
        }
    }

    public static void showDialog(SimpleDialog dialog) {
        doInTransaction(new ShowDialogAction(dialog));
    }

}
