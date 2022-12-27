package com.example.asus.hotelmanagement;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.text.InputType;
import java.util.Calendar;
import android.widget.DatePicker;
import android.app.DatePickerDialog.OnDateSetListener;
import android.view.Menu;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GuestLogin extends Activity implements OnClickListener{

    EditText editTextCheck_in,editTextCheck_out;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    Calendar newDate;
    Button buttonService1,buttonLogout1,buttonCancel1,buttonSearch1,buttonMessage1;
    String email,check_in1,check_out1;
    ConnectionClass connectionClass;
    TextView textViewMessagecount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_login);

        editTextCheck_in = (EditText) findViewById(R.id.editTextCheck_in);
        editTextCheck_out = (EditText) findViewById(R.id.editTextCheck_out);
        connectionClass = new ConnectionClass();
        buttonService1 = (Button) findViewById(R.id.buttonService);
        buttonLogout1 = (Button) findViewById(R.id.buttonLogout);
        buttonCancel1 = (Button) findViewById(R.id.buttonCancel);
        buttonSearch1 = (Button) findViewById(R.id.buttonSearch);
        textViewMessagecount = (TextView) findViewById(R.id.message_count);
        buttonMessage1 = (Button) findViewById(R.id.buttonMessage);


        try {
            Connection con = connectionClass.CONN();//connect database and write query
            if (con == null) {
                Toast.makeText(GuestLogin.this,"Check your Internet Access", Toast.LENGTH_SHORT).show();
            }
            else {
                String userid = getIntent().getExtras().getString("Email");
                String query = "select * from [person] where email= '" + userid + "'";      //find the id of guest
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    String id=rs.getString("id");
                    int id1=Integer.parseInt(id);
                    String query1 = "select count(*) from req_room where id='"+id1+"' and (status='accepted' or status='decline')";         //count the no of messages
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
            Toast.makeText(GuestLogin.this, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }



        buttonService1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email=getIntent().getExtras().getString("Email");
                Intent intent = new Intent(GuestLogin.this,Service.class);
                intent.putExtra("Email",email);
                startActivity(intent);
            }
        });

        buttonLogout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GuestLogin.this,Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // To clean up all activities
                startActivity(intent);
                finish();
            }
        });

        buttonCancel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email=getIntent().getExtras().getString("Email");
                Intent intent = new Intent(GuestLogin.this,CancelBooking.class);
                intent.putExtra("Email",email);
                startActivity(intent);
            }
        });

        buttonMessage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email=getIntent().getExtras().getString("Email");
                Intent intent = new Intent(GuestLogin.this,GuestMessage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Email",email);
                startActivity(intent);
            }
        });



            buttonSearch1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
                    if (editTextCheck_in.getText().toString().length() == 0) {              //validation for inputs
                        Toast.makeText(GuestLogin.this, "select values for check-in", Toast.LENGTH_SHORT).show();
                    } else if (editTextCheck_out.getText().toString().length() == 0) {
                        Toast.makeText(GuestLogin.this, "select values for check-out", Toast.LENGTH_SHORT).show();
                    }

                    try {
                        if (mdformat.parse(editTextCheck_in.getText().toString()).compareTo(mdformat.parse(editTextCheck_out.getText().toString())) >= 0) {
                            Toast.makeText(GuestLogin.this, "check-in should be lesser than check-out", Toast.LENGTH_SHORT).show();
                        }
                    else{
                    Intent intent = new Intent(GuestLogin.this, GuestAvailableRoom.class);
                    intent.putExtra("check_in", editTextCheck_in.getText().toString());
                    intent.putExtra("check_out", editTextCheck_out.getText().toString());
                    intent.putExtra("Email", getIntent().getExtras().getString("Email"));
                    startActivity(intent);
                    }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
            });




        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

    findViewsById();

    setDateTimeField();
    }


    private void findViewsById() {
        editTextCheck_in = (EditText) findViewById(R.id.editTextCheck_in);
        editTextCheck_in.setInputType(InputType.TYPE_NULL);
        editTextCheck_in.requestFocus();

        editTextCheck_out = (EditText) findViewById(R.id.editTextCheck_out);
        editTextCheck_out.setInputType(InputType.TYPE_NULL);
        editTextCheck_out.requestFocus();

    }

    private void setDateTimeField() {                       //datepickerdialog for check-in and check-out
        editTextCheck_in.setOnClickListener(this);
        editTextCheck_out.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();

       fromDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                toDatePickerDialog.getDatePicker().setMinDate(newDate.getTime().getTime()+24*60*60*1000);
                editTextCheck_in.setText(dateFormatter.format(newDate.getTime()));
                check_in1=editTextCheck_in.getText().toString();
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        toDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                editTextCheck_out.setText(dateFormatter.format(newDate.getTime()));
                check_out1=editTextCheck_out.getText().toString();
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        if(view == editTextCheck_in) {
            fromDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            fromDatePickerDialog.show();
        } else if(view == editTextCheck_out) {
            toDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            toDatePickerDialog.show();
        }

    }


}
