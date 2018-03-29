package com.example.user.cinemaapplication.Activites;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.cinemaapplication.R;

public class HistoryListActivity extends android.support.v4.app.Fragment{



    private static HistoryListActivity staticHistoryListActivity;

    public static HistoryListActivity getHistoryListActivity() {
        return staticHistoryListActivity;
    }

    public HistoryListActivity() {
        staticHistoryListActivity = this;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.activity_history,container,false);

        TextView txt = (TextView) rootview.findViewById(R.id.txtxd);
        txt.setText("i dont know");

        return rootview;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
