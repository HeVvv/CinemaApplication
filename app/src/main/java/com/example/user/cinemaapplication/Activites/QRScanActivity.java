package com.example.user.cinemaapplication.Activites;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.cinemaapplication.Adds.FileAdapter;
import com.example.user.cinemaapplication.Adds.JSONUtils;
import com.example.user.cinemaapplication.Adds.ListData;
import com.example.user.cinemaapplication.Adds.TicketListAdapter;
import com.example.user.cinemaapplication.Adds.TicketSClass;
import com.example.user.cinemaapplication.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.MySSLSocketFactory;
import com.loopj.android.http.TextHttpResponseHandler;
import com.mysql.fabric.xmlrpc.base.Param;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.Flash;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Parameter;
import java.security.Policy;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import github.nisrulz.qreader.QRDataListener;
import github.nisrulz.qreader.QREader;

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
    private TextView glassesCount;
    private boolean isFlashOn;
    private boolean hasFlash;
    private Camera camera;
    private Camera.Parameters params;


    private Context mContext;
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
                getActivity().recreate();
            } else {
            }
        }
    }


    private void getCamera() {
        if (camera == null) {
            try {
                camera = Camera.open();
                params = camera.getParameters();
            } catch (RuntimeException e) {
            }
        }
    }

    private void turnOnFlash() {
        if (!isFlashOn) {
            if (camera == null || params == null) {
                return;
            }
            System.out.println("on");
            if(params != null) {
                params = camera.getParameters();
            }
            params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(params);
            camera.startPreview();
            isFlashOn = true;
        }

    }
    private void turnOffFlash() {
        if (isFlashOn) {
            if (camera == null || params == null) {
                return;
            }
            System.out.println("off");
            if(params != null) {
                params = camera.getParameters();
            }
            params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(params);
            camera.stopPreview();
            isFlashOn = false;
        }
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

        glassesCount = (TextView) getActivity().findViewById(R.id.glassesCount);
        text = (TextView) getActivity().findViewById(R.id.responseInfo);
        text.setTextAppearance(getActivity(), R.style.TextAppearance_AppCompat_Title);
        text.setGravity(Gravity.CENTER);
        text.setTextColor(Color.rgb(51,51,51));

        responseImage = (ImageView) getActivity().findViewById(R.id.imageView);
        responseImage.setImageDrawable(null);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        camera = Camera.open();
//        params =
//        CameraView cameraView = new CameraView(mContext);


        mContext = getContext();
        final View rootView = inflater.inflate(R.layout.activity_qrscan, container, false);
        final AsyncHttpClient clientTicket = new AsyncHttpClient();
        clientTicket.setBasicAuth(LoginActivity.getStaticLoginActivity().getUsername(), LoginActivity.getStaticLoginActivity().getPassword());
        clientTicket.setSSLSocketFactory(MySSLSocketFactory.getFixedSocketFactory());

        if (ContextCompat.checkSelfPermission(rootView.getContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(QRScanActivity.staticQRScanActivity.getActivity(), new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
        }

        try {
            ID_DEVICE = Integer.parseInt((FileAdapter.readFromFile(getContext().getFilesDir() + "/" + "ID.txt")).trim());
        }catch (NumberFormatException e){
            e.printStackTrace();
            Toast.makeText(rootView.getContext(),"ID_DEVICE not set!",Toast.LENGTH_SHORT).show();
            startActivity(getActivity().getIntent());
        }

        final CameraView cameraView = (CameraView) rootView.findViewById(R.id.cameraView);

        textAdd = (TextView) rootView.findViewById(R.id.responseinfo2);
        textAdd.setTextAppearance(rootView.getContext(),R.style.TextAppearance_AppCompat);
        textAdd.setGravity(Gravity.CENTER);
        textAdd.setTextColor(Color.rgb(51,51,51));
        glassesCount = (TextView) getActivity().findViewById(R.id.glassesCount);

        responseImage = (ImageView) rootView.findViewById(R.id.imageView);
        responseImage.setImageDrawable(null);

        mySurfaceView = (SurfaceView) rootView.findViewById(R.id.camera);
        SurfaceHolder previewHolder = mySurfaceView.getHolder();

        ticketHistoryList = (ListView) rootView.findViewById(R.id.ticketHistoryList);
        adapter = new TicketListAdapter(getActivity(), ticketList);
        ticketHistoryList.setAdapter(adapter);

//        Button btnSwitch = (Button) rootView.findViewById(R.id.btnSwitch);
//        btnSwitch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (cameraView.getFlash() == Flash.ON) {
//                    cameraView.setFlash(Flash.OFF);
//                    System.out.println("Turning off flash");
////                    turnOffFlash();
//                } else {
//                    cameraView.setFlash(Flash.ON);
//                    System.out.println("Turning on flash");
////                    turnOnFlash();
//                }
//            }
//        });

        final File historyfile = new File(getContext().getFilesDir() + "/history.txt");

        qrEader = new QREader.Builder(getActivity(), mySurfaceView, new QRDataListener() {
            @Override
            public void onDetected(final String data) {
                if( (  (data.equals(OLD_DATA)) && (Calendar.getInstance().getTime().getTime() - OLD_DATE.getTime() < 2500)  ) ){

                } else {
                    System.out.println("scanning" + data);
                    OLD_DATA = data;
                    OLD_DATE = Calendar.getInstance().getTime();
                    Handler mainHandler = new Handler(Looper.getMainLooper());
                    Runnable myRunnable = new Runnable() {
                        @Override
                        public void run() {

                            Vibrator v = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);

                            // Vibrate for 500 milliseconds
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                v.vibrate(VibrationEffect.createOneShot(500,VibrationEffect.DEFAULT_AMPLITUDE));
                            }else{
                                //deprecated in API 26
                                v.vibrate(200);
                            }
//                            String datatest = "4172/30800/232/5//11";
//                            final String test = "1/" + QRScanActivity.getStaticQRScanActivity().getAuditoriumsIDS() + "/" + datatest;
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
                                        System.out.println("response");
                                        final String appended = responseString.concat(" ");
                                        System.out.println(appended);
                                        String status = appended.substring(0, 1);
                                        final String response = appended.substring(2, appended.length());
                                        final List<String> info = contentString(response);

                                        text.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                text.setText(info.get(0));
                                                text.setTextSize(30);
                                                textAdd.setText(info.get(1));
                                                textAdd.setTextSize(18);
                                                FileAdapter.writeFile(appended,historyfile);
                                                Typeface type = Typeface.createFromAsset(getActivity().getAssets(),"Fontfabric - UniNeueBlack.otf");
                                                glassesCount.setTypeface(type);
                                                int color = Color.argb(1,251,251,251);
                                                glassesCount.setTextColor(color);
                                                glassesCount.setTextSize(125);
                                                glassesCount.setText(info.get(2));
//                                                glassesCount.setText("3");
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
                                        } else if(status.equals("3")){
                                            responseImage.setImageResource(R.drawable.glasses);
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
