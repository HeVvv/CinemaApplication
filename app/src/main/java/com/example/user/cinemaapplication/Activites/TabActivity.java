package com.example.user.cinemaapplication.Activites;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.style.TabStopSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.user.cinemaapplication.Adds.CustomViewPager;
import com.example.user.cinemaapplication.Adds.FileAdapter;
import com.example.user.cinemaapplication.Adds.PagerAdapter;
import com.example.user.cinemaapplication.R;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import github.nisrulz.qreader.QREader;


public class TabActivity extends AppCompatActivity {

    private boolean state;
    private Fragment frag_scan = new QRScanActivity();
    private Fragment frag_history = new HistoryListActivity();
    private Fragment frag_choose = new AuditChoosingActivity();
    private PagerAdapter adapter;
    private CustomViewPager viewPager;
    private TabLayout tabLayout;
    private LinearLayout tabStrip;

    private int[] tabIcons = {
            R.drawable.tickets,
            R.drawable.video_camera,
            R.drawable.barcode
    };

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (CustomViewPager) findViewById(R.id.pager);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        adapter = new PagerAdapter(getSupportFragmentManager());
        adapter.addFragment(frag_history,"История");
        adapter.addFragment(frag_choose,"Выбор");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);

        tabStrip = ((LinearLayout) tabLayout.getChildAt(0));

        Timer myTimer = new Timer();
        final Handler uiHandler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("scan tab tick");
                if (!(checkAuditsEmpty())){
                    // add
                    if(tabStrip.getChildCount() == 2) {
                        adapter.addFragment(frag_scan, "Скан");
                        adapter.notifyDataSetChanged();
                        viewPager.setAdapter(adapter);
                        setupTabIcons();
                        try {
                            tabLayout.getTabAt(1).select();
                        }catch (NullPointerException e){
                            e.printStackTrace();
                        }
                        System.out.println("added");
                    }
                }else{
                    //remove
                    if(tabStrip.getChildCount() == 3) {
                        adapter.removeFragment(frag_scan, "Скан");
                        adapter.notifyDataSetChanged();
                        viewPager.setAdapter(adapter);
                            tabLayout.getTabAt(0).setIcon(tabIcons[0]);
                            tabLayout.getTabAt(1).setIcon(tabIcons[1]);

                        try {
                            tabLayout.getTabAt(1).select();
                        }catch (NullPointerException e){
                            e.printStackTrace();
                        }
                        System.out.println("removed");
                    }
                }
            }
        };

        myTimer.scheduleAtFixedRate(new TimerTask() { // Определяем задачу
            @Override
            public void run() {
                uiHandler.post(runnable);

            }
        }, 0L, 3500 );

        uiHandler.removeCallbacks(runnable);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });
        try {
            tabLayout.getTabAt(1).select();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public Runnable tabControl = new Runnable() {
        @Override
        public void run() {
            System.out.println("scan tab tick");
            if (!(checkAuditsEmpty())){
                // add
                if(tabStrip.getChildCount() == 2) {
                    adapter.addFragment(frag_scan, "Скан");
                    adapter.notifyDataSetChanged();
                    viewPager.setAdapter(adapter);
                    try {
                        tabLayout.getTabAt(1).select();
                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }
                    System.out.println("added");
                }
            }else{
                //remove
                if(tabStrip.getChildCount() == 3) {
                    adapter.removeFragment(frag_scan, "Скан");
                    adapter.notifyDataSetChanged();
                    viewPager.setAdapter(adapter);
                    try {
                        tabLayout.getTabAt(1).select();
                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }
                    System.out.println("removed");
                }
            }
        }
    };

    public boolean checkAuditsEmpty(){
        try {   
            state = AuditChoosingActivity.getStaticAuditChoosingActivity().getAuditIDS().isEmpty();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
            if (state) {
                return true;
            } else {
                return false;
            }
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cinema_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(TabActivity.this,Settings_AboutActivity.class);
            startActivity(intent);
            return true;
        }
        if( id == R.id.action_delete){
            FileAdapter.deleteFile(TabActivity.this);
            System.out.println("Deleted histoty file!");
            return true;
        }

        if( id == R.id.Log_off){
            Intent intent = new Intent(TabActivity.this,LoginActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.cinema_activity,menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        switch(id){
//            case R.id.action_settings:
//                Intent intentSettings = new Intent(this, AuditChoosingActivity.class);
//                startActivity(intentSettings);
//                return true;
//            case R.id.action_qrScan:
//                Intent intentQR = new Intent(this, QRScanActivity.class);
//                startActivity(intentQR);
//                return true;
//            case R.id.Log_off:
//                Intent intentLogOff = new Intent(this, LoginActivity.class);
//                startActivity(intentLogOff);
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }


//        if(tabStrip.getChildCount() > 2) {
//            tabStrip.getChildAt(2).setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//
//                    if (checkAuditsEmpty()) {
//                        if (!QRScanActivity.getStaticQRScanActivity().getQReader().isCameraRunning()) {
//                            QRScanActivity.getStaticQRScanActivity().getQReader().start();
//                        }
//
//                        Toast.makeText(TabActivity.this, "Заполните аудитории для проверки!", Toast.LENGTH_SHORT).show();
//                        return true;
//                    } else {
//                        return false;
//                    }
//                }
//            });