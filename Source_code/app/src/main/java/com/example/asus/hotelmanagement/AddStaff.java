package com.example.asus.hotelmanagement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.DatePicker;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class AddStaff extends AppCompatActivity {
    Spinner spinnerGender1;
    EditText editTextFirstname1,editTextLastname1,editTextNo1,editTextStreet1,editTextCity1,editTextCountry1,editTextPhone11,editTextPhone22,editTextEmail1,editTextSalary1,editTextWorkingHours1;
    Button buttonAdd1;
    ConnectionClass connectionClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_staff);

        connectionClass = new ConnectionClass();
        editTextFirstname1 = (EditText) findViewById(R.id.editTextFirstname);
        editTextLastname1 = (EditText) findViewById(R.id.editTextLastname);
        editTextNo1 = (EditText) findViewById(R.id.editTextNo);
        editTextStreet1 = (EditText) findViewById(R.id.editTextStreet);
        editTextCity1 = (EditText) findViewById(R.id.editTextCity);
        editTextCountry1 = (EditText) findViewById(R.id.editTextCountry);
        editTextPhone11 = (EditText) findViewById(R.id.editTextPhone1);
        editTextPhone22 = (EditText) findViewById(R.id.editTextPhone2);
        spinnerGender1 = (Spinner) findViewById(R.id.spinnerGender);
        editTextEmail1 = (EditText) findViewById(R.id. editTextEmail);
        editTextSalary1 = (EditText) findViewById(R.id. editTextSalary);
        editTextWorkingHours1 = (EditText) findViewById(R.id. editTextWorkingHours);
        buttonAdd1 = (Button) findViewById(R.id.buttonAdd);

        buttonAdd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoAdd doAdd = new DoAdd();//this is the asynctask, which is used to process
                // is background to reduce load on app process
                doAdd.execute("");

            }

        });
    }

    public class DoAdd extends AsyncTask<String, String, String> {
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
        String gender = spinnerGender1.getSelectedItem().toString();
        String email = editTextEmail1.getText().toString();
        String salary  = editTextSalary1.getText().toString();
        String working_hours  = editTextWorkingHours1.getText().toString();



        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... params) {//validation for all input
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
            else if(phone1.length()<9 || phone1.length()>15){
                z = "Phone No1 length should be between 8 and 16";
            }
            else if(phone2.length()<9 || phone2.length()>15){
                z = "Phone No2 length should be between 8 and 16";
            }
            else if(!email.matches("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"

                    +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"

                    +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."

                    +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"

                    +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"

                    +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$")){
                z= "Invalid Email";
            }
            else if(salary.length()>9){
                z = "Salary should be less than 999999999";
            }
            else if(Float.valueOf(working_hours)>260.00 || working_hours.length()>6){
                z = "Working hours should be less than 260";
            }
            else
            {
                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {                                                          //connection check

                        z = "Check your Internet Access";
                    } else {
                        long pho1=Long.parseLong(phone1);
                        long pho2=Long.parseLong(phone2);
                        int sal=Integer.parseInt(salary);
                        float wor_hou=Float.parseFloat(working_hours);                          //insert details to person table
                        String query1 = "insert into [person] values('"+firstname+"','"+lastname+"','"+no+"','"+street+"','"+city+"','"+country+"','"+email+"','"+pho1+"','"+pho2+"','"+gender+"')";
                        PreparedStatement preparedStatement = con.prepareStatement(query1);
                        preparedStatement.executeUpdate();

                        String query2 = "select top 1 * from [person] order by id desc";        //find id in person table
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query2);
                        if (rs.next()) {
                            String id = rs.getString("id");
                            int id1=Integer.parseInt(id);                                       //insert details to staff table
                            String query3 = "insert into [staff] values('"+id1+"','"+sal+"','"+wor_hou+"','active')";
                            PreparedStatement preparedStatement1 = con.prepareStatement(query3);
                            preparedStatement1.executeUpdate();
                            z = "Add successfully";
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
        protected void onPostExecute(String z) {
            Toast.makeText(AddStaff.this, z, Toast.LENGTH_SHORT).show();
            if (isSuccess) {
                Intent Intent = new Intent(AddStaff.this, StaffEntry.class);            //start activity staffentry
                startActivity(Intent);
                finish();
            }
        }
    }
}
