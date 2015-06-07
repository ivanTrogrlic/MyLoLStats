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
    Thread thread;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final DatabaseStuff db = new DatabaseStuff(getActivity());

        View v = inflater.inflate(R.layout.best_win_rate, container, false);
        tvBestWinRate = (TextView) v.findViewById(R.id.tvBestWinRateChamp);
        TextAnimation textAnimation = new TextAnimation(tvBestWinRate);

        final NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMinimumFractionDigits(0);
        formatter.setMaximumFractionDigits(2);

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

        final String finalBestWinRateChamp = bestWinRateChamp;
        final Double bestWinRate = maxEntry;

        textAnimation.setCharacterDelay(200);
        textAnimation.animateText(finalBestWinRateChamp);

        int orientation=this.getResources().getConfiguration().orientation;
        if(orientation == Configuration.ORIENTATION_PORTRAIT){
            final CustomGauge gauge = new CustomGauge(getActivity());
            final RelativeLayout relativeLayout = (RelativeLayout) v.findViewById(R.id.rect);
            relativeLayout.addView(gauge);

            if(bestWinRate != null){
                thread = new Thread(new Runnable() {
                    @Override
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
                                    break;
                                }
                            }
                        try {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tvBestWinRate.append("\n" + formatter.format(bestWinRate) + "%");
                                }
                            });
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    });
                //thread.start();
        }}
        else{
            final CustomGaugeLandscape gauge = new CustomGaugeLandscape(getActivity());
            final RelativeLayout relativeLayout = (RelativeLayout) v.findViewById(R.id.rect);
            relativeLayout.addView(gauge);

            if(bestWinRate != null){
                thread = new Thread(new Runnable() {
                    @Override
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
                                break;
                            }
                        }
                        try {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tvBestWinRate.append("\n" + formatter.format(bestWinRate) + "%");
                                }
                            });
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                //thread.start();
        }}

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        thread.interrupt();
    }

    @Override
    public void onPause() {
        super.onPause();
        thread.interrupt();
    }

    @Override
    public void onResume() {
        super.onResume();
        thread.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        thread.interrupt();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}
