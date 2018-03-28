package com.example.user.cinemaapplication.Activites;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.cinemaapplication.Adds.JSONUtils;
import com.example.user.cinemaapplication.R;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by User on 24.03.2018.
 */

public class Settings_AboutActivity extends Activity {

    private TextView txt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        setContentView(R.layout.activity_settings_about);

        txt = (TextView) findViewById(R.id.infoCinema);
//        txt.setText(LoginActivity.getStaticLoginActivity().getCINEMA_NAME());
        txt.setText(QRScanActivity.getStaticQRScanActivity().getListData().toString());
        final Button btn = (Button) findViewById(R.id.idbtn);

//        btn.setOnClickListener(new View.OnClickListener(){
//
//            public void onClick(View v){
//                Set<Integer> newset = AuditChoosingActivity.getSettingsActivity().getAuditIDS();
//                txt.setText(newset.toString());
//
//                HashSet<String> strs = new HashSet<String>(newset.size());
//                for(Integer integer : newset)
//                    strs.add(integer.toString());
//                StringBuilder sb = new StringBuilder();
//                for (String s : strs)
//                {
//                    sb.append(s);
//                    sb.append(" ");
//                }
//
//                System.out.println(sb.toString());
//                txt.setText(sb);
//
//            }
//        });

        super.onCreate(savedInstanceState);
    }
}
