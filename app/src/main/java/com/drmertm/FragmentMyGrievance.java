package com.drmertm;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.drmertm.ModelClass.GrievanceWidResponse;
import com.drmertm.ModelClass.GrievencePOJO;
import com.drmertm.ModelClass.LoginResponse;
import com.drmertm.ModelClass.MyGrievance;
import com.drmertm.ModelClass.MyGrievanceApplyLISTResponse;
import com.drmertm.ModelClass.StateResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentMyGrievance extends Fragment {
    ArrayList<GrievencePOJO> list;
    RecyclerView recyclerView;
    GravinceAdapter gravinceAdapter;
    private JsonPlaceHolderApi mAPIService;
    SessionManager sessionManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fragment_my_grievance, container, false);
        sessionManager = new SessionManager(getActivity());
        mAPIService = ApiUtils.getAPIService();
        InitView(v);
        Click();
        getGrievanceList();
        return v;
    }


    public void setAdapter(){
        gravinceAdapter=new GravinceAdapter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(gravinceAdapter);
        gravinceAdapter.notifyDataSetChanged();
    }


    private void Click() {
    }

    private void InitView(View v) {
        /*list = (ListView) v.findViewById(R.id.notification_list);*/
        recyclerView=(RecyclerView)v.findViewById(R.id.recyclerView);
    }

/*
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
                holder.txt_section.setText(nitification1s.get(position).getSection());
                holder.txt_dept.setText(nitification1s.get(position).getDep());
                holder.txt_gritype.setText(nitification1s.get(position).getGritype());
                holder.txt_griunino.setText(nitification1s.get(position).getGriunino());


                holder.txt_viewdetails.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(getActivity(),ActivityMyGrievanceDatails.class);
                        startActivity(intent);
                    }
                });

            }catch (Exception e){
                e.printStackTrace();
            }
            return v;
        }
    }
*/

