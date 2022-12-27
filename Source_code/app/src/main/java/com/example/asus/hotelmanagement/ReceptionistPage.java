package com.example.asus.hotelmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.AsyncTask;
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

public class ReceptionistPage extends AppCompatActivity {
    private ArrayList<ListReqServices> reqServicesArrayList;
    private  ArrayList<String> list ;
    private RecyclerView recyclerView;
    private MyAppAdapter myAppAdapter;
    private ConnectionClass connectionClass;
    ResultSet rs1;
    Statement stmt1;
    Boolean success=false;
    Button buttonService1,buttonLogout1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receptionist_page);

        buttonLogout1 = (Button) findViewById(R.id.buttonLogout);
        buttonService1 = (Button) findViewById(R.id.buttonService);
        reqServicesArrayList = new ArrayList<>();
        myAppAdapter = new MyAppAdapter(reqServicesArrayList, ReceptionistPage.this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);//Recylcerview Declaration
        SeparatorDecoration decoration = new SeparatorDecoration(this, Color.GRAY, 1.5f);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAppAdapter);
        connectionClass = new ConnectionClass();
        SyncData orderData = new SyncData();
        orderData.execute("");

        buttonLogout1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                Intent intent = new Intent(ReceptionistPage.this, Login.class);
                startActivity(intent);
                finish();
            }

        });

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


                    String query3= "select * from req_services where status = 'reserve'";
                    stmt1 = conn.createStatement();
                    rs1 = stmt1.executeQuery(query3);

                    if (rs1 != null) // if resultset not null, I add items to itemArraylist using class created
                    {
                        while (rs1.next())
                        {
                            try {
                                reqServicesArrayList.add(new ListReqServices("Room no : "+ rs1.getString("room_no"),"Service no : "+rs1.getString("service_no")));

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
            Toast.makeText(ReceptionistPage.this, msg + "", Toast.LENGTH_LONG).show();
            if (success == false)
            {
                Toast.makeText(ReceptionistPage.this, msg , Toast.LENGTH_LONG).show();
            }
            else {
                try
                {
                    myAppAdapter = new MyAppAdapter(reqServicesArrayList ,ReceptionistPage.this);
                    recyclerView.setAdapter(myAppAdapter);
                } catch (Exception ex)
                {

                }

            }
        }
    }
    public class MyAppAdapter extends RecyclerView.Adapter<MyAppAdapter.ViewHolder> {
        private List<ListReqServices> values;
        public Context context;




        public class ViewHolder extends RecyclerView.ViewHolder {
            // public image room_no and service_no
            public TextView room_no,service_no;
            public View layout;
            public Button button1;
            public RelativeLayout relativeLayout;
            public ViewHolder(View v) {
                super(v);
                layout = v;

                room_no = (TextView) v.findViewById(R.id.textViewRoomNo);
                service_no = (TextView) v.findViewById(R.id.textViewService_no);
                button1=(Button) v.findViewById(R.id.button1);
                relativeLayout = (RelativeLayout) itemView.findViewById(R.id.ac2);
            }
        }

        // Constructor
        public MyAppAdapter(List<ListReqServices> myDataset, Context context) {
            values = myDataset;
            this.context = context;
        }

        // Create new views (invoked by the layout manager) and inflates
        @Override
        public MyAppAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.list_reqservice, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        // Binding items to the view
        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            final ListReqServices classListItems = values.get(position);
            holder.room_no.setText(classListItems.getRoom_no());
            holder.service_no.setText(classListItems.getService_no());

           holder.button1.setOnClickListener(
                    new View.OnClickListener()
                    {
                        public void onClick(View view)
                        {

                            try {
                                Connection con = connectionClass.CONN();
                                if (con == null) {

                                    Toast.makeText(ReceptionistPage.this,"Check your Internet Access", Toast.LENGTH_SHORT).show();
                                }

                                else {
                                    String r_no = classListItems.getRoom_no().replace("Room no : ","");
                                    int room_no1=Integer.parseInt(r_no);
                                    String no = classListItems.getService_no().replace("Service no : ","");
                                    int service_no1=Integer.parseInt(no);

                                    String query1 = "update [req_services] set status='provide' where room_no='"+room_no1+"' and service_no='"+service_no1+"'";
                                    PreparedStatement preparedStatement = con.prepareStatement(query1);
                                    preparedStatement.executeUpdate();

                                    Toast.makeText(ReceptionistPage.this,"update successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(ReceptionistPage.this,ReceptionistPage.class);
                                    startActivity(intent);
                                }

                            } catch (Exception ex) {
                                Toast.makeText(ReceptionistPage.this,  ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
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
