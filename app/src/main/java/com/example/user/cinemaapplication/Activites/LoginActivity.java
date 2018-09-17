package com.example.user.cinemaapplication.Activites;

import com.dcastalia.localappupdate.DownloadApk;
import com.example.user.cinemaapplication.Adds.FileAdapter;
import com.example.user.cinemaapplication.Adds.ListData;
import com.example.user.cinemaapplication.Adds.RetrieveFeedTask;
import com.example.user.cinemaapplication.R;
import com.loopj.android.http.*;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.Buffer;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

import cz.msebera.android.httpclient.Header;

import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;

public class LoginActivity extends AppCompatActivity implements View.OnTouchListener {

    private EditText username;
    private EditText password;
    private String CINEMA_NAME = "Arena Minsk";
    private String version = "";
    private String git_version = "";

    private TextView idText;
    private TextView Cancel;
    private ImageView Logo;
    private TextView versionText;

    private List<String> THEATER_COLOR = new ArrayList<>();
    private int THEATER_ID;
    private HashMap<String, Integer> DATA = new HashMap<>();
    private HashMap<Integer, String> THEATER_DATA = new HashMap<>();

    private String HardCodedUsername = "Starastsin_A";
    private String HardCodedPassword = "test";

    private String versionFile_git = "https://raw.githubusercontent.com/HeVvv/SilverApkUpdateFolder/master/androidVersionFile.txt";
    public String google = "http://google.com";
    private String apkFile_git = "https://github.com/HeVvv/SilverApkUpdateFolder/raw/master/app-release.apk";
    private AsyncTask mtask;

    private static ProgressDialog bar;
    private static Context context;

    private static String downloadUrl;
    private PackageInfo pInfo;

    public PackageInfo getpInfo() {
        return pInfo;
    }

    private static LoginActivity staticLoginActivity;

    public static LoginActivity getStaticLoginActivity() {
        return staticLoginActivity;
    }

    public LoginActivity() throws PackageManager.NameNotFoundException {
        staticLoginActivity = this;
    }

    public String getUsername() {
        return HardCodedUsername;
    }

    public String getPassword() {
        return HardCodedPassword;
    }

    public int getTHEATER_ID() {
        return THEATER_ID;
    }


    private static Uri getUriFromFile(String location) {
        return Build.VERSION.SDK_INT < 24 ? Uri.fromFile(new File(location + "app-release.apk")) : FileProvider.getUriForFile(getStaticLoginActivity().getApplication(), "com.example.user.cinemaapplication.provider", new File(location + "app-release.apk"));
    }

    public static void OpenNewVersion(String location) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(getUriFromFile(location), "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(FLAG_GRANT_READ_URI_PERMISSION);
//        intent.addFlags(1);
        context.startActivity(intent);
        LoginActivity.getStaticLoginActivity().finish();
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

    public void startDownloadingApk(String url) {
        downloadUrl = url;
        if (downloadUrl != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    (new DownloadNewVersion()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
            });
        }

    }


