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
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ivan.database.DatabaseStuff;

import java.text.NumberFormat;
import java.util.List;

public class SearchByKda extends Fragment {
    TextView championKda, kdaQuote, tvWinRate;
    EditText champion;
    Button checkKda;
    View procent;
    String winRate;
    LinearLayout procentHolder;
    View line;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final DatabaseStuff db = new DatabaseStuff(getActivity());

        View v = inflater.inflate(R.layout.search_by_kda, container, false);
        championKda = (TextView) v.findViewById(R.id.tvChampionKda);
        kdaQuote = (TextView) v.findViewById(R.id.tvKdaResultQuote);
        tvWinRate = (TextView) v.findViewById(R.id.tvChampionWinRate);
        champion = (EditText) v.findViewById(R.id.etEnterChampionName);
        checkKda = (Button) v.findViewById(R.id.bCheckChampionKda);
        procent = v.findViewById(R.id.vProcent);
        line = v.findViewById(R.id.line);
        procentHolder = (LinearLayout) v.findViewById(R.id.procentHolder);

        checkKda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkFields()) {

                    NumberFormat formatter = NumberFormat.getNumberInstance();
                    formatter.setMinimumFractionDigits(0);
                    formatter.setMaximumFractionDigits(2);

                    List<Double> kda = db.checkKda(champion.getText().toString());
                    Double total = 0.0;
                    Double count = 0.0;
                    for (Double k : kda) {
                        total += k;
                        count++;
                    }
                    total = total / count;
                    if (!total.isNaN()) {
                        if (total <= 2) {
                            championKda.setTextColor(Color.GRAY);
                            kdaQuote.setText("Gotta practice some more with this guy my friend, " +
                                    "or play something else.");
                        } else if (total > 2 && total <= 5) {
                            championKda.setTextColor(Color.GREEN);
                            kdaQuote.setText("Ok..Ok.. not good, not bad..");
                        } else if (total > 5) {
                            championKda.setTextColor(Color.BLUE);
                            kdaQuote.setText("Nice KDA, youre good with this one :)");
                        }
                        championKda.setText(formatter.format(total) + " KDA");
                    } else {
                        championKda.setText("No such champion, or you just havent played it yet.");
                    }
                    line.setVisibility(View.VISIBLE);
                    procentHolder.setVisibility(View.VISIBLE);

                    winRate = formatter.format(db.checkWinRate(champion.getText().toString()));

                    scaleView(procent, 0, (float) db.checkWinRate(champion.getText().toString()) / 100);

                }
            }});

        return v;
    }

    private boolean checkFields() {

        boolean allRight = true;

        if (champion.getText().toString().length() == 0){
            champion.setError("No champion name entered!");
            allRight = false;
        }

        return allRight;

    }

    public void scaleView(View v, float startScale, float endScale) {
        Animation anim = new ScaleAnimation(
                startScale, endScale,
                1f, 1f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f);
        anim.setFillAfter(true);
        anim.setDuration(2000);
        v.setVisibility(View.VISIBLE);
        v.startAnimation(anim);

        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                tvWinRate.setText("Win rate:" + "\n" + "wait for it...");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                tvWinRate.setText("Win rate:" + "\n" + winRate + "%");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

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