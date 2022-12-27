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
import android.widget.EditText;
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

public class AdminMessage extends AppCompatActivity {

    private ArrayList<ListReqRoom> reqRoomArrayList;
    private  ArrayList<String> list ;
    private RecyclerView recyclerView;
    private MyAppAdapter myAppAdapter;
    ConnectionClass connectionClass;
    ResultSet rs1;
    Statement stmt1;
    Boolean success=false;
    TextView textViewMessagecount;
    Button buttonGuest1,buttonStaff1,buttonLogout1,buttonRoom1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_message);


        connectionClass = new ConnectionClass();
        buttonGuest1 = (Button) findViewById(R.id.buttonGuest);
        buttonStaff1 = (Button) findViewById(R.id.buttonStaff);
        buttonLogout1 = (Button) findViewById(R.id.buttonLogout);
        buttonRoom1 = (Button) findViewById(R.id.buttonRoom);
        textViewMessagecount = (TextView) findViewById(R.id.message_count);

        try {
            Connection con = connectionClass.CONN();
            if (con == null) {

                Toast.makeText(AdminMessage.this,"Check your Internet Access", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(AdminMessage.this, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

        buttonGuest1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminMessage.this,AdminPage.class);
                startActivity(intent);


            }
        });

        buttonStaff1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminMessage.this,StaffEntry.class);
                startActivity(intent);

            }
        });

        buttonLogout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminMessage.this,Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // To clean up all activities
                startActivity(intent);
                finish();
            }
        });

        buttonRoom1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminMessage.this,RoomEntry.class);
                startActivity(intent);
            }
        });



        reqRoomArrayList = new ArrayList<>();
        myAppAdapter = new MyAppAdapter(reqRoomArrayList, AdminMessage.this);
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


                    String query3= "select * from [req_room] where status='request'";
                    stmt1 = conn.createStatement();
                    rs1 = stmt1.executeQuery(query3);

                    if (rs1 != null) // if resultset not null, I add items to itemArraylist using class created
                    {

                        while (rs1.next())
                        {
                            try {
                                reqRoomArrayList.add(new ListReqRoom("Room no : "+ rs1.getString("room_no"),"Check in : " +rs1.getString("check_in"),"Check out : "+rs1.getString("check_out")));
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
            Toast.makeText(AdminMessage.this, msg + "", Toast.LENGTH_LONG).show();
            if (success == false)
            {
                Toast.makeText(AdminMessage.this, msg , Toast.LENGTH_LONG).show();
            }
            else {
                try
                {
                    myAppAdapter = new MyAppAdapter(reqRoomArrayList ,AdminMessage.this);
                    recyclerView.setAdapter(myAppAdapter);
                } catch (Exception ex)
                {

                }

            }
        }
    }
    public class MyAppAdapter extends RecyclerView.Adapter<MyAppAdapter.ViewHolder> {
        private List<ListReqRoom> values;
        public Context context;




        public class ViewHolder extends RecyclerView.ViewHolder {
            // public image title and image url
            public TextView no,check_in,check_out;
            public View layout;
            public Button button1,button2;
            public RelativeLayout relativeLayout;

            public ViewHolder(View v) {
                super(v);
                layout = v;

                no = (TextView) v.findViewById(R.id.textViewRoomNo);
                check_in = (TextView) v.findViewById(R.id.textViewCheckIn);
                check_out =(TextView) v.findViewById(R.id.textViewCheckOut);
                button1=(Button) v.findViewById(R.id.button1);
                button2=(Button) v.findViewById(R.id.button2);
                relativeLayout = (RelativeLayout) itemView.findViewById(R.id.ac3);


            }
        }

        // Constructor
        public MyAppAdapter(List<ListReqRoom> myDataset, Context context) {
            values = myDataset;
            this.context = context;
        }

        // Create new views (invoked by the layout manager) and inflates
        @Override
        public MyAppAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.list_reqroom, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        // Binding items to the view
        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            final ListReqRoom classListItems = values.get(position);
            holder.no.setText(classListItems.getRoom_no());
            holder.check_in.setText(classListItems.getCheck_in());
            holder.check_out.setText(classListItems.getCheck_out());
            holder.button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Connection con = connectionClass.CONN();
                        if (con == null) {

                            Toast.makeText(AdminMessage.this,"Check your Internet Access", Toast.LENGTH_SHORT).show();
                        }

                        else {
                                String r_no = classListItems.getRoom_no().replace("Room no : ","");
                                int room_no1 = Integer.parseInt(r_no);
                                String check_in1 = classListItems.getCheck_in().replace("Check in : ","");
                                String check_out1 = classListItems.getCheck_out().replace("Check out : ","");
                                String query1 = "update [req_room] set status='accepted' where room_no='" + room_no1 + "' and status='request' and check_in=cast('"+check_in1+"' as date) and check_out= cast('"+check_out1+"' as date)";
                                PreparedStatement preparedStatement = con.prepareStatement(query1);
                                preparedStatement.executeUpdate();

                                String query3 = "select * from [req_room] where room_no='" + room_no1 + "' and check_in=cast('"+check_in1+"' as date) and check_out= cast('"+check_out1+"' as date)";
                                Statement stmt3 = con.createStatement();
                                ResultSet rs = stmt3.executeQuery(query3);

                            if (rs.next()) {
                                String id = rs.getString("id");
                                int id1=Integer.parseInt(id);
                                String adults = rs.getString("no_adults");
                                int adults1=Integer.parseInt(adults);
                                String children = rs.getString("no_children");
                                int children1=Integer.parseInt(children);
                                String query2 = "insert into [stay] values('"+room_no1+"','" + id1 + "',cast('"+check_in1+"' as date),cast('"+check_out1+"' as date ),'"+adults1+"','"+children1+"','reserve')";
                                PreparedStatement preparedStatement2 = con.prepareStatement(query2);
                                preparedStatement2.executeUpdate();

                                Toast.makeText(AdminMessage.this, "update successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(AdminMessage.this, AdminMessage.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }

                        }


                    } catch (Exception ex) {
                        Toast.makeText(AdminMessage.this,  ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }

                }

            });
            holder.button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Connection con = connectionClass.CONN();                    //connection check
                        if (con == null) {

                            Toast.makeText(AdminMessage.this,"Check your Internet Access", Toast.LENGTH_SHORT).show();
                        }

                        else {
                            String r_no = classListItems.getRoom_no().replace("Room no : ","");
                            int room_no1 = Integer.parseInt(r_no);
                            String check_in1 = classListItems.getCheck_in().replace("Check in : ","");
                            String check_out1 = classListItems.getCheck_out().replace("Check out : ","");               //update req_room table
                            String query1 = "update [req_room] set status='decline' where room_no='" + room_no1 + "' and status='request' and check_in=cast('"+check_in1+"' as date) and check_out= cast('"+check_out1+"' as date)";
                            PreparedStatement preparedStatement = con.prepareStatement(query1);
                            preparedStatement.executeUpdate();
                            Toast.makeText(AdminMessage.this, "update successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AdminMessage.this, AdminMessage.class);
                            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);

                        }


                    } catch (Exception ex) {
                        Toast.makeText(AdminMessage.this,  ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
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
