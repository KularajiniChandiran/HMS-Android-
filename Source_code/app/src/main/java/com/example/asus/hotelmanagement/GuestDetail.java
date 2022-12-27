package com.example.asus.hotelmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GuestDetail extends AppCompatActivity {

    TextView textViewFirstname1,textViewLastname1,textViewBirthday1,textViewNo1,textViewStreet1,textViewCity1,textViewCountry1,textViewGender1,textViewPhone11,textViewPhone22;
    Button buttonClose1;
    String z;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_detail);

        buttonClose1 = (Button) findViewById(R.id.buttonClose);
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

        textViewFirstname1.setText(getIntent().getExtras().getString("Firstname"));             //set values for all textviews
        textViewLastname1.setText(getIntent().getExtras().getString("Lastname"));
        //textViewBirthday1.setText(getIntent().getExtras().getString("Birthday"));
        textViewNo1.setText(getIntent().getExtras().getString("No"));
        textViewStreet1.setText(getIntent().getExtras().getString("Street"));
        textViewCity1.setText(getIntent().getExtras().getString("City"));
        textViewCountry1.setText(getIntent().getExtras().getString("Country"));
        textViewGender1.setText(getIntent().getExtras().getString("Gender"));
        textViewPhone11.setText(getIntent().getExtras().getString("Phone1"));
        textViewPhone22.setText(getIntent().getExtras().getString("Phone2"));

        buttonClose1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(GuestDetail.this, AdminPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // To clean up all activities
                startActivity(intent);
            }

        });
    }
}
