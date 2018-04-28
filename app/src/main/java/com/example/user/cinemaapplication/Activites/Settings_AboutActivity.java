package com.example.user.cinemaapplication.Activites;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.cinemaapplication.Adds.FileAdapter;
import com.example.user.cinemaapplication.R;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;


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

        String str = FileAdapter.readFromFile(getApplication().getFilesDir()+ "/" + "ID.txt");

        TextView txtID = (TextView) findViewById(R.id.id_info);
        txtID.setGravity(Gravity.CENTER);
        TextView txtLogin = (TextView) findViewById(R.id.login_info);
        txtLogin.setGravity(Gravity.CENTER);

        if(!(str.isEmpty())) {
            txtID.setText("Текущее ID устройства : " + str);
        }else {
            txtID.setText("Текущее ID устройства : " + "Не определено");
        }
            txtLogin.setText("Логин : " + LoginActivity.getStaticLoginActivity().getUsername());


        Button btn = (Button) findViewById(R.id.changeID);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings_AboutActivity.this, EditID.class);
                startActivity(intent);
            }
        });

        super.onCreate(savedInstanceState);
    }
}
