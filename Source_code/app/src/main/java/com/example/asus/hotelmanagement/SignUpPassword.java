package com.example.asus.hotelmanagement;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class SignUpPassword extends AppCompatActivity {

    ConnectionClass connectionClass;
    EditText editTextPassword1,editTextRePassword1;
    Button buttonSignUp1;
    String firstname,lastname,birthday,no,street,city,country,gender,phone1,phone2,email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_password);

        firstname = getIntent().getExtras().getString("Firstname");
        lastname = getIntent().getExtras().getString("Lastname");
        //birthday = getIntent().getExtras().getString("Birthday");
        no = getIntent().getExtras().getString("No");
        street = getIntent().getExtras().getString("Street");
        city = getIntent().getExtras().getString("City");
        country = getIntent().getExtras().getString("Country");
        gender = getIntent().getExtras().getString("Gender");
        phone1 = getIntent().getExtras().getString("Phone1");
        phone2 = getIntent().getExtras().getString("Phone2");
        email = getIntent().getExtras().getString("Email");

        editTextPassword1 = (EditText) findViewById(R.id.editTextPassword);
        editTextRePassword1 = (EditText) findViewById(R.id.editTextRePassword);
        buttonSignUp1 = (Button) findViewById(R.id.buttonSignUp);

        buttonSignUp1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                DOSignUp doSignUp = new DOSignUp();//this is the asynctask, which is used to process
                // is background to reduce load on app process
                doSignUp.execute("");
            }

        });
    }

    public class DOSignUp extends AsyncTask<String, String, String> {
        String z = "";
        Boolean isSuccess = false;
        String role = "guest";
        String password = editTextPassword1.getText().toString();
        String password1 = editTextRePassword1.getText().toString();
        long pho1=Long.parseLong(phone1);
        long pho2=Long.parseLong(phone2);



        @Override
        protected void onPreExecute() {
            // pbbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            if (password.trim().equals("") ) {
                z = "Please enter password";
            }
            else if(password1.trim().equals("")){
                z="Please re-enter password";
            }
            else if(password.length()<6 || password.length()>10){
                z="Password must be at least 6  and less than 10 characters";
            }
            else if(!password.equals(password1)){
                z="Both passwords are not equal";
            }
            else {
                try {
                    connectionClass = new ConnectionClass();
                    Connection con = connectionClass.CONN();
                    if (con == null) {

                        z = "Check your Internet Access";
                    } else {
                        String query1 = "insert into [person] values('" + firstname + "','" + lastname + "','" + no + "','" + street + "','" + city + "','" + country + "','" + email + "','" + pho1 + "','" + pho2 + "','"+gender+"' )";
                        String query2 = "insert into [user] values('" + email + "','" + password + "','" + role + "')";
                        PreparedStatement preparedStatement = con.prepareStatement(query1);
                        preparedStatement.executeUpdate();
                        PreparedStatement preparedStatement1 = con.prepareStatement(query2);
                        preparedStatement1.executeUpdate();
                        z="Recorded successfully";
                        isSuccess = true;
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
                Toast.makeText(SignUpPassword.this, z, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignUpPassword.this,Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
                startActivity(intent);
                finish();
            }
            else{
                Toast.makeText(SignUpPassword.this, z, Toast.LENGTH_SHORT).show();
            }
        }

    }
}
