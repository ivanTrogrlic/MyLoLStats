package com.example.ivan.animation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.view.View;

import com.example.ivan.mylolstatistics.R;

public class CustomGaugeLandscape extends View {
    public CustomGaugeLandscape(Context context) {
        super(context);
        init();
    }

    private Paint mPaint;
    private RectF mRect;
    private int mStartAngel = 0;
    private int mSweepAngel = 180;
    private int mStartValue = 0;
    private int mEndValue = 1000;
    private int mValue;
    private double mPointAngel = ((double) Math.abs(mSweepAngel) / (mEndValue - mStartValue));
    private int mPoint;


    private void init() {

        float mStrokeWidth = 50;

        mPaint = new Paint();
        //mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);
        mRect = new RectF();

        mValue = mStartValue;
        mPoint = mStartAngel;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float mRectLeft;
        float mRectTop;
        float mRectRight;
        float mRectBottom;
        int mPointSize = 0;

        canvas.rotate(180, getMeasuredWidth() / 2, getMeasuredHeight() / 2);

        float padding = 20;
        float width = getWidth() - (padding*2);
        float height = getHeight() - (padding*2);
        float radius = (width > height ? width : height);

        mRectLeft = padding*2;
        mRectTop = height - radius + padding*2 -100;
        mRectRight = width;
        mRectBottom = height - radius + padding + height;

        mRect.set(mRectLeft, mRectTop, mRectRight, mRectBottom);

        mPaint.setColor(Color.WHITE);
        mPaint.setShader(null);
        canvas.drawArc(mRect, mStartAngel, mSweepAngel, false, mPaint);
        mPaint.setColor(Color.WHITE);
        mPaint.setShader(new LinearGradient(0, 0, getWidth(), getHeight() / 2, getResources().getColor(R.color.primary_dark), getResources().getColor(R.color.accent), Shader.TileMode.REPEAT));
        if (mPointSize>0) {
            if (mPoint > mStartAngel + mPointSize/2) {
                canvas.drawArc(mRect, mPoint - mPointSize/2, mPointSize, false, mPaint);
            }
            else {
                canvas.drawArc(mRect, mPoint, mPointSize, false, mPaint);
            }
        }
        else {
            if (mValue==mStartValue)
                canvas.drawArc(mRect, mStartAngel, 1, false, mPaint);
            else
                canvas.drawArc(mRect, mStartAngel, mPoint - mStartAngel, false, mPaint);
        }

    }

    public void setValue(int value) {
        mValue = value;
        mPoint = (int) (mStartAngel + (mValue-mStartValue) * mPointAngel);
        invalidate();
    }

}
