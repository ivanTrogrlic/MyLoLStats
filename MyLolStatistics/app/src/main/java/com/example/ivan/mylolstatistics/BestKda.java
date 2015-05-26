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
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.cardiomood.android.controls.gauge.SpeedometerGauge;
import com.example.ivan.database.DatabaseStuff;

import java.text.NumberFormat;
import java.util.Map;

public class BestKda extends Fragment {

    TextView maxKda, maxKdaChampion;
    private SpeedometerGauge speedometer;

    MediaPlayer mediaPlayer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final DatabaseStuff db = new DatabaseStuff(getActivity());

        View v = inflater.inflate(R.layout.best_kda, container, false);
        maxKda = (TextView) v.findViewById(R.id.tvBestKda);
        maxKdaChampion  = (TextView) v.findViewById(R.id.tvBestKdaChampion);

        Map<String, Double> map = db.bestKda();
        Map.Entry<String, Double> maxEntry = null;

        for (Map.Entry<String, Double> entry : map.entrySet())
        {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
            {
                maxEntry = entry;
            }
        }

        if(maxEntry != null) {
            animateTextView(0.0, maxEntry.getValue(), maxKda);
            maxKdaChampion.setText(maxEntry.getKey());
        }

        speedometer = (SpeedometerGauge) v.findViewById(R.id.speedometer);

        speedometer.setMaxSpeed(20);
        speedometer.setLabelConverter(new SpeedometerGauge.LabelConverter() {
            @Override
            public String getLabelFor(double progress, double maxProgress) {
                return String.valueOf((int) Math.round(progress));
            }
        });
        speedometer.setMaxSpeed(20);
        speedometer.setMajorTickStep(5);
        speedometer.setMinorTicks(1);
        speedometer.addColoredRange(2, 5, Color.GREEN);
        speedometer.addColoredRange(5, 8, Color.YELLOW);
        speedometer.addColoredRange(8, 20, Color.RED);
        speedometer.setSpeed(maxEntry.getValue(), 1000, 300);

        if(maxEntry.getValue() >=20){
            mediaPlayer = MediaPlayer.create(v.getContext(), R.raw.bell);
            mediaPlayer.start();
        }

        return v;
    }

    public void animateTextView(Double initialValue, Double finalValue, final TextView textview) {
        DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator(0.8f);
        Double start = Math.min(initialValue, finalValue);
        Double end = Math.max(initialValue, finalValue);
        Double difference = Math.abs(finalValue - initialValue);
        Handler handler = new Handler();
        for (Double count = start; count <= end+0.01; count+=0.01) {
            Double time = Math.round(decelerateInterpolator.getInterpolation((float)((count) / difference)) * 80) * count;
            final Double finalCount = ((initialValue > finalValue) ? initialValue - count : count);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    NumberFormat formatter = NumberFormat.getNumberInstance();
                    formatter.setMinimumFractionDigits(2);
                    formatter.setMaximumFractionDigits(2);
                    textview.setText(formatter.format(finalCount) + "");
                    if(finalCount <= 2){
                        textview.setTextColor(Color.GRAY);
                    }else if(finalCount > 2 && finalCount <=5){
                        textview.setTextColor(Color.GREEN);
                    }else if(finalCount > 5 && finalCount <=8){
                        textview.setTextColor(Color.YELLOW);}
                    else{
                        textview.setTextColor(Color.RED);
                    }
                }
            }, Math.round(time));
        }
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