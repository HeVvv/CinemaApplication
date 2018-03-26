package com.example.user.cinemaapplication.Activites;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.user.cinemaapplication.Adds.ListData;

import java.util.HashMap;

/**
 * Created by User on 22.03.2018.
 */

public class SplashActivity extends AppCompatActivity{
    private HashMap <String,Integer> DATA = new HashMap<>();


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

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().post(new Runnable(){
            @Override
            public void run() {
                ListData.loadAuditData();

                LoginActivity.getStaticLoginActivity().getPassword();

                Intent mainIntent = new Intent(SplashActivity.this,TabActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();

            }
        });
    }
}
