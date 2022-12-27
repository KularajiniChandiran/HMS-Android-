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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class RemoveStaff extends AppCompatActivity {
    EditText editTextEmail1;
    Button buttonRemove1;
    ConnectionClass connectionClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_staff);

        connectionClass = new ConnectionClass();
        buttonRemove1 = (Button) findViewById(R.id.buttonRemove);
        editTextEmail1 = (EditText) findViewById(R.id.editTextEmail);

        buttonRemove1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DoRemove doRemove = new DoRemove();
                doRemove.execute();
            }

        });
    }

    public class DoRemove extends AsyncTask<String, String, String> {
        String z = "";
        Boolean isSuccess = false;


        String userid = editTextEmail1.getText().toString();


        @Override
        protected void onPreExecute() {

        }


        @Override
        protected String doInBackground(String... params) {                 //validation for input
            if (userid.trim().equals("")) {
                z = "Please enter Email";
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
                        String query1= "select * from [person] where email= '" + userid + "'";          //get id of staff
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query1);
                        if (rs.next()) {
                            String id = rs.getString("id");
                            String query2= "select * from staff where id= '" + id + "'";
                            Statement stmt1 = con.createStatement();
                            ResultSet rs1 = stmt1.executeQuery(query2);
                            if (rs1.next()) {
                                String query3 = "update staff set status='deactive' where id='"+id+"'";
                                PreparedStatement preparedStatement1 = con.prepareStatement(query3);
                                preparedStatement1.executeUpdate();
                                z = "Remove successfully";
                                isSuccess = true;
                            }
                            else{
                                z="No staff to this email";
                            }

                        } else {
                            z = "No staff to this email";
                            isSuccess = false;
                        }
                    }


                } catch (Exception ex) {
                    isSuccess = false;
                    z =ex.getMessage().toString();
                }
            }
            return z;
        }

        @Override
        protected void onPostExecute(String z) {
            //pbbar.setVisibility(View.GONE);
            Toast.makeText(RemoveStaff.this, z, Toast.LENGTH_SHORT).show();
            if (isSuccess) {
                Intent intent = new Intent(RemoveStaff.this,StaffEntry.class);
                startActivity(intent);
            }


        }



    }
}
