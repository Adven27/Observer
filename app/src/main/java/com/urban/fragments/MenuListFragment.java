package com.urban.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.tools.CategoryAdapter;
import com.tools.PrototypeView;
import com.tools.PrototypeView.ShowCategoryAction;
import com.urban.data.Category;
import com.urban.data.dao.DAO;
import com.urban.observer.R;

import java.util.ArrayList;

public class MenuListFragment extends ListFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setListAdapter(new CategoryAdapter(
                getActivity(),
                R.layout.sliding_menu_list_item,
                R.id.sliding_menu_item_label,
                getCategoriesFromDB()));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        PrototypeView.setCurrentContainerId(R.id.main_container);
        PrototypeView.doInTransaction(new ShowCategoryAction(getCategoriesFromDB().get(position)));
    }

    private ArrayList<Category> getCategoriesFromDB() {
        try {
            return (ArrayList<Category>)DAO.getAll(Category.class);
        } catch (Exception e) {
            Log.e("", "Error during db access", e);
            return null;
        }
    }
}
