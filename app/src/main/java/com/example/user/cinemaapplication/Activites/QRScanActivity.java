package com.example.user.cinemaapplication.Activites;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dlazaro66.qrcodereaderview.QRToViewPointTransformer;
import com.example.user.cinemaapplication.Adds.FileAdapter;
import com.example.user.cinemaapplication.Adds.JSONUtils;
import com.example.user.cinemaapplication.Adds.ListData;
import com.example.user.cinemaapplication.Adds.TicketListAdapter;
import com.example.user.cinemaapplication.Adds.TicketSClass;
import com.example.user.cinemaapplication.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.MySSLSocketFactory;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import github.nisrulz.qreader.QRDataListener;
import github.nisrulz.qreader.QREader;

import static android.content.Context.MODE_PRIVATE;

public class QRScanActivity extends Fragment {

    private SurfaceView mySurfaceView;
    private QREader qrEader;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private String OLD_DATA = "";
    private Date OLD_DATE = Calendar.getInstance().getTime();
    private HashMap<String, Integer> list = ListData.loadAuditData();
    private final static String FILE_NAME = "history.txt";
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
    private TicketListAdapter adapter;
    private List<String> ticketList = new ArrayList<>();
    private ListView ticketHistoryList;
    private int ID_DEVICE;

    TextView text;
    TextView textAdd;
    ImageView responseImage;



    public String check_ticket_url = "https://soft.silverscreen.by:8443/wsglobal/webapi/check/ticket";

    private static QRScanActivity staticQRScanActivity;

    public static QRScanActivity getStaticQRScanActivity() {
        return staticQRScanActivity;
    }

    public QRScanActivity() {
        staticQRScanActivity = this;
    }

