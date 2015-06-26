package com.example.ivan.mylolstatistics;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ivan.database.DatabaseStuff;
import com.example.ivan.dialogs.SearchDialog;
import com.example.ivan.entitet.GameStatistics;

import java.text.NumberFormat;
import java.util.List;

public class DisplayStatistics extends Fragment implements SearchDialog.NoticeDialogListener{

    private TableLayout table_layout;
    private FrameLayout frame;
    Animation animation;
    private View coordinatorLayoutView;
    private DatabaseStuff db;
    View tableRow;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        db = new DatabaseStuff(getActivity());

        View v = inflater.inflate(R.layout.check_statistics, container, false);
        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);

        coordinatorLayoutView = v.findViewById(R.id.snackbarPosition);
        frame = (FrameLayout) v.findViewById(R.id.selected_option_fragment);
        table_layout = (TableLayout) v.findViewById(R.id.main_table);
        tableRow= inflater.inflate(R.layout.table_row, container, false);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchDialog searchDialog = new SearchDialog();
                searchDialog.setTargetFragment(DisplayStatistics.this, 0);
                searchDialog.show(getFragmentManager(), "Search");
            }
        });

        final List<GameStatistics> gamesList = db.getAllStatistics();

        buildTable(gamesList, 7);

        animation = AnimationUtils.loadAnimation(getActivity(),
                R.anim.slide_out);

        return v;

    }

    private void buildTable(List<GameStatistics> game, int cols) {

        for (int i = 0; i < game.size(); i++) {

            final TableRow row = new TableRow(getActivity());
            TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);
            final float scale = getResources().getDisplayMetrics().density;
            int marginLeftRight = (int) (5 * scale + 0.5f);
            int marginTopBottom = (int) (1 * scale + 0.5f);
            tableRowParams.setMargins(marginLeftRight, marginTopBottom, marginLeftRight, marginTopBottom);
            row.setLayoutParams(tableRowParams);
            row.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.table_row_selector, null));

            for (int j = 0; j < cols; j++) {

                TextView tv = new TextView(getActivity());
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));
                tv.setPadding(5, 5, 5, 5);

                switch (j){
                    case 0:
                        tv.setText(game.get(i).getChampionName());
                        break;
                    case 1:
                        tv.setText(game.get(i).getGameOutcome());
                        break;
                    case 2:
                        tv.setText(String.valueOf(game.get(i).getKills()));
                        break;
                    case 3:
                        tv.setText(String.valueOf(game.get(i).getDeaths()));
                        break;
                    case 4:
                        tv.setText(String.valueOf(game.get(i).getAssists()));
                        break;
                    case 5:
                        NumberFormat formatter = NumberFormat.getNumberInstance();
                        formatter.setMinimumFractionDigits(0);
                        formatter.setMaximumFractionDigits(2);
                        String output = formatter.format(game.get(i).getKda());
                        tv.setText(output);
                        break;
                    case 6:
                        tv.setText(String.valueOf(game.get(i).getId()));
                        tv.setVisibility(View.INVISIBLE);
                        tv.setWidth(0);
                        break;
                    default:
                        break;
                }
                tv.setGravity(Gravity.CENTER);
                row.addView(tv);
                row.setClickable(true);
                row.setOnCreateContextMenuListener(tablerowOnClickListener);
            }

            table_layout.addView(row);

        }

    }

    private View.OnCreateContextMenuListener tablerowOnClickListener = new View.OnCreateContextMenuListener()
    {
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Choose an option:");
            int selectedRow = 0;

            for (int i = 0; i < table_layout.getChildCount(); i++)
            {
                View row = table_layout.getChildAt(i);
                if (row == v)
                {
                    selectedRow = i;
                }
            }

            menu.add(0, selectedRow, 0, "Delete");
            menu.add(0, selectedRow, 0, "Update");
            menu.add(0, selectedRow, 0, "Cancel");

        }
    };



    public boolean onContextItemSelected(final MenuItem item) {
        if (item.getTitle() == "Delete") {

            final View row = table_layout.getChildAt(item.getItemId());
            final DatabaseStuff db = new DatabaseStuff(getActivity());
            TableRow t = (TableRow) row;
            TextView rowId = (TextView) t.getChildAt(6);
            final String id = rowId.getText().toString();

            final View.OnClickListener clickListener = new View.OnClickListener() {
                public void onClick(View v) {
                    db.deleteGame(id);
                    row.startAnimation(animation);
                }
            };

            final Snackbar snackbar = Snackbar.make(coordinatorLayoutView, R.string.delete_row, Snackbar.LENGTH_LONG);
            snackbar.setAction(R.string.snackbar_action_undo, clickListener);
            snackbar.show();

            new Thread(new Runnable() {
                @Override
                public void run() {
                        try {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    row.setBackgroundResource(R.drawable.cell_shape_two_hover);
                                }
                            });
                            Thread.sleep(3500);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        try {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    row.setBackgroundResource(R.drawable.cell_shape_two);
                                }
                            });
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                }

            }).start();

            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {}

                @Override
                public void onAnimationRepeat(Animation animation) {}

                @Override
                public void onAnimationEnd(Animation animation) {
                    new Thread(){
                        @Override
                        public void run() {
                            try {
                                synchronized (this) {
                                    wait(10);
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            table_layout.removeView(row);
                                            table_layout.invalidate();
                                        }
                                    });
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                }
            });

        }
        else if (item.getTitle() == "Update") {

            View row = table_layout.getChildAt(item.getItemId());
            final DatabaseStuff db = new DatabaseStuff(getActivity());
            TableRow t = (TableRow) row;
            TextView rowId = (TextView) t.getChildAt(6);
            String id = rowId.getText().toString();
            GameStatistics game = db.getGame(id);

            UpdateGame newFragment = new UpdateGame();
            Bundle args = new Bundle();
            args.putParcelable("game", game);
            newFragment.setArguments(args);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            if (frame != null) {

                transaction.replace(R.id.selected_option_fragment, newFragment);
                transaction.addToBackStack(UpdateGame.class.getName());
                transaction.commit();

            }else{

                transaction.replace(R.id.fragment_container, newFragment);
                transaction.addToBackStack(UpdateGame.class.getName());
                transaction.commit();

            }

        }
        else if (item.getTitle() == "Cancel") {
            Toast.makeText(getActivity(), "Action canceled", Toast.LENGTH_SHORT).show();
        }
        else {
            return false;
        }
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDialogPositiveClick(String champion) {

        List<GameStatistics> gamesList;
        if(!champion.equals("")) {
            gamesList = db.getSpecificChampionStatistics(champion);
        }else{
            gamesList = db.getAllStatistics();
        }
        table_layout.removeAllViews();
        table_layout.addView(tableRow);
        buildTable(gamesList, 7);

    }

}