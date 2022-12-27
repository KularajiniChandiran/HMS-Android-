package com.example.asus.hotelmanagement;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GuestMessage extends AppCompatActivity {
    private ArrayList<ListAcceptRoom> acceptRoomArrayList;
    private  ArrayList<String> list ;
    private RecyclerView recyclerView;
    private MyAppAdapter myAppAdapter;
    ConnectionClass connectionClass;
    ResultSet rs1;
    Statement stmt1;
    Boolean success=false;
    Button buttonService1,buttonLogout1,buttonCancel1,buttonRoom1;
    TextView textViewMessagecount;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_message);

        connectionClass = new ConnectionClass();
        buttonService1 = (Button) findViewById(R.id.buttonService);
        buttonLogout1 = (Button) findViewById(R.id.buttonLogout);
        buttonCancel1 = (Button) findViewById(R.id.buttonCancel);
        buttonRoom1 = (Button) findViewById(R.id.buttonRoom);
        textViewMessagecount = (TextView) findViewById(R.id.message_count);



        try {
            Connection con = connectionClass.CONN();
            if (con == null) {
                Toast.makeText(GuestMessage.this,"Check your Internet Access", Toast.LENGTH_SHORT).show();
            }
            else {
                String userid = getIntent().getExtras().getString("Email");             //find the id of guest
                String query = "select * from [person] where email= '" + userid + "'";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    String id=rs.getString("id");
                    int id1=Integer.parseInt(id);
                    String query1 = "select count(*) from req_room where id='"+id1+"' and (status='accepted' or status='decline')";             //count the no of messages
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
            Toast.makeText(GuestMessage.this, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }



        buttonService1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email=getIntent().getExtras().getString("Email");
                Intent intent = new Intent(GuestMessage.this,Service.class);
                intent.putExtra("Email",email);
                startActivity(intent);
            }
        });

        buttonLogout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GuestMessage.this,Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // To clean up all activities
                startActivity(intent);
                finish();
            }
        });

        buttonCancel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email=getIntent().getExtras().getString("Email");
                Intent intent = new Intent(GuestMessage.this,CancelBooking.class);
                intent.putExtra("Email",email);
                startActivity(intent);
            }
        });


        buttonRoom1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GuestMessage.this,GuestLogin.class);
                intent.putExtra("Email",getIntent().getExtras().getString("Email"));
                startActivity(intent);

            }
        });



        acceptRoomArrayList = new ArrayList<>();
        myAppAdapter = new MyAppAdapter(acceptRoomArrayList, GuestMessage.this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);//Recylcerview Declaration
        SeparatorDecoration decoration = new SeparatorDecoration(this, Color.GRAY, 1.5f);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAppAdapter);
        connectionClass = new ConnectionClass();
        SyncData orderData = new SyncData();
        orderData.execute("");
    }

    private class SyncData extends AsyncTask<String, String, String>
    {


        String msg = "Internet/DB_Credentials/Windows_FireWall_TurnOn Error, See Android Monitor in the bottom For details!";
        ProgressDialog progress;

        @Override
        protected void onPreExecute() //Starts the progress dailog
        {
        }

        @Override
        protected String doInBackground(String... strings)  // Connect to the database, write query and add items to array list
        {

            try
            {
                Connection conn = connectionClass.CONN(); //Connection Object
                if (conn == null)
                {
                    success = false;
                }
                else {
                    String email = getIntent().getExtras().getString("Email");
                    String query2 = "select id from [person] where email= '" + email + "'";
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query2);
                    if (rs.next()) {
                        String id=rs.getString("id");
                        int id1=Integer.parseInt(id);
                        String query3 = "select * from [req_room] where id='"+id1+"' and (status='accepted' or status='decline')";
                        stmt1 = conn.createStatement();
                        rs1 = stmt1.executeQuery(query3);

                        if (rs1 != null) // if resultset not null, I add items to itemArraylist using class created
                    {
                        while (rs1.next())
                        {
                            try {
                                acceptRoomArrayList.add(new ListAcceptRoom("Room no : "+rs1.getString("room_no"),"Check in : " + rs1.getString("check_in"),"Check out : "+rs1.getString("check_out"),"Status : "+rs1.getString("status")));
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                        msg = "Found";
                        success = true;
                    }
                    else {
                        msg = "No Data found!";
                        success = false;
                    }
                    }

                }
            } catch (Exception e)
            {
                e.printStackTrace();
                Writer writer = new StringWriter();
                e.printStackTrace(new PrintWriter(writer));
                msg = writer.toString();
                success = false;
            }
            return msg;

        }

        @Override
        protected void onPostExecute(String msg) // disimissing progress dialoge, showing error and setting up my listview
        {
            //  progress.dismiss();
            Toast.makeText(GuestMessage.this, msg + "", Toast.LENGTH_LONG).show();
            if (success == false)
            {
                Toast.makeText(GuestMessage.this, msg , Toast.LENGTH_LONG).show();
            }
            else {
                try
                {
                    myAppAdapter = new MyAppAdapter(acceptRoomArrayList ,GuestMessage.this);
                    recyclerView.setAdapter(myAppAdapter);
                } catch (Exception ex)
                {

                }

            }
        }
    }
    public class MyAppAdapter extends RecyclerView.Adapter<MyAppAdapter.ViewHolder> {
        private List<ListAcceptRoom> values;
        public Context context;




        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView no,check_in,check_out,status;
            public View layout;
            public Button button1;
            public RelativeLayout relativeLayout;
            public ViewHolder(View v) {
                super(v);
                layout = v;

                no = (TextView) v.findViewById(R.id.textViewRoomNo);
                check_in = (TextView) v.findViewById(R.id.textViewCheckIn);
                check_out =(TextView) v.findViewById(R.id.textViewCheckOut);
                status=(TextView) v.findViewById(R.id.textViewStatus);
                button1=(Button) v.findViewById(R.id.button1);
                relativeLayout = (RelativeLayout) itemView.findViewById(R.id.ac4);


            }
        }

        // Constructor
        public MyAppAdapter(List<ListAcceptRoom> myDataset, Context context) {
            values = myDataset;
            this.context = context;
        }

        // Create new views (invoked by the layout manager) and inflates
        @Override
        public MyAppAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.list_acceptroom, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        // Binding items to the view
        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            final ListAcceptRoom classListItems = values.get(position);
            holder.no.setText(classListItems.getRoom_no());
            holder.check_in.setText(classListItems.getCheck_in());
            holder.check_out.setText(classListItems.getCheck_out());
            holder.status.setText(classListItems.getStatus());
            holder.button1.setOnClickListener(
                    new View.OnClickListener()
                    {
                        public void onClick(View view)
                        {

                            try {
                                Connection con = connectionClass.CONN();
                                if (con == null) {

                                    Toast.makeText(GuestMessage.this,"Check your Internet Access", Toast.LENGTH_SHORT).show();
                                }

                                else {
                                        String email = getIntent().getExtras().getString("Email");
                                        String query2 = "select id from [person] where email= '" + email + "'";
                                        Statement stmt = con.createStatement();
                                        ResultSet rs = stmt.executeQuery(query2);
                                        if (rs.next()) {
                                            String id = rs.getString("id");
                                            int id1 = Integer.parseInt(id);
                                            String r_no = classListItems.getRoom_no().replace("Room no : ","");
                                            int room_no1 = Integer.parseInt(r_no);
                                            String check_in1 = classListItems.getCheck_in().replace("Check in : ","");
                                            String check_out1 = classListItems.getCheck_out().replace("Check out : ","");
                                            String query1 = "update [req_room] set status='view' where room_no='" + room_no1 + "' and id='" + id1 + "' and check_in=cast('"+check_in1+"' as date) and check_out= cast('"+check_out1+"' as date)";
                                            PreparedStatement preparedStatement = con.prepareStatement(query1);
                                            preparedStatement.executeUpdate();
                                            Toast.makeText(GuestMessage.this, "update successfully", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(GuestMessage.this, GuestMessage.class);
                                            intent.putExtra("Email",email);
                                            startActivity(intent);
                                        }
                                    }


                            } catch (Exception ex) {
                                Toast.makeText(GuestMessage.this,  ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


        }
        @Override
        public int getItemCount() {
            return values.size();
        }




    }

    public class SeparatorDecoration extends RecyclerView.ItemDecoration {
        private final Paint mPaint;

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();

            // we want to retrieve the position in the list
            final int position = params.getViewAdapterPosition();

            // and add a separator to any view but the last one
            if (position < state.getItemCount()) {
                outRect.set(0, 0, 0, (int) mPaint.getStrokeWidth()); // left, top, right, bottom
            } else {
                outRect.setEmpty(); // 0, 0, 0, 0
            }
        }

        public SeparatorDecoration(Context context, int color, float heightDp) {
            mPaint = new Paint();
            mPaint.setColor(color);
            final float thickness = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    heightDp, context.getResources().getDisplayMetrics());
            mPaint.setStrokeWidth(thickness);
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            // we set the stroke width before, so as to correctly draw the line we have to offset by width / 2
            final int offset = (int) (mPaint.getStrokeWidth() / 2);

            // this will iterate over every visible view
            for (int i = 0; i < parent.getChildCount(); i++) {
                // get the view
                final View view = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();

                // get the position
                final int position = params.getViewAdapterPosition();

                // and finally draw the separator
                if (position < state.getItemCount()) {
                    c.drawLine(view.getLeft(), view.getBottom() + offset, view.getRight(), view.getBottom() + offset, mPaint);
                }
            }
        }
    }
}
