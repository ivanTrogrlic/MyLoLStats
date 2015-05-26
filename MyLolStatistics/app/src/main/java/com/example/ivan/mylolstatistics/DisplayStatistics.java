/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.ivan.mylolstatistics;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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
import com.example.ivan.entitet.GameStatistics;

import java.text.NumberFormat;
import java.util.List;

public class DisplayStatistics extends Fragment {

    private TableLayout table_layout;
    private FrameLayout frame;
    Animation animation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final DatabaseStuff db = new DatabaseStuff(getActivity());

        View v = inflater.inflate(R.layout.check_statistics, container, false);
        frame = (FrameLayout) v.findViewById(R.id.selected_option_fragment);
        table_layout = (TableLayout) v.findViewById(R.id.main_table);

        final List<GameStatistics> gamesList = db.getAllStatistics();

        buildTable(gamesList, 7);

        animation = AnimationUtils.loadAnimation(getActivity(),
                R.anim.slide_out);

        return v;

    }

    private void buildTable(List<GameStatistics> game, int cols) {

        for (int i = 0; i < game.size(); i++) {

            TableRow row = new TableRow(getActivity());
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT));
            row.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.cell_shape_two));

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
            db.deleteGame(id);

            Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();

            row.startAnimation(animation);

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

}