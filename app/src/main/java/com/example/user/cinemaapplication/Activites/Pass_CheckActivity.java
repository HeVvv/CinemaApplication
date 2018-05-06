package com.example.user.cinemaapplication.Activites;

import android.app.Activity;
import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.cinemaapplication.R;

public class Pass_CheckActivity extends Activity{

    private EditText passEdit;
    private Button okBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_AppCompat);
        setContentView(R.layout.activity_pass_check);

        final String MANAGER_PASS = "456654";

        passEdit = (EditText) findViewById(R.id.editPassSet);
        okBtn = (Button) findViewById(R.id.okBtn);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(passEdit.getText().toString().equals(MANAGER_PASS)){
                    Intent intent = new Intent(Pass_CheckActivity.this, Settings_AboutActivity.class);
                    Toast.makeText(getApplication(),"Пароль верный!",Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                } else{
                    Toast.makeText(getApplication(),"Пароль неверный!",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