    public HashMap<String, Integer> getListData() {
        return list;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = getActivity().getIntent();
                getActivity().finish();
                startActivity(intent);
            } else {
            }
        }
    }

    public QREader getQReader(){
        return qrEader;
    }

    public String getAuditoriumsIDS(){
        Set<Integer> newset = AuditChoosingActivity.getStaticAuditChoosingActivity().getAuditIDS();

        HashSet<String> strs = new HashSet<String>(newset.size());
        for(Integer integer : newset)
            strs.add(integer.toString());
        StringBuilder sb = new StringBuilder();
        String prefix = "";
        for (String s : strs)
        {
            sb.append(prefix);
            prefix = " ";
            sb.append(s);
        }

        System.out.println(sb.toString());
        return sb.toString();
    }

    public List<String> contentString(String s){
        List<String> info = new ArrayList<>();

        StringTokenizer st = new StringTokenizer(s, "|");
        while(st.hasMoreTokens()){
           info.add(st.nextToken());
        }
        return info;
    }

    @Override
    public void onPause() {
        super.onPause();
        qrEader.stop();
    }

    @Override
    public void onResume() {
        super.onResume();
        qrEader.start();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        ticketHistoryList = (ListView) getActivity().findViewById(R.id.ticketHistoryList);
        adapter = new TicketListAdapter(getActivity(), ticketList);
        ticketHistoryList.setAdapter(adapter);


        text = (TextView) getActivity().findViewById(R.id.responseInfo);
        text.setTextAppearance(getActivity(), R.style.TextAppearance_AppCompat_Title);
        text.setGravity(Gravity.CENTER);
        text.setTextColor(Color.rgb(51,51,51));

        responseImage = (ImageView) getActivity().findViewById(R.id.imageView);
        responseImage.setImageDrawable(null);
//        responseImage.setImageResource(R.drawable.ic_launcher_foreground);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        ID_DEVICE = Integer.parseInt((FileAdapter.readFromFile(getContext().getFilesDir()+"/"+ "ID.txt")).trim());

        System.out.println(ID_DEVICE);

        View rootView = inflater.inflate(R.layout.activity_qrscan, container, false);
        final AsyncHttpClient clientTicket = new AsyncHttpClient();
        clientTicket.setBasicAuth(LoginActivity.getStaticLoginActivity().getUsername(), LoginActivity.getStaticLoginActivity().getPassword());
        clientTicket.setSSLSocketFactory(MySSLSocketFactory.getFixedSocketFactory());


        textAdd = (TextView) rootView.findViewById(R.id.responseinfo2);
        textAdd.setTextAppearance(rootView.getContext(),R.style.TextAppearance_AppCompat);
        textAdd.setGravity(Gravity.CENTER);
        textAdd.setTextColor(Color.rgb(51,51,51));

        responseImage = (ImageView) rootView.findViewById(R.id.imageView);
        responseImage.setImageDrawable(null);
//        responseImage.setImageResource(R.drawable.ic_launcher_foreground);

        mySurfaceView = (SurfaceView) rootView.findViewById(R.id.camera);

        //ломается адаптер
        ticketHistoryList = (ListView) rootView.findViewById(R.id.ticketHistoryList);
        adapter = new TicketListAdapter(getActivity(), ticketList);
        ticketHistoryList.setAdapter(adapter);

        if (ContextCompat.checkSelfPermission(rootView.getContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(QRScanActivity.staticQRScanActivity.getActivity(), new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
        }


        qrEader = new QREader.Builder(getActivity(), mySurfaceView, new QRDataListener() {

            @Override
            public void onDetected(final String data) {
                if (data.equals(OLD_DATA) && Calendar.getInstance().getTime().getTime() - OLD_DATE.getTime() < 2500) {
                } else {
                    System.out.println("scanning" + data);
                    OLD_DATA = data;
                    OLD_DATE = Calendar.getInstance().getTime();
                    Handler mainHandler = new Handler(Looper.getMainLooper());
                    Runnable myRunnable = new Runnable() {
                        @Override
                        public void run() {
//                            String datatest = "4172/30800/232/5//11";
//                            final String test = "1/" + QRScanActivity.getStaticQRS    canActivity().getAuditoriumsIDS() + "/" + datatest;

                            final String test = ID_DEVICE + "/" + QRScanActivity.getStaticQRScanActivity().getAuditoriumsIDS() + "/" + data;
                            try {
                                try {
                                    TicketSClass ticketSClass = new TicketSClass(test);

                                String json = JSONUtils.parseObjectToJson(ticketSClass);
                                StringEntity entity = null;
                                try {
                                    entity = new StringEntity(json);
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                                clientTicket.put(getContext(), check_ticket_url, entity, "application/json", new TextHttpResponseHandler() {

                                    @Override
                                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                        System.out.println("error1 " + statusCode + "~~~~" + responseString);
                                        text.setText("Error " + statusCode);
                                    }

                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, final String responseString) {
                                        String status = responseString.substring(0, 1);
                                        final String response = responseString.substring(2, responseString.length());
                                        final List<String> info = contentString(response);
                                        text.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                text.setText(info.get(0));
                                                textAdd.setText(info.get(1));
                                                FileAdapter.writeFile(responseString,getContext());
                                            }
                                        });
                                        ticketList.add(responseString + "|" + dateFormat.format(Calendar.getInstance().getTime()));
                                        adapter.notifyDataSetChanged();
                                        if (status.equals("0")) {
                                            responseImage.setImageResource(R.drawable.cancel);
                                        } else if (status.equals("1")) {
                                            responseImage.setImageResource(R.drawable.accept);
                                        } else if (status.equals("2")) {
                                            responseImage.setImageResource(R.drawable.exclam);
                                        }
                                        if (ticketList.size() > 3) {
                                            ticketList.subList(0, ticketList.size() - 3).clear();
                                        }
                                    }
                                });
                                }catch(NumberFormatException e){
                                    e.printStackTrace();
                                    Toast.makeText(getActivity().getApplicationContext(), "Неверный формат QR кода!",Toast.LENGTH_LONG).show();
                                }
                            }catch (IOException e){
                                e.printStackTrace();
                                System.out.println(e);
                            }
                        }
                    };
                    mainHandler.post(myRunnable);
                }
            }
        }
        ).facing(QREader.BACK_CAM)
                .enableAutofocus(true)
                .height(mySurfaceView.getHeight())
                .width(mySurfaceView.getWidth())
                .build();
        qrEader.initAndStart(mySurfaceView);
        return rootView;
    }
}

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//        setContentView(R.layout.activity_qrscan);
//        final AsyncHttpClient clientSQL = new AsyncHttpClient();
//        clientSQL.setBasicAuth(LoginActivity.getStaticLoginActivity().getUsername(), LoginActivity.getStaticLoginActivity().getPassword());
//        clientSQL.setSSLSocketFactory(MySSLSocketFactory.getFixedSocketFactory());
//
//        final List<String> ticketList = new ArrayList<>();
//
//        text = (TextView) findViewById(R.id.responseInfo);
//        text.setTextAppearance(this,R.style.TextAppearance_AppCompat_Title);
//        text.setGravity(Gravity.CENTER);
//
//        responseImage = (ImageView) findViewById(R.id.imageView);
//        //responseImage.setImageResource(R.drawable.ic_launcher_foreground);
//
//        final ListView ticketHistoryList = (ListView) findViewById(R.id.ticketHistoryList);
//
//        mySurfaceView = (SurfaceView) findViewById(R.id.camera);
//        final TicketListAdapter adapter = new TicketListAdapter(QRScanActivity.this, ticketList);
//        ticketHistoryList.setAdapter(adapter);
//
//        if (ContextCompat.checkSelfPermission(QRScanActivity.this, Manifest.permission.CAMERA)
//                == PackageManager.PERMISSION_DENIED){
//            ActivityCompat.requestPermissions(QRScanActivity.this, new String[] {Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
//        }
//
//
//        qrEader = new QREader.Builder(QRScanActivity.this, mySurfaceView, new QRDataListener() {
//            @Override
//            public void onDetected(final String data) {
//                if(data.equals(OLD_DATA) && Calendar.getInstance().getTime().getTime() - OLD_DATE.getTime() < 3500 ){
//                }else{
//                OLD_DATA = data;
//                OLD_DATE = Calendar.getInstance().getTime();
//                Handler mainHandler = new Handler(Looper.getMainLooper());
//                Runnable myRunnable = new Runnable() {
//                    @Override
//                    public void run() {
////                      String data = "826/27372/203/5//11";
//
//                        String test = "1/5 6" + "/"+ data;
//                            try {
//                                TicketSClass ticketSClass = new TicketSClass(test);
//                                String json = JSONUtils.parseObjectToJson(ticketSClass);
//                                StringEntity entity = null;
//                                try {
//                                    entity = new StringEntity(json);
//                                } catch (UnsupportedEncodingException e) {
//                                    e.printStackTrace();
//                                }
//
//                                clientSQL.put(QRScanActivity.this, check_ticket_url, entity, "application/json", new TextHttpResponseHandler() {
//                                    @Override
//                                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
//                                        String status = responseString.substring(0, 1);
//                                        final String response = responseString.substring(2, responseString.length());
//
//                                        text.post(new Runnable() {
//                                            @Override
//                                            public void run() {
//                                                text.setText(response);
//                                            }
//                                        });
//                                        ticketList.add(response);
//                                        adapter.notifyDataSetChanged();
//                                        if (status.equals("0")) {
//                                            responseImage.setImageResource(R.drawable.response_cancel);
//                                        } else if (status.equals("1")) {
//                                            responseImage.setImageResource(R.drawable.response_ok);
//                                        } else if (status.equals("2")) {
//                                            responseImage.setImageResource(R.drawable.response_qm);
//                                        }
//                                        if (ticketList.size() > 3) {
//                                            ticketList.subList(0, ticketList.size() - 3).clear();
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onFailure(int statusCode, Header[] headers, String errorString, Throwable throwable) {
//                                        System.out.println("error1 " + statusCode + "~~~~" + errorString);
//                                    }
//                                });
//                            }catch (IOException e ){
//                                e.printStackTrace();
//                            }
//                    }
//                };
//                mainHandler.post(myRunnable);
//                }
//            }
//        }
//        ).facing(QREader.BACK_CAM)
//                .enableAutofocus(true)
//                .height(mySurfaceView.getHeight())
//                .width(mySurfaceView.getWidth())
//                .build();
//        qrEader.initAndStart(mySurfaceView);
//        super.onCreate(savedInstanceState);
//    }
//}
    /*@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        //set the main content layout of the Activity
        setContentView(R.layout.activity_qrscan);
        CameraView cameraView = (CameraView)findViewById(R.id.camera);
        cameraView.start();
    }*/


//
//    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        //set the main content layout of the Activity
//        setContentView(R.layout.activity_qrscan);
//        CameraView cameraView = (CameraView)findViewById(R.id.camera);
//        cameraView.start();
//    }
//
//    //product qr code mode
//    public void scanQR(View v) {
//        try {
//            //start the scanning activity from the com.google.zxing.client.android.SCAN intent
//            Intent intent = new Intent(ACTION_SCAN);
//            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
//            startActivityForResult(intent, 0);
//        } catch (ActivityNotFoundException anfe) {
//            //on catch, show the download dialog
//            showDialog(QRScanActivity.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
//        }
//    }
//
//    //alert dialog for downloadDialog
//    private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
//        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
//        downloadDialog.setTitle(title);
//        downloadDialog.setMessage(message);
//        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialogInterface, int i) {
//                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
//                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                try {
//                    act.startActivity(intent);
//                } catch (ActivityNotFoundException anfe) {
//                }
//            }
//        });
//        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialogInterface, int i) {
//            }
//        });
//        return downloadDialog.show();
//    }
//
//    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
//        if (requestCode == 0) {
//            if (resultCode == RESULT_OK) {
//                //get the extras that are returned from the intent
//                // content is -> ?????? hi ivan your ticket is 16661
//                String contents = intent.getStringExtra("SCAN_RESULT");
//                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
//
//                //Toast toast = Toast.makeText(this, "Content:" + contents + " Format:" + format, Toast.LENGTH_LONG);
//                //make id search if needed
//                String ticketID = contents.substring(36);
//                Toast toast = Toast.makeText(this,ticketID,Toast.LENGTH_SHORT);
//                toast.show();
//            }
//        }
//    }




//                            String str = "826/27372/203/5/''/11";
//
//                            ArrayList<String> listpr1 = new ArrayList<>();
//                            listpr1.add("tradingValue");
//                            listpr1.add("idTicketValue");
//                            listpr1.add("showValue");
//                            listpr1.add("rowValue");
//                            listpr1.add("prefixValue");
//                            listpr1.add("seatValue");
//
//                            ArrayList<String> listpr2 = new ArrayList<>();
//                            String[] token = str.split("/");
//                            for(String t : token){
//                                listpr2.add(t);
//                            }
//
//                            Iterator<String> it1 = listpr1.iterator();
//                            Iterator<String> it2 = listpr2.iterator();
//
//                            jsonParams.put("salespointsValues", 1);
//                            jsonParams.put("auditoriumsValue", "1 4 5 6");
//                            while(it1.hasNext() && it2.hasNext()){
//                                if(it2.equals("")){
//                                    jsonParams.put(it1.next(),it2.next().toString());
//                                }else{
//                                    jsonParams.put(it1.next(), Integer.parseInt(it2.next()));
//                                }
//                            }