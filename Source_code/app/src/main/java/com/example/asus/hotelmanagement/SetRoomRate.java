package com.example.asus.hotelmanagement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SetRoomRate extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinnerRoomType1;
    Button buttonSetRate1;
    EditText editTextPrice1;
    ConnectionClass connectionClass;
    ResultSet rs;
    PreparedStatement stmt;
    String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_room_rate);

        buttonSetRate1 = (Button) findViewById(R.id.buttonSetRate);
        spinnerRoomType1 = (Spinner) findViewById(R.id.spinnerRoomType);
        editTextPrice1 = (EditText) findViewById(R.id.editTextPrice);
        connectionClass = new ConnectionClass();

        buttonSetRate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DOSetRate  doSetRate= new DOSetRate();//this is the asynctask, which is used to process
                // is background to reduce load on app process
                doSetRate.execute("");
            }

        });


        try {
            Connection con = connectionClass.CONN();
            query= "select * from [room_type]";
            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();
            while (rs.next()) {
                String id = rs.getString("type_name");
                data.add(id);
            }
            String[] array = data.toArray(new String[0]);
            ArrayAdapter NoCoreAdapter = new ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, data);
            spinnerRoomType1.setAdapter(NoCoreAdapter);

        } catch (SQLException e) {

            e.printStackTrace();
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int position, long id) {
        String name = spinnerRoomType1.getSelectedItem().toString();

        spinnerRoomType1 = (Spinner)parent;

        if(spinnerRoomType1.getId() == R.id.spinnerRoomType)
        {
            Toast.makeText(SetRoomRate.this, name, Toast.LENGTH_SHORT)
                    .show();

        }

    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public class  DOSetRate  extends AsyncTask<String, String, String> {
        String z = "";
        Boolean isSuccess = false;

        String price = editTextPrice1.getText().toString();
        String room_type =spinnerRoomType1.getSelectedItem().toString();

        @Override
        protected void onPreExecute() {
            // pbbar.setVisibility(View.VISIBLE);
        }


        @Override
        protected String doInBackground(String... params) {
            if (price.trim().equals("") ) {
                z = "Please enter value ";
            }
            else if(!price.matches("[0-9]*")){
                z="Enetr correct value";
            }
            else if(price.length()>8){
                z="Enetr price less than 99999999";
            }
            else {
                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {

                        z = "Check your Internet Access";
                    }

                    else {
                        int price1 =Integer.parseInt(price);
                        String query1 = "update [room_type] set price ='"+price1+"' where type_name='"+room_type+"'";
                        PreparedStatement preparedStatement = con.prepareStatement(query1);
                        preparedStatement.executeUpdate();
                        z="set room rate successfully";
                        isSuccess = true;

                    }


                } catch (Exception ex) {
                    isSuccess = false;
                    z = "Exceptions";
                }
            }
            return z;
        }

        @Override
        protected void onPostExecute(String r) {
            Toast.makeText(SetRoomRate.this, z, Toast.LENGTH_SHORT).show();
            if (isSuccess) {
                Intent Intent = new Intent(SetRoomRate.this, RoomEntry.class);
                startActivity(Intent);
            }

        }



    }
}
