package com.drmertm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.drmertm.ModelClass.MyGrievance;

import java.util.ArrayList;

public class ActivityMyGrievance extends AppCompatActivity {

    ImageView img_back;
    ListView list;
    ArrayList<MyGrievance> nitification1s=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_grievance);
        InitView();
        Click();

        nitification1s.clear();
        for (int i=0;i<3;i++)
        {
//            MyGrievance myGrievance = new MyGrievance("PER20720204","Type 2","Personnel","Personnel Confodential Section","Completed");
//            nitification1s.add(myGrievance);
        }

        list.isSmoothScrollbarEnabled();
        list.setDrawingCacheEnabled(true);
        list.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        list.setAdapter(new MyArrayAdapter(ActivityMyGrievance.this));

    }


    private void Click() {

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void InitView() {

        img_back = (ImageView) findViewById(R.id.img_back);
        list = (ListView) findViewById(R.id.notification_list);
    }


    private class MyArrayAdapter extends BaseAdapter {

        private LayoutInflater mInflater;


        public MyArrayAdapter(FragmentActivity con) {
            // TODO Auto-generated constructor stub
            mInflater = LayoutInflater.from(con);

        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return nitification1s.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            final ListContent holder;
            View v = convertView;
            if (v == null)
            {
                v = mInflater.inflate(R.layout.custome_notification_list, null);

                holder = new ListContent();
                holder.txt_viewdetails = (TextView) v.findViewById(R.id.txt_viewdetails);
                holder.txt_status = (TextView) v.findViewById(R.id.txt_status);
                holder.txt_section = (TextView) v.findViewById(R.id.txt_section);
                holder.txt_dept = (TextView) v.findViewById(R.id.txt_dept);
                holder.txt_gritype = (TextView) v.findViewById(R.id.txt_gritype);
                holder.txt_griunino = (TextView) v.findViewById(R.id.txt_griunino);



                v.setTag(holder);
            } else {

                holder = (ListContent) v.getTag();
            }
            try{
                holder.txt_status.setText(nitification1s.get(position).getStatus());
//                holder.txt_section.setText(nitification1s.get(position).getSection());
//                holder.txt_dept.setText(nitification1s.get(position).getDep());
//                holder.txt_gritype.setText(nitification1s.get(position).getGritype());
//                holder.txt_griunino.setText(nitification1s.get(position).getGriunino());


                holder.txt_viewdetails.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(ActivityMyGrievance.this,ActivityMyGrievanceDatails.class);
                        startActivity(intent);
                    }
                });

            }catch (Exception e){
                e.printStackTrace();
            }
            return v;
        }
    }

    static class ListContent {

        TextView txt_viewdetails,txt_status,txt_section,txt_dept,txt_gritype,txt_griunino;

    }
}
