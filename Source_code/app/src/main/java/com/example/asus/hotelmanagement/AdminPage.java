package com.example.asus.hotelmanagement;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class AdminPage extends AppCompatActivity {

    EditText editTextEmail1;
    Button buttonSearch1,buttonStaff1,buttonLogout1,buttonRoom1,buttonMessage1;
    String firstname,lastname,no,street,city,country,phone1,phone2,gender,birthday;
    ConnectionClass connectionClass;
    TextView textViewMessagecount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        connectionClass = new ConnectionClass();
        editTextEmail1 = (EditText) findViewById(R.id. editTextEmail);
        buttonSearch1 = (Button) findViewById(R.id.buttonSearch);
        buttonStaff1 = (Button) findViewById(R.id.buttonStaff);
        buttonLogout1 = (Button) findViewById(R.id.buttonLogout);
        buttonRoom1 = (Button) findViewById(R.id.buttonRoom);
        textViewMessagecount = (TextView) findViewById(R.id.message_count);
        buttonMessage1 = (Button) findViewById(R.id.buttonMessage);


        try {
            Connection con = connectionClass.CONN();
            if (con == null) {

                Toast.makeText(AdminPage.this,"Check your Internet Access", Toast.LENGTH_SHORT).show();
            }
            else {
                String query = "select count(*) from req_room where status='request'";//count of message
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    int rowcount=rs.getInt(1);
                    String rowcount1=Integer.toString(rowcount);
                    if(rowcount >0) {
                        textViewMessagecount.setText(rowcount1);
                    }
                    else{
                        textViewMessagecount.setVisibility(View.GONE);
                    }

                }

            }


        } catch (Exception ex) {
            Toast.makeText(AdminPage.this, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

        buttonSearch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DoSearch doSearch = new DoSearch();
                doSearch.execute("");


            }
        });

        buttonStaff1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminPage.this,StaffEntry.class);
                startActivity(intent);

            }
        });

        buttonLogout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminPage.this,Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // To clean up all activities
                startActivity(intent);
                finish();
            }
        });

        buttonRoom1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminPage.this,RoomEntry.class);
                startActivity(intent);
            }
        });

        buttonMessage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminPage.this,AdminMessage.class);
                startActivity(intent);
            }
        });
    }

    public class DoSearch extends AsyncTask<String, String, String> {
        String z = "";
        Boolean isSuccess = false;

        String userid = editTextEmail1.getText().toString();


        @Override
        protected void onPreExecute() {

        }


        @Override
        protected String doInBackground(String... params) {
            if (userid.trim().equals("")) {
                z = "Please enter Email";
            }                                       //validation for email
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
                    } else {
                        String query = "select * from person where email= '" + userid + "'";//get details from person
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);

                        if (rs.next()) {

                            firstname = rs.getString("first_name");
                            lastname = rs.getString("last_name");
                            no = rs.getString("address_no");
                            street = rs.getString("address_street");
                            city = rs.getString("address_city");
                            country = rs.getString("address_country");
                            phone1 = rs.getString("phone1");
                            phone2 = rs.getString("phone2");
                            gender = rs.getString("gender");
                            //birthday = rs.getString("date_of_birth");
                            z = firstname;
                            isSuccess = true;

                        }
                        else {
                            z = "No account";
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
                Intent intent = new Intent(AdminPage.this,GuestDetail.class);//move following details to guestdetail
                intent.putExtra("Firstname",firstname);
                intent.putExtra("Lastname",lastname);
                intent.putExtra("No",no);
                intent.putExtra("Street",street);
                intent.putExtra("City",city);
                intent.putExtra("Country",country);
                intent.putExtra("Phone1",phone1);
                intent.putExtra("Phone2",phone2);
                intent.putExtra("Gender",gender);
                startActivity(intent);
            }
            else {
                Toast.makeText(AdminPage.this, z, Toast.LENGTH_SHORT).show();
            }

        }
    }
}
