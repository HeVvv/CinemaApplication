package com.example.user.cinemaapplication.Activites;

    import android.app.Fragment;
import android.os.Bundle;
    import android.os.Handler;
    import android.os.Looper;
    import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

    import com.example.user.cinemaapplication.Adds.FileAdapter;
    import com.example.user.cinemaapplication.Adds.TicketListAdapter;
import com.example.user.cinemaapplication.R;

    import java.io.BufferedReader;
    import java.io.File;
    import java.io.FileInputStream;
    import java.io.FileNotFoundException;
    import java.io.IOException;
    import java.io.InputStreamReader;
    import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
    import java.util.Timer;
    import java.util.TimerTask;

public class HistoryListActivity extends android.support.v4.app.Fragment{

    private static HistoryListActivity staticHistoryListActivity;

    public static HistoryListActivity getHistoryListActivity() {
        return staticHistoryListActivity;
    }

    public HistoryListActivity() {
        staticHistoryListActivity = this;
    }

    TextView textView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.activity_history,container,false);

        textView = (TextView) rootview.findViewById(R.id.textVIEW);

        //broken
        Timer myTimer = new Timer(); // Создаем таймер
        final Handler uiHandler = new Handler();
        myTimer.schedule(new TimerTask() { // Определяем задачу
            @Override
            public void run() {
                uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(FileAdapter.readFile(getContext()));
                    }
                });
            };
        }, 0L, 2000 ); //




        List<String> ticketList = new ArrayList<>();

        return rootview;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
