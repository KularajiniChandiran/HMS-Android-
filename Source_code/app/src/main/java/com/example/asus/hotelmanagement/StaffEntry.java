package com.example.asus.hotelmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class StaffEntry extends AppCompatActivity {
    Button buttonView1,buttonUpdate1,buttonRemove1, buttonAdd1,buttonGuest1,buttonRoom1,buttonLogout1,buttonMessage1;
    ConnectionClass connectionClass;
    TextView textViewMessagecount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_entry);

        connectionClass = new ConnectionClass();
        buttonView1=(Button) findViewById(R.id.buttonView);
        buttonRemove1=(Button) findViewById(R.id.buttonRemove);
        buttonUpdate1=(Button) findViewById(R.id.buttonUpdate);
        buttonAdd1=(Button) findViewById(R.id.buttonAdd);
        buttonGuest1=(Button) findViewById(R.id.buttonGuest);
        buttonRoom1=(Button) findViewById(R.id.buttonRoom);
        buttonLogout1=(Button) findViewById(R.id.buttonLogout);
        textViewMessagecount = (TextView) findViewById(R.id.message_count);
        buttonMessage1 = (Button) findViewById(R.id.buttonMessage);


        try {
            Connection con = connectionClass.CONN();
            if (con == null) {

                Toast.makeText(StaffEntry.this,"Check your Internet Access", Toast.LENGTH_SHORT).show();
            }
            else {
                String query = "select count(*) from req_room where status='request'";
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
                    //Toast.makeText(StaffEntry.this,"successful", Toast.LENGTH_SHORT).show();

                }

            }


        } catch (Exception ex) {
            Toast.makeText(StaffEntry.this, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }


        buttonAdd1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                Intent intent = new Intent(StaffEntry.this, AddStaff.class);
                startActivity(intent);
            }

        });

        buttonView1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                Intent intent = new Intent(StaffEntry.this, ViewStaff.class);
                startActivity(intent);
            }

        });

        buttonRemove1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                Intent intent = new Intent(StaffEntry.this, RemoveStaff.class);
                startActivity(intent);
            }

        });

        buttonUpdate1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                Intent intent = new Intent(StaffEntry.this, UpdateStaffDetail.class);
                startActivity(intent);
            }

        });

        buttonGuest1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                Intent intent = new Intent(StaffEntry.this, AdminPage.class);
                startActivity(intent);
            }

        });

        buttonRoom1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                Intent intent = new Intent(StaffEntry.this,RoomEntry.class);
                startActivity(intent);
            }

        });

        buttonLogout1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                Intent intent = new Intent(StaffEntry.this, Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // To clean up all activities
                startActivity(intent);
                finish();
            }

        });

        buttonMessage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StaffEntry.this,AdminMessage.class);
                startActivity(intent);

            }
        });


    }

}
