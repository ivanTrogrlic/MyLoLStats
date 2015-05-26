package com.example.ivan.mylolstatistics;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.ivan.dialogs.DeleteEverythingDialog;

public class MainActivity extends FragmentActivity
        implements ListOfOptionsFragment.OnOptionSelected {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listofoptions);

        if (findViewById(R.id.fragment_container) != null) {

            if (savedInstanceState != null) {
                return;
            }

            ListOfOptionsFragment firstFragment = new ListOfOptionsFragment();
            firstFragment.setArguments(getIntent().getExtras());
            getFragmentManager().beginTransaction().add(R.id.fragment_container, firstFragment).commit();
        }
    }

    public void onSelectedJobSelected(int position) {

        FrameLayout f = (FrameLayout) findViewById(R.id.selected_option_fragment);

        if (f != null) {

            if(position == 0){

                AddNewGame newFragment = new AddNewGame();
                Bundle args = new Bundle();
                newFragment.setArguments(args);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.selected_option_fragment, newFragment);
                transaction.addToBackStack(AddNewGame.class.getName());
                transaction.commit();

            }
            else if(position == 1){
                DisplayStatistics newFragment = new DisplayStatistics();
                Bundle args = new Bundle();
                newFragment.setArguments(args);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.selected_option_fragment, newFragment);
                transaction.addToBackStack(DisplayStatistics.class.getName());
                transaction.commit();
            }
            else if(position == 2){
                SearchByKda newFragment = new SearchByKda();
                Bundle args = new Bundle();
                newFragment.setArguments(args);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.selected_option_fragment, newFragment);
                transaction.addToBackStack(SearchByKda.class.getName());
                transaction.commit();
            }
            else if(position == 3){

                BestKda newFragment = new BestKda();
                Bundle args = new Bundle();
                newFragment.setArguments(args);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.selected_option_fragment, newFragment);
                transaction.addToBackStack(BestKda.class.getName());
                transaction.commit();
            }
            else if(position == 4){

                BestWinRate newFragment = new BestWinRate();
                Bundle args = new Bundle();
                newFragment.setArguments(args);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.selected_option_fragment, newFragment);
                transaction.addToBackStack(BestWinRate.class.getName());
                transaction.commit();
            }

        } else if(position == 0){

            AddNewGame newFragment = new AddNewGame();
            Bundle args = new Bundle();
            newFragment.setArguments(args);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(AddNewGame.class.getName());
            transaction.commit();
        }
        else if(position == 1){

            DisplayStatistics newFragment = new DisplayStatistics();
            Bundle args = new Bundle();
            newFragment.setArguments(args);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(DisplayStatistics.class.getName());
            transaction.commit();
        }
        else if(position == 2){

            SearchByKda newFragment = new SearchByKda();
            Bundle args = new Bundle();
            newFragment.setArguments(args);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(SearchByKda.class.getName());
            transaction.commit();
        }
        else if(position == 3){

            BestKda newFragment = new BestKda();
            Bundle args = new Bundle();
            newFragment.setArguments(args);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(BestKda.class.getName());
            transaction.commit();
        }
        else if(position == 4){

            BestWinRate newFragment = new BestWinRate();
            Bundle args = new Bundle();
            newFragment.setArguments(args);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(BestWinRate.class.getName());
            transaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                Toast.makeText(getApplicationContext(), "About menu item pressed", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_deleteEverything:
                DeleteEverythingDialog delete = new DeleteEverythingDialog();
                delete.show(getFragmentManager(), "Delete");
                break;
        }
        return super.onOptionsItemSelected(item);


    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0 ){
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

}
