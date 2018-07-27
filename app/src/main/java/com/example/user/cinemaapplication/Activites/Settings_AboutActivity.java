package com.example.user.cinemaapplication.Activites;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dcastalia.localappupdate.DownloadApk;
import com.example.user.cinemaapplication.Adds.FileAdapter;
import com.example.user.cinemaapplication.R;


import java.io.File;

public class Settings_AboutActivity extends AppCompatActivity {

    private TextView txt;
    private SurfaceView mySurfaceView;
    private Button button;
    private Button buttonShow;
    private SurfaceView overlay;
    private Button buttonRemove;
    private LinearLayout relativelayout;
    private ImageView response;
    private SharedPreferences mSettings;

    private boolean isFlashOn;
    private boolean hasFlash;
    private Camera camera;
    private Camera.Parameters params;

    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_COUNTER = "counter";


    private static Settings_AboutActivity staticSettings_AboutActivity;
    public static Settings_AboutActivity getStaticSettings_AboutActivityt(){
        return staticSettings_AboutActivity;
    }
    public Settings_AboutActivity(){
        staticSettings_AboutActivity = this;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        setTheme(R.style.Theme_AppCompat);
        setContentView(R.layout.activity_settings_about);
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        String idStr = FileAdapter.readFromFile(getApplication().getFilesDir()+ "/" + "ID.txt");

        TextView txtID = (TextView) findViewById(R.id.id_info);
        txtID.setGravity(Gravity.CENTER);
        TextView txtLogin = (TextView) findViewById(R.id.login_info);
        txtLogin.setGravity(Gravity.CENTER);

        if(!(idStr.isEmpty())) {
            txtID.setText("Текущее ID устройства : " + idStr);
        }else {
            txtID.setText("Текущее ID устройства : " + "Не определено");
        }
            txtLogin.setText("Логин : " + LoginActivity.getStaticLoginActivity().getUsername());


        Button btnID = (Button) findViewById(R.id.changeID);
        btnID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings_AboutActivity.this, EditID.class);
                startActivity(intent);
            }
        });

        Button btnHistory = (Button) findViewById(R.id.deleteHistory);
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File historyFile = new File(getApplication().getApplicationContext().getFilesDir(), "history.txt");
                FileAdapter.deleteFile(historyFile);
                Toast.makeText(getApplication(),"Файл истории удален!",Toast.LENGTH_LONG).show();
                System.out.println("Deleted histoty file!");
            }
        });

        Button btnswitch = (Button) findViewById(R.id.btnSwitch);
        btnswitch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
//                String url = "https://github.com/Piashsarker/AndroidAppUpdateLibrary/raw/master/app-debug.apk";
                String url = "https://github.com/HeVvv/Sapk/raw/master/app-debug.apk";
                DownloadApk downloadApk = new DownloadApk(Settings_AboutActivity.this);
                downloadApk.startDownloadingApk(url);
            }
        });

//        Button btnVibrate = (Button) findViewById(R.id.vibrTest);
//        btnVibrate.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
//                int action = event.getAction();
//                if(action == MotionEvent.ACTION_DOWN){
//                    vibrator.vibrate(2000);
//                }else if(action == MotionEvent.ACTION_UP){
//                    vibrator.cancel();
//                }
//                return true;
//            }
//        });
//
//        final Button btnVibrate2 = (Button) findViewById(R.id.vibrTest2);
//        btnVibrate2.setHapticFeedbackEnabled(true);
//        btnVibrate2.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v) {
//                Settings.System.getInt(Settings_AboutActivity.staticSettings_AboutActivity.getContentResolver(),Settings.System.HAPTIC_FEEDBACK_ENABLED,0);
//                btnVibrate2.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//                System.out.println("vibr");
//            }
//        });

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Settings_AboutActivity.this,TabActivity.class);
        i.addCategory(Intent.CATEGORY_HOME);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}
