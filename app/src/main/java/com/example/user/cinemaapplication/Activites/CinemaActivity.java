package com.example.user.cinemaapplication.Activites;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by User on 11.10.2017.
 */

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.example.user.cinemaapplication.Adds.ListAdapter;
import com.example.user.cinemaapplication.Adds.ListData;
import com.example.user.cinemaapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CinemaActivity extends Activity {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    ExpandableListView expListView;
    ExpandableListAdapter expListAdapter;

    List<String> expListTitle;
    HashMap<String, List<String>> expListDetail;
    private static CinemaActivity staticCinemaActivity;
    public static CinemaActivity getStaticCinemaActivity(){
        return staticCinemaActivity;
    }
    public CinemaActivity(){
        staticCinemaActivity = this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.cinema_activity);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Отменяем анимацию обновления
                        mSwipeRefreshLayout.setRefreshing(false);
                        expListView = (ExpandableListView) findViewById(R.id.expListView);
                        expListDetail = ListData.loadListData();
                        expListTitle = new ArrayList<>(expListDetail.keySet());
                        expListAdapter = new ListAdapter(getApplicationContext(), expListTitle,expListDetail);

                        expListView.setAdapter(expListAdapter);
                        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

                            @Override
                            public void onGroupExpand(int groupPosition) {
                            }
                        });

                        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

                            @Override
                            public void onGroupCollapse(int groupPosition) {
                            }
                        });
                        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                            @Override
                            public boolean onChildClick(ExpandableListView parent, View v,
                                                        int groupPosition, int childPosition, long id) {

                                //properly parse string
                                String unparsed = expListDetail.get(expListTitle.get(groupPosition)).get(childPosition);
                                String forwardedID = unparsed.substring(unparsed.length()-3);
                                String sessionInfo = unparsed.substring(0,unparsed.length()-4);

                                Intent intent = new Intent(CinemaActivity.this, TicketActivity.class);
                                intent.putExtra("INFO",sessionInfo);
                                intent.putExtra("ID",forwardedID);
                                startActivity(intent);
                                return false;
                            }
                        });
                    }
                }, 4000);
            }
        });

        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }


}