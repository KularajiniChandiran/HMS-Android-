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

public class Login extends AppCompatActivity {
    ConnectionClass connectionClass;
    EditText editTextEmail1, editTextPassword1;
    Button buttonLogin1,buttonSignup1;
    String query,query1;
    Statement stmt;
    ResultSet rs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        connectionClass = new ConnectionClass();
        editTextEmail1 = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword1 = (EditText) findViewById(R.id.editTextPassword);
        buttonLogin1 = (Button) findViewById(R.id.buttonLogin);
        buttonSignup1 = (Button) findViewById(R.id.buttonSignup);

        buttonLogin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DOLogin doLogin = new DOLogin();//this is the asynctask, which is used to process
                // is background to reduce load on app process
                doLogin.execute("");
            }

        });


        buttonSignup1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,SignUp.class);
                startActivity(intent);
            }

        });
    }

    public class DOLogin extends AsyncTask<String, String, String> {
        String z = "";
        Boolean isSuccess = false;
        String role;

        String userid = editTextEmail1.getText().toString();
        String password = editTextPassword1.getText().toString();


        @Override
        protected void onPreExecute() {
        }


        @Override
        protected String doInBackground(String... params) {
            if (userid.trim().equals("") || password.trim().equals("")) {
                z = "Please enter Username and Password";
            }
            else if(!userid.matches("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"

                    +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"

                    +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."

                    +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"

                    +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"

                    +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$")){
                z= "Invalid Email";
            }
            else {
                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {

                        z = "Check your Internet Access";
                    }
                    else {
                        query = "select * from [user] where email= '" + userid + "' and password= '" + password + "'";
                        stmt = con.createStatement();
                        rs = stmt.executeQuery(query);

                        if (rs.next()) {
                            z = "Login successful";
                            role = rs.getString("role");
                            isSuccess = true;

                        } else {
                            z = "Invalid credentials";
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
            Toast.makeText(Login.this, z, Toast.LENGTH_SHORT).show();
            if (isSuccess) {
                if(role.equals("guest")){
                    Intent intent = new Intent(Login.this, GuestLogin.class);
                    intent.putExtra("Email",userid);
                    startActivity(intent);
                    finish();
                }
                else if(role.equals("receptionist")){
                    Intent intent1 = new Intent(Login.this,ReceptionistPage.class);
                    startActivity(intent1);
                    finish();
                }
                else{
                    Intent intent1 = new Intent(Login.this,AdminPage.class);
                    startActivity(intent1);
                    finish();
                }
            }

        }



    }
}
