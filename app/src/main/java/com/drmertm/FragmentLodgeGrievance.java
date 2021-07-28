package com.drmertm;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.drmertm.Adapter.FruitAdapter;
import com.drmertm.ModelClass.CitiesResponse;
import com.drmertm.ModelClass.ColonyResponse;
import com.drmertm.ModelClass.DeptModelClass;
import com.drmertm.ModelClass.DeptResponse;
import com.drmertm.ModelClass.GrieTypeResponse;
import com.drmertm.ModelClass.GrievanceApplyResponse;
import com.drmertm.ModelClass.LoginResponse;
import com.drmertm.ModelClass.MyGrievance;
import com.drmertm.ModelClass.SectionResponse;
import com.drmertm.ModelClass.StateResponse;
import com.drmertm.ModelClass.StationModelClass;
import com.drmertm.ModelClass.StationResponse;
import com.drmertm.ModelClass.WorkingStationResponse;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;


public class FragmentLodgeGrievance extends Fragment {

    LinearLayout layout_yes,layout_no,layout_attach,layout_doty,layout_dotn;
    LinearLayout layout_state,layout_dept,layout_hide,layout_grie,layout_quaroa,layout_auto;
    Spinner spidept,spisect,spigrie,spistation,spistates,spicities,spiworcity,spiconoly;
    private JsonPlaceHolderApi mAPIService;
    SessionManager sessionManager;

    TextView txt_submit,txt_reset,txt_choosefile,tv_no_fill_chosen;
    ArrayList<String> deptnamelist = new ArrayList<>();
    ArrayList<String> deptidlist = new ArrayList<>();
    ArrayList<String> deptiscolonylist = new ArrayList<>();
    ArrayList<String> sectnamelist = new ArrayList<>();
    ArrayList<String> sectidlist = new ArrayList<>();
    ArrayList<String> grienamelist = new ArrayList<>();
    ArrayList<String> grieidlist = new ArrayList<>();
    ArrayList<String> stationnamelist = new ArrayList<>();
    ArrayList<String> stationidlist = new ArrayList<>();
    ArrayList<String> statesnamelist = new ArrayList<>();
    ArrayList<String> statesidlist = new ArrayList<>();
    ArrayList<String> citiesnamelist = new ArrayList<>();
    ArrayList<String> citiesidlist = new ArrayList<>();
    ArrayList<String> workingcitiesnamelist = new ArrayList<>();
    ArrayList<String> workingcitiesidlist = new ArrayList<>();
    ArrayList<String> colonynamelist = new ArrayList<>();
    ArrayList<String> colonyidlist = new ArrayList<>();

    int PICK_FILE_CODE=101;
    public String picturePath="";
    public String emp_id="";
    public String department_id="";
    public String grievancetype_id="";
    public String section_id="";
    public String description="";
    public String attach_status="0";
    public String current_office="";
    public String station_id="";
    public String residential_address="";
    public String state_id="11";
    public String city_id="283";
    public String attachment="";
    public  String picturename="";
    public  String working_city_id="0";
    public  String colony_id="0";
    EditText txt_desc,txt_curroff,txt_address;

    private Uri filePath;

    private ArrayList<StationModelClass> fruitArrayList;

    private FruitAdapter fruitAdapter;
    TextView txt_auto;
    String name;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     View v = inflater.inflate(R.layout.fragment_fragment_lodge_grievance, container, false);
        sessionManager = new SessionManager(getActivity());
        mAPIService = ApiUtils.getAPIService();
        InitView(v);
        Click();

        fruitArrayList = new ArrayList<>();



        if(Utils.isInternetConnected(getActivity())) {

            deptnamelist.clear();
            deptidlist.clear();
            deptiscolonylist.clear();
            stationnamelist.clear();
            stationidlist.clear();
            statesnamelist.clear();
            statesidlist.clear();
            citiesnamelist.clear();
            citiesidlist.clear();
            workingcitiesnamelist.clear();
            workingcitiesidlist.clear();
            fruitArrayList.clear();
            senddeptlist();


        }
        else {
            CustomAlertdialog.createDialog(getActivity(),getString(R.string.no_internet));
        }


