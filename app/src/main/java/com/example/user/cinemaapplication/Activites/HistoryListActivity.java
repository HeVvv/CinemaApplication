package com.example.user.cinemaapplication.Activites;

    import android.app.Fragment;
    import android.content.Context;
    import android.os.Bundle;
    import android.os.Handler;
    import android.os.Looper;
    import android.view.KeyEvent;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.Button;
    import android.widget.ListView;
    import android.widget.TextView;

    import com.example.user.cinemaapplication.Adds.FileAdapter;
    import com.example.user.cinemaapplication.Adds.TicketListAdapter;
    import com.example.user.cinemaapplication.R;
    import com.google.zxing.common.StringUtils;

    import java.io.BufferedReader;
    import java.io.File;
    import java.io.FileInputStream;
    import java.io.FileNotFoundException;
    import java.io.IOException;
    import java.io.InputStreamReader;
    import java.util.ArrayList;
    import java.util.Arrays;
    import java.util.HashSet;
    import java.util.List;
    import java.util.Scanner;
    import java.util.Set;
    import java.util.Timer;
    import java.util.TimerTask;

    import github.nisrulz.qreader.QREader;


public class HistoryListActivity extends android.support.v4.app.Fragment{

    private static HistoryListActivity staticHistoryListActivity;

    public static HistoryListActivity getHistoryListActivity() {
        return staticHistoryListActivity;
    }

    public HistoryListActivity() {
        staticHistoryListActivity = this;
    }

    TextView textView;
    Button refresh;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_history,container,false);

        final List<String> ticketList = buildHistoryList(getContext());

        final List<String> emptyList = new ArrayList<>();
        emptyList.add("История пуста.");

        final TicketListAdapter adapter;
        final ListView ticketHistoryList = (ListView) rootView.findViewById(R.id.History);

        adapter = new TicketListAdapter(getActivity(), ticketList);
        ticketHistoryList.setAdapter(adapter);

        final Handler mainHandler = new Handler(Looper.getMainLooper());
        Runnable myRunnable = new Runnable() { // Определяем задачу
            @Override
            public void run() {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        List<String> ticketList = buildHistoryList(getContext());
                        adapter.notifyDataSetChanged();
                        System.out.println("timer tick");
                    }
                });
            }
        };
        mainHandler.post(myRunnable);
        return rootView;
    }


    public List<String> buildHistoryList(Context context){
        String strs = FileAdapter.readFromFile(context.getFilesDir() + "/" + "history.txt");
        List<String> historyList = new ArrayList<String>(Arrays.asList(strs.split("\n")));
        return historyList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
