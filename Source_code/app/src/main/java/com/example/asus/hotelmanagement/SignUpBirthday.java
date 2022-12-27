package com.example.asus.hotelmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

public class SignUpBirthday extends AppCompatActivity {

    DatePicker simpleDatePicker;
    Button buttonNext1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_birthday);

        // initiate the date picker and a button
        simpleDatePicker = (DatePicker) findViewById(R.id.simpleDatePicker);
        buttonNext1 = (Button) findViewById(R.id.buttonNext);


        // perform click event on submit button
        buttonNext1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String firstname = getIntent().getExtras().getString("Firstname");
                String lastname = getIntent().getExtras().getString("Lastname");

                // get the values for day of month , month and year from a date picker
                int day = simpleDatePicker.getDayOfMonth();
                int month = simpleDatePicker.getMonth() + 1;
                int year = simpleDatePicker.getYear();

                String birthday = day+"/"+month+"/"+year;

                Intent intent = new Intent(SignUpBirthday.this,SignUpAddress.class);
                intent.putExtra("Birthday",birthday);
                intent.putExtra("Firstname",firstname);
                intent.putExtra("Lastname",lastname);
                startActivity(intent);

            }
        });

    }
}
