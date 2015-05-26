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
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ivan.database.DatabaseStuff;
import com.example.ivan.entitet.GameStatistics;

public class UpdateGame extends Fragment {
    EditText championPlayed, kills, deaths, assists;
    Button addNewGame;
    Spinner gameOutcome;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final DatabaseStuff db = new DatabaseStuff(getActivity());
        View v = inflater.inflate(R.layout.add_new_game, container, false);

        final GameStatistics passedGame =  getArguments().getParcelable("game");

        championPlayed = (EditText) v.findViewById(R.id.etChampion);
        gameOutcome = (Spinner) v.findViewById(R.id.spinnerGameOutcome);
        kills = (EditText) v.findViewById(R.id.etKills);
        assists = (EditText) v.findViewById(R.id.etAssists);
        deaths = (EditText) v.findViewById(R.id.etDeaths);
        addNewGame = (Button) v.findViewById(R.id.bAddNewGame);
        addNewGame.setText("Update");

        if(passedGame!=null){
            championPlayed.setText(passedGame.getChampionName());
            gameOutcome.setSelection(getIndex(gameOutcome, passedGame.getGameOutcome()));
            kills.setText(Integer.toString(passedGame.getKills()));
            assists.setText(Integer.toString(passedGame.getAssists()));
            deaths.setText(Integer.toString(passedGame.getDeaths()));
        }

        addNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GameStatistics game = null;
                if (passedGame != null) {
                    game = new GameStatistics(passedGame.getId(),championPlayed.getText().toString(),
                            gameOutcome.getSelectedItem().toString(),
                            Integer.parseInt(kills.getText().toString()),
                            Integer.parseInt(deaths.getText().toString()),
                            Integer.parseInt(assists.getText().toString()));
                }

                db.updateGame(game);

                Toast.makeText(getActivity(), "Game updated", Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }

    static private int getIndex(Spinner spinner, String myString)
    {
        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                index = i;
                break;
            }
        }
        return index;
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