package com.example.user.cinemaapplication.Activites;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user.cinemaapplication.R;


public class Settings_AboutActivity extends AppCompatActivity {

    private TextView txt;
    private SurfaceView mySurfaceView;
    private Button button;
    private Button buttonShow;
    private SurfaceView overlay;
    private Button buttonRemove;
    private LinearLayout relativelayout;
    private ImageView response;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        setContentView(R.layout.activity_settings_about);
        mySurfaceView = (SurfaceView) findViewById(R.id.cameraSettings);
        final LinearLayout ll = (LinearLayout) findViewById(R.id.overlay);


        final int i = 0;

        button = (Button) findViewById(R.id.drawoverlay);
        button.setText("SetImage");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i == 0){
                    response = (ImageView) findViewById(R.id.response);
                    response.setImageResource(R.drawable.response_ok);
                    if(response.getParent()!=null)
                        ((ViewGroup)response.getParent()).removeView(response); // <- fix
                    ll.addView(response);
                }
            }
        });

        buttonRemove = (Button) findViewById(R.id.removeoverlay);
        buttonRemove.setText("Remove");
        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response.setVisibility(View.GONE);
                System.out.println("Gone");
            }
        });

        buttonShow = (Button) findViewById(R.id.showOverlay);

        buttonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response.setVisibility(View.VISIBLE);
                System.out.println("Visible");
            }
        });

        super.onCreate(savedInstanceState);
    }
}
