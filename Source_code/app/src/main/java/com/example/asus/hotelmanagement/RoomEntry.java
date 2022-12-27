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

public class RoomEntry extends AppCompatActivity {

    Button buttonGuest1,buttonStaff1,buttonLogout1,buttonAll1,buttonAvailable1,buttonRate1,buttonMessage1;
    ConnectionClass connectionClass;
    TextView textViewMessagecount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_entry);

        buttonGuest1=(Button) findViewById(R.id.buttonGuest);
        buttonStaff1=(Button) findViewById(R.id.buttonStaff);
        buttonLogout1=(Button) findViewById(R.id.buttonLogout);
        buttonAll1=(Button) findViewById(R.id.buttonAll);
        buttonAvailable1=(Button) findViewById(R.id.buttonAvailable);
        buttonRate1=(Button) findViewById(R.id.buttonRate);
        textViewMessagecount = (TextView) findViewById(R.id.message_count);
        connectionClass = new ConnectionClass();
        buttonMessage1 = (Button) findViewById(R.id.buttonMessage);

        try {
            Connection con = connectionClass.CONN();
            if (con == null) {

                Toast.makeText(RoomEntry.this,"Check your Internet Access", Toast.LENGTH_SHORT).show();
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

                }

            }


        } catch (Exception ex) {
            Toast.makeText(RoomEntry.this, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

        buttonGuest1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                Intent intent = new Intent(RoomEntry.this, AdminPage.class);
                startActivity(intent);

            }

        });

        buttonStaff1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                Intent intent = new Intent(RoomEntry.this,StaffEntry.class);
                startActivity(intent);

            }

        });

        buttonLogout1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                Intent intent = new Intent(RoomEntry.this, Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // To clean up all activities
                startActivity(intent);
                finish();
            }

        });

        buttonAll1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                Intent intent = new Intent(RoomEntry.this,AllRoomDetails.class);
                startActivity(intent);
            }

        });

        buttonAvailable1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                Intent intent = new Intent(RoomEntry.this,AvailableRoomDetails.class);
                startActivity(intent);
            }

        });

        buttonMessage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RoomEntry.this,AdminMessage.class);
                startActivity(intent);
            }
        });



        buttonRate1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(RoomEntry.this, SetRoomRate.class);
                startActivity(intent);
            }

        });


    }
}
