package com.example.user.cinemaapplication.Adds;


import android.app.Application;
import android.util.Log;


import com.example.user.cinemaapplication.Classes.AuditoriumsClass;
import com.example.user.cinemaapplication.Classes.SessionClass;
import com.example.user.cinemaapplication.Activites.LoginActivity;
import com.loopj.android.http.AsyncHttpClient;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.MySSLSocketFactory;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
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

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.impl.client.BasicCookieStore;


public class ListData extends Application {

    private static HashMap<String,Integer> AuditData = new HashMap<>();
    private static HashMap<String, List<String>> expDetails = new HashMap<>();


    private static ListData staticListData;
    public static ListData getStaticListData() {
        return staticListData;
    }
    public ListData() {
        staticListData = this;
    }

    final static String url12 = "https://soft.silverscreen.by:8443/wscinema/webapi/auditoriums";
    final static String url22 = "https://soft.silverscreen.by:8443/wscinema/webapi/show/auditorium/";


    // private static HashMap<String,Integer> AuditData = new HashMap<>();
    //HashMap sorting
    public HashMap<String,Integer> getAuditData(){
        return AuditData;
    }

    public String getUrl12(){
        return url12;
    }

    public HashMap<String,List<String>> getExpDetails(){
        return expDetails;
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
        final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        final AsyncHttpClient client_loadAuditData = new AsyncHttpClient();
            client_loadAuditData.setBasicAuth(LoginActivity.getStaticLoginActivity().getUsername(), LoginActivity.getStaticLoginActivity().getPassword());
            client_loadAuditData.setSSLSocketFactory(MySSLSocketFactory.getFixedSocketFactory());
            client_loadAuditData.get(url12, null, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, final JSONArray responseSessionBody) {
                    try {
                        System.out.println("~~~~");
                        List<AuditoriumsClass> auditClass = new ArrayList<>();
                        for (int i = 0; i < responseSessionBody.length(); i++) {
                            auditClass = JSONUtils.toList(AuditoriumsClass.class, responseSessionBody.toString());
                            AuditData.put(auditClass.get(i).getAcronym(), auditClass.get(i).getId());
                        }
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        System.out.println("Outer Url/Json error! " + statusCode + " " + responseSessionBody.toString());
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    System.out.println("error" + statusCode + "~~~~" + errorResponse);
                }
            });
        return AuditData;
    }
    public static void loadData(final HashMap<String,Integer> data){
        System.out.println("Loading list.");
        final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        final AsyncHttpClient client_loadAuditData = new AsyncHttpClient();
        client_loadAuditData.setBasicAuth(LoginActivity.getStaticLoginActivity().getUsername(), LoginActivity.getStaticLoginActivity().getPassword());
        client_loadAuditData.setSSLSocketFactory(MySSLSocketFactory.getFixedSocketFactory());
        client_loadAuditData.get(url12, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, final JSONArray responseSessionBody) {
                try {
                    System.out.println("~~~~");
                    List<AuditoriumsClass> auditClass = new ArrayList<>();
                    for (int i = 0; i < responseSessionBody.length(); i++) {
                        auditClass = JSONUtils.toList(AuditoriumsClass.class, responseSessionBody.toString());
                        data.put(auditClass.get(i).getAcronym(), auditClass.get(i).getId());
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    System.out.println("Outer Url/Json error! " + statusCode + " " + responseSessionBody.toString());
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                System.out.println("error" + statusCode + "~~~~" + errorResponse);
            }
        });
    }
}

