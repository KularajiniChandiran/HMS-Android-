package com.example.asus.hotelmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewStaff2 extends AppCompatActivity {

    TextView textViewFirstname1,textViewLastname1,textViewBirthday1,textViewNo1,textViewStreet1,textViewCity1,textViewCountry1,textViewGender1,textViewPhone11,textViewPhone22,textViewSalary1,textViewWorkingHours1;
    Button buttonClose1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_staff2);

        buttonClose1=(Button) findViewById(R.id.buttonClose);
        textViewFirstname1 =(TextView) findViewById(R.id.textViewFirstname1);
        textViewLastname1 =(TextView) findViewById(R.id.textViewLastname1);
        //textViewBirthday1 =(TextView) findViewById(R.id.textViewBirthday1);
        textViewNo1 =(TextView) findViewById(R.id.textViewNo1);
        textViewStreet1 =(TextView) findViewById(R.id.textViewStreet1);
        textViewCity1 =(TextView) findViewById(R.id.textViewCity1);
        textViewCountry1 =(TextView) findViewById(R.id.textViewCountry1);
        textViewGender1 =(TextView) findViewById(R.id.textViewGender1);
        textViewPhone11 =(TextView) findViewById(R.id.textViewPhone11);
        textViewPhone22 =(TextView) findViewById(R.id.textViewPhone22);
        textViewSalary1 =(TextView) findViewById(R.id.textViewSalary1);
        textViewWorkingHours1 =(TextView) findViewById(R.id.textViewWorkingHours1);

        textViewFirstname1.setText(getIntent().getExtras().getString("firstname"));
        textViewLastname1.setText(getIntent().getExtras().getString("lastname"));
        //textViewBirthday1.setText(getIntent().getExtras().getString("birthday"));
        textViewNo1.setText(getIntent().getExtras().getString("no"));
        textViewStreet1.setText(getIntent().getExtras().getString("street"));
        textViewCity1.setText(getIntent().getExtras().getString("city"));
        textViewCountry1.setText(getIntent().getExtras().getString("country"));
        textViewGender1.setText(getIntent().getExtras().getString("gender"));
        textViewPhone11.setText(getIntent().getExtras().getString("phone1"));
        textViewPhone22.setText(getIntent().getExtras().getString("phone2"));
        textViewSalary1.setText(getIntent().getExtras().getString("salary"));
        textViewWorkingHours1.setText(getIntent().getExtras().getString("working_hours"));

        buttonClose1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(ViewStaff2.this, StaffEntry.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

        });
    }
}
