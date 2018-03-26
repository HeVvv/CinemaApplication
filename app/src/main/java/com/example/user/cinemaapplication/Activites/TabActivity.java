package com.example.user.cinemaapplication.Activites;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

import com.example.user.cinemaapplication.R;

public class TabActivity extends ActivityGroup {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cinema_activity,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.action_settings:
                Intent intentSettings = new Intent(this, AuditChoosingActivity.class);
                startActivity(intentSettings);
                return true;
            case R.id.action_qrScan:
                Intent intentQR = new Intent(this, QRScanActivity.class);
                startActivity(intentQR);
                return true;
            case R.id.Log_off:
                Intent intentLogOff = new Intent(this, LoginActivity.class);
                startActivity(intentLogOff);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);


        TabHost tabHost = (TabHost) findViewById(R.id.tabHost); // initiate TabHost
        TabHost.TabSpec spec; // Reusable TabSpec for each t
        Intent intent; // Reusable Intent for each tab

        tabHost.setup(this.getLocalActivityManager());

        spec = tabHost.newTabSpec("home"); // Create a new TabSpec using tab host
        spec.setIndicator("Выбор залов"); // set the “HOME” as an indicator
        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent(this, AuditChoosingActivity.class);
        spec.setContent(intent);
        tabHost.addTab(spec);

        // Do the same for the other tabs

        spec = tabHost.newTabSpec("Contact"); // Create a new TabSpec using tab host
        spec.setIndicator("Скан"); // set the “CONTACT” as an indicator
        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent(this, QRScanActivity.class);
        spec.setContent(intent);
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("About"); // Create a new TabSpec using tab host
        spec.setIndicator("История"); // set the “ABOUT” as an indicator
        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent(this, CinemaActivity.class);
        spec.setContent(intent);
        tabHost.addTab(spec);

//        spec = tabHost.newTabSpec("Settings"); // Create a new TabSpec using tab host
//        spec.setIndicator("Settings"); // set the “ABOUT” as an indicator
//        // Create an Intent to launch an Activity for the tab (to be reused)
//        intent = new Intent(this, Settings_AboutActivity.class);
//        spec.setContent(intent);
//        tabHost.addTab(spec);

        //set tab which one you want to open first time 0 or 1 or 2
        tabHost.setCurrentTab(0);


//        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
//            @Override
//            public void onTabChanged(String tabId) {
//                // display the name of the tab whenever a tab is changed
//                Toast.makeText(getApplicationContext(), tabId, Toast.LENGTH_SHORT).show();
//            }
//        });


    }

    private AppCompatActivity mClass = new AppCompatActivity();

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