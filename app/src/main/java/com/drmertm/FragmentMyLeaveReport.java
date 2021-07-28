package com.drmertm;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.drmertm.ModelClass.MyLeaveReport;

import java.util.ArrayList;

public class FragmentMyLeaveReport extends Fragment {
    ListView list;
    ArrayList<MyLeaveReport> nitification1s=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v = inflater.inflate(R.layout.fragment_fragment_my_leave_report, container, false);
        InitView(v);

        nitification1s.clear();
        for (int i=0;i<3;i++)
        {
            MyLeaveReport myGrievance = new MyLeaveReport("LAP","12/7 indore","i want to go out if town","2020-07-24","2020-07-30","pending");
            nitification1s.add(myGrievance);
        }

        list.isSmoothScrollbarEnabled();
        list.setDrawingCacheEnabled(true);
        list.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        list.setAdapter(new MyArrayAdapter(getActivity()));
       return v;
    }

    private void InitView(View v) {
        list = (ListView) v.findViewById(R.id.notification_list);
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
                v = mInflater.inflate(R.layout.custome_myleavereport_list, null);

                holder = new ListContent();
                holder.txt_viewdetails = (TextView) v.findViewById(R.id.txt_viewdetails);
                holder.txt_status = (TextView) v.findViewById(R.id.txt_status);
                holder.txt_enddate = (TextView) v.findViewById(R.id.txt_enddate);
                holder.txt_fromdate = (TextView) v.findViewById(R.id.txt_fromdate);
                holder.txt_leavedesk = (TextView) v.findViewById(R.id.txt_leavedesk);




                v.setTag(holder);
            } else {

                holder = (ListContent) v.getTag();
            }
            try{
                holder.txt_status.setText(nitification1s.get(position).getStatus());
                holder.txt_leavedesk.setText(nitification1s.get(position).getLeavedesk());
                holder.txt_fromdate.setText(nitification1s.get(position).getFromdate());
                holder.txt_enddate.setText(nitification1s.get(position).getEnddate());


                holder.txt_viewdetails.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(getActivity(),ActivityMyLeaveReportDatails.class);
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

        TextView txt_leavedesk,txt_fromdate,txt_enddate,txt_status,txt_viewdetails;

    }
}
