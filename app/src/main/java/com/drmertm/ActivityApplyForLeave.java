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
import android.widget.ListView;
import android.widget.TextView;

import com.drmertm.ModelClass.Applyforleave;
import com.drmertm.ModelClass.MyGrievance;

import java.util.ArrayList;

public class ActivityApplyForLeave extends AppCompatActivity {

    ImageView img_back;
    ListView list;
    ArrayList<Applyforleave> nitification1s=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_for_leave);

        InitView();
        Click();
//test
        nitification1s.clear();
        for (int i=0;i<3;i++)
        {
            Applyforleave myGrievance = new Applyforleave("LAP","50","Apply","0");
            nitification1s.add(myGrievance);
        }

        list.isSmoothScrollbarEnabled();
        list.setDrawingCacheEnabled(true);
        list.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        list.setAdapter(new MyArrayAdapter(ActivityApplyForLeave.this));

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
        list = (ListView) findViewById(R.id.applyleave_list);
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
                v = mInflater.inflate(R.layout.custome_applyleave_list, null);

                holder = new ListContent();
                holder.txt_leavetype = (TextView) v.findViewById(R.id.txt_leavetype);
                holder.txt_clobalan = (TextView) v.findViewById(R.id.txt_clobalan);
                holder.txt_apply = (TextView) v.findViewById(R.id.txt_apply);




                v.setTag(holder);
            } else {

                holder = (ListContent) v.getTag();
            }
            try{

                holder.txt_clobalan.setText(nitification1s.get(position).getCloseblance());
                holder.txt_leavetype.setText(nitification1s.get(position).getLeavetype());


                String action = nitification1s.get(position).getAction();

                if (action.contains("Apply"))
                {
                    holder.txt_apply.setVisibility(View.VISIBLE);
                }
                else
                {
                    holder.txt_apply.setVisibility(View.GONE);
                }


                holder.txt_apply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(ActivityApplyForLeave.this,ActivityLeaveApply.class);
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

        TextView txt_leavetype,txt_clobalan,txt_apply;

    }
}
