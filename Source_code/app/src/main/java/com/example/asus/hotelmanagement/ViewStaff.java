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

public class ViewStaff extends AppCompatActivity {

    Button buttonSearch1;
    ConnectionClass connectionClass;
    EditText editTextEmail1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_staff);

        connectionClass = new ConnectionClass();
        buttonSearch1=(Button) findViewById(R.id.buttonSearch);
        editTextEmail1 = (EditText) findViewById(R.id.editTextEmail);

        buttonSearch1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                DoSearch doSearch = new DoSearch();
                doSearch.execute();
            }

        });
    }

    public class DoSearch extends AsyncTask<String, String, String> {
        String z = "";
        Boolean isSuccess = false;
        String firstname;
        String lastname;
        String no;
        String street;
        String city;
        String country;
        String phone1;
        String phone2;
        String birthday;
        String gender;
        String salary;
        String working_hours;

        String userid = editTextEmail1.getText().toString();


        /*@Override
        protected void onPreExecute() {
            pbbar.setVisibility(View.VISIBLE);
        }*/


        @Override
        protected String doInBackground(String... params) {
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
                    } else {
                        String query = "select * from [person],[staff] where email= '" + userid + "' and person.id=staff.id";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);

                        if (rs.next()) {
                            z = " Details found";
                            firstname = rs.getString("first_name");
                            lastname = rs.getString("last_name");
                            no = rs.getString("address_no");
                            street = rs.getString("address_street");
                            city = rs.getString("address_city");
                            country = rs.getString("address_country");
                            phone1 = rs.getString("phone1");
                            phone2 = rs.getString("phone2");
                            //birthday = rs.getString("date_of_birth");
                            gender = rs.getString("gender");
                            salary = rs.getString("salary");
                            working_hours = rs.getString("working_hours");
                            isSuccess = true;

                        } else {
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
            //pbbar.setVisibility(View.GONE);
            Toast.makeText(ViewStaff.this, z, Toast.LENGTH_SHORT).show();
            if (isSuccess) {
                Intent intent = new Intent(ViewStaff.this,ViewStaff2.class);
                intent.putExtra("firstname",firstname);
                intent.putExtra("lastname",lastname);
                intent.putExtra("no",no);
                intent.putExtra("street",street);
                intent.putExtra("city",city);
                intent.putExtra("country",country);
                intent.putExtra("phone1",phone1);
                intent.putExtra("phone2",phone2);
                intent.putExtra("gender",gender);
                //intent.putExtra("birthday",birthday);
                intent.putExtra("working_hours",working_hours);
                intent.putExtra("salary",salary);
                startActivity(intent);
            }

        }



    }
}
