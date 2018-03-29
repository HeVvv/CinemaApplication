package com.example.user.cinemaapplication.Activites;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import com.example.user.cinemaapplication.Adds.HashMapSort;
import com.example.user.cinemaapplication.Adds.JSONUtils;
import com.example.user.cinemaapplication.Adds.ListData;
import com.example.user.cinemaapplication.Adds.TicketSClass;
import com.example.user.cinemaapplication.R;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.xml.transform.Result;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import github.nisrulz.qreader.QRDataListener;
import github.nisrulz.qreader.QREader;

/**
 * Created by User on 22.03.2018.
 */

public class SplashActivity extends AppCompatActivity{
    private HashMap <String,Integer> DATA = new HashMap<>();
    private HashMap <String,Integer> DATA2 = new HashMap<>();

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
    public HashMap<String, Integer> getDATA2(){
        return DATA2;
    }


    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);


        Handler handler1 = new Handler();
        handler1.post(new Runnable() {
            @Override
            public void run() {
                DATA = ListData.loadAuditData();
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
        }, 1500);



    }
}
