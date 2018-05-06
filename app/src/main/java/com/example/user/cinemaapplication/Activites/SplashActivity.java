package com.example.user.cinemaapplication.Activites;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import com.example.user.cinemaapplication.Adds.FileAdapter;
import com.example.user.cinemaapplication.Adds.HashMapSort;
import com.example.user.cinemaapplication.Adds.JSONUtils;
import com.example.user.cinemaapplication.Adds.ListData;
import com.example.user.cinemaapplication.Adds.TicketSClass;
import com.example.user.cinemaapplication.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.xml.transform.Result;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import github.nisrulz.qreader.QRDataListener;
import github.nisrulz.qreader.QREader;

public class SplashActivity extends AppCompatActivity{
    private HashMap <String,Integer> DATA = new HashMap<>();
    private Set<Integer> DATA2 = new HashSet<>();
    private int MY_CAMERA_REQUEST_CODE = 101;
    private HashMap<Integer,String> THEATER_DATA = new HashMap<>();
    private List<String> THEATER_COLOR = new ArrayList<>();

    Context ctx = this;

    private static SplashActivity staticSplashActivity;
    public static SplashActivity getStaticSplashActivity(){
        return staticSplashActivity;
    }
    public SplashActivity(){
        staticSplashActivity = this;
    }

    public HashMap<String, Integer> getDATA(){
        return DATA;
    }

    public HashMap<Integer, String> getTHEATER_DATA() {
        return THEATER_DATA;
    }
    public List<String> getTHEATER_COLOR(){
        return THEATER_COLOR;
    }

    @Override
    public void onCreate(Bundle savedInstance) {

        super.onCreate(savedInstance);
        setTheme(R.style.Theme_AppCompat_NoActionBar);
        setContentView(R.layout.activity_splash);

        ShimmerFrameLayout container =
                (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container);
        container.startShimmerAnimation();


        File historyFile = new File(ctx.getFilesDir(), "history.txt");
        try {
            if (historyFile.createNewFile()) {
                System.out.println("Файл создан: " + historyFile.getAbsolutePath());
            } else {
                System.out.println("Не удалось создать файл.");
            }
        } catch (IOException ex) {
            System.out.println("Creation file Error");
        }

        File file1 = new File(ctx.getFilesDir(), "ID.txt");
        try {
            if (file1.createNewFile()) {
                System.out.println("Файл создан: " + file1.getAbsolutePath());
            } else {
                System.out.println("Не удалось создать файл.");
            }
        } catch (IOException ex) {
            System.out.println("Creation file Error");
        }


        Handler handler1 = new Handler();
        handler1.post(new Runnable() {
            @Override
            public void run() {
                DATA = ListData.loadAuditData();
                THEATER_DATA = ListData.loadTheaterInfo();
                THEATER_COLOR = ListData.loadTheaterColor();
            }
        });

        Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent openMainActivity =  new Intent(SplashActivity.this, TabActivity.class);

                startActivity(openMainActivity);
                finish();
            }
        }, 3500);

    }
}
