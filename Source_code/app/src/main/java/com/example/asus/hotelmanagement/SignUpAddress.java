package com.example.asus.hotelmanagement;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpAddress extends AppCompatActivity {

    EditText editTextNo1,editTextStreet1,editTextCity1,editTextCountry1;
    Button buttonNext1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_address);

        editTextNo1 = (EditText) findViewById(R.id.editTextNo);
        editTextStreet1 = (EditText) findViewById(R.id.editTextStreet);
        editTextCity1 = (EditText) findViewById(R.id.editTextCity);
        editTextCountry1 = (EditText) findViewById(R.id.editTextCountry);

        buttonNext1 = (Button) findViewById(R.id.buttonNext);

        buttonNext1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DONext doNext = new DONext();//this is the asynctask, which is used to process
                // is background to reduce load on app process
                doNext.execute("");
            }

        });
    }

    public class DONext extends AsyncTask<String, String, String> {
        String z = "";
        Boolean isSuccess = false;

        String no= editTextNo1.getText().toString();
        String street = editTextStreet1.getText().toString();
        String city = editTextCity1.getText().toString();
        String country = editTextCountry1.getText().toString();


        String firstname = getIntent().getExtras().getString("Firstname");
        String lastname = getIntent().getExtras().getString("Lastname");
        //String birthday = getIntent().getExtras().getString("Birthday");

        @Override
        protected String doInBackground(String... params) {
            if (no.trim().equals("") || street.trim().equals("") || city.trim().equals("") || country.trim().equals("")) {
                z = "Please enter all details";
            }

            else if(!no.matches("[0-9]*\\/*[0-9]*")){
                z="Enter correct value for No(format 43 or 33/8)";
            }
            else if(!street.matches("[a-zA-Z]+"))
            {
                z="Enter only alphabetic letters for street";
            }
            else if(!city.matches("[a-zA-Z]+"))
            {
                z="Enter only alphabetic letters for city";
            }
            else if(!country.matches("[a-zA-Z]+"))
            {
                z="Enter only alphabetic letters for country";
            }
            else{
                isSuccess = true;
            }

            return z;
        }

        @Override
        protected void onPostExecute(String z) {
            if (isSuccess) {
                Intent intent = new Intent(SignUpAddress.this,SignUpGender.class);
                intent.putExtra("No",no);
                intent.putExtra("Street",street);
                intent.putExtra("City",city);
                intent.putExtra("Country",country);
                intent.putExtra("Firstname",firstname);
                intent.putExtra("Lastname",lastname);
                //intent.putExtra("Birthday",birthday);
                startActivity(intent);
            }
            else{
                Toast.makeText(SignUpAddress.this, z, Toast.LENGTH_SHORT).show();
            }
        }

    }
}
