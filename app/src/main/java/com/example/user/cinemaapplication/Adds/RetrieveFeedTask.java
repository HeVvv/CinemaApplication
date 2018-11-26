package com.example.user.cinemaapplication.Adds;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.renderscript.ScriptGroup;
import android.util.Log;

import com.example.user.cinemaapplication.Activites.LoginActivity;
import com.example.user.cinemaapplication.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;

public class RetrieveFeedTask extends AsyncTask<String, Void, String> {

    private Exception exception;
    private PackageInfo pInfo;
    private String version = "";
    volatile String git_version = "";

    private String versionFile_git = "https://raw.githubusercontent.com/HeVvv/SilverApkUpdateFolder/master/androidVersionFile.txt";
    public String google = "http://google.com";
    private String apkFile_git = "https://github.com/HeVvv/SilverApkUpdateFolder/raw/master/app-release.apk";
    private AsyncTask mtask;

    @Override
    protected String doInBackground(String... strings) {
        try {
            try {
                URL versionFile_gitUrl = new URL(strings[0]);
//                System.out.println(strings[0]);
//                System.out.println(versionFile_gitUrl);
                InputStream is =null;
                try {
                    is = versionFile_gitUrl.openStream();
                }catch (Exception e){
//                    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    e.printStackTrace();
                }
                if(is!=null){
                    Scanner s = new Scanner(is);
                    this.git_version = s.nextLine();
//                    System.out.println("--------------------------------" + this.git_version);
                }
            }catch (IOException pokemon){
                pokemon.printStackTrace();
            }
            return git_version;
        } catch (Exception pokemon) {
            pokemon.printStackTrace();
            return null;
        } finally {
//            System.out.println("doinbground");
            return "s";
        }
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        System.out.println("onPreExecute");
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            pInfo = LoginActivity.getStaticLoginActivity().getPackageManager().getPackageInfo(LoginActivity.getStaticLoginActivity().getPackageName(), 0);
        }catch(PackageManager.NameNotFoundException pokemon){
            pokemon.printStackTrace();
        }
        version = pInfo.versionName;

//        System.out.println("onPostExecute " + s);
        if(!(git_version.isEmpty())){
            System.out.println("Git version -> " + git_version);
            System.out.println("Mob version -> " + version);
            if(!git_version.equals(version)){
                System.out.println("Start update!");
                System.out.println("Url string -> " + apkFile_git);
                LoginActivity.getStaticLoginActivity().setTheme(R.style.Theme_AppCompat_NoActionBar);
                LoginActivity.getStaticLoginActivity().startDownloadingApk(apkFile_git);
            }else{
                System.out.println("Device is up to date!");
            }
        }else{
            System.out.println("git version file is null or couldnt load version file");
        }
    }
}