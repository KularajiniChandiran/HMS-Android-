package com.example.asus.hotelmanagement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.content.Intent;
import android.widget.Spinner;
import android.widget.Toast;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class UpdateStaff extends AppCompatActivity {
    EditText editTextFirstname1,editTextLastname1,editTextNo1,editTextStreet1,editTextCity1,editTextCountry1,editTextPhone11,editTextPhone22,editTextGender1,editTextBirthday1,editTextSalary1,editTextWorkingHours1;
    Button  buttonUpdate1,buttonCancel1;
    ConnectionClass connectionClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_staff);

        connectionClass = new ConnectionClass();
        buttonUpdate1=(Button) findViewById(R.id.buttonUpdate);
        buttonCancel1=(Button) findViewById(R.id.buttonCancel);
        editTextFirstname1 =(EditText) findViewById(R.id.editTextFirstname);
        editTextLastname1 =(EditText) findViewById(R.id.editTextLastname);
        editTextNo1 =(EditText) findViewById(R.id.editTextNo);
        editTextStreet1 =(EditText) findViewById(R.id.editTextStreet);
        editTextCity1 =(EditText) findViewById(R.id.editTextCity);
        editTextCountry1 =(EditText) findViewById(R.id.editTextCountry);
        editTextPhone11 =(EditText) findViewById(R.id.editTextPhone1);
        editTextPhone22 =(EditText) findViewById(R.id.editTextPhone2);
        editTextGender1 =(EditText) findViewById(R.id.editTextGender);
        //editTextBirthday1 =(EditText) findViewById(R.id.editTextBirthday);
        editTextSalary1 =(EditText) findViewById(R.id.editTextSalary);
        editTextWorkingHours1 =(EditText) findViewById(R.id.editTextWorkingHours);

        editTextFirstname1.setText(getIntent().getExtras().getString("firstname"));
        editTextLastname1.setText(getIntent().getExtras().getString("lastname"));
        editTextNo1.setText(getIntent().getExtras().getString("no"));
        editTextStreet1.setText(getIntent().getExtras().getString("street"));
        editTextCity1.setText(getIntent().getExtras().getString("city"));
        editTextCountry1.setText(getIntent().getExtras().getString("country"));
        editTextPhone11.setText(getIntent().getExtras().getString("phone1"));
        editTextPhone22.setText(getIntent().getExtras().getString("phone2"));
        editTextGender1 .setText(getIntent().getExtras().getString("gender"));
        //editTextBirthday1.setText(getIntent().getExtras().getString("birthday"));
        editTextSalary1.setText(getIntent().getExtras().getString("salary"));
        editTextWorkingHours1.setText(getIntent().getExtras().getString("working_hours"));

        buttonUpdate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DOUpdate  doUpdate = new DOUpdate();//this is the asynctask, which is used to process
                // is background to reduce load on app process
                doUpdate.execute("");
            }

        });


        buttonCancel1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(UpdateStaff.this, StaffEntry.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // To clean up all activities
                startActivity(intent);
            }

        });

    }
    public class DOUpdate extends AsyncTask<String, String, String> {
        String z = "";
        Boolean isSuccess = false;

        String firstname = editTextFirstname1.getText().toString();
        String lastname = editTextLastname1.getText().toString();
        String no = editTextNo1.getText().toString();
        String street = editTextStreet1.getText().toString();
        String city = editTextCity1.getText().toString();
        String country = editTextCountry1.getText().toString();
        String phone1 = editTextPhone11.getText().toString();
        String phone2 = editTextPhone22.getText().toString();
        //String birthday = editTextBirthday1.getText().toString();
        String gender = editTextGender1 .getText().toString();
        String salary  = editTextSalary1.getText().toString();
        String working_hours  = editTextWorkingHours1.getText().toString();

        String email = getIntent().getExtras().getString("email");

        @Override
        protected void onPreExecute() {
            // pbbar.setVisibility(View.VISIBLE);
        }


        @Override
        protected String doInBackground(String... params) {
            if (firstname.trim().equals("") || lastname.trim().equals("") || no.trim().equals("") || street.trim().equals("") || city.trim().equals("") || country.trim().equals("") || gender.trim().equals("") || email.trim().equals("") ||phone1.trim().equals("") || phone2.trim().equals("")  || salary.trim().equals("") ||working_hours.trim().equals("") ) {
                z = "Please enter all  details ";
            }
            else if(!firstname.matches("[a-zA-Z]+")){
                z="Enter only alphabetic letters for firstname";
            }
            else if(!lastname.matches("[a-zA-Z]+")){
                z="Enter only alphabetic letters for lastname";
            }
            else if(!no.matches("[0-9]*\\/*[0-9]*")){
                z="Enter correct value for No(format 43 or 33/8)";

            }
            else if(!street.matches("[a-zA-Z]+")){
                z="Enter only alphabetic letters for street";
            }
            else if(!city.matches("[a-zA-Z]+")){
                z="Enter only alphabetic letters for city";
            }
            else if(!country.matches("[a-zA-Z]+")){
                z="Enter only alphabetic letters for country";
            }
            else if(!gender.matches("Male") && !gender.matches("Female")){
                z="Gender should be Male or Female value";
            }
            else if(phone1.length()<9 || phone1.length()>15){
                z = "Phone No1 length should be between 8 and 16";
            }
            else if(phone2.length()<9 || phone2.length()>15){
                z = "Phone No2 length should be between 8 and 16";
            }
            else if(salary.length()>9){
                z = "Salary should be less than 999999999";
            }
            else if(Float.valueOf(working_hours)>260.00){
                z = "Working hours should be less than 260";
            }
            else {
                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {

                        z = "Check your Internet Access";
                    }

                    else {
                        long pho1=Long.parseLong(phone1);
                        long pho2=Long.parseLong(phone2);
                        int sal=Integer.parseInt(salary);
                        float wor_hou=Float.parseFloat(working_hours);
                        String query1 = "update [person] set first_name ='"+firstname+"',last_name='"+lastname+"',address_no='"+no+"',address_street='"+street+"',address_city='"+city+"',address_country='"+country+"',gender='"+gender+"',phone1='"+pho1+"',phone2='"+pho2+"' where email='"+email+"'";
                        PreparedStatement preparedStatement = con.prepareStatement(query1);
                        preparedStatement.executeUpdate();
                        String query2 = "select id from [person] where email= '" + email + "'";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query2);
                        if (rs.next()) {
                            String id = rs.getString("id");
                            int id1=Integer.parseInt(id);
                            String query3 = "update [staff] set working_hours='" + wor_hou + "',salary='" + sal + "' where id='" + id1 + "'";
                            PreparedStatement preparedStatement1 = con.prepareStatement(query3);
                            preparedStatement1.executeUpdate();
                            z="update successfully";
                            isSuccess = true;
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
        protected void onPostExecute(String r) {
            Toast.makeText(UpdateStaff.this, z, Toast.LENGTH_SHORT).show();
            if (isSuccess) {
                Intent intent = new Intent(UpdateStaff.this, StaffEntry.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }
    }
}
