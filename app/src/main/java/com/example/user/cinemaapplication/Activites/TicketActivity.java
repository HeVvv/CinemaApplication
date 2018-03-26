package com.example.user.cinemaapplication.Activites;

import android.content.Intent;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class TicketActivity extends AppCompatActivity {


    private Button onSearchButton;
    private Button onQRScanButton;

    private ListView ticketListView;

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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.action_settings:
                Intent intentSettings = new Intent(this, AuditChoosingActivity.class);
                startActivity(intentSettings);
                return true;
            case R.id.action_qrScan:
                Intent intentQR = new Intent(this, QRScanActivity.class);
                startActivity(intentQR);
                return true;
            case R.id.Log_off:
                Intent intentLogOff = new Intent(this, LoginActivity.class);
                startActivity(intentLogOff);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticket_activity);

        TextView objText = (TextView)findViewById(R.id.sessionInfo);
        final String ticketUrls = "https://soft.silverscreen.by:8443/wsticket/webapi/info/show/";
        final String ticketUrle = "/tickets";
        final List<String> ticketList = new ArrayList<String>();

        Bundle extras = getIntent().getExtras();
        Intent intent = getIntent();
        ListView listView = (ListView)findViewById(R.id.ticketListView);

        final String ID = extras.getString("ID");
        final String INFO = extras.getString("INFO");

        objText.setText(INFO);
        //List view object with tickets -->>
        AsyncHttpClient client = new AsyncHttpClient();
        client.setSSLSocketFactory(MySSLSocketFactory.getFixedSocketFactory());
        client.get(ticketUrls + ID + ticketUrle,null,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    List<TicketClass> ticketClass = JSONUtils.toList(TicketClass.class, response.toString());
                    for(int i = 0; i < response.length();i++){
                        ticketList.add("[" + ticketClass.get(i).getId() + "]"
                                        + " row: " + ticketClass.get(i).getRow()
                                        + " seat: " + ticketClass.get(i).getSeat()
                                        + " status: " + ticketClass.get(i).getStatus()
                                );
                    }
                    System.out.println("Ticket list -> " + ticketList.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                System.out.println("Error ~~~ " + statusCode + " " + errorResponse);
            }
        });
        TicketListAdapter adapter = new TicketListAdapter(this,ticketList);
        //setListAdapter(new TicketListAdapter(this, ticketList));
        listView.setAdapter(adapter);
    }
    protected void setOnQRScanButtonClick(View view){
        Intent intent = new Intent(this, QRScanActivity.class);
        startActivity(intent);
    }
}