        layout_dotn.setBackground(getResources().getDrawable(R.drawable.blue_fill_dot));
        layout_doty.setBackground(getResources().getDrawable(R.drawable.gray_fill_dot));
        layout_attach.setVisibility(View.GONE);
     return v;
    }

    private void Click() {
        layout_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_dotn.setBackground(getResources().getDrawable(R.drawable.blue_fill_dot));
                layout_doty.setBackground(getResources().getDrawable(R.drawable.gray_fill_dot));
                layout_attach.setVisibility(View.GONE);
                tv_no_fill_chosen.setText("No fill chosen");
                picturePath="";
                attach_status="0";
            }
        });

        layout_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_dotn.setBackground(getResources().getDrawable(R.drawable.gray_fill_dot));
                layout_doty.setBackground(getResources().getDrawable(R.drawable.blue_fill_dot));
                layout_attach.setVisibility(View.VISIBLE);
                attach_status="1";
            }
        });

        spidept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Object item = adapterView.getItemAtPosition(position);

                if (adapterView.getId() == R.id.spidept) {
                    String gender = adapterView.getItemAtPosition(position).toString();
//                    Toast.makeText(getActivity(), gender, Toast.LENGTH_SHORT).show();

                    String sectionid = deptidlist.get(position);
                    String is_colony = deptiscolonylist.get(position);


                    if (!sectionid.contains("0"))
                    {
//                        Toast.makeText(getActivity(), gender, Toast.LENGTH_SHORT).show();
                        System.out.println("dept id >>>>>>>>>>>>>>"+sectionid);
                        department_id=sectionid;

                        if(Utils.isInternetConnected(getActivity())) {

                            grienamelist.clear();
                            grieidlist.clear();
                            sendgrielist(department_id);
                        }
                        else {
                            CustomAlertdialog.createDialog(getActivity(),getString(R.string.no_internet));
                        }

                        layout_grie.setVisibility(View.VISIBLE);

                        if (is_colony.contains("0"))
                        {
                            layout_hide.setVisibility(View.GONE);
                            layout_quaroa.setVisibility(View.GONE);

                        }
                        else
                        {

                            layout_hide.setVisibility(View.VISIBLE);
                            layout_quaroa.setVisibility(View.VISIBLE);
                        }

                    }
                    else
                    {
                        layout_grie.setVisibility(View.GONE);
                    }
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // TODO Auto-generated method stub

            }
        });

        /*
          spisect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Object item = adapterView.getItemAtPosition(position);

                if (adapterView.getId() == R.id.spisect) {
                    String gender = adapterView.getItemAtPosition(position).toString();
//                    Toast.makeText(getActivity(), gender, Toast.LENGTH_SHORT).show();

                    String sectionid1 = sectidlist.get(position);


                    if (!sectionid1.contains("0"))
                    {
//                        Toast.makeText(getActivity(), gender, Toast.LENGTH_SHORT).show();
                        System.out.println("sect id >>>>>>>>>>>>>>"+sectionid1);
                        section_id=sectionid1;

                    }
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // TODO Auto-generated method stub

            }
        });

         */

        spigrie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Object item = adapterView.getItemAtPosition(position);

                if (adapterView.getId() == R.id.spigrie) {
                    String gender = adapterView.getItemAtPosition(position).toString();
//                    Toast.makeText(getActivity(), gender, Toast.LENGTH_SHORT).show();

                    String sectionid = grieidlist.get(position);

                    System.out.println("grie id >>>>>>>>>>>>>>"+sectionid);
                    if (!sectionid.equals("0"))
                    {
//                        Toast.makeText(getActivity(), gender, Toast.LENGTH_SHORT).show();
                        grievancetype_id=sectionid;

                    }
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // TODO Auto-generated method stub

            }
        });

        spistation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Object item = adapterView.getItemAtPosition(position);

                if (adapterView.getId() == R.id.spistation) {
                    String gender = adapterView.getItemAtPosition(position).toString();
//                    Toast.makeText(getActivity(), gender, Toast.LENGTH_SHORT).show();

                    String sectionid = stationidlist.get(position);


                    if (!sectionid.contains("0"))
                    {
//                        Toast.makeText(getActivity(), gender, Toast.LENGTH_SHORT).show();
                        System.out.println("grie id >>>>>>>>>>>>>>"+sectionid);
                        station_id=sectionid;

                    }
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // TODO Auto-generated method stub

            }
        });

        spiworcity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Object item = adapterView.getItemAtPosition(position);

                if (adapterView.getId() == R.id.spiworcity) {
                    String gender = adapterView.getItemAtPosition(position).toString();
//                    Toast.makeText(getActivity(), gender, Toast.LENGTH_SHORT).show();

                    String sectionid = workingcitiesidlist.get(position);


                    if (!sectionid.equals("0"))
                    {
//                        Toast.makeText(getActivity(), gender, Toast.LENGTH_SHORT).show();
                        System.out.println("Working city id >>>>>>>>>>>>>>"+sectionid);
                        working_city_id=sectionid;

                        if(Utils.isInternetConnected(getActivity())) {

                            System.out.println("Colony service calling >>>>>>>>>>>>>>");
                            colonynamelist.clear();
                            colonyidlist.clear();
                            sendcolonylist(working_city_id);
                        }
                        else {
                            CustomAlertdialog.createDialog(getActivity(),getString(R.string.no_internet));
                        }

                    }
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // TODO Auto-generated method stub

            }
        });

        spistates.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Object item = adapterView.getItemAtPosition(position);

                if (adapterView.getId() == R.id.spistates) {
                    String gender = adapterView.getItemAtPosition(position).toString();
//                    Toast.makeText(getActivity(), gender, Toast.LENGTH_SHORT).show();

                    String sectionid = statesidlist.get(position);
                    citiesnamelist.clear();
                    citiesidlist.clear();
                    sendcitieslist(sectionid);

                    if (!sectionid.contains("0"))
                    {
//                        Toast.makeText(getActivity(), gender, Toast.LENGTH_SHORT).show();
                        System.out.println("grie id >>>>>>>>>>>>>>"+sectionid);
                        state_id=sectionid;


                    }
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // TODO Auto-generated method stub

            }
        });

        spiconoly.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Object item = adapterView.getItemAtPosition(position);

                if (adapterView.getId() == R.id.spiconoly) {
                    String gender = adapterView.getItemAtPosition(position).toString();
//                    Toast.makeText(getActivity(), gender, Toast.LENGTH_SHORT).show();

                    String sectionid = colonyidlist.get(position);
                    if (!sectionid.contains("0"))
                    {
//                        Toast.makeText(getActivity(), gender, Toast.LENGTH_SHORT).show();
                        System.out.println("city id >>>>>>>>>>>>>>"+sectionid);
                        colony_id=sectionid;


                    }
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // TODO Auto-generated method stub

            }
        });

        spicities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Object item = adapterView.getItemAtPosition(position);

                if (adapterView.getId() == R.id.spicities) {
                    String gender = adapterView.getItemAtPosition(position).toString();
//                    Toast.makeText(getActivity(), gender, Toast.LENGTH_SHORT).show();

                    String sectionid = citiesidlist.get(position);
                    if (!sectionid.contains("0"))
                    {
//                        Toast.makeText(getActivity(), gender, Toast.LENGTH_SHORT).show();
                        System.out.println("city id >>>>>>>>>>>>>>"+sectionid);
                            city_id=sectionid;


                    }
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // TODO Auto-generated method stub

            }
        });


        txt_choosefile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FILE_CODE);
                } else {
                    Intent intent = new Intent();
                    intent.setType("*/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_FILE_CODE);
                }


            }
        });

        txt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                description=txt_desc.getText().toString().trim();
                current_office=txt_curroff.getText().toString().trim();
                residential_address=txt_address.getText().toString().trim();

                if (current_office.isEmpty())
                {
                    CustomAlertdialog.createDialog(getActivity(),"Please enter current posting office !");
                }
                else if (txt_auto.getText().toString().trim().isEmpty())
                {
                    CustomAlertdialog.createDialog(getActivity(),"Please enter posting station's code !");
                }
                else {
                    sendGrievanceApply();
                }
            }
        });

        txt_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClearData();
            }
        });

        layout_auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertdialogbox();
            }
        });


    }

    private void InitView(View v) {

        txt_auto = (TextView) v.findViewById(R.id.txt_auto);
        layout_yes = (LinearLayout) v.findViewById(R.id.layout_yes);
        layout_no = (LinearLayout) v.findViewById(R.id.layout_no);
        layout_attach = (LinearLayout) v.findViewById(R.id.layout_attach);
        layout_doty = (LinearLayout) v.findViewById(R.id.layout_doty);
        layout_dotn = (LinearLayout) v.findViewById(R.id.layout_dotn);
        layout_state = (LinearLayout) v.findViewById(R.id.layout_state);
        layout_dept = (LinearLayout) v.findViewById(R.id.layout_dept);
        layout_hide = (LinearLayout) v.findViewById(R.id.layout_hide);
        layout_grie = (LinearLayout) v.findViewById(R.id.layout_grie);
        layout_quaroa = (LinearLayout) v.findViewById(R.id.layout_quaroa);
        layout_auto = (LinearLayout) v.findViewById(R.id.layout_auto);
        spidept = (Spinner) v.findViewById(R.id.spidept);
//        spisect = (Spinner) v.findViewById(R.id.spisect);
        spigrie = (Spinner) v.findViewById(R.id.spigrie);
        spistation = (Spinner) v.findViewById(R.id.spistation);
        spistates = (Spinner) v.findViewById(R.id.spistates);
        spicities = (Spinner) v.findViewById(R.id.spicities);
        spiworcity = (Spinner) v.findViewById(R.id.spiworcity);
        spiconoly = (Spinner) v.findViewById(R.id.spiconoly);
        txt_submit = (TextView) v.findViewById(R.id.txt_submit);
        txt_reset = (TextView) v.findViewById(R.id.txt_reset);
        txt_choosefile= (TextView) v.findViewById(R.id.txt_choosefile);
        tv_no_fill_chosen= (TextView) v.findViewById(R.id.tv_no_fill_chosen);
        txt_desc=(EditText)v.findViewById(R.id.txt_desc);
        txt_curroff=(EditText)v.findViewById(R.id.txt_curroff);
        txt_address=(EditText)v.findViewById(R.id.txt_address);
    }

    public void senddeptlist() {
        Customprogress.showPopupProgressSpinner(getActivity(),true);
        mAPIService.getListdept().enqueue(new Callback<DeptResponse>() {
            @Override
            public void onResponse(Call<DeptResponse> call, Response<DeptResponse> response) {

//                Customprogress.showPopupProgressSpinner(ActivityLogin.this,false);

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

                        //0 is for colony and working city hide and 1 is for colony and working city visible
                        deptnamelist.add(0,"Select");
                        deptidlist.add(0,"0");
                        deptiscolonylist.add(0,"0");

                        for (int i=0;i<response.body().getData().size();i++)
                        {
                            String dept_id = response.body().getData().get(i).getId();
                            String dept_name = response.body().getData().get(i).getDEPTLONGDESC();
                            String is_colony = response.body().getData().get(i).getIsColony();


//                            DeptModelClass deptModelClass = new DeptModelClass(dept_name,dept_id);
//                            deptModelClassArrayList.add(deptModelClass);

                            deptnamelist.add(dept_name);
                            deptidlist.add(dept_id);
                            deptiscolonylist.add(is_colony);
                        }
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, deptnamelist);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spidept.setAdapter(dataAdapter);
                    }
                    else
                    {
//                        Toast.makeText(ActivityLogin.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }


                    sendstationlist();

                }
            }

            @Override
            public void onFailure(Call<DeptResponse> call, Throwable t) {
//                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }
    public void sendsectlist(String id) {
//        Customprogress.showPopupProgressSpinner(ActivityLogin.this,true);
        mAPIService.section(id).enqueue(new Callback<SectionResponse>() {
            @Override
            public void onResponse(Call<SectionResponse> call, Response<SectionResponse> response) {

//                Customprogress.showPopupProgressSpinner(ActivityLogin.this,false);

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

                        sectnamelist.add(0,"Select");
                        sectidlist.add(0,"0");
                        for (int i=0;i<response.body().getData().size();i++)
                        {
                            String dept_id = response.body().getData().get(i).getId();
                            String dept_name = response.body().getData().get(i).getName();


//                            DeptModelClass deptModelClass = new DeptModelClass(dept_name,dept_id);
//                            deptModelClassArrayList.add(deptModelClass);

                            sectnamelist.add(dept_name);
                            sectidlist.add(dept_id);
                        }
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, sectnamelist);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spisect.setAdapter(dataAdapter);
                    }
                    else
                    {
//                        Toast.makeText(ActivityLogin.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<SectionResponse> call, Throwable t) {
//                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }

    public void sendgrielist(String deptid) {
//        Customprogress.showPopupProgressSpinner(ActivityLogin.this,true);
        mAPIService.getListgrietype(deptid).enqueue(new Callback<GrieTypeResponse>() {
            @Override
            public void onResponse(Call<GrieTypeResponse> call, Response<GrieTypeResponse> response) {

//                Customprogress.showPopupProgressSpinner(ActivityLogin.this,false);

                if(response.isSuccessful()) {
//                    Log.i(TAG, "post submitted to API." + response.body().toString());
//                    response.body().getMessage();
//                    response.body().getStatus();
//                    response.body().getData();
                    System.out.println(" grievance Status >>>>>>>>>>>>"+response.body().getStatus());
                    System.out.println(" grievance Message >>>>>>>>>>>>"+response.body().getMessage());

                    boolean status = response.body().getStatus();

                    if (status==true)
                    {

                        grienamelist.add(0,"Select");
                        grieidlist.add(0,"0");
                        for (int i=0;i<response.body().getData().size();i++)
                        {
                            String dept_id = response.body().getData().get(i).getId();
                            String dept_name = response.body().getData().get(i).getName();


//                            DeptModelClass deptModelClass = new DeptModelClass(dept_name,dept_id);
//                            deptModelClassArrayList.add(deptModelClass);

                            grienamelist.add(dept_name);
                            grieidlist.add(dept_id);
                        }
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, grienamelist);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spigrie.setAdapter(dataAdapter);
                    }
                    else
                    {
                        grienamelist.add(0,"Select");
                        grieidlist.add(0,"0");
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, grienamelist);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spigrie.setAdapter(dataAdapter);

//                        Toast.makeText(ActivityLogin.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<GrieTypeResponse> call, Throwable t) {
//                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }

    public void sendstationlist() {
//        Customprogress.showPopupProgressSpinner(ActivityLogin.this,true);
        mAPIService.getListstation().enqueue(new Callback<StationResponse>() {
            @Override
            public void onResponse(Call<StationResponse> call, Response<StationResponse> response) {

//                Customprogress.showPopupProgressSpinner(ActivityLogin.this,false);

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

                        stationnamelist.add(0,"Select");
                        stationidlist.add(0,"0");

                        for (int i=0;i<response.body().getData().size();i++)
                        {
                            String dept_id = response.body().getData().get(i).getId();
                            String dept_name = response.body().getData().get(i).getSTNCODE();
                            String dept_name2 = response.body().getData().get(i).getSTNSHORTDESC();
                            String dept_name3 = response.body().getData().get(i).getSTNLONGDSCP();

                            stationnamelist.add(dept_name3);
                            stationidlist.add(dept_id);

//                            System.out.println("full name >>>>>>>>>>"+dept_name2);
                            StationModelClass stationModelClass = new StationModelClass(dept_name,dept_id);
                            fruitArrayList.add(stationModelClass);
                        }
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, stationnamelist);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spistation.setAdapter(dataAdapter);

                    }
                    else
                    {
//                        Toast.makeText(ActivityLogin.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    sendworkingstationlist();
                }
            }


            @Override
            public void onFailure(Call<StationResponse> call, Throwable t) {
//                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }

    public void sendworkingstationlist() {
//        Customprogress.showPopupProgressSpinner(ActivityLogin.this,true);
        mAPIService.getListworkingstation().enqueue(new Callback<WorkingStationResponse>() {
            @Override
            public void onResponse(Call<WorkingStationResponse> call, Response<WorkingStationResponse> response) {

//                Customprogress.showPopupProgressSpinner(ActivityLogin.this,false);

                if(response.isSuccessful()) {
                   System.out.println("working city Status >>>>>>>>>>>>"+response.body().getStatus());
                    System.out.println("working city Message >>>>>>>>>>>>"+response.body().getMessage());

                    boolean status = response.body().getStatus();

                    if (status==true)
                    {

                        workingcitiesnamelist.add(0,"Select");
                        workingcitiesidlist.add(0,"0");

                        for (int i=0;i<response.body().getData().size();i++)
                        {
                            String dept_id = response.body().getData().get(i).getId();
                            String dept_name = response.body().getData().get(i).getSTNCODE();
                            String dept_name2 = response.body().getData().get(i).getSTNSHORTDESC();
                            String dept_name3 = response.body().getData().get(i).getSTNLONGDSCP();


                            System.out.println("Working city name >>>>>>>>>"+dept_name2);
                            workingcitiesnamelist.add(dept_name2);
                            workingcitiesidlist.add(dept_id);

                        }

                        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, workingcitiesnamelist);
                        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spiworcity.setAdapter(dataAdapter1);
                    }
                    else
                    {
//                        Toast.makeText(ActivityLogin.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    sendstateslist();
                }
            }

            @Override
            public void onFailure(Call<WorkingStationResponse> call, Throwable t) {
//                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }

    public void sendstateslist() {
//        Customprogress.showPopupProgressSpinner(ActivityLogin.this,true);
        mAPIService.getListstates().enqueue(new Callback<StateResponse>() {
            @Override
            public void onResponse(Call<StateResponse> call, Response<StateResponse> response) {

                Customprogress.showPopupProgressSpinner(getActivity(),false);

                if(response.isSuccessful()) {

                    boolean status = response.body().getStatus();

                    if (status==true)
                    {

                        statesnamelist.add(0,"Select");
                        statesidlist.add(0,"0");
                        for (int i=0;i<response.body().getData().size();i++)
                        {
                            String dept_id = response.body().getData().get(i).getId();
                            String dept_name = response.body().getData().get(i).getName();

                            statesnamelist.add(dept_name);
                            statesidlist.add(dept_id);
                        }
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, statesnamelist);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spistates.setAdapter(dataAdapter);

                    }
                    else
                    {
//                        Toast.makeText(ActivityLogin.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<StateResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(getActivity(),false);
//                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }

    public void sendcitieslist(String state_id) {
//        Customprogress.showPopupProgressSpinner(ActivityLogin.this,true);
        mAPIService.getListcities(state_id).enqueue(new Callback<CitiesResponse>() {
            @Override
            public void onResponse(Call<CitiesResponse> call, Response<CitiesResponse> response) {

//                Customprogress.showPopupProgressSpinner(ActivityLogin.this,false);

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

                        citiesnamelist.add(0,"Select");
                        citiesidlist.add(0,"0");
                        for (int i=0;i<response.body().getData().size();i++)
                        {
                            String dept_id = response.body().getData().get(i).getId();
                            String dept_name = response.body().getData().get(i).getCity();


//                            DeptModelClass deptModelClass = new DeptModelClass(dept_name,dept_id);
//                            deptModelClassArrayList.add(deptModelClass);

                            citiesnamelist.add(dept_name);
                            citiesidlist.add(dept_id);
                        }
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, citiesnamelist);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spicities.setAdapter(dataAdapter);
                    }
                    else
                    {
//                        Toast.makeText(ActivityLogin.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<CitiesResponse> call, Throwable t) {
//                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }

    public void sendcolonylist(String id) {
//        Customprogress.showPopupProgressSpinner(ActivityLogin.this,true);
        mAPIService.colonylist(id).enqueue(new Callback<ColonyResponse>() {
            @Override
            public void onResponse(Call<ColonyResponse> call, Response<ColonyResponse> response) {

//                Customprogress.showPopupProgressSpinner(ActivityLogin.this,false);

                if(response.isSuccessful()) {
//                    Log.i(TAG, "post submitted to API." + response.body().toString());
//                    response.body().getMessage();
//                    response.body().getStatus();
//                    response.body().getData();
                    System.out.println("colony Status >>>>>>>>>>>>"+response.body().getStatus());
                    System.out.println(" colony Message >>>>>>>>>>>>"+response.body().getMessage());

                    boolean status = response.body().getStatus();

                    if (status==true)
                    {

                        colonynamelist.add(0,"Select");
                        colonyidlist.add(0,"0");
                        for (int i=0;i<response.body().getData().size();i++)
                        {
                            String dept_id = response.body().getData().get(i).getId();
                            String dept_name = response.body().getData().get(i).getName();


//                            DeptModelClass deptModelClass = new DeptModelClass(dept_name,dept_id);
//                            deptModelClassArrayList.add(deptModelClass);

                            colonynamelist.add(dept_name);
                            colonyidlist.add(dept_id);
                        }
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, colonynamelist);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spiconoly.setAdapter(dataAdapter);
                    }
                    else
                    {
//                        Toast.makeText(ActivityLogin.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<ColonyResponse> call, Throwable t) {
//                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }

    public void sendGrievanceApply() {
        emp_id=sessionManager.getSavedEmpno();

        String content="";
        content+="emp_id="+emp_id+"\n";
        content+="department_id="+department_id+"\n";
        content+="grievancetype_id="+grievancetype_id+"\n";
        content+="working_city_id="+working_city_id+"\n";
        content+="colony_id="+colony_id+"\n";
        content+="description="+description+"\n";
        content+="attach_status="+attach_status+"\n";
        content+="current_office="+current_office+"\n";
        content+="station_id="+station_id+"\n";
        content+="residential_address="+residential_address+"\n";
        content+="state_id="+state_id+"\n";
        content+="city_id="+city_id+"\n";
        content+="attachment="+picturePath+"\n";
        Log.e("content",content);

        if(attach_status.equalsIgnoreCase("0")) {


            Customprogress.showPopupProgressSpinner(getActivity(),true);
            mAPIService.getGrievanceApply2(
                    emp_id, department_id, grievancetype_id, working_city_id,colony_id,
                    description,attach_status,current_office,station_id,
                    residential_address,state_id,city_id,
                    ""
            ).enqueue(new Callback<GrievanceApplyResponse>() {
                @Override
                public void onResponse(Call<GrievanceApplyResponse> call, Response<GrievanceApplyResponse> response) {

                    Customprogress.showPopupProgressSpinner(getActivity(),false);

                    System.out.println("response body >>>>>>>" + response.body());
                    System.out.println("response  >>>>>>>" + response);
                    System.out.println("Status >>>>>>>>>>>>111" + response.body().getStatus());
                    System.out.println("Message >>>>>>>>>>>>222" + response.body().getMessage());
                    if (response.isSuccessful()) {
//                    Log.i(TAG, "post submitted to API." + response.body().toString());
//                    response.body().getMessage();
//                    response.body().getStatus();
//                    response.body().getData();
//                        System.out.println("Status >>>>>>>>>>>>" + response.body().getStatus());
//                        System.out.println("Message >>>>>>>>>>>>" + response.body().getMessage());

                        boolean status = response.body().getStatus();

                        if (status == true) {

                            String sectionName = response.body().getSectionName();
                            String departmentName = response.body().getDepartmentName();
                            String grievanceNumber = response.body().getGrievanceNumber();
                            createDialog(getActivity(),"Applied Successfully\nGrievance no -"+grievanceNumber+"\nDepartment -"+departmentName+"\nSection -"+sectionName);
//                            Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            ClearData();
                        } else {
                            Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }

                @Override
                public void onFailure(Call<GrievanceApplyResponse> call, Throwable t) {
                    Log.e(TAG, "Unable to submit post to API." + t.getMessage());
                    Customprogress.showPopupProgressSpinner(getActivity(),false);
                }
            });
        }else{
            if(picturename.toString().trim().equalsIgnoreCase("")
            ||picturename==null){
                Toast.makeText(getActivity(),"Please choose file",Toast.LENGTH_SHORT).show();
            }else{

                uploadMultiFile();
            }
        }
    }

    private void uploadMultiFile() {

        try {



            MultipartBody.Builder builder = new MultipartBody.Builder();
            builder.setType(MultipartBody.FORM);

            builder.addFormDataPart("emp_id", emp_id);
            builder.addFormDataPart("department_id", department_id);
            builder.addFormDataPart("grievancetype_id", grievancetype_id);
            builder.addFormDataPart("working_city_id", working_city_id);
            builder.addFormDataPart("colony_id", colony_id);
            builder.addFormDataPart("description", description);
            builder.addFormDataPart("attach_status", attach_status);
            builder.addFormDataPart("current_office", current_office);
            builder.addFormDataPart("station_id", station_id);
            builder.addFormDataPart("residential_address", residential_address);
            builder.addFormDataPart("state_id", state_id);
            builder.addFormDataPart("city_id", city_id);

            System.out.println("File path >>>>>>>>>>>>"+filePath);
            String path = FilePath.getPath(getActivity(), filePath);
//            String path = FilePath.getPath(getActivity(), filePath);
            System.out.println("path >>>>>>>>>>>>"+path);
            File file = new File(path);

            builder.addFormDataPart("attachment", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));

            Customprogress.showPopupProgressSpinner(getActivity(),true);
            MultipartBody requestBody = builder.build();
            Call<GrievanceApplyResponse> call = mAPIService.getGrievanceApply(requestBody);
            call.enqueue(new Callback<GrievanceApplyResponse>() {
                @Override
                public void onResponse(Call<GrievanceApplyResponse> call, Response<GrievanceApplyResponse> response) {

                    Customprogress.showPopupProgressSpinner(getActivity(),false);
                    System.out.println("grievance response >>>>>>"+response);
                    String sectionName = response.body().getSectionName();
                    String departmentName = response.body().getDepartmentName();
                    String grievanceNumber = response.body().getGrievanceNumber();
                    createDialog(getActivity(),"Applied Successfully\nGrievance no -"+grievanceNumber+"\nDepartment -"+departmentName+"\nSection -"+sectionName);
//                    Toast.makeText(getActivity(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    ClearData();

                }

                @Override
                public void onFailure(Call<GrievanceApplyResponse> call, Throwable t) {

                    Customprogress.showPopupProgressSpinner(getActivity(),false);

                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();

                    Log.d("TAG >>>>>>>>>>>>", "Error " + t.getMessage());
                }
            });


        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FILE_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            picturename = getRealPathFromURI(filePath);
            tv_no_fill_chosen.setText(picturename);
            Log.i(TAG, "onActivityResult: file path : " + picturename);
            /*
            System.out.println("File data >>>>>>>>>>>"+data.getData());
            Uri uri = data.getData();

            System.out.println("File Uri >>>>>>>>>>>"+uri);
            getImageFilePath(uri);

             */

        } else {
            Toast.makeText(getActivity(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
        }

    }

    public void getImageFilePath(Uri uri) {

        try {
            File file = new File(uri.getPath());
            String[] filePath = file.getPath().split(":");
            String image_id = filePath[filePath.length - 1];
            Cursor cursor = getActivity().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, MediaStore.Images.Media._ID + " = ? ", new String[]{image_id}, null);
            if (cursor != null) {
                cursor.moveToFirst();
                String imagePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                System.out.println("Image Path >>>>>>>"+imagePath);
                cursor.close();

                picturePath = imagePath;
                System.out.println("File path >>>>>>>>>>>"+picturePath);

                picturename = getRealPathFromURI(uri);

                tv_no_fill_chosen.setText(picturename);
                Log.i(TAG, "onActivityResult: file path : " + picturename);

            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private String getRealPathFromURI(Uri contentURI)
    {
        String thePath = "no-path-found";
        try {

            String[] filePathColumn = {MediaStore.Images.Media.DISPLAY_NAME};
            Cursor cursor = getActivity().getContentResolver().query(contentURI, filePathColumn, null, null, null);
            if(cursor.moveToFirst()){
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                thePath = cursor.getString(columnIndex);
            }
            cursor.close();

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return  thePath;

    }

    public  void ClearData()
    {
        txt_desc.setText("");
        txt_curroff.setText("");
        txt_address.setText("");
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false);
        }
        ft.detach(this).attach(this).commit();
    }

    public void alertdialogbox()
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();

        View dialogView=inflater.inflate(R.layout.alertdialog_autocomplete_custom_view,null);
        builder.setCancelable(false);
        builder.setView(dialogView);
        Button btn_positive =  dialogView.findViewById(R.id.dialog_positive_btn);
        Button btn_negative =  dialogView.findViewById(R.id.dialog_negative_btn);
        AppCompatAutoCompleteTextView autoTextViewCustom = (AppCompatAutoCompleteTextView) dialogView.findViewById(R.id.autoTextView);
        final Dialog dialog = builder.create();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        System.out.println("fruitArrayList >>>>>>>>>>"+fruitArrayList);
        fruitAdapter = new FruitAdapter(getActivity(), R.layout.custom_autocomplete_row, fruitArrayList);
        autoTextViewCustom.setThreshold(1);
        autoTextViewCustom.setAdapter(fruitAdapter);
        fruitAdapter.notifyDataSetChanged();
       autoTextViewCustom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                FruitAdapter fruit = (FruitAdapter) adapterView.getItemAtPosition(i);
//                fruitDesc.setText(fruit.getDesc());
                String sectionid = fruitArrayList.get(i).getId();
                name = fruitArrayList.get(i).getName();
                station_id=sectionid;
                System.out.println("grie id >>>>>>>>>>>>>>"+sectionid);
            }
        });


        btn_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.cancel();
              txt_auto.setText(name);

            }
        });

        btn_negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void createDialog(final Context context, final String message)
    {
        final Dialog dialog = new Dialog(context);


        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_warning);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        TextView text = (TextView) dialog.findViewById(R.id.content);
        text.setText(message);

        ((AppCompatButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }



}
