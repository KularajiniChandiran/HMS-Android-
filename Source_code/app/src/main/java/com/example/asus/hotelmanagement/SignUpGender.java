package com.example.asus.hotelmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

public class SignUpGender extends AppCompatActivity {

    Spinner spinnerGender1;
    Button buttonNext1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_gender);

        buttonNext1 = (Button) findViewById(R.id.buttonNext);
        spinnerGender1 = (Spinner) findViewById(R.id.spinnerGender);

        buttonNext1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String firstname = getIntent().getExtras().getString("Firstname");
                String lastname = getIntent().getExtras().getString("Lastname");
                String no = getIntent().getExtras().getString("No");
                String street = getIntent().getExtras().getString("Street");
                String city = getIntent().getExtras().getString("City");
                String country = getIntent().getExtras().getString("Country");
                //String birthday = getIntent().getExtras().getString("Birthday");

                String gender = spinnerGender1.getSelectedItem().toString();

                Intent intent = new Intent(SignUpGender.this,SignUpPhone.class);
                intent.putExtra("Gender",gender);
                intent.putExtra("Firstname",firstname);
                intent.putExtra("Lastname",lastname);
                intent.putExtra("No",no);
                intent.putExtra("Street",street);
                intent.putExtra("City",city);
                intent.putExtra("Country",country);
                //intent.putExtra("Birthday",birthday);
                startActivity(intent);

            }
        });
    }
}
