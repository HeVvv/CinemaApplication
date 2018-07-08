package com.example.user.cinemaapplication.Activites;

import com.example.user.cinemaapplication.Adds.FileAdapter;
import com.example.user.cinemaapplication.Adds.ListData;
import com.example.user.cinemaapplication.R;
import com.loopj.android.http.*;

import android.content.Intent;
import android.os.Bundle;
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


    private static LoginActivity staticLoginActivity;
    public static LoginActivity getStaticLoginActivity(){
        return staticLoginActivity;
    }
    public LoginActivity(){
        staticLoginActivity = this;
    }

    public int getDEVICE_NUMBER(){ return DEVICE_NUMBER; }
    public String getCINEMA_NAME(){  return CINEMA_NAME; }
    public String getUsername(){
        return username.getText().toString();
    }
    public String getPassword(){
        return password.getText().toString();
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

    public void onLogin(View view) {

        AsyncHttpClient client_main = new AsyncHttpClient();
        client_main.setBasicAuth(username.getText().toString(), password.getText().toString());
        client_main.setSSLSocketFactory(MySSLSocketFactory.getFixedSocketFactory());
        client_main.get("https://inlogic.org:8443/security-1.0/webapi/auth/login/" + username.getText().toString(), null, new JsonHttpResponseHandler() {
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
                if(statusCode != 200 && statusCode != 408 && statusCode != 401 ){
                    Toast toast = new Toast(LoginActivity.this);
                    toast.makeText(getApplicationContext(), "Ошибка! id-" + statusCode,Toast.LENGTH_SHORT).show();
                    toast.setGravity(Gravity.CENTER, 0, 0);
                }
                System.out.println("error" + statusCode + "~~~~" + responseString);
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
                if(statusCode != 200 && statusCode != 408 && statusCode != 401 ){
                    Toast toast = new Toast(LoginActivity.this);
                    toast.makeText(getApplicationContext(), "Ошибка! id-" + statusCode,Toast.LENGTH_SHORT).show();
                    toast.setGravity(Gravity.CENTER, 0, 0);
                }
                System.out.println("error" + statusCode + "~~~~" + errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    System.out.println("error" + statusCode + "~~~~" + errorResponse);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_AppCompat_NoActionBar);
        setContentView(R.layout.activity_main);

        Cancel = (TextView) findViewById(R.id.cancel);
        Cancel.setOnTouchListener(this);


        Logo = (ImageView) findViewById(R.id.logo);
        Logo.setImageResource(R.drawable.logo_prog);


        username = (EditText)findViewById(R.id.usernameInput);
        password = (EditText)findViewById(R.id.passInput);


        password.setOnKeyListener(new View.OnKeyListener() {
                                      public boolean onKey(View v, int keyCode, KeyEvent event) {
                                          // If the event is a key-down event on the "enter" button
                                          if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                                                  (keyCode == KeyEvent.KEYCODE_ENTER)) {
                                              // Perform action on key press
                                              LoginActivity.getStaticLoginActivity().onLogin(v);
                                              return true;
                                          }
                                          return false;
                                      }
                                  });

        //tested
//        username.setText("Tkachev_A");
//        password.setText("111111");


        super.onCreate(savedInstanceState);
    }



}
