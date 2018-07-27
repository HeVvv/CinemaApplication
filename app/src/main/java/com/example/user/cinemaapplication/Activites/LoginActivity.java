package com.example.user.cinemaapplication.Activites;

import com.example.user.cinemaapplication.Adds.FileAdapter;
import com.example.user.cinemaapplication.Adds.ListData;
import com.example.user.cinemaapplication.R;
import com.loopj.android.http.*;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity implements View.OnTouchListener {

    private EditText username;
    private EditText password;
    private String CINEMA_NAME = "Arena Minsk";
    private int DEVICE_NUMBER = 1;

    private TextView Cancel;
    private ImageView Logo;

    private List<String> THEATER_COLOR = new ArrayList<>();
    private int THEATER_ID;
    private HashMap <String,Integer> DATA = new HashMap<>();
    private HashMap<Integer,String> THEATER_DATA = new HashMap<>();

    private String HardCodedUsername = "Starastsin_A";
    private String HardCodedPassword = "test";

    private static LoginActivity staticLoginActivity;
    public static LoginActivity getStaticLoginActivity(){
        return staticLoginActivity;
    }
    public LoginActivity() throws PackageManager.NameNotFoundException {
        staticLoginActivity = this;
    }

    public int getDEVICE_NUMBER(){ return DEVICE_NUMBER; }
    public String getCINEMA_NAME(){  return CINEMA_NAME; }
    public String getUsername(){
        return HardCodedUsername;
    }
    public String getPassword(){
        return HardCodedPassword;
    }

    public HashMap<String, Integer> getDATA(){
        return DATA;
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

    public void onLogin(View view) throws UnsupportedEncodingException {

//        System.out.println(URLEncoder.encode(username.getText().toString(),"UTF-8"));
//        String loginAuth = new String(username.getText().toString().getBytes("UTF-8"), "UTF-8");
//        byte ptext[] = username.getText().toString().getBytes("UTF-8");
//        String logina = new String(ptext,"UTF-8");
//        System.out.println(logina);
//        System.out.println(loginAuth);

//        String loginUrl = "https://soft.silverscreen.by:8443/security-1.0/webapi/auth/login/";
        String loginUrl = "https://inlogic.org:8443/security-1.0/webapi/auth/login/";

        final AsyncHttpClient client_main = new AsyncHttpClient();
        client_main.setBasicAuth(HardCodedUsername, HardCodedPassword);
        client_main.setSSLSocketFactory(MySSLSocketFactory.getFixedSocketFactory());
        client_main.get(loginUrl + URLEncoder.encode(username.getText().toString(),"UTF-8"), null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject responseBody) {
                Intent intent = new Intent(LoginActivity.this, SplashActivity.class);
                final String str = FileAdapter.readFromFile(getApplication().getFilesDir()+ "/" + "ID.txt");
                if (!(str.isEmpty())) {
                    final int device_id = Integer.parseInt(str.trim());
                    System.out.println("device id->" + device_id);
                    THEATER_ID = ListData.getTheaterByDEVICE_ID(device_id);
                }
                startActivity(intent);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                if (statusCode == 401){
                    Toast toast = new Toast(LoginActivity.this);
                    toast.makeText(getApplicationContext(),"Неправильные данные", Toast.LENGTH_SHORT).show();
                    toast.setGravity(Gravity.CENTER, 0, 0);
                }
                if(statusCode == 408){
                    Toast toast = new Toast(LoginActivity.this);
                    toast.makeText(getApplicationContext(),"Истекло время ожидания",Toast.LENGTH_SHORT).show();
                    toast.setGravity(Gravity.CENTER, 0, 0);
                }
                if(statusCode == 0)
                {
                    Toast toast = new Toast(LoginActivity.this);
                    toast.makeText(getApplicationContext(), "Проверьте подключение к интернету!" + statusCode,Toast.LENGTH_SHORT).show();
                    toast.setGravity(Gravity.CENTER, 0, 0);
                }
                if(statusCode != 200 && statusCode != 408 && statusCode != 401 ){
                    Toast toast = new Toast(LoginActivity.this);
                    toast.makeText(getApplicationContext(), "Ошибка! id-" + statusCode,Toast.LENGTH_SHORT).show();
                    toast.setGravity(Gravity.CENTER, 0, 0);
                }

                System.out.println("error" + statusCode + "~~~~1" + responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                if (statusCode == 401){
                    Toast toast = new Toast(LoginActivity.this);
                    toast.makeText(getApplicationContext(),"Неправильные данные", Toast.LENGTH_SHORT).show();
                    toast.setGravity(Gravity.CENTER, 0, 0);
                }
                if(statusCode == 408){
                    Toast toast = new Toast(LoginActivity.this);
                    toast.makeText(getApplicationContext(),"Истекло время ожидания",Toast.LENGTH_SHORT).show();
                    toast.setGravity(Gravity.CENTER, 0, 0);
                }
                if(statusCode == 0)
                {
                    Toast toast = new Toast(LoginActivity.this);
                    toast.makeText(getApplicationContext(), "Проверьте подключение к интернету!" + statusCode,Toast.LENGTH_SHORT).show();
                    toast.setGravity(Gravity.CENTER, 0, 0);
                }
                if(statusCode != 200 && statusCode != 408 && statusCode != 401 ){
                    Toast toast = new Toast(LoginActivity.this);
                    toast.makeText(getApplicationContext(), "Ошибка! id-" + statusCode,Toast.LENGTH_SHORT).show();
                    toast.setGravity(Gravity.CENTER, 0, 0);
                }
                System.out.println("error" + statusCode + "~~~~2" + errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                System.out.println("error" + statusCode + "~~~~" + errorResponse);
                if (statusCode == 401){
                    Toast toast = new Toast(LoginActivity.this);
                    toast.makeText(getApplicationContext(),"Неправильные данные", Toast.LENGTH_SHORT).show();
                    toast.setGravity(Gravity.CENTER, 0, 0);
                }
                if(statusCode == 408) {
                    Toast toast = new Toast(LoginActivity.this);
                    toast.makeText(getApplicationContext(), "Истекло время ожидания", Toast.LENGTH_SHORT).show();
                    toast.setGravity(Gravity.CENTER, 0, 0);
                }
                if(statusCode == 0)
                {
                    Toast toast = new Toast(LoginActivity.this);
                    toast.makeText(getApplicationContext(), "Проверьте подключение к интернету!" + statusCode,Toast.LENGTH_SHORT).show();
                    toast.setGravity(Gravity.CENTER, 0, 0);
                }
                if(statusCode != 200 && statusCode != 408 && statusCode != 401 ){
                    Toast toast = new Toast(LoginActivity.this);
                    toast.makeText(getApplicationContext(), "Ошибка! id-" + statusCode,Toast.LENGTH_SHORT).show();
                    toast.setGravity(Gravity.CENTER, 0, 0);
                }
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        finishAndRemoveTask();
        return true;
    }

    private boolean STATE = true;


    public boolean updateCheck(){
        return STATE;
    }


    // https://github.com/HeVvv/Sapk/blob/master/app-debug.apk

    // problema v imeni, smena imeni pri vipuske novoi versii
    String testpath = "/storage/self/primary/Download/apk_downloader_1.17.apk";

    public void updateInstall(){
        if(updateCheck()){

            String versionName = null;
            try {
                versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            String path = "/storage/self/primary/Download/app-release"+versionName+".apk";
            String testpath =  "/storage/self/primary/Download/Scinema2.1r.apk";

            File file = new File(testpath);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                System.out.println("1");
                Uri fileUri = FileProvider.getUriForFile(getBaseContext(), getApplicationContext().getPackageName() + ".provider", file);
                System.out.println("uri -> " + fileUri);
                Intent intent = new Intent(Intent.ACTION_VIEW, fileUri);
                intent.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true);
                intent.setDataAndType(fileUri, "application/vnd.android" + ".package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                // sharedprer settings for state of update
                startActivity(intent);
            } else {
                System.out.println("2");
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                // sharedprer settings for state of update
                startActivity(intent);
            }
        }
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
                    Toast.makeText(LoginActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_AppCompat_NoActionBar);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(LoginActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                1);

        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            int verCode = pInfo.versionCode;

            System.out.println("Version name -> " + version);
            System.out.println("Version code -> " + verCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        Cancel = (TextView) findViewById(R.id.cancel);
        Cancel.setOnTouchListener(this);

        Logo = (ImageView) findViewById(R.id.logo);
        Logo.setImageResource(R.drawable.logo_prog);

        username = (EditText) findViewById(R.id.usernameInput);
        password = (EditText) findViewById(R.id.passInput);

        password.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    try {
                        LoginActivity.getStaticLoginActivity().onLogin(v);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    return true;
                }
                return false;
            }
        });

        username.setText("Tkachev_A");
        password.setText("111111");

        super.onCreate(savedInstanceState);

    }
}