    public void onLogin(View view) throws UnsupportedEncodingException {


//        System.out.println(URLEncoder.encode(username.getText().toString(),"UTF-8"));
//        String loginAuth = new String(username.getText().toString().getBytes("UTF-8"), "UTF-8");
//        byte ptext[] = username.getText().toString().getBytes("UTF-8");
//        String logina = new String(ptext,"UTF-8");
//        System.out.println(logina);
//        System.out.println(loginAuth);

        String loginUrl = "https://soft.silverscreen.by:8443/security-1.0/webapi/auth/login/";
//        String loginUrl = "https://inlogic.org:8443/security-1.0/webapi/auth/login/";

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

    public class DownloadNewVersion extends AsyncTask<String, Integer, Boolean> {
    public DownloadNewVersion() {
    }

    protected void onPreExecute() {
        super.onPreExecute();
        if (bar == null) {
            bar = new ProgressDialog(context);
            bar.setCancelable(false);
            bar.setMessage("Connecting...");
            bar.setIndeterminate(true);
            bar.setCanceledOnTouchOutside(false);
            bar.show();
        }
    }

    protected void onProgressUpdate(Integer... progress) {
        super.onProgressUpdate(progress);
        bar.setIndeterminate(false);
        bar.setMax(100);
        bar.setProgress(progress[0]);
        String msg = "";
        if (progress[0] > 99) {
            msg = "Finishing... ";
        } else {
            msg = "Downloading... " + progress[0] + "%";
        }
        bar.setMessage(msg);
    }

    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        if (bar.isShowing() && bar != null) {
            bar.dismiss();
            bar = null;
        }

        if (result) {
            Toast.makeText(LoginActivity.getStaticLoginActivity().getApplication(), "Update Done", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(LoginActivity.getStaticLoginActivity().getApplication(), "Error: Try Again", Toast.LENGTH_SHORT).show();
        }
    }

    protected Boolean doInBackground(String... args0) {
        Boolean flag = false;

        try {
            URL url = new URL("https://github.com/HeVvv/SilverApkUpdateFolder/raw/master/app-release.apk");
            HttpsURLConnection c = (HttpsURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setSSLSocketFactory(HttpsURLConnection.getDefaultSSLSocketFactory());
            c.setDoOutput(true);
            c.connect();
            String PATH = Environment.getExternalStorageDirectory() + "/Download/";
            File file = new File(PATH);
            file.mkdirs();
            File outputFile = new File(file, "app-release.apk");
            if (outputFile.exists()) {
                outputFile.delete();
            }

            FileOutputStream fos = new FileOutputStream(outputFile);
            InputStream is = c.getInputStream();
            int total_size = c.getContentLength();
            byte[] buffer = new byte[1024];
            int downloaded = 0;

            int len1;
            while ((len1 = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len1);
                downloaded += len1;
                int per = downloaded * 100 / total_size;
                this.publishProgress(new Integer[]{per});
            }

            fos.close();
            is.close();
            LoginActivity.OpenNewVersion(PATH);
            flag = true;
        } catch (MalformedURLException mfpokemon) {
            mfpokemon.printStackTrace();
        } catch (IOException iopokemon) {
            iopokemon.printStackTrace();
        }

        return flag;
    }

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
        context = LoginActivity.this;

        String idStr = FileAdapter.readFromFile(getApplication().getFilesDir()+ "/" + "ID.txt");
        try {
            pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
        }catch(PackageManager.NameNotFoundException pokemon){
            pokemon.printStackTrace();
        }

        if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(LoginActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);
        }

        try {
            pInfo = LoginActivity.getStaticLoginActivity().getPackageManager().getPackageInfo(LoginActivity.getStaticLoginActivity().getPackageName(), 0);
        }catch(PackageManager.NameNotFoundException pokemon){
            pokemon.printStackTrace();
        }
        version = pInfo.versionName;

        Cancel = (TextView) findViewById(R.id.cancel);
        Cancel.setOnTouchListener(this);
        Cancel.setGravity(Gravity.CENTER);

        Logo = (ImageView) findViewById(R.id.logo);
        Logo.setImageResource(R.drawable.ic_frontrow_logo);


        username = (EditText) findViewById(R.id.usernameInput);
        username.setHint("Логин");
        password = (EditText) findViewById(R.id.passInput);
        password.setHint("Пароль");

        versionText = (TextView) findViewById(R.id.versionText);
        idText = (TextView) findViewById(R.id.idText);

        versionText.setText("Ticket Reader v " + version);
        versionText.setGravity(Gravity.CENTER);
        versionText.setTextSize(7);

        idText.setText("ID #" + idStr);
        idText.setGravity(Gravity.CENTER);
        idText.setTextSize(7);



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

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mtask = new RetrieveFeedTask().execute(versionFile_git);
            }
        });

        version = LoginActivity.getStaticLoginActivity().getpInfo().versionName;

        super.onCreate(savedInstanceState);
    }
}
