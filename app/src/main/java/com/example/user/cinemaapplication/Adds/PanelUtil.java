package com.example.user.cinemaapplication.Adds;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.user.cinemaapplication.R;
import com.google.android.gms.vision.text.Element;

import java.util.ArrayList;

public class PanelUtil extends SurfaceView implements SurfaceHolder.Callback {

    private ArrayList<Element> mElements = new ArrayList<Element>();

    public PanelUtil(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setBackgroundColor(Color.TRANSPARENT);
        this.setZOrderOnTop(true); //necessary
        getHolder().setFormat(PixelFormat.TRANSPARENT);
        getHolder().addCallback(this);
    }


    public void doDrawQM(Canvas canvas) {
        Drawable d = getResources().getDrawable(R.drawable.response_qm);
        d.setBounds(5, 5, 5, 5);
        d.draw(canvas);
    }

    public void doDrawOk(Canvas canvas){
        Drawable d = getResources().getDrawable(R.drawable.response_ok);
        d.setBounds(5, 5, 5, 5);
        d.draw(canvas);
    }

    public void doDrawCancel(Canvas canvas){
        Drawable d = getResources().getDrawable(R.drawable.response_cancel);
        d.setBounds(5, 5, 5, 5);
        d.draw(canvas);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // TODO Auto-generated method stub
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
//        if (!mThread.isAlive()) {
//            mThread = new ViewThread(this);
//            mThread.setRunning(true);
//            mThread.start();
//        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
//        if (mThread.isAlive()) {
//            mThread.setRunning(false);
//        }
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        synchronized (mElements) {
//            mElements.add(new Element(getResources(), (int) event.getX(), (int) event.getY()));
//        }
//        return super.onTouchEvent(event);
//    }
}