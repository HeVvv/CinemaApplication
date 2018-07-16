package com.example.user.cinemaapplication.Activites;



import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.CompoundButtonCompat;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.cinemaapplication.Adds.HashMapSort;
import com.example.user.cinemaapplication.Adds.ListData;
import com.example.user.cinemaapplication.R;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AuditChoosingActivity extends Fragment {



    // test swiperefreshLayout

    private static String auditIDs = "";
    private int XD = 0;
    private HashMap<String,Integer> listInfo1 = new HashMap<String,Integer>();
    private Set<Integer> auditIDS = new HashSet<>();
    private int MY_CAMERA_REQUEST_CODE = 102;

    private static AuditChoosingActivity staticAuditChoosingActivity;
    public static AuditChoosingActivity getStaticAuditChoosingActivity(){
        return staticAuditChoosingActivity;
    }
    public AuditChoosingActivity(){
        staticAuditChoosingActivity = this;
    }


    public Set<Integer> getAuditIDS(){ return auditIDS;}

    public static String parseString(String input) {
        char inp[] = input.toCharArray();
        char reg1 = ',';
        char req2 = ' ';
        String returnString = "";
        for (int i = 1; i < inp.length - 1; i++) {
            if ((inp[i] != reg1) && (inp[i] != req2)) {
                returnString += inp[i] + " ";
            }
        }
        auditIDs = returnString;
        return returnString;
    }

    public static String parseIntString(Integer x) {
        String returnString = "";
        returnString = x + "";
        return returnString;
    }

    private static HashMap sortByValues(HashMap map) {
        List list = new LinkedList(map.entrySet());
        // Defined Custom Comparator here
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o1)).getValue())
                        .compareTo(((Map.Entry) (o2)).getValue());
            }
        });

        // Here I am copying the sorted list in HashMap
        // using LinkedHashMap to preserve the insertion order
        HashMap sortedHashMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }
        return sortedHashMap;
    }

    private void setCheckBoxColor(CheckBox checkBox, int checkedColor, int uncheckedColor) {
        int states[][] = {{android.R.attr.state_checked}, {}};
        int colors[] = {checkedColor, uncheckedColor};
        CompoundButtonCompat.setButtonTintList(checkBox, new
                ColorStateList(states, colors));
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(AuditChoosingActivity.getStaticAuditChoosingActivity().getContext(),"permission granted", Toast.LENGTH_SHORT).show();
                Intent intent = getActivity().getIntent();
                getActivity().finish();
                startActivity(intent);
                getActivity().recreate();
            } else {
                Toast.makeText(AuditChoosingActivity.getStaticAuditChoosingActivity().getContext(),"permission not granted", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private int fetchAccentColor() {
        TypedValue typedValue = new TypedValue();
        TypedArray a = getContext().obtainStyledAttributes(typedValue.data, new int[] { R.attr.colorAccent });
        int color = a.getColor(0, 0);
        a.recycle();
        return color;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_auditch, container, false);

        if (ContextCompat.checkSelfPermission(rootView.getContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
        }

        HashMap <String,Integer> unsorted = SplashActivity.getStaticSplashActivity().getDATA();

        Map <String,Integer> listinfo = HashMapSort.sortByComparator(unsorted,true);

        LinearLayout ll = (LinearLayout) rootView.findViewById(R.id.checkBoxField);
        ll.setPadding(5,5,5,5);


        for (Map.Entry entry : listinfo.entrySet()) {
            final CheckBox ch = new CheckBox(rootView.getContext());

            ch.setText(entry.getKey().toString());
            ch.setId(Integer.parseInt(entry.getValue().toString()));
            ch.setBackgroundColor(Color.argb(55,51,51,51));
            setCheckBoxColor(ch,fetchAccentColor(),Color.WHITE);
            ch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                    if(!isChecked){
                        System.out.println(ch.getId() + "Id not checked");
                        auditIDS.remove(ch.getId());
                    }
                    else
                    {
                        System.out.println(ch.getId() + "Id is checked");
                        auditIDS.add(ch.getId());
                    }
                }
            });
            ll.addView(ch);
        }
        return rootView;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}