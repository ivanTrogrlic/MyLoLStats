package com.example.ivan.mylolstatistics;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ivan.dialogs.DeleteEverythingDialog;

public class MainActivity extends AppCompatActivity
        implements ListOfOptionsFragment.OnOptionSelected {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listofoptions);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {

            if (savedInstanceState != null) {
                return;
            }

            ListOfOptionsFragment firstFragment = new ListOfOptionsFragment();
            firstFragment.setArguments(getIntent().getExtras());
            getFragmentManager().beginTransaction().add(R.id.fragment_container, firstFragment).commit();
        }
    }

    public void onSelectedJobSelected(int position) throws InstantiationException, IllegalAccessException {

        //FrameLayout f = (FrameLayout) findViewById(R.id.selected_option_fragment);
        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {

            switch(position){
                case 0:
                    AddNewGame addNewGameFragment = new AddNewGame();
                    fragmentTransactionLandscape(addNewGameFragment);
                    break;
                case 1:
                    DisplayStatistics displayStatisticsFragment = new DisplayStatistics();
                    fragmentTransactionLandscape(displayStatisticsFragment);
                    break;
                case 2:
                    SearchByKda searchByKdaFragment = new SearchByKda();
                    fragmentTransactionLandscape(searchByKdaFragment);
                    break;
                case 3:
                    BestKda bestKdaFragment = new BestKda();
                    fragmentTransactionLandscape(bestKdaFragment);
                    break;
                case 4:
                    BestWinRate bestWinRateFragment = new BestWinRate();
                    fragmentTransactionLandscape(bestWinRateFragment);
                    break;
            }

        }else{

            switch(position){
                case 0:
                    AddNewGame addNewGameFragment = new AddNewGame();
                    fragmentTransactionPortrait(addNewGameFragment);
                    break;
                case 1:
                    DisplayStatistics displayStatisticsFragment = new DisplayStatistics();
                    fragmentTransactionPortrait(displayStatisticsFragment);
                    break;
                case 2:
                    SearchByKda searchByKdaFragment = new SearchByKda();
                    fragmentTransactionPortrait(searchByKdaFragment);
                    break;
                case 3:
                    BestKda bestKdaFragment = new BestKda();
                    fragmentTransactionPortrait(bestKdaFragment);
                    break;
                case 4:
                    BestWinRate bestWinRateFragment = new BestWinRate();
                    fragmentTransactionPortrait(bestWinRateFragment);
                    break;
            }
        }
    }

    private void fragmentTransactionPortrait(Fragment newFragment) {
        Bundle args = new Bundle();
        newFragment.setArguments(args);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void fragmentTransactionLandscape(Fragment newFragment) throws IllegalAccessException, InstantiationException {
        Bundle args = new Bundle();
        newFragment.setArguments(args);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.selected_option_fragment, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
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
