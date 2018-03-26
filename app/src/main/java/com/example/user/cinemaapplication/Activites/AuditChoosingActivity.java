package com.example.user.cinemaapplication.Activites;


import android.app.Activity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.user.cinemaapplication.Adds.ListData;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class AuditChoosingActivity extends Activity {
    private static String auditIDs = "";
    private int XD = 0;
    private static AuditChoosingActivity staticAuditChoosingActivity;
    public static AuditChoosingActivity getSettingsActivity(){
        return staticAuditChoosingActivity;
    }
    public AuditChoosingActivity(){
        staticAuditChoosingActivity = this;
    }
    public String getIDs(){
        return auditIDs;
    }



    public static String parseString(String input){
        char inp[] = input.toCharArray();
        char reg1 = ',';
        char req2 = ' ';
        String returnString = "";
        for(int i = 1; i < inp.length-1;i++){
            if((inp[i] != reg1) && (inp[i] != req2)){
                returnString += inp[i] + " ";
            }
        }
        auditIDs = returnString;
        return returnString;
    }
    public static String parseIntString(Integer x){
        String returnString = "";
        returnString = x + "";
        return returnString;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        final Set<Integer> checkedAuditNames = new HashSet<>();
        final HashMap<String,Integer> auditNames = ListData.loadAuditData();

        ScrollView sv = new ScrollView(AuditChoosingActivity.this);
        final LinearLayout ll1 = new LinearLayout(AuditChoosingActivity.this);

        ll1.setOrientation(LinearLayout.VERTICAL);
        sv.addView(ll1);

        final TextView tv = new TextView(this);
        tv.setText(auditIDs);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,20 );
        ll1.addView(tv);

        final Button b = new Button(this);
        b.setText("Выберите аудитории для проверки.");
        ll1.addView(b);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                b.setEnabled(false);
                for (Map.Entry entry : auditNames.entrySet()) {

                    final CheckBox ch = new CheckBox(AuditChoosingActivity.this);
                    ch.setText(entry.getKey().toString());
                    ch.setId(Integer.parseInt(entry.getValue().toString()));
                    if(XD!= 0) {
                        for(int i = 0; i < auditNames.size();i++){
                            if (auditIDs.contains(parseIntString(ch.getId()))) {
                                ch.setChecked(true);
                            }
                        }
                    }
                    ll1.addView(ch);
                    ch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                    {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                            if(!isChecked){
                                checkedAuditNames.remove(ch.getId());
                                String auditIDs = AuditChoosingActivity.parseString(checkedAuditNames.toString());
                                tv.setText(auditIDs);
//                                Toast.makeText(getApplicationContext(), "Убран " + ch.getId(),
//                                        Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                checkedAuditNames.add(ch.getId());
                                String auditIDs = AuditChoosingActivity.parseString(checkedAuditNames.toString());
                                tv.setText(auditIDs);
//                                Toast.makeText(getApplicationContext(), "Добавлен" + ch.getId(),
//                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }

                XD++;

                final Button chbtn = new Button(AuditChoosingActivity.this);
                chbtn.setText("Подтвердить.");
                ll1.addView(chbtn);
                chbtn.setStateListAnimator(null);
                chbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ll1.removeViewsInLayout(2,auditNames.size()+1);
                        System.out.println();
                        checkedAuditNames.clear();
                        b.setEnabled(true);
                    }
                });
            }
        });
        this.setContentView(sv);
    }
}
/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auditch);

        List<Integer> checkBoxIdList = new ArrayList<Integer>();
        int id = 0;
        String[] list2 = {"A", "B", "C", "D", "E"};

        ScrollView sv = new ScrollView(this);
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        sv.addView(ll);

        TextView tv = new TextView(this);
        tv.setText("CatchInfo");
        tv.setTextColor(Color.rgb(200,0,0));
        ll.addView(tv);
        for(int i=0;i<list2.size();i++)
        {
            ck = new CheckBox(this);
            ck.setId(id);
            checkBoxIdList.add(id);
            ck.setTag(list2[i]); // set the tag values so that you can refer to them later.
            ll.addView(ck);
            ck.setOnCheckedChangeListener(handleCheck(ck));  //Calling the function, add this line in your code
        }
        Button btnHome = new Button(this);
        btnHome.setText("Home");
        ll.addView(btnHome);
        Button btnSubmit = new Button(this);
        btnSubmit.setText("Submit");
        ll.addView(btnSubmit);
        this.setContentView(sv);
    }
}   */


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_auditch);
//        setTitle("CheckBox");
//
//        mMessageEditText = (EditText) findViewById(R.id.editText);
//        mColorCheckBox = (CheckBox)findViewById(R.id.checkBoxColor);
//        mBoldCheckBox = (CheckBox)findViewById(R.id.checkBoxBold);
//
//        Button mCrowsCounterButton = (Button) findViewById(R.id.buttonCounter);
//        mCrowsCounterButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(mColorCheckBox.isChecked())
//                    mMessageEditText.setTextColor(Color.RED);
//                else
//                    mMessageEditText.setTextColor(Color.BLACK);
//
//                if(mBoldCheckBox.isChecked())
//                    mMessageEditText.setTypeface(Typeface.DEFAULT_BOLD);
//                else
//                    mMessageEditText.setTypeface(Typeface.DEFAULT);
//
//                mMessageEditText.setText("Я насчитал " + ++mCount + " ворон");
//            }
//        });
//    }
//}