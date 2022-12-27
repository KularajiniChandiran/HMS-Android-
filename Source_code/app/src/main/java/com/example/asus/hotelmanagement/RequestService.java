package com.example.asus.hotelmanagement;

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

import android.content.Intent;
import android.os.AsyncTask;

public class RequestService extends AppCompatActivity {
    Button buttonRequest1, buttonCancel1;
    ConnectionClass connectionClass;
    EditText editTextRoom_no1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_service);

        connectionClass = new ConnectionClass();
        buttonRequest1=(Button) findViewById(R.id.buttonRequest);
        editTextRoom_no1 = (EditText) findViewById(R.id.editTextRoom_no);
        buttonCancel1 = (Button) findViewById(R.id.buttonCancel);

        buttonRequest1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                DoRequest doRequest = new DoRequest();
                doRequest.execute("");
            }

        });

        buttonCancel1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(RequestService.this, GuestLogin.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // To clean up all activities
                intent.putExtra("Email",getIntent().getExtras().getString("Email"));
                startActivity(intent);
            }

        });
    }

    public class DoRequest extends AsyncTask<String, String, String> {
        String z = "";
        Boolean isSuccess = false;

        String room_no = editTextRoom_no1.getText().toString();

        String service_no = getIntent().getExtras().getString("service_no");
        int service_no1 = Integer.parseInt(service_no);



        @Override
        protected void onPreExecute() {
        }


        @Override
        protected String doInBackground(String... params) {
            if (room_no.trim().equals("")) {
                z = "Please enter Room_no";
            } else {
                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {

                        z = "Check your Internet Access";
                    }
                    else if(!room_no.matches("[0-9]*")){
                        z="Enter numbers only";
                    }
                    else {
                        int room_no1 = Integer.parseInt(room_no);
                        String query = "select * from [room] where room_no= '" + room_no1 + "'";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);

                        if (rs.next()) {
                        String query1 = "insert into [req_services] values('"+room_no1+"','"+service_no1+"','reserve')";
                        PreparedStatement preparedStatement = con.prepareStatement(query1);
                        preparedStatement.executeUpdate();
                        z="requested successfully";
                        isSuccess=true;
                        }
                        else {
                            z="Invalid room no";
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
            if (isSuccess) {
                Toast.makeText(RequestService.this, r, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RequestService.this, Service.class);
                intent.putExtra("Email",getIntent().getExtras().getString("Email"));
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
            else{
                Toast.makeText(RequestService.this, r, Toast.LENGTH_SHORT).show();
            }


        }



    }
}
