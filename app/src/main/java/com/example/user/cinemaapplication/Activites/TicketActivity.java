package com.example.user.cinemaapplication.Activites;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.user.cinemaapplication.Adds.JSONUtils;
import com.example.user.cinemaapplication.Adds.TicketListAdapter;
import com.example.user.cinemaapplication.Classes.TicketClass;
import com.example.user.cinemaapplication.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.MySSLSocketFactory;

import org.json.JSONArray;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class TicketActivity extends AppCompatActivity {


    private Button onSearchButton;
    private Button onQRScanButton;

    private ListView ticketListView;
    private TextView test;

    private static TicketActivity staticTicketActivity;
    public static TicketActivity getStaticTicketActivity(){
        return staticTicketActivity;
    }
    public TicketActivity(){
        staticTicketActivity= this;
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cinema_activity, menu);
        return true;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppThemeArena);
        setContentView(R.layout.ticket_activity);
        test = (TextView) findViewById(R.id.testTEXT);
        test.setText("333");
    }

}






