package com.example.asus.hotelmanagement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class CancelBooking extends AppCompatActivity {
    ConnectionClass connectionClass;
    EditText editTextRoomno1;
    Button buttonCancel2,buttonService1,buttonLogout1,buttonRoom1,buttonMessage1;
    String email;
    TextView textViewMessagecount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_booking);

        connectionClass = new ConnectionClass();
        editTextRoomno1 = (EditText) findViewById(R.id. editTextRoomno);
        buttonCancel2 = (Button) findViewById(R.id.buttonCancel1);
        buttonService1 = (Button) findViewById(R.id.buttonService);
        buttonLogout1 = (Button) findViewById(R.id.buttonLogout);
        buttonRoom1 = (Button) findViewById(R.id.buttonRoom);
        textViewMessagecount = (TextView) findViewById(R.id.message_count);
        buttonMessage1 = (Button) findViewById(R.id.buttonMessage);
        email = getIntent().getExtras().getString("Email");

        try {
            Connection con = connectionClass.CONN();// Connect to the database, write query
            if (con == null) {
                Toast.makeText(CancelBooking.this,"Check your Internet Access", Toast.LENGTH_SHORT).show();
            }
            else {
                String userid = getIntent().getExtras().getString("Email");
                String query = "select * from [person] where email= '" + userid + "'";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    String id=rs.getString("id");
                    int id1=Integer.parseInt(id);
                    String query1 = "select count(*) from req_room where id='"+id1+"' and (status='accepted' or status='decline')";//count no of messages
                    Statement stmt1 = con.createStatement();
                    ResultSet rs1 = stmt1.executeQuery(query1);
                    if (rs1.next()) {
                        int rowcount = rs1.getInt(1);
                        String rowcount1 = Integer.toString(rowcount);
                        if(rowcount >0) {
                            textViewMessagecount.setText(rowcount1);
                        }
                        else{
                            textViewMessagecount.setVisibility(View.GONE);
                        }

                    }

                }

            }
        }
        catch (Exception ex) {
            Toast.makeText(CancelBooking.this, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }



        buttonCancel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DoCancel doCancel = new DoCancel();
                doCancel.execute("");


            }
        });

        buttonService1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CancelBooking.this,Service.class);
                startActivity(intent);
            }
        });

        buttonRoom1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CancelBooking.this,GuestLogin.class);
                intent.putExtra("Email",email);
                startActivity(intent);
            }
        });

        buttonLogout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CancelBooking.this,Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // To clean up all activities
                startActivity(intent);
                finish();
            }
        });

        buttonMessage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CancelBooking.this,GuestMessage.class);
                intent.putExtra("Email",email);
                startActivity(intent);
            }
        });

    }

    public class DoCancel extends AsyncTask<String, String, String> {
        String z = "";
        Boolean isSuccess = false;
        Boolean isGet =false;
        int id;


        String room_no = editTextRoomno1.getText().toString();


        @Override
        protected void onPreExecute() {
        }


        @Override
        protected String doInBackground(String... params) {//validation for inputs
            if (room_no.trim().equals("")) {
                z = "Please enter Room no";
            }
            else if(!room_no.matches("[0-9]*")){
                z="Enetr correct value";
            }
            else {
                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {

                        z = "Check your Internet Access";
                    } else {
                        String query2 = "select id from [person] where email= '" + email + "'";         //get id of guest
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query2);
                        if (rs.next()) {
                            String id = rs.getString("id");
                            int id1=Integer.parseInt(id);
                            int room_no1=Integer.parseInt(room_no);                                 //check room is reserved or not
                            String query4 = "select * from [stay] where room_no= '" + room_no1 + "' and status='reserve' and id='"+id1+"'";
                            Statement stmt4 = con.createStatement();
                            ResultSet rs4 = stmt4.executeQuery(query4);

                            if (rs4.next()) {
                                String query3 = "update [stay] set status='cancel' where id='" + id1 + "' and room_no='" + room_no1 + "' and status='reserve'";//cancel booking
                                PreparedStatement preparedStatement1 = con.prepareStatement(query3);
                                preparedStatement1.executeUpdate();
                                isSuccess = true;
                                z = "Cancelled successfully";
                            }
                            else {
                                z="No reservation";
                            }
                        }

                    }


                } catch (Exception ex) {
                    isSuccess = false;
                    z = "Exceptions";
                }
            }
            return z;
        }

        @Override
        protected void onPostExecute(String z) {
            Toast.makeText(CancelBooking.this, z, Toast.LENGTH_SHORT).show();
            if (isSuccess) {
                Intent intent = new Intent(CancelBooking.this,CancelBooking.class);     //start activity cancelbooking
                intent.putExtra("Email",email);
                startActivity(intent);
            }

        }

    }

}
