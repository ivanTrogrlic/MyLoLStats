package com.example.ivan.mylolstatistics;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;


public class ListOfOptionsFragment extends ListFragment {
    OnOptionSelected mCallback;

    public interface OnOptionSelected {
        void onSelectedJobSelected(int position) throws java.lang.InstantiationException, IllegalAccessException;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] values = new String[] { "Add new game" , "Delete and update", "Check KDA & win rate",
                 "Best KDA", "Best Win rate"};

        setListAdapter(new MainArrayAdapter(getActivity(), values));

    }

    @Override
    public void onStart() {
        super.onStart();

        if (getFragmentManager().findFragmentById(R.id.selected_option_fragment) != null) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (OnOptionSelected) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnOptionSelectedListener");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        try {
            mCallback.onSelectedJobSelected(position);
        } catch (java.lang.InstantiationException|IllegalAccessException e) {
            e.printStackTrace();
        }
        getListView().setItemChecked(position, true);

    }
}
