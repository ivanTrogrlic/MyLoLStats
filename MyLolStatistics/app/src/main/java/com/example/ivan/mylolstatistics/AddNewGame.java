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

public class AddNewGame extends Fragment {
    EditText championPlayed, kills, deaths, assists;
    Button addNewGame;
    Spinner gameOutcome;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final DatabaseStuff db = new DatabaseStuff(getActivity());

        View v = inflater.inflate(R.layout.add_new_game, container, false);
        championPlayed = (EditText) v.findViewById(R.id.etChampion);
        kills = (EditText) v.findViewById(R.id.etKills);
        deaths = (EditText) v.findViewById(R.id.etDeaths);
        assists = (EditText) v.findViewById(R.id.etAssists);
        gameOutcome = (Spinner) v.findViewById(R.id.spinnerGameOutcome);
        addNewGame = (Button) v.findViewById(R.id.bAddNewGame);

        addNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if(checkFields()) {

                   GameStatistics game = new GameStatistics(championPlayed.getText().toString(),
                           gameOutcome.getSelectedItem().toString(),
                           Integer.parseInt(kills.getText().toString()),
                           Integer.parseInt(deaths.getText().toString()),
                           Integer.parseInt(assists.getText().toString()));

                   db.addGame(game);

                   Toast.makeText(getActivity(), "Game added", Toast.LENGTH_SHORT).show();
               }else{
                   Toast.makeText(getActivity(), "You forgot to enter one or more things", Toast.LENGTH_SHORT).show();
               }
            }
        });

        return v;
    }

    private boolean checkFields() {

        boolean allRight = true;

        if (championPlayed.getText().toString().length() == 0){
            championPlayed.setError("No champion name entered!");
            allRight = false;
        }
        if(kills.getText().toString().length() == 0){
            kills.setError("No kills entered!");
            allRight = false;
        }
        if(assists.getText().toString().length() == 0){
            assists.setError("No assists entered!");
            allRight = false;
        }
        if(deaths.getText().toString().length() == 0){
            deaths.setError("No deaths entered!");
            allRight = false;
        }

        return allRight;

    }

    @Override
    public void onStart() {super.onStart();}

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }
}