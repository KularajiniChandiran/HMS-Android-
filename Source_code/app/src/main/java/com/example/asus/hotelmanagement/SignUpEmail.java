package com.example.asus.hotelmanagement;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class SignUpEmail extends AppCompatActivity {

    EditText editTextEmail1;
    Button buttonNext1;
    ConnectionClass connectionClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_email);

        editTextEmail1 = (EditText) findViewById(R.id.editTextEmail);
        buttonNext1 = (Button) findViewById(R.id.buttonNext);
        connectionClass = new ConnectionClass();

        buttonNext1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DONext doNext = new DONext();//this is the asynctask, which is used to process
                // is background to reduce load on app process
                doNext.execute("");
            }

        });
    }

    public class DONext extends AsyncTask<String, String, String> {
        String z = "";
        Boolean isSuccess = false;

        String firstname = getIntent().getExtras().getString("Firstname");
        String lastname = getIntent().getExtras().getString("Lastname");
        String birthday = getIntent().getExtras().getString("Birthday");
        String no = getIntent().getExtras().getString("No");
        String street = getIntent().getExtras().getString("Street");
        String city = getIntent().getExtras().getString("City");
        String country = getIntent().getExtras().getString("Country");
        String gender = getIntent().getExtras().getString("Gender");
        String phone1 = getIntent().getExtras().getString("Phone1");
        String phone2 = getIntent().getExtras().getString("Phone2");

        String email =  editTextEmail1.getText().toString();

        @Override
        protected String doInBackground(String... params) {
            if (email.trim().equals("")) {
                z = "Please enter email";
            }

            else if(!email.matches("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"

                    +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"

                    +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."

                    +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"

                    +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"

                    +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$")){
                z= "Invalid Email";
            }

            else{
                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {

                        z = "Check your Internet Access";
                    }
                    else {
                        String query = "select * from [user] where email= '" + email + "'";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);

                        if (rs.next()==false) {
                            isSuccess = true;

                        } else {
                            z = "Already have account to this email";
                            isSuccess = false;
                        }


                    }


                } catch (Exception ex) {
                    isSuccess = false;
                    z = ex.getMessage().toString();
                }


            }

            return z;
        }

        @Override
        protected void onPostExecute(String z) {
            if (isSuccess) {
                Intent intent = new Intent(SignUpEmail.this,SignUpPassword.class);
                intent.putExtra("Email",email);
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
                Toast.makeText(SignUpEmail.this, z, Toast.LENGTH_SHORT).show();
            }
        }

    }
}
