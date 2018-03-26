package com.example.user.cinemaapplication.Adds;


import android.app.Application;
import android.net.Uri;
import android.util.Log;


import com.example.user.cinemaapplication.Classes.AuditoriumsClass;
import com.example.user.cinemaapplication.Classes.SessionClass;
import com.example.user.cinemaapplication.Activites.LoginActivity;
import com.google.gson.Gson;
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

        // dont use, not testsed
    public static String executePost(String targetURL, String urlParameters) {
        HttpURLConnection connection = null;

        try {
            //Create connection
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            connection.setRequestProperty("Content-Length",
                    Integer.toString(urlParameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches(false);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.close();

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}

//
//    HttpResponse response = null;
//        try {
//                HttpClient client = new DefaultHttpClient();
//                HttpGet request = new HttpGet();
//                request.setURI(new URI(uri));
//                request.addHeader("Authorisation", "Basic + ");
//                response = client.execute(request);
//                } catch (URISyntaxException e) {
//                e.printStackTrace();
//                } catch (ClientProtocolException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//                } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//                }
//                return response.getEntity().getContent();;

