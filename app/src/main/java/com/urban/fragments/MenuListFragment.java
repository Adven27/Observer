package com.urban.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.test.R;
import com.tools.CategoryAdapter;
import com.tools.PrototypeView;
import com.tools.PrototypeView.ShowCategoryAction;
import com.urban.data.Category;
import com.urban.data.dao.DAO;

public class MenuListFragment extends ListFragment {

    ArrayList<Category> categories = new ArrayList<Category>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            categories = (ArrayList<Category>)DAO.getAll(Category.class);
        } catch (Exception e) {
            Log.e("", "Error during db access", e);
        }

        setListAdapter(new CategoryAdapter(getActivity(),
                android.R.layout.simple_list_item_1, categories));

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        PrototypeView.setCurrentContainerId(R.id.main_container);
        PrototypeView.doInTransaction(new ShowCategoryAction(categories.get(position)), true);

    }
}
