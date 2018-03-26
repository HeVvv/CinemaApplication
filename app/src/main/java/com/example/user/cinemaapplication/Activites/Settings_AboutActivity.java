package com.example.user.cinemaapplication.Activites;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.example.user.cinemaapplication.R;

/**
 * Created by User on 24.03.2018.
 */

public class Settings_AboutActivity extends Activity {

    private TextView txt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        setContentView(R.layout.activity_settings_about);

        txt = (TextView) findViewById(R.id.infoCinema);
        txt.setText(LoginActivity.getStaticLoginActivity().getCINEMA_NAME());


        super.onCreate(savedInstanceState);
    }
}
