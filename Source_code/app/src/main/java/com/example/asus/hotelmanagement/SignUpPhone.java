package com.example.asus.hotelmanagement;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpPhone extends AppCompatActivity {

    EditText editTextphone1,editTextphone2;
    Button buttonNext1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_phone);

        editTextphone1 = (EditText) findViewById(R.id.editTextPhone1);
        editTextphone2 = (EditText) findViewById(R.id.editTextPhone2);
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

        String phone1 = editTextphone1.getText().toString();
        String phone2 = editTextphone2.getText().toString();
        String firstname = getIntent().getExtras().getString("Firstname");
        String lastname = getIntent().getExtras().getString("Lastname");
        String birthday = getIntent().getExtras().getString("Birthday");
        String no = getIntent().getExtras().getString("No");
        String street = getIntent().getExtras().getString("Street");
        String city = getIntent().getExtras().getString("City");
        String country = getIntent().getExtras().getString("Country");
        String gender = getIntent().getExtras().getString("Gender");


        @Override
        protected String doInBackground(String... params) {
            if (phone1.trim().equals("") || phone2.trim().equals("")) {
                z = "Please enter all details";
            }
            else if(phone1.length()<9 || phone1.length()>15){
                z = "Phone No1 length should be between 8 and 16";
            }
            else if(phone2.length()<9 || phone2.length()>15){
                z = "Phone No2 length should be between 8 and 16";
            }
            else{
                isSuccess = true;
            }

            return z;
        }

        @Override
        protected void onPostExecute(String z) {
            if (isSuccess) {
                Intent intent = new Intent(SignUpPhone.this,SignUpEmail.class);
                intent.putExtra("Phone1",phone1);
                intent.putExtra("Phone2",phone2);
                intent.putExtra("Firstname",firstname);
                intent.putExtra("Lastname",lastname);
                //intent.putExtra("Birthday",birthday);
                intent.putExtra("No",no);
                intent.putExtra("Street",street);
                intent.putExtra("City",city);
                intent.putExtra("Country",country);
                intent.putExtra("Gender",gender);
                startActivity(intent);
            }
            else{
                Toast.makeText(SignUpPhone.this, z, Toast.LENGTH_SHORT).show();
            }
        }

    }
}
