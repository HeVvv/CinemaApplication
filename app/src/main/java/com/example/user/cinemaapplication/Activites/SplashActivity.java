package com.example.user.cinemaapplication.Activites;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.user.cinemaapplication.Adds.FileAdapter;
import com.example.user.cinemaapplication.Adds.ListData;
import com.example.user.cinemaapplication.R;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SplashActivity extends AppCompatActivity{

    private Set<Integer> DATA2 = new HashSet<>();
    private int MY_CAMERA_REQUEST_CODE = 101;
    private List<String> THEATER_COLOR = new ArrayList<>();
        private int THEATER_ID;

    private HashMap <String,Integer> DATA = new HashMap<>();
    private HashMap<Integer,String> THEATER_DATA = new HashMap<>();

    Context ctx = this;

    private static SplashActivity staticSplashActivity;
    public static SplashActivity getStaticSplashActivity(){
        return staticSplashActivity;
    }
    public SplashActivity(){
        staticSplashActivity = this;
    }

    private static final String HISTORY_FILE_DATE = "history_file_date";

    public HashMap<String, Integer> getDATA(){
        return DATA;
    }
    public void setDATA(){
        System.out.println("Re-setting data");
        DATA = new HashMap<>();
    }
    public int getTHEATER_ID(){
        return THEATER_ID;
    }
    public HashMap<Integer, String> getTHEATER_DATA() {
        return THEATER_DATA;
    }
    public List<String> getTHEATER_COLOR(){
        return THEATER_COLOR;
    }

    public boolean isFirstDayOfTheMonth(Date date){
        if(date == null){
            return false;
        }
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        if(c.get(Calendar.DAY_OF_MONTH) == 1){
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(SplashActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
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

        SharedPreferences sp = getSharedPreferences(HISTORY_FILE_DATE,
                Context.MODE_PRIVATE);
        boolean hasVisited = sp.getBoolean("hasVisited",false);
        long days = 30;
        Date purgeDate = new Date();
        purgeDate.setTime(days * 1000 * 3600 * 24);


        if (ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(SplashActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);
        }


        if (!hasVisited) {
            SharedPreferences.Editor e = sp.edit();
            e.putBoolean("hasVisited",true);
            e.putLong("historyPurgeTime",purgeDate.getTime());
            e.apply();
        }

        long currentMillis = new Date().getTime();
        long millisIn30Days = days * 24 * 60 * 60 * 1000;
        long historyCreateTime = sp.getLong("historyCreateTime",0);
        boolean result = historyCreateTime < (currentMillis - millisIn30Days);

        if(result){
            FileAdapter.deleteFile(historyFile);
            System.out.println("History file deleted due to expire date");
        }

        try {
            if (historyFile.createNewFile()) {
                System.out.println("Файл создан: " + historyFile.getAbsolutePath());
                Date createDate = Calendar.getInstance().getTime();
                SharedPreferences.Editor e = sp.edit();
                e.putLong("historyCreateTime",createDate.getTime());
                e.apply();
                System.out.println(System.currentTimeMillis());
            } else {
                System.out.println("Не удалось создать файл." + historyFile);
            }
        } catch (IOException ex) {
            System.out.println("Creation file Error");
        }

        File file1 = new File(ctx.getFilesDir(), "ID.txt");
        try {
            if (file1.createNewFile()) {
                System.out.println("Файл создан: " + file1.getAbsolutePath());
            } else {
                System.out.println("Не удалось создать файл." + file1);
            }
        } catch (IOException ex) {
            System.out.println("Creation file Error");
        }

        //подрезать под один запрос в gettheaterbydeviceID
        final Handler mainHandler = new Handler(Looper.getMainLooper());

        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                final String str = FileAdapter.readFromFile(getApplication().getFilesDir()+ "/" + "ID.txt");
                if (!(str.isEmpty())) {
                    final int device_id = Integer.parseInt(str.trim());
                    System.out.println("device id->" + device_id);
                    THEATER_ID = ListData.getTheaterByDEVICE_ID(device_id);
                }

                DATA = ListData.loadAuditData();
                THEATER_DATA = ListData.loadTheaterInfo();
                THEATER_COLOR = ListData.loadTheaterColor();
            }
        });

//        mainHandler.post(new Runnable() {
//            @Override
//            public void run() {
//                DATA = ListData.loadAuditData();
//                mainHandler.sendEmptyMessage(2);
//
//            }
//
//        });
//
//        mainHandler.post(new Runnable() {
//            @Override
//            public void run() {
//                THEATER_DATA = ListData.loadTheaterInfo();
//                THEATER_COLOR = ListData.loadTheaterColor();
//                mainHandler.sendEmptyMessage(3);
//
//            }
//        });
//        SplashActivity.this.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                if (!(str.isEmpty())) {
//                    THEATER_ID = ListData.getTheaterByDEVICE_ID(device_id);
//                    System.out.println("getting theater id");
//                }
//
//            }
//        });

        mainHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent openMainActivity = new Intent(SplashActivity.this, TabActivity.class);
                System.out.println("Theater id on splash = " + LoginActivity.getStaticLoginActivity().getTHEATER_ID());
                startActivity(openMainActivity);
                finish();
            }
        }, 7000);
    }
}
