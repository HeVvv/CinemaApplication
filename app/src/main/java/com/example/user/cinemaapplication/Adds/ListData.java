package com.example.user.cinemaapplication.Adds;


import android.app.Application;
import android.net.Uri;
import android.util.Log;


import com.example.user.cinemaapplication.Activites.AuditChoosingActivity;
import com.example.user.cinemaapplication.Classes.AuditoriumsClass;
import com.example.user.cinemaapplication.Classes.SalespointsClass;
import com.example.user.cinemaapplication.Classes.SessionClass;
import com.example.user.cinemaapplication.Activites.LoginActivity;
import com.example.user.cinemaapplication.Classes.TheaterClass;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.loopj.android.http.AsyncHttpClient;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.MySSLSocketFactory;
import com.loopj.android.http.SyncHttpClient;
import com.mysql.jdbc.authentication.MysqlClearPasswordPlugin;

import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.HttpVersion;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.conn.scheme.SchemeRegistry;
import cz.msebera.android.httpclient.impl.client.BasicCookieStore;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;
import cz.msebera.android.httpclient.params.BasicHttpParams;
import cz.msebera.android.httpclient.params.HttpProtocolParams;
import cz.msebera.android.httpclient.protocol.HTTP;

import static cz.msebera.android.httpclient.HttpHeaders.USER_AGENT;


public class ListData extends Application {

    private static HashMap<String,Integer> AuditData = new HashMap<>();
    private static HashMap<String, List<String>> expDetails = new HashMap<>();
    private static HashMap<Integer, List<String>> TheaterInfo = new HashMap<>();
    private static HashMap<Integer,String> TheaterID_Name = new HashMap<>();
    private static List<String> TheaterColor = new ArrayList<>();
    private static int THEATER_ID;

    private static ListData staticListData;
    public static ListData getStaticListData() {
        return staticListData;
    }
    public ListData() {
        staticListData = this;
    }
//
//    final static String url12 = "https://inlogic.org:8443/wscinema/webapi/auditoriums";
//    final static String url22 = "https://inlogic.org:8443/wscinema/webapi/show/auditorium/";
//    final static String urlTheaterInfo = "https://inlogic.org:8443/wsglobal/webapi/theater/android";
//    final static String urlConn = "https://inlogic.org:8443/wsglobal/webapi/salespoints/android/"; // + device_id

    final static String url12 = "https://soft.silverscreen.by:8443/wscinema/webapi/auditoriums";
    final static String url22 = "https://soft.silverscreen.by:8443/wscinema/webapi/show/auditorium/";
    final static String urlTheaterInfo = "https://soft.silverscreen.by:8443/wsglobal/webapi/theater/android";
    final static String urlConn = "https://soft.silverscreen.by:8443/wsglobal/webapi/salespoints/android/"; // + device_id

    //private static HashMap<String,Integer> AuditData = new HashMap<>();
    //HashMap sorting
    public static HashMap<String,Integer> getAuditData(){
        System.out.println("Audit data is ->" + AuditData);
        return AuditData;
    }
    public static int getTheaterId(){
        return THEATER_ID;
    }
    public static HashMap<Integer,String> getTheaterID_Name(){
        return TheaterID_Name;
    }

