package com.example.ivan.mylolstatistics;

import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ivan.animation.TextAnimation;
import com.example.ivan.animation.CustomGauge;
import com.example.ivan.animation.CustomGaugeLandscape;
import com.example.ivan.database.DatabaseStuff;

import java.text.NumberFormat;
import java.util.Map;


public class BestWinRate extends Fragment {

    int i;
    TextView tvBestWinRate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final DatabaseStuff db = new DatabaseStuff(getActivity());

        View v = inflater.inflate(R.layout.best_win_rate, container, false);
        tvBestWinRate = (TextView) v.findViewById(R.id.tvBestWinRateChamp);
        TextAnimation textAnimation = new TextAnimation(tvBestWinRate);

        Map<String, Double> map = db.bestWinRate();
        Double maxEntry = null;
        String bestWinRateChamp = "";

        for (Map.Entry<String, Double> entry : map.entrySet())
        {
            if (maxEntry == null || ((Double)db.checkWinRate(entry.getKey())).compareTo(maxEntry) > 0)
            {
                maxEntry = db.checkWinRate(entry.getKey());
                bestWinRateChamp = entry.getKey();
            }
        }

        final double bestWinRate= maxEntry;
        final String finalBestWinRateChamp = bestWinRateChamp;
        final Double finalMaxEntry = maxEntry;

        textAnimation.setCharacterDelay(200);
        textAnimation.animateText(finalBestWinRateChamp);

        final NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMinimumFractionDigits(0);
        formatter.setMaximumFractionDigits(2);

        int orientation=this.getResources().getConfiguration().orientation;
        if(orientation == Configuration.ORIENTATION_PORTRAIT){
            final CustomGauge gauge = new CustomGauge(getActivity());
            final RelativeLayout relativeLayout = (RelativeLayout) v.findViewById(R.id.rect);
            relativeLayout.addView(gauge);
            new Thread() {
                public void run() {
                    for (i=1;i< bestWinRate+1;i++) {
                        try {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    gauge.setValue(i*10);
                                }
                            });
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvBestWinRate.append("\n" + formatter.format(finalMaxEntry) + "%");
                        }
                    });
                }
            }.start();
        }
        else{
            final CustomGaugeLandscape gauge = new CustomGaugeLandscape(getActivity());
            final RelativeLayout relativeLayout = (RelativeLayout) v.findViewById(R.id.rect);
            relativeLayout.addView(gauge);
            new Thread() {
                public void run() {
                    for (i=1;i< bestWinRate+1;i++) {
                        try {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    gauge.setValue(i*10);
                                }
                            });
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvBestWinRate.append("\n" + formatter.format(finalMaxEntry) + "%");
                        }
                    });
                }
            }.start();
        }

        return v;
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
