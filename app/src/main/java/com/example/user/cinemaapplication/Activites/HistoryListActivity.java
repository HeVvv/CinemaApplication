package com.example.user.cinemaapplication.Activites;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.user.cinemaapplication.Adds.TicketListAdapter;
import com.example.user.cinemaapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by User on 28.02.2018.
 */

public class HistoryListActivity extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        TextView txt = (TextView) getView().findViewById(R.id.txtxd);
        txt.setText("i dont know");
        return inflater.inflate(R.layout.activity_history,container,false);
    }
}
