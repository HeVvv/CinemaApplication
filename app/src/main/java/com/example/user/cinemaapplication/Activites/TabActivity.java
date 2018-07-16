package com.example.user.cinemaapplication.Activites;

import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.style.TabStopSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.Toast;

import com.example.user.cinemaapplication.Adds.CustomViewPager;
import com.example.user.cinemaapplication.Adds.FileAdapter;
import com.example.user.cinemaapplication.Adds.ListData;
import com.example.user.cinemaapplication.Adds.PagerAdapter;
import com.example.user.cinemaapplication.R;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;



public class TabActivity extends AppCompatActivity {

    private boolean state;
    private Fragment frag_scan = new QRScanActivity();
    private Fragment frag_history = new HistoryListActivity();
    private Fragment frag_choose = new AuditChoosingActivity();
    private PagerAdapter adapter;
    private CustomViewPager viewPager;
    private TabLayout tabLayout;
    private LinearLayout tabStrip;
    private int THEATER_ID;
    private boolean isFlashOn;
    private boolean hasFlash;
    private Camera camera;
    private Camera.Parameters params;


    private int[] tabIcons = {
            R.drawable.tickets,
            R.drawable.video_camera,
            R.drawable.barcode
    };

    private static TabActivity staticTabActivity;
    public static TabActivity getStaticTabActivity(){
        return staticTabActivity;
    }
    public TabActivity(){
        staticTabActivity = this;
    }


    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[1]);
        tabLayout.getTabAt(1).setIcon(tabIcons[2]);
        tabLayout.getTabAt(2).setIcon(tabIcons[0]);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HashMap<Integer,String> theater_data = SplashActivity.getStaticSplashActivity().getTHEATER_DATA();
        List<String> theater_color = SplashActivity.getStaticSplashActivity().getTHEATER_COLOR();
//
//        System.out.println(LoginActivity.getStaticLoginActivity().getTHEATER_COLOR());
//        System.out.println(LoginActivity.getStaticLoginActivity().getTHEATER_DATA());
//        System.out.println(LoginActivity.getStaticLoginActivity().getTHEATER_ID());

        THEATER_ID = ListData.getTheaterId();

        System.out.println(THEATER_ID + " THEATER ID ON TAB");
        for (Map.Entry entry : theater_data.entrySet()) {
            if(Integer.parseInt(entry.getKey().toString()) == THEATER_ID) {
                System.out.println("Entry ->" + entry.getValue());
                setTitle(entry.getValue().toString());
            }
        }

        if((THEATER_ID == 2) || (THEATER_ID == 1)){
            setTheme(R.style.AppThemeArena);
        }
        if(THEATER_ID == 3){
            setTheme(R.style.AppThemeVelcom);
        }

        if(THEATER_ID == 0){
            setTheme(R.style.Theme_AppCompat_NoActionBar);
        }

        setContentView(R.layout.activity_tab2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (CustomViewPager) findViewById(R.id.pager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        adapter = new PagerAdapter(getSupportFragmentManager());
        adapter.addFragment(frag_choose,"Выбор");
        adapter.addFragment(frag_history,"История");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(tabIcons[1]);
        tabLayout.getTabAt(1).setIcon(tabIcons[0]);

        tabStrip = ((LinearLayout) tabLayout.getChildAt(0));

        Timer myTimer = new Timer();
        final Handler uiHandler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (!(checkAuditsEmpty())){
                    // add
                    if(tabStrip.getChildCount() == 2) {
                        adapter.removeFragment(frag_history,"История");
                        adapter.addFragment(frag_scan, "Скан");
                        adapter.addFragment(frag_history,"История");

                        try{
                            adapter.notifyDataSetChanged();
                        }catch (IllegalStateException e){
                            e.printStackTrace();
                        }
                        viewPager.setAdapter(adapter);
                        setupTabIcons();
                        try {
//                            tabLayout.getTabAt(1).select();
                        }catch (NullPointerException e){
                            e.printStackTrace();
                        }
//                        System.out.println("added");
                    }
                }else{
                    //remove
                    if(tabStrip.getChildCount() == 3) {
                        adapter.removeFragment(frag_scan, "Скан");
                        try{
                            adapter.notifyDataSetChanged();
                        }catch (IllegalStateException e){
                            e.printStackTrace();
                        }
                        viewPager.setAdapter(adapter);
                            tabLayout.getTabAt(0).setIcon(tabIcons[1]);
                            tabLayout.getTabAt(1).setIcon(tabIcons[0]);

                        try {
//                            tabLayout.getTabAt(1).select();
                        }catch (NullPointerException e){
                            e.printStackTrace();
                        }
//                        System.out.println("removed");
                    }
                }
            }
        };

        myTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                uiHandler.post(runnable);
            }
        }, 0L, 1500 );

        uiHandler.removeCallbacks(runnable);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
//                HistoryListActivity.getHistoryListActivity().historyListUpdate(HistoryListActivity.getHistoryListActivity().getAdapter(),HistoryListActivity.getHistoryListActivity().getTicketHistoryList());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });
        try {
            tabLayout.getTabAt(0).select();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

//    public Runnable tabControl = new Runnable() {
//        @Override
//        public void run() {
//            System.out.println("scan tab tick");
//            if (!(checkAuditsEmpty())){
//                // add
//                if(tabStrip.getChildCount() == 2) {
//                    adapter.addFragment(frag_scan, "Скан");
//                    adapter.notifyDataSetChanged();
//                    viewPager.setAdapter(adapter);
//                    try {
//                        tabLayout.getTabAt(1).select();
//                    }catch (NullPointerException e){
//                        e.printStackTrace();
//                    }
//                    System.out.println("added");
//                }
//            }else{
//                //remove
//                if(tabStrip.getChildCount() == 3) {
//                    adapter.removeFragment(frag_scan, "Скан");
//                    adapter.notifyDataSetChanged();
//                    viewPager.setAdapter(adapter);
//                    try {
//                        tabLayout.getTabAt(1).select();
//                    }catch (NullPointerException e){
//                        e.printStackTrace();
//                    }
//                    System.out.println("removed");
//                }
//            }
//        }
//    };

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

    private static final int TIME_DELAY = 2000;
    private static long back_pressed;


    @Override
    public void onBackPressed() {
        if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
            Intent i = new Intent(TabActivity.this,LoginActivity.class);
            i.addCategory(Intent.CATEGORY_HOME);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else {
            Toast.makeText(getBaseContext(), "Press once again to exit!",
                    Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(TabActivity.this,Pass_CheckActivity.class);
            startActivity(intent);
            return true;
        }

        if( id == R.id.exit){
            Intent i = new Intent(TabActivity.this,LoginActivity.class);
            i.addCategory(Intent.CATEGORY_HOME);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
//            Intent intent = new Intent(TabActivity.this,LoginActivity.class);
//            startActivity(intent);
            return true;
        }
//        if( id == R.id.action_flashlight){
//            if (isFlashOn) {
//                Toast.makeText(getBaseContext(), "Flashlight off!",
//                        Toast.LENGTH_SHORT).show();
//                turnOffFlash();
//            } else {
//                Toast.makeText(getBaseContext(), "Flashlight on!",
//                        Toast.LENGTH_SHORT).show();
//                turnOnFlash();
//            }
//                return true;
//        }
        return super.onOptionsItemSelected(item);
    }
}
