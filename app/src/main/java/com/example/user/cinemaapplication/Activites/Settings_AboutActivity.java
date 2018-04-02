package com.example.user.cinemaapplication.Activites;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user.cinemaapplication.Adds.JSONUtils;
import com.example.user.cinemaapplication.Adds.PanelUtil;
import com.example.user.cinemaapplication.R;
import com.google.android.gms.vision.text.Line;

import java.util.HashSet;
import java.util.Set;

import cz.msebera.android.httpclient.client.cache.Resource;


public class Settings_AboutActivity extends AppCompatActivity {

    private TextView txt;
    private SurfaceView mySurfaceView;
    private Button button;
    private SurfaceView overlay;
    private Canvas mCanvas;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        setContentView(R.layout.activity_settings_about);
        mySurfaceView = (SurfaceView) findViewById(R.id.cameraSettings);
        LinearLayout ll = (LinearLayout) findViewById(R.id.overlay);
        final ImageView iv = (ImageView) findViewById(R.id.response);


        button = (Button) findViewById(R.id.drawoverlay);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv.setImageResource(R.drawable.response_ok);
            }
        });

        super.onCreate(savedInstanceState);
    }
}
