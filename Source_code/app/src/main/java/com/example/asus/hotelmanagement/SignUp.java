package com.example.asus.hotelmanagement;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    EditText editTextFirstname1, editTextLastname1;
    Button buttonNext1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextFirstname1 = (EditText) findViewById(R.id. editTextFirstname);
        editTextLastname1 = (EditText) findViewById(R.id.editTextLastname);
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

        String firstname = editTextFirstname1.getText().toString();
        String lastname = editTextLastname1.getText().toString();
        @Override
        protected String doInBackground(String... params) {
            if (firstname.trim().equals("") || lastname.trim().equals("")) {
                z = "Please enter Firstname and Lastname";
            }

            else if(!firstname.matches("[a-zA-Z]+")){
                z="Enter only alphabetic letters for firstname";

            }
            else if(!lastname.matches("[a-zA-Z]+")){
                    z="Enter only alphabetic letters for lastname";

                }
            else{
                isSuccess = true;
            }

            return z;
        }

        @Override
        protected void onPostExecute(String z) {
            if (isSuccess) {
                Intent intent = new Intent(SignUp.this,SignUpAddress.class);
                intent.putExtra("Firstname",firstname);
                intent.putExtra("Lastname",lastname);
                startActivity(intent);
            }
            else{
                Toast.makeText(SignUp.this, z, Toast.LENGTH_SHORT).show();
            }
        }

    }

}
