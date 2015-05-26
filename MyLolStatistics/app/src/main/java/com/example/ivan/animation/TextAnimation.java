package com.example.ivan.animation;

import android.os.Handler;
import android.widget.TextView;

public class TextAnimation {

    Handler mHandler = new Handler();
    TextView tv;
    private CharSequence mText;
    private int mIndex;
    private long mDelay = 500;

    public TextAnimation(TextView tv){
        this.tv = tv;
    }

    Runnable characterAdder = new Runnable() {
        @Override
        public void run() {
            tv.setText(mText.subSequence(0, mIndex++));
            if(mIndex <= mText.length()) {
                mHandler.postDelayed(characterAdder, mDelay);
            }
        }
    };

    public void animateText(CharSequence text) {
        mText = text;
        mIndex = 0;

        tv.setText("");
        mHandler.removeCallbacks(characterAdder);
        mHandler.postDelayed(characterAdder, mDelay);
    }

    public void setCharacterDelay(long millis) {
        mDelay = millis;
    }

}
