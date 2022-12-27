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

public class BookRoom extends AppCompatActivity {
    EditText editTextAdultsNo1, editTextChildrenNo1;
    Button buttonReserve1, buttonCancel1;
    ConnectionClass connectionClass;
    String check_in,check_out,email,room_no;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_room);

        connectionClass = new ConnectionClass();
        editTextAdultsNo1 = (EditText) findViewById(R.id.editTextAdultsNo);
        editTextChildrenNo1 = (EditText) findViewById(R.id.editTextChildrenNo);
        buttonReserve1 = (Button) findViewById(R.id.buttonReserve);
        buttonCancel1 = (Button) findViewById(R.id.buttonCancel);

         email = getIntent().getExtras().getString("email");
        check_in = getIntent().getExtras().getString("check_in");
        check_out = getIntent().getExtras().getString("check_out");
        room_no = getIntent().getExtras().getString("room_no");

        buttonReserve1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoReserve doReserve = new DoReserve();//this is the asynctask, which is used to process
                // is background to reduce load on app process
                doReserve.execute("");

            }

        });

        buttonCancel1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(BookRoom.this, GuestLogin.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // To clean up all activities
                intent.putExtra("Email",email);
                startActivity(intent);
            }

        });
    }

    public class DoReserve extends AsyncTask<String, String, String> {
        String z = "";
        Boolean isSuccess = false;


        String adults = editTextAdultsNo1.getText().toString();
        String children = editTextChildrenNo1.getText().toString();


        int room_no1 = Integer.parseInt(room_no);


        @Override
        protected void onPreExecute() {
        }


        @Override
        protected String doInBackground(String... params) {// Connect to the database, write query
            if (adults.trim().equals("") || children.trim().equals("")) {
                z = "Please enter no of adults and no of children";
            } else {
                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {

                        z = "Check your Internet Access";
                    }
                    else if (Integer.valueOf(adults)>9 || Integer.valueOf(adults)<1) {//validation for adults no and children no
                        z = "Adults should be less than 10 and greater than 1 ";
                    }
                    else if (Integer.valueOf(children)>9) {
                        z = "Children should be less than 10 ";
                    }
                    else {
                        String query = "select * from [person] where email= '" + email + "'";//get details from person
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);

                        if (rs.next()) {
                            String id = rs.getString("id");
                            int id1 = Integer.parseInt(id);
                            int adults1 = Integer.parseInt(adults);
                            int children1 = Integer.parseInt(children);                                 //insert values to req_room
                            String query1 = "insert into [req_room] values('" + room_no1 + "','" + id1 + "',cast('"+check_in+"' as date),cast('"+check_out+"' as date ),'" + adults1 + "','" + children1 + "','request')";
                            PreparedStatement preparedStatement = con.prepareStatement(query1);
                            preparedStatement.executeUpdate();
                            z = "successful";
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
            // pbbar.setVisibility(View.GONE);
            Toast.makeText(BookRoom.this, z, Toast.LENGTH_SHORT).show();

            if (isSuccess) {

                    Intent intent = new Intent(BookRoom.this,GuestAvailableRoom.class);//start next GuestAvailableRoom
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("Email",email);
                    intent.putExtra("check_in",check_in);
                    intent.putExtra("check_out",check_out);
                    startActivity(intent);

                }


        }

    }
}