/*
    static class ListContent {

        TextView txt_viewdetails,txt_status,txt_section,txt_dept,txt_gritype,txt_griunino;

    }
*/

    public class GravinceAdapter extends RecyclerView.Adapter<GravinceAdapter.MyViewHolder>{
        Context context;
        public GravinceAdapter(Context context){
            this.context=context;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.custome_notification_list, parent,false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            String status = list.get(position).getStatus();

            if (status.contains("0"))
            {
                holder.txt_status.setText("Pending");
                holder.layout_withdrawal.setVisibility(View.GONE);
            }
            else if (status.contains("1"))
            {
                holder.txt_status.setText("Pending");
                holder.layout_withdrawal.setVisibility(View.GONE);
            }
            else if (status.contains("2"))
            {
                holder.txt_status.setText("Complete");
                holder.layout_withdrawal.setVisibility(View.GONE);
            }
            else if (status.contains("3"))
            {
                holder.txt_status.setText("Return");
                holder.layout_withdrawal.setVisibility(View.GONE);
            }
            else if (status.contains("4"))
            {
                holder.txt_status.setText("Withdrawal");
                holder.layout_withdrawal.setVisibility(View.GONE);
            }
            else if (status.contains("5"))
            {
                holder.txt_status.setText("Forwarded");
                holder.layout_withdrawal.setVisibility(View.GONE);
            }

            holder.txt_section.setText(list.get(position).getSectionName());
            holder.txt_dept.setText(list.get(position).getDepartmentName());
            holder.txt_gritype.setText(list.get(position).getGrievancetypeName());
            holder.txt_griunino.setText(list.get(position).getUniqueId());

            holder.txt_viewdetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),ActivityMyGrievanceDatails.class);

                    intent.putExtra("refno",list.get(position).getUniqueId());
                    intent.putExtra("date",list.get(position).getCreatedOn());
                    intent.putExtra("typegri",list.get(position).getGrievancetypeName());
                    intent.putExtra("desc",list.get(position).getDescription());
                    intent.putExtra("dept",list.get(position).getMyDepartmentName());
                    intent.putExtra("section",list.get(position).getSectionName());
                    intent.putExtra("inchsec","-");
                    intent.putExtra("penretrep",holder.txt_status.getText().toString().trim());
                    intent.putExtra("pfno",sessionManager.getSavedEmpno());
                    intent.putExtra("comname",sessionManager.getSavedUserName());
                    intent.putExtra("comdesi",list.get(position).getDesignationName());
                    intent.putExtra("comdept",list.get(position).getDepartmentName());
                    intent.putExtra("comstation",list.get(position).getStationName());
                    intent.putExtra("comoffadd",list.get(position).getCurrentOffice());
                    intent.putExtra("comresadd",list.get(position).getResidentialAddress());
                    intent.putExtra("compincode","-");
                    intent.putExtra("commobile",list.get(position).getEmpMobileNumber());
                    intent.putExtra("remark",list.get(position).getRemark());
                    intent.putExtra("replyattachment",list.get(position).getReplyattachment());
                    startActivity(intent);
                }
            });

            holder.txt_withdrawal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(Utils.isInternetConnected(getActivity())) {

                        System.out.println("emp no >>>>>>>>>>"+sessionManager.getSavedEmpno());
                        System.out.println("id >>>>>>>>>>"+list.get(position).getId());
                        sendwithdrawal(sessionManager.getSavedEmpno(),list.get(position).getId(),"");
                    }
                    else {
                        CustomAlertdialog.createDialog(getActivity(),getString(R.string.no_internet));
                    }

                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{
            TextView txt_viewdetails,txt_status,txt_section,txt_dept,txt_gritype,txt_griunino,txt_withdrawal;
            LinearLayout layout_withdrawal;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                txt_viewdetails = (TextView) itemView.findViewById(R.id.txt_viewdetails);
                txt_status = (TextView) itemView.findViewById(R.id.txt_status);
                txt_section = (TextView) itemView.findViewById(R.id.txt_section);
                txt_dept = (TextView) itemView.findViewById(R.id.txt_dept);
                txt_gritype = (TextView) itemView.findViewById(R.id.txt_gritype);
                txt_griunino = (TextView) itemView.findViewById(R.id.txt_griunino);
                txt_withdrawal = (TextView) itemView.findViewById(R.id.txt_withdrawal);
                layout_withdrawal = (LinearLayout) itemView.findViewById(R.id.layout_withdrawal);
            }
        }
    }

    public void getGrievanceList() {
        Customprogress.showPopupProgressSpinner(getActivity(),true);
        mAPIService.getGrievanceList(sessionManager.getSavedEmpno()).enqueue(new Callback<MyGrievanceApplyLISTResponse>() {
            @Override
            public void onResponse(Call<MyGrievanceApplyLISTResponse> call, Response<MyGrievanceApplyLISTResponse> response) {

//                Customprogress.showPopupProgressSpinner(ActivityLogin.this,false);
                Customprogress.showPopupProgressSpinner(getActivity(),false);
                if(response.isSuccessful()) {
                    System.out.println("Status >>>>>>>>>>>>"+response.body().getStatus());
                    System.out.println("Message >>>>>>>>>>>>"+response.body().getMessage());

                    boolean status = response.body().getStatus();
                    list=new ArrayList<>();
                    if (status==true)
                    {
                        MyGrievanceApplyLISTResponse res = response.body();
                        for (int i=0;i<res.getData().size();i++){

                           String id = response.body().getData().get(i).getId();
                            String description = response.body().getData().get(i).getDescription();
                             String attachment = response.body().getData().get(i).getAttachment();
                           String pin = response.body().getData().get(i).getPin();
                            String remark = response.body().getData().get(i).getRemark();
                            String replyattachment = response.body().getData().get(i).getReplyattachment();
                            String uniqueId = response.body().getData().get(i).getUniqueId();
                            String forwardToId = response.body().getData().get(i).getForwardToId();
                            String forwardFromId = response.body().getData().get(i).getForwardFromId();
                            String empId = response.body().getData().get(i).getEmpId();
                            String departmentId = response.body().getData().get(i).getDepartmentId();
                            String departmentIsColony = response.body().getData().get(i).getDepartmentIsColony();
                            String workingCityId = response.body().getData().get(i).getWorkingCityId();
                            String colonyId = response.body().getData().get(i).getColonyId();
                            String sectionId = response.body().getData().get(i).getSectionId();
                            String grievancetypeId = response.body().getData().get(i).getGrievancetypeId();
                            String attachStatus = response.body().getData().get(i).getAttachStatus();
                            String currentOffice = response.body().getData().get(i).getCurrentOffice();
                            String stationId = response.body().getData().get(i).getStationId();
                            String residentialAddress = response.body().getData().get(i).getResidentialAddress();
                            String stateId = response.body().getData().get(i).getStateId();
                            String cityId = response.body().getData().get(i).getCityId();
                            String forwardMainId = response.body().getData().get(i).getForwardMainId();
                            String returnReasonId = response.body().getData().get(i).getReturnReasonId();
                            String returnDate = response.body().getData().get(i).getReturnDate();
                            String completeDate = response.body().getData().get(i).getCompleteDate();
                            String withdrawalDate = response.body().getData().get(i).getWithdrawalDate();
                            String forwardDate = response.body().getData().get(i).getForwardDate();
                            String gstatus = response.body().getData().get(i).getStatus();
                            String applyDate = response.body().getData().get(i).getApplyDate();
                            String createdOn = response.body().getData().get(i).getCreatedOn();
                            String updatedOn = response.body().getData().get(i).getUpdatedOn();
                            String myDepartmentName = response.body().getData().get(i).getMyDepartmentName();
                            String departmentName = response.body().getData().get(i).getDepartmentName();
                            String sectionName = response.body().getData().get(i).getSectionName();
                            String grievancetypeName = response.body().getData().get(i).getGrievancetypeName();
                            String stationName = response.body().getData().get(i).getStationName();
                            String stateName = response.body().getData().get(i).getStateName();
                            String cityName = response.body().getData().get(i).getCityName();
                            String empMobileNumber = response.body().getData().get(i).getEmpMobileNumber();
                            String designationName = response.body().getData().get(i).getDesignationName();

                            list.add(new GrievencePOJO( id,  uniqueId, forwardToId, forwardFromId, empId,  departmentId,  departmentIsColony,  workingCityId,  colonyId, sectionId, grievancetypeId,  description,attachStatus, attachment, currentOffice,  stationId,  residentialAddress,stateId,  cityId, pin, forwardMainId, returnReasonId,  remark, replyattachment, returnDate,  completeDate, withdrawalDate,  forwardDate,  gstatus,  applyDate, createdOn,  updatedOn,myDepartmentName,  departmentName, sectionName,  grievancetypeName,  stationName, stateName,  cityName, empMobileNumber,designationName));
                        }
                        setAdapter();
                    }
                    else
                    {
//                        Toast.makeText(ActivityLogin.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<MyGrievanceApplyLISTResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(getActivity(),false);
//                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }


    public void sendwithdrawal(String empid, String grieid, String remark) {
        Customprogress.showPopupProgressSpinner(getActivity(),true);
        System.out.println("emp id >>>>>>>>>>>>"+empid);
        System.out.println("grievance id >>>>>>>>>>>>"+grieid);
        mAPIService.grievancewid(empid, grieid).enqueue(new Callback<GrievanceWidResponse>() {
            @Override
            public void onResponse(Call<GrievanceWidResponse> call, Response<GrievanceWidResponse> response) {

                Customprogress.showPopupProgressSpinner(getActivity(),false);

                if(response.isSuccessful()) {
//                    Log.i(TAG, "post submitted to API." + response.body().toString());
//                    response.body().getMessage();
//                    response.body().getStatus();
//                    response.body().getData();
                    System.out.println("Status >>>>>>>>>>>>"+response.body().getStatus());
                    System.out.println("Message >>>>>>>>>>>>"+response.body().getMessage());

                    boolean status = response.body().getStatus();

                    if (status==true)
                    {
                        Toast.makeText(getActivity(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        ClearData();
                    }
                    else
                    {
                        Toast.makeText(getActivity(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<GrievanceWidResponse> call, Throwable t) {
//                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }

    public  void ClearData()
    {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false);
        }
        ft.detach(this).attach(this).commit();
    }

}
