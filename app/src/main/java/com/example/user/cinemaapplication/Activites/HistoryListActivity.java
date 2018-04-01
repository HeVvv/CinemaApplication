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

        final ListView ticketHistoryList = (ListView) rootView.findViewById(R.id.History);
        final TicketListAdapter adapter = new TicketListAdapter(getActivity(), ticketList);
        ticketHistoryList.setAdapter(adapter);
//        textView = (TextView) rootView.findViewById(R.id.textVIEW);
//        refresh = (Button) rootView.findViewById(R.id.refresh);
//        refresh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                System.out.println("reading from file");
//
//                String s = FileAdapter.readFromFile(getContext());
//                textView.setText("");
//                textView.setText(s);
//
//                System.out.println("This is list history -> " + buildHistoryList(getContext()));
//            }
//        });

        //broken
        Timer myTimer = new Timer(); // Создаем таймер
        final Handler uiHandler = new Handler();
        myTimer.schedule(new TimerTask() { // Определяем задачу
            @Override
            public void run() {
                uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        List<String> ticketList = buildHistoryList(getContext());
                        adapter.notifyDataSetChanged();
                        System.out.println("timer tick");
                    }
                });
            }
        }, 0L, 3000 ); //

        //not tested

        return rootView;
    }

    public List<String> buildHistoryList(Context context){

        String strs = FileAdapter.readFromFile(context);
        List<String> historyList = new ArrayList<String>(Arrays.asList(strs.split("\n")));
        return historyList;

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