    //loading ExpListView with sessions info
    public static HashMap<String, List<String>> loadListData() {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Loading list.");
        final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        final AsyncHttpClient client_loadListData = new AsyncHttpClient();
        client_loadListData.setBasicAuth(LoginActivity.getStaticLoginActivity().getUsername(), LoginActivity.getStaticLoginActivity().getPassword());
        client_loadListData.setSSLSocketFactory(MySSLSocketFactory.getFixedSocketFactory());
        client_loadListData.get(url12, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    AuditoriumsClass auditoriumsClass = new AuditoriumsClass();
                    for (int i = 0; i < response.length(); i++) {
                        auditoriumsClass = JSONUtils.parseJsonToObject(response.toString(), AuditoriumsClass.class);
                        final String auditName = auditoriumsClass.getAcronym();
                        AsyncHttpClient client2 = new AsyncHttpClient();
                        client2.setBasicAuth(LoginActivity.getStaticLoginActivity().getUsername(), LoginActivity.getStaticLoginActivity().getPassword());
                        client2.setSSLSocketFactory(MySSLSocketFactory.getFixedSocketFactory());
                        client2.get(url22 + auditoriumsClass.getId(), null, new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                try {
                                    SessionClass sessClass = new SessionClass();
                                    List<String> sessList = new ArrayList<>();
                                    //handle time check?
                                    for (int k = 0; k < 3; k++) {
                                        sessClass = JSONUtils.parseJsonToObject(response.toString(),SessionClass.class);
                                        sessList.add(sessClass.getAcronymEvent() + " " + dateFormat.format(sessClass.getStart())+ " №" +sessClass.getId());
                                    }
                                    if (sessList.isEmpty()) {
                                        sessList.add("This auditorium has no sessions today [-]");
                                    }
                                    expDetails.put(auditName, sessList);
                                    System.out.println(expDetails);
                                } catch (Exception e) {
                                    System.out.println("Inner Url/Json error! " + statusCode + " " + response.toString());
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                super.onFailure(statusCode, headers, responseString, throwable);
                                System.out.println("error" + statusCode + "~~~~" + responseString);
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                super.onFailure(statusCode, headers, throwable, errorResponse);
                                System.out.println("error" + statusCode + "~~~~" + errorResponse);
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                                super.onFailure(statusCode, headers, throwable, errorResponse);
                                System.out.println("error" + statusCode + "~~~~" + errorResponse);
                            }

                        });
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    System.out.println("Outer Url/Json error! " + statusCode + " " + response.toString());
                    e.printStackTrace();
                }

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, final JSONArray responseSessionBody) {
                try {
                    List<AuditoriumsClass> auditClass = new ArrayList<>();
                    for (int i = 0; i < responseSessionBody.length(); i++) {
                        auditClass = JSONUtils.toList(AuditoriumsClass.class, responseSessionBody.toString());
                        final String auditName = auditClass.get(i).getAcronym();
                        AsyncHttpClient client2 = new AsyncHttpClient();
                        client2.setBasicAuth(LoginActivity.getStaticLoginActivity().getUsername(), LoginActivity.getStaticLoginActivity().getPassword());
                        client2.setSSLSocketFactory(MySSLSocketFactory.getFixedSocketFactory());
                        client2.get(url22 + auditClass.get(i).getId(), null, new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                System.out.println("got url22 response");
                            }

                            @Override
                            public void onSuccess(int statusCode, Header[] headers1, JSONArray responseSessionBody2) {
                                try {
                                    List<SessionClass> sessClass = new ArrayList<>();
                                    List<String> sessList = new ArrayList<>();
                                    //handle time check?
                                    for (int k = 0; k < 3; k++) {
                                        sessClass = JSONUtils.toList(SessionClass.class, responseSessionBody2.toString());
                                        sessList.add(sessClass.get(k).getAcronymEvent() + " " + dateFormat.format(sessClass.get(k).getStart())+ " №" +sessClass.get(k).getId());
                                    }
                                    if (sessList.isEmpty()) {
                                        sessList.add("This auditorium has no sessions today [-]");
                                    }
                                    expDetails.put(auditName, sessList);
                                } catch (Exception e) {
                                    System.out.println("Inner Url/Json error! " + statusCode + " " + responseSessionBody2.toString());
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                super.onFailure(statusCode, headers, responseString, throwable);
                                System.out.println("error" + statusCode + "~~~~" + responseString);
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                super.onFailure(statusCode, headers, throwable, errorResponse);
                                System.out.println("error" + statusCode + "~~~~" + errorResponse);
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                                super.onFailure(statusCode, headers, throwable, errorResponse);
                                System.out.println("error" + statusCode + "~~~~" + errorResponse);
                            }

                        });
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    System.out.println("Outer Url/Json error! " + statusCode + " " + responseSessionBody.toString());
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                System.out.println("error" + statusCode + "~~~~" + responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                System.out.println("error" + statusCode + "~~~~" + errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                System.out.println("error" + statusCode + "~~~~" + errorResponse);
            }
        });
        return expDetails;
    }

    //loading data for settings activity
    public static HashMap<String,Integer> loadAuditData(){
        System.out.println("Loading list.");
        final AsyncHttpClient client_loadAuditData = new AsyncHttpClient();
            client_loadAuditData.setBasicAuth(LoginActivity.getStaticLoginActivity().getUsername(), LoginActivity.getStaticLoginActivity().getPassword());
            client_loadAuditData.setSSLSocketFactory(MySSLSocketFactory.getFixedSocketFactory());
            client_loadAuditData.get(url12, null, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        System.out.println("~~~~");
                        AuditoriumsClass auditoriumsClass = new AuditoriumsClass();
                        for (int i = 0; i < response.length(); i++) {
                            auditoriumsClass = JSONUtils.parseJsonToObject(response.toString(),AuditoriumsClass.class);
                            if(auditoriumsClass.getTheater() == ListData.getTheaterId()){
                                AuditData.put(auditoriumsClass.getAcronym(), auditoriumsClass.getId());
                                System.out.println(AuditData);
                            }
                        }
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        System.out.println("Outer Url/Json error! " + statusCode + " " + response.toString());
                        e.printStackTrace();
                    }
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, final JSONArray responseSessionBody) {
                    try {
                        System.out.println("~~~~");
                        List<AuditoriumsClass> auditClass = new ArrayList<>();
                        for (int i = 0; i < responseSessionBody.length(); i++) {
                            auditClass = JSONUtils.toList(AuditoriumsClass.class, responseSessionBody.toString());
                            if(auditClass.get(i).getTheater() == ListData.getTheaterId()){
                                AuditData.put(auditClass.get(i).getAcronym(), auditClass.get(i).getId());
                            }
                        }
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        System.out.println("Outer Url/Json error! " + statusCode + " " + responseSessionBody.toString());
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                    System.out.println("error" + statusCode + "~~~~" + responseString);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    System.out.println("error" + statusCode + "~~~~" + errorResponse);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    System.out.println("error" + statusCode + "~~~~" + errorResponse);
                }
            });
        return AuditData;
    }


    public static HashMap<Integer,String> loadTheaterInfo(){

        final AsyncHttpClient client_loadTheaterInfo = new AsyncHttpClient();
        client_loadTheaterInfo.setBasicAuth(LoginActivity.getStaticLoginActivity().getUsername(), LoginActivity.getStaticLoginActivity().getPassword());
        client_loadTheaterInfo.setSSLSocketFactory(MySSLSocketFactory.getFixedSocketFactory());
        client_loadTheaterInfo.get(urlTheaterInfo, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    TheaterClass theaterClass = new TheaterClass();
                    for(int i = 0; i < response.length(); i++){
                        theaterClass = JSONUtils.parseJsonToObject(response.toString(),TheaterClass.class);
                        TheaterID_Name.put(theaterClass.getId(),theaterClass.getName());
//                        System.out.println("Theater id name -> " + TheaterID_Name);
                    }
                } catch (Exception e) {
                    System.out.println("Outer Url/Json error! " + statusCode + " " + response.toString());
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, final JSONArray responseSessionBody) {
                try {
                    List<TheaterClass> theaterClasses = new ArrayList<>();
                    for(int i = 0; i < responseSessionBody.length(); i++){
                        theaterClasses = JSONUtils.toList(TheaterClass.class,responseSessionBody.toString());
                        TheaterID_Name.put(theaterClasses.get(i).getId(),theaterClasses.get(i).getName());
                        System.out.println(TheaterID_Name);
                    }
                } catch (Exception e) {
                    System.out.println("Outer Url/Json error! " + statusCode + " " + responseSessionBody.toString());
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                System.out.println("error" + statusCode + "~~~~" + responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                System.out.println("error" + statusCode + "~~~~" + errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                System.out.println("error" + statusCode + "~~~~" + errorResponse);
            }
        });
        return TheaterID_Name;
    }

    public static List<String> loadTheaterColor(){

        final AsyncHttpClient client_loadTheaterInfo = new AsyncHttpClient();
        client_loadTheaterInfo.setBasicAuth(LoginActivity.getStaticLoginActivity().getUsername(), LoginActivity.getStaticLoginActivity().getPassword());
        client_loadTheaterInfo.setSSLSocketFactory(MySSLSocketFactory.getFixedSocketFactory());
        client_loadTheaterInfo.get(urlTheaterInfo, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                System.out.println(response);
                try {
                    TheaterClass theaterClass = new TheaterClass();
                    for(int i = 0; i < response.length(); i++){
                        theaterClass= JSONUtils.parseJsonToObject(response.toString(),TheaterClass.class);
                        TheaterColor.add(theaterClass.getColor());
                    }

                } catch (Exception e) {
                    System.out.println("Outer Url/Json error! " + statusCode + " " + response.toString());
                    e.printStackTrace();
                }

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, final JSONArray responseSessionBody) {
                try {
                    List<TheaterClass> theaterClasses = new ArrayList<>();
                    for(int i = 0; i < responseSessionBody.length(); i++){
                        theaterClasses = JSONUtils.toList(TheaterClass.class,responseSessionBody.toString());
                        TheaterColor.add(theaterClasses.get(i).getColor());
                    }

                } catch (Exception e) {
                    System.out.println("Outer Url/Json error! " + statusCode + " " + responseSessionBody.toString());
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                System.out.println("error" + statusCode + "~~~~" + responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                System.out.println("error" + statusCode + "~~~~" + errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                System.out.println("error" + statusCode + "~~~~" + errorResponse);
            }
        });
        return TheaterColor;
    }

    public static int getTheaterByDEVICE_ID(int device_id){
        System.out.println("Getting theater id");
        final AsyncHttpClient client_theater_device = new AsyncHttpClient();
        client_theater_device.setBasicAuth(LoginActivity.getStaticLoginActivity().getUsername(), LoginActivity.getStaticLoginActivity().getPassword());
        client_theater_device.setSSLSocketFactory(MySSLSocketFactory.getFixedSocketFactory());
        client_theater_device.get(urlConn + device_id, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject responseSessionBody) {
                try {
                    SalespointsClass salespointsClasses = new SalespointsClass();
                    for(int i = 0; i < responseSessionBody.length(); i++){
                        salespointsClasses = JSONUtils.parseJsonToObject(responseSessionBody.toString(),SalespointsClass.class);
                        THEATER_ID = salespointsClasses.getTheater();
//                        System.out.println("got theater id" + THEATER_ID);
                    }
                } catch (Exception e) {
                    System.out.println("Outer Url/Json error! " + statusCode + " " + responseSessionBody.toString());
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                System.out.println("error" + statusCode + "~~~~" + responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                System.out.println("error" + statusCode + "~~~~" + errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                System.out.println("error" + statusCode + "~~~~" + errorResponse);
            }
        });
        System.out.println(THEATER_ID + "~~");
        return THEATER_ID;
    }
}
