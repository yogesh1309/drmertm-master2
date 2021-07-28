package com.drmertm;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.drmertm.Adapter.RailwayProfMultipleImageAdapter;
import com.drmertm.ModelClass.DeptResponse;
import com.drmertm.ModelClass.IdCardResponse;
import com.drmertm.ModelClass.IdCardStatusResponse;
import com.drmertm.ModelClass.StationData;
import com.drmertm.ModelClass.StationResponse;
import com.drmertm.ModelClass.UserDetailData;
import com.drmertm.ModelClass.UserDetailResponse;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.drmertm.ManifestPermission.hasPermissions;

public class IDCardActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    //------------------------file code====================================
    String encodeimg1 = "", cartkey, signimage, multipleimages;
    String path = "";
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private int REQUEST_CAMERA1 = 3, SELECT_FILE1 = 2;
    private int REQUEST_CAMERA2 = 4, SELECT_FILE2 = 5;
    private String userChoosenTask;
    File uploadFileI;
//    FragmentActivity activity;
    private static final int GALLERY_REQUEST = 100;
    int PICK_FILE_CODE=101;
    public  String picturename="";
    private Uri filePath;
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 1;
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS2 = 2;
    public static final int PERMISSION_ALL = 101;
    int count = 0;
    //------------------------file code====================================
    private String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };
    Button btn_submit, btn_saveasdraft,btn_idstatus;
    Context context;
    private JsonPlaceHolderApi mAPIService;
    SessionManager sessionManager;
    EditText edt_hrmsid, edt_empname, et_station,edt_designation, edt_fathersname, edt_birthdate, et_complexion, et_identicationmark;
    //new editext for new form fields
    EditText  et_employee_name, edt_height,et_designation, et_fathers_name;
    TextView tv_employee_image_name, tv_employee_sign_image, tv_railwayidproof;
    LinearLayout  ll_new_empname, ll_new_designation, ll_new_fathersname, ll_new_birthdate;
    LinearLayout ll_hrmsid, ll_employeename, ll_designation, ll_fathersname, ll_dob, ll_emp_image, ll_employee_sign_image, ll_railwayproof;
    UserDetailData idcardata;
    Spinner   sp_height,sp_complexion;
    ArrayList<String> stations = new ArrayList<>();
    ArrayList<String> stationids = new ArrayList<>();
    ArrayList<String> stationshortname = new ArrayList<>();
    String station_id, station_name, department_id, department_name, height="", employeeimage;
    String emp_id,SERVICESTATUS, emp_name, designation, fathers_name, dob, complexion="", identification_mark="", paylevel,railwayid;
    ArrayList<String> deptnamelist = new ArrayList<>();
    ArrayList<String> deptidlist = new ArrayList<>();
    ArrayList<String> deptiscolonylist = new ArrayList<>();
    ArrayList<String> files = new ArrayList<>();

    LinearLayout lay_complex,ll_new_height,ll_new_complex,layout_identify,ll_new_identify,ll_raiwaymultiimg,lay_height;
    EditText edt_complex,edt_identify,et_department;
    ImageView img_idcard,img_empsign,img_back,img_cardstatus;
    RecyclerView rec_railwaymultiimg;
    RailwayProfMultipleImageAdapter imageAdapter;
    EditText et_retdob;
    String chosecode = "0";
    String retirement_date;
    ArrayAdapter<CharSequence> complexingadapter;
    Date d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_card);
        init();

        if (Utils.isInternetConnected(IDCardActivity.this)) {
            IDCardDetail(sessionManager.getSavedEmpno());
        } else {
            CustomAlertdialog.createDialog(IDCardActivity.this, getString(R.string.no_internet));
        }
    }

    private void init() {
        context = IDCardActivity.this;
        sessionManager = new SessionManager(IDCardActivity.this);
        mAPIService = ApiUtils.getAPIService();
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));
        btn_saveasdraft = findViewById(R.id.btn_saveasdraft);
        btn_submit = findViewById(R.id.btn_submit);
        lay_height = findViewById(R.id.lay_height);
        ll_new_height = findViewById(R.id.ll_new_height);
        edt_height = findViewById(R.id.edt_height);
        btn_submit.setOnClickListener(this);
        btn_saveasdraft.setOnClickListener(this);
        //spinner
//        sp_station = findViewById(R.id.sp_station);
//        sp_department = findViewById(R.id.sp_department);
        et_department = findViewById(R.id.et_department);
        sp_complexion = findViewById(R.id.sp_complexion);
        sp_height = findViewById(R.id.sp_height);
        img_idcard = findViewById(R.id.img_idcard);
        et_station = findViewById(R.id.et_station);
        img_back = findViewById(R.id.img_back);
        et_retdob = findViewById(R.id.et_retdob);
//        sp_station.setOnItemSelectedListener(this);
//        sp_department.setOnItemSelectedListener(this);


        ll_new_empname = findViewById(R.id.ll_new_empname);
        ll_new_designation = findViewById(R.id.ll_new_designation);
        ll_new_fathersname = findViewById(R.id.ll_new_fathersname);
        ll_dob = findViewById(R.id.ll_dob);
        ll_new_birthdate = findViewById(R.id.ll_new_birthdate);
        ll_designation = findViewById(R.id.ll_designation);
        ll_hrmsid = findViewById(R.id.ll_hrmsid);
        ll_employeename = findViewById(R.id.ll_employeename);
        layout_identify = findViewById(R.id.layout_identify);
        edt_identify = findViewById(R.id.edt_identify);
        ll_new_identify = findViewById(R.id.ll_new_identify);
        ll_raiwaymultiimg = findViewById(R.id.ll_raiwaymultiimg);
        rec_railwaymultiimg = findViewById(R.id.rec_railwaymultiimg);
        ll_fathersname = findViewById(R.id.ll_fathersname);
        ll_employee_sign_image = findViewById(R.id.ll_employee_sign_image);
        ll_railwayproof = findViewById(R.id.ll_railwayproof);
        lay_complex = findViewById(R.id.lay_complex);
        edt_complex = findViewById(R.id.edt_complex);
        ll_new_complex = findViewById(R.id.ll_new_complex);
        ll_employee_sign_image.setOnClickListener(this);
        img_back.setOnClickListener(this);
        ll_emp_image = findViewById(R.id.ll_emp_image);
        img_empsign = findViewById(R.id.img_empsign);
        ll_emp_image.setOnClickListener(this);
        ll_railwayproof.setOnClickListener(this);
        //old editext fields
        edt_hrmsid = findViewById(R.id.edt_hrmsid);
        edt_empname = findViewById(R.id.edt_empname);
        edt_designation = findViewById(R.id.edt_designation);
        edt_fathersname = findViewById(R.id.edt_fathersname);
        edt_birthdate = findViewById(R.id.edt_birthdate);


        //new editext fields
        et_employee_name = findViewById(R.id.et_employee_name);
        et_designation = findViewById(R.id.et_designation);
        et_fathers_name = findViewById(R.id.et_fathers_name);
        et_complexion = findViewById(R.id.et_complexion);
        et_identicationmark = findViewById(R.id.et_identicationmark);

        tv_employee_sign_image = findViewById(R.id.tv_employee_sign_image);
        tv_employee_sign_image.setOnClickListener(this);
        tv_employee_image_name = findViewById(R.id.tv_employee_image_name);
        tv_employee_image_name.setOnClickListener(this);
        tv_railwayidproof = findViewById(R.id.tv_railwayidproof);
        tv_railwayidproof.setOnClickListener(this);
        img_cardstatus = findViewById(R.id.img_cardstatus);
        img_cardstatus.setOnClickListener(this);
        btn_idstatus = findViewById(R.id.btn_idstatus);
        btn_idstatus.setOnClickListener(this);

        ArrayAdapter<CharSequence> height = ArrayAdapter.createFromResource(this, R.array.selectheight, android.R.layout.simple_spinner_item);
        height.setDropDownViewResource(R.layout.item_spinner_card);
        sp_height.setAdapter(height);
        sp_height.setOnItemSelectedListener(this);

        complexingadapter = ArrayAdapter.createFromResource(this, R.array.selectcomplexion, android.R.layout.simple_spinner_item);
        complexingadapter.setDropDownViewResource(R.layout.item_spinner_card);
         sp_complexion.setAdapter(complexingadapter);
        sp_complexion.setOnItemSelectedListener(this);
    }

    //final submit >>>1
    ///draft submit>>>
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                System.out.println("is valid status >>>>>>>>>"+isValid());
                if (isValid()) {
                    if (SERVICESTATUS.equals("SR")||SERVICESTATUS.equals("ST")||SERVICESTATUS.equals("sr")||SERVICESTATUS.equals("st"))
                    {
//                        Toast.makeText(context, ""+SERVICESTATUS, Toast.LENGTH_SHORT).show();
                        uploadIDcard("1");
                    }
                    else {
                        Toast.makeText(context, "Your are not aligible to submit.", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case R.id.btn_saveasdraft:
                if (isValidDraft()) {

                    if (SERVICESTATUS.equals("SR")||SERVICESTATUS.equals("ST")||SERVICESTATUS.equals("sr")||SERVICESTATUS.equals("st"))
                    {
//                        Toast.makeText(context, ""+SERVICESTATUS, Toast.LENGTH_SHORT).show();
                         uploadIDcard("0");
                    }
                    else {
                        Toast.makeText(context, "Your are not aligible to submit.", Toast.LENGTH_SHORT).show();
                    }
                }
//                uploadIDcard("0");
                break;

            case R.id.ll_emp_image:
//                selectEmployeeImage();
                /*chosecode="1";
                if (!hasPermissions(context, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(IDCardActivity.this, PERMISSIONS, REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                } else {
                    if (selectImage()) {
                        CropImage.activity().start(this);
                    }
                }*/
                Intent  intent1 = new Intent(context,ActivityImgCropping.class);
                intent1.putExtra("key",501);
                startActivityForResult(intent1,501);
                break;

            case R.id.tv_employee_image_name:
//                selectEmployeeImage();
               /* chosecode="1";
                if (!hasPermissions(context, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(IDCardActivity.this, PERMISSIONS, REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                } else {
                    if (selectImage()) {
                        CropImage.activity().start(this);
                    }
                }*/
                Intent  intent2 = new Intent(context,ActivityImgCropping.class);
                intent2.putExtra("key",501);
                startActivityForResult(intent2,501);
                break;


            case R.id.ll_employee_sign_image:
//                selectEmployeesignImage();
               /* chosecode="2";
                if (!hasPermissions(context, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(IDCardActivity.this, PERMISSIONS, REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS2);
                } else {
                    if (selectImage()) {
                        CropImage.activity().start(this);
                    }
                }*/
                Intent  intent3 = new Intent(context,ActivityImgCropping.class);
                intent3.putExtra("key",502);
                startActivityForResult(intent3,502);
                break;

            case R.id.tv_employee_sign_image:
                /*chosecode="2";
                if (!hasPermissions(context, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(IDCardActivity.this, PERMISSIONS, REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS2);
                } else {
                    if (selectImage()) {
                        CropImage.activity().start(this);
                    }
                }*/
                Intent  intent4 = new Intent(context,ActivityImgCropping.class);
                intent4.putExtra("key",502);
                startActivityForResult(intent4,502);
                break;
            case R.id.ll_railwayproof:
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(IDCardActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FILE_CODE);
                } else
                    {
                    String[] mimeTypes = {"image/*", "application/pdf"};
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
                        if (mimeTypes.length > 0)
                        {
                            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                        }
                    } else {
                        String mimeTypesStr = "";

                        for (String mimeType : mimeTypes) {
                            mimeTypesStr += mimeType + "|";
                        }

                        intent.setType(mimeTypesStr.substring(0, mimeTypesStr.length() - 1));
                    }
                    startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_FILE_CODE);
                }

                break;

            case R.id.tv_railwayidproof:
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(IDCardActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FILE_CODE);
                } else {
                    Intent intent = new Intent();
                    intent.setType("*/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_FILE_CODE);
                }

                break;
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.img_cardstatus:
                Intent intent = new Intent(context,IdCardStatusActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_idstatus:
                Intent intent5 = new Intent(context,IdCardStatusActivity.class);
                startActivity(intent5);
                break;


        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean selectImage() {
        boolean isPermission = false;
        if (!hasPermissions(context, PERMISSIONS)) {
            boolean showRationale = shouldShowRequestPermissionRationale(Manifest.permission.CAMERA);
            if (!showRationale) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            } else {
                ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
            }
        } else {
            isPermission = true;

        }

        return isPermission;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS) {
            if (grantResults.length > 0) {
                for (int i = 0; i < permissions.length; i++) {

                    if (permissions[i].equals(Manifest.permission.CAMERA)) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            Log.e("msg", "<--accounts granted");
                            count++;
                        } else {
                            DialogUtility.showToast(context, "Permission is required. ");
                        }
                    } else if (permissions[i].equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            Log.e("msg", "<--read phone granted");
                            count++;
                        } else {
                            DialogUtility.showToast(context, "Permission is required...");
                        }
                    } else if (permissions[i].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            Log.e("msg", "<--read phone granted");
                            count++;
                        } else {
                            DialogUtility.showToast(context, "Permission is required...");
                        }
                    }
                }
            }

        }
    }

    public void IDCardDetail(String empid) {
        Customprogress.showPopupProgressSpinner(IDCardActivity.this, true);
        mAPIService.userdetail(empid).enqueue(new Callback<UserDetailResponse>() {
            @Override
            public void onResponse(Call<UserDetailResponse> call, Response<UserDetailResponse> response) {
                Customprogress.showPopupProgressSpinner(IDCardActivity.this, false);

                if (response.isSuccessful()) {
                    idcardata = response.body().getData();

                    System.out.println("Status >>>>>>>>>>>>" + response.body().getStatus());
                    System.out.println("Message >>>>>>>>>>>>" + response.body().getMessage());

                    boolean status = response.body().getStatus();
                    emp_id = idcardata.getEMPNO();
                    station_name = idcardata.getStation_name();
                    department_name = idcardata.getDepartment_name();
                    emp_name = idcardata.getEMPNAME();
                    designation = idcardata.getDESIGCODE();
                    fathers_name = idcardata.getFATHERNAME();
//                    dob = idcardata.getBIRTHDATE();
                    paylevel = idcardata.getPAYLEVEL();

                    SERVICESTATUS = idcardata.getSERVICESTATUS();

                    et_station.setText(station_name);
                    et_station.setEnabled(false);
                    station_id = idcardata.getStation_id();

                    et_department.setText(department_name);
                    et_department.setEnabled(false);
                    department_id = idcardata.getDepartment_id();


                    String ret_date = idcardata.getRETIREMENTDATE();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        System.out.println("date >>>>>>>>>>"+ret_date);
                        d = sdf.parse(ret_date);
                        SimpleDateFormat targetFormat = new SimpleDateFormat("dd-MM-yyyy");
//                        SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
                        ret_date = targetFormat.format(d);
                    } catch (ParseException ex) {
                        Log.v("Exception", ex.getLocalizedMessage());
                    }
                    System.out.println("date2 >>>>>>>>>>"+ret_date);
                    et_retdob.setText(ret_date);
                    et_retdob.setEnabled(false);
                    retirement_date = ret_date;

                    if (status == true) {
                        if (idcardata.getHRMSID().equals("")) {
                            ll_hrmsid.setVisibility(View.GONE);
                        } else {
                            edt_hrmsid.setText(idcardata.getHRMSID());
                            edt_hrmsid.setEnabled(false);
                            ll_hrmsid.setVisibility(View.VISIBLE);
                        }

                        if (idcardata.getEMPNAME().equals("")) {
                            ll_new_empname.setVisibility(View.GONE);
                            ll_employeename.setVisibility(View.VISIBLE);
                        } else {
                            edt_empname.setText(idcardata.getEMPNAME());
                            edt_empname.setEnabled(false);
                            ll_employeename.setVisibility(View.GONE);
                            ll_new_empname.setVisibility(View.VISIBLE);
                        }

                        if (idcardata.getDesignationName().equals("")) {
                            ll_designation.setVisibility(View.VISIBLE);
                            ll_new_designation.setVisibility(View.GONE);
                        } else {
                            edt_designation.setText(idcardata.getDesignationName());
                            edt_designation.setEnabled(false);
                            ll_designation.setVisibility(View.GONE);
                            ll_new_designation.setVisibility(View.VISIBLE);
                        }

                        if (idcardata.getFATHERNAME().equals("")) {
                            ll_fathersname.setVisibility(View.VISIBLE);
                            ll_new_fathersname.setVisibility(View.GONE);
                        } else {
                            edt_fathersname.setText(idcardata.getFATHERNAME());
                            edt_fathersname.setEnabled(false);
                            ll_fathersname.setVisibility(View.GONE);
                            ll_new_fathersname.setVisibility(View.VISIBLE);
                        }

                        String birth_date = idcardata.getBIRTHDATE();
                        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
//                        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/mm/yyyy");
                        try {
                            System.out.println("date >>>>>>>>>>"+birth_date);
                            d = sdf1.parse(birth_date);
                            SimpleDateFormat targetFormat = new SimpleDateFormat("dd-MM-yyyy");
//                            SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
                            birth_date = targetFormat.format(d);
                        }
                        catch (ParseException ex)
                        {
                            Log.v("Exception", ex.getLocalizedMessage());
                        }
                        if (idcardata.getBIRTHDATE().equals("")) {
                            ll_new_birthdate.setVisibility(View.VISIBLE);
                            ll_dob.setVisibility(View.GONE);
                        } else {
                            dob = birth_date;
                            edt_birthdate.setText(birth_date);
                            edt_birthdate.setEnabled(false);
                            ll_dob.setVisibility(View.GONE);
                            ll_new_birthdate.setVisibility(View.VISIBLE);
                        }
                    } else {
                        // Toast.makeText(ActivityDashboard.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
                stationlist();
            }

            @Override
            public void onFailure(Call<UserDetailResponse> call, Throwable t) {
//                Log.e(TAG, "Unable to submit post to API.");
                Toast.makeText(IDCardActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

       /* if (adapterView.getId() == R.id.sp_station) {
            station_id = stationids.get(i);
            Log.d("station_ids", station_id);

        } else */
       /* if (adapterView.getId() == R.id.sp_department) {
            if (i > 0) {
                department_id = deptidlist.get(i);
                Log.d("department_id", department_id);
            }
        } else*/
        if (adapterView.getId() == R.id.sp_height) {
            if (i > 0) {
                height = sp_height.getSelectedItem().toString();
                Log.d("height>>", height);
            }
        }
        else if (adapterView.getId() == R.id.sp_complexion) {
            if (i > 0) {
                complexion = sp_complexion.getSelectedItem().toString();
                Log.d("complexion>>", complexion);
            }
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void stationlist() {
        Customprogress.showPopupProgressSpinner(context, true);
        mAPIService.getListstation().enqueue(new Callback<StationResponse>() {
            @Override
            public void onResponse(Call<StationResponse> call, Response<StationResponse> response) {

                Customprogress.showPopupProgressSpinner(context, false);

                if (response.isSuccessful()) {
                    departmentlist();
                    boolean status = response.body().getStatus();

                    if (status == true) {
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            stations.add(response.body().getData().get(i).getSTNSHORTDESC());
                            stationids.add(response.body().getData().get(i).getId());
                            stationshortname.add(response.body().getData().get(i).getSTNSHORTDESC());
                        }

                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, stationshortname);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                        sp_station.setAdapter(dataAdapter);
                        Log.d("above station name>>>", station_name);
                      /*  if (sp_station != null) {
                            int spinpos = dataAdapter.getPosition(station_name);
                            //sp_station.setSelection(spinpos);
//                            stationshortname.get(spinpos);
                            //Log.d("station name>>>",stationshortname.get(spinpos));

                            sp_station.setSelection(spinpos);
//                            sp_station.setSelection(spinpos);
                            Log.d("Below station name>>>", station_name);
                        }*/
                        //

                    } else {
                        Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<StationResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);
                Log.e("TAG", "Unable to submit post to API.");
                Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void departmentlist() {
        Customprogress.showPopupProgressSpinner(context, true);
        mAPIService.getListdeptNew().enqueue(new Callback<DeptResponse>() {
            @Override
            public void onResponse(Call<DeptResponse> call, Response<DeptResponse> response) {

                Customprogress.showPopupProgressSpinner(IDCardActivity.this, false);

                if (response.isSuccessful())
                {
                    if(Utils.isInternetConnected(context)) {

                        sendIdCardStatus();
                    }
                    else {
                        CustomAlertdialog.createDialog(context,getString(R.string.no_internet));
                    }

                    boolean status = response.body().getStatus();

                    if (status == true) {

                        for (int i = 0; i < response.body().getData().size(); i++) {
                            String dept_id = response.body().getData().get(i).getId();
                            String dept_name = response.body().getData().get(i).getDEPTLONGDESC();
                            String is_colony = response.body().getData().get(i).getIsColony();


//                            DeptModelClass deptModelClass = new DeptModelClass(dept_name,dept_id);
//                            deptModelClassArrayList.add(deptModelClass);

                            deptnamelist.add(dept_name);
                            deptidlist.add(dept_id);
                            deptiscolonylist.add(is_colony);
                        }
                        ArrayAdapter<String> datadepartment = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, deptnamelist);
                        datadepartment.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                       /* sp_department.setAdapter(datadepartment);
                        if (sp_department != null) {
                            int spinpos = datadepartment.getPosition(department_name);
                            //sp_station.setSelection(spinpos);
//                            stationshortname.get(spinpos);
                            //Log.d("station name>>>",stationshortname.get(spinpos));

                            sp_department.setSelection(spinpos);
//
                            Log.d("Below department name>>", department_name);
                        }*/

                    } else {
//                        Toast.makeText(ActivityLogin.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }


                }
            }

            @Override
            public void onFailure(Call<DeptResponse> call, Throwable t) {
//                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }

    public void sendIdCardStatus() {
        Customprogress.showPopupProgressSpinner(context,true);
        mAPIService.idcardstatus(sessionManager.getSavedEmpno()).enqueue(new Callback<IdCardStatusResponse>() {
            @Override
            public void onResponse(Call<IdCardStatusResponse> call, Response<IdCardStatusResponse> response) {

                Customprogress.showPopupProgressSpinner(context,false);

                if(response.isSuccessful()) {

                    boolean status = response.body().getStatus();

                    if (status==true)
                    {
                        emp_id = response.body().getData().getEmpNo();
                        emp_name = response.body().getData().getEmpName();
                        designation = response.body().getData().getDesignation();
                        fathers_name = response.body().getData().getFatherName();
                        station_id = response.body().getData().getStationId();
                        department_id = response.body().getData().getDepartmentId();
//                        dob = response.body().getData().getDob();
                        height = response.body().getData().getHeight();
                        complexion = response.body().getData().getComplexion();
                        identification_mark = response.body().getData().getIdentificationMark();
                        paylevel = response.body().getData().getPaylevel();
//                        retirement_date = et_retdob.getText().toString().trim();

                        et_identicationmark.setText(identification_mark);
                        encodeimg1 = response.body().getData().getEmpImageIdCard();
                        signimage = response.body().getData().getEmpSignImage();
                        if (response.body().getData().getRailwayIdProofArray().size()<=0)
                        {
                        }
                        else {
                            railwayid = response.body().getData().getRailwayIdProofArray().get(0);
                            System.out.println("railwayid >>>>>>>>>"+railwayid);
//                            tv_railwayidproof.setText(railwayid);
                            for (int i=0;i<response.body().getData().getRailwayIdProofArray().size();i++)
                            {
                                files.add(response.body().getData().getRailwayIdProofArray().get(i));
                            }
                        }

                       String IdStatus = response.body().getData().getIdStatus();
                       String SubmitStatus = response.body().getData().getSubmitStatus();
                        System.out.println("IdStatus >>>>>>>>>>"+IdStatus);
                        System.out.println("SubmitStatus >>>>>>>>>>"+SubmitStatus);
                       // IdStatus = 2 means button show and IdStatus = 0,1 means hide
                       // SubmitStatus = 0 means button show and SubmitStatus = 1 means hide
                        if (IdStatus.equals("0") || IdStatus.equals("1"))
                        {
                            if (IdStatus.equals("1"))
                            {
                                btn_saveasdraft.setVisibility(View.VISIBLE);
                                btn_submit.setVisibility(View.VISIBLE);
                            }
                            else{
                                if (SubmitStatus.equals("0"))
                                {
                                    btn_saveasdraft.setVisibility(View.VISIBLE);
                                    btn_submit.setVisibility(View.VISIBLE);
                                }
                                else if (SubmitStatus.equals("1"))
                                {
                                    btn_saveasdraft.setVisibility(View.GONE);
                                    btn_submit.setVisibility(View.GONE);
                                }
                            }
                        }
                        else {
                            btn_saveasdraft.setVisibility(View.VISIBLE);
                            btn_submit.setVisibility(View.VISIBLE);
                        }

                        ArrayAdapter<CharSequence> height = ArrayAdapter.createFromResource(context, R.array.selectheight, android.R.layout.simple_spinner_item);
                        height.setDropDownViewResource(R.layout.item_spinner_card);
                        sp_height.setAdapter(height);
                        String heightString = response.body().getData().getHeight();
                        if (sp_height != null) {
                            int spinpos = height.getPosition(heightString);
                            sp_height.setSelection(spinpos);
                        }

                        if (heightString.equals(""))
                        {
                            lay_height.setVisibility(View.VISIBLE);
                            ll_new_height.setVisibility(View.GONE);
                        }
                        else {

                            if (IdStatus.equals("2"))
                            {
                                lay_height.setVisibility(View.VISIBLE);
                                ll_new_height.setVisibility(View.GONE);
                                int spinpos = height.getPosition(heightString);
                                sp_height.setSelection(spinpos);
                            }
                            else {

                                if (IdStatus.equals("1"))
                                {
                                    lay_height.setVisibility(View.VISIBLE);
                                    ll_new_height.setVisibility(View.GONE);
                                    int spinpos = height.getPosition(heightString);
                                    sp_height.setSelection(spinpos);
                                }
                                else if (SubmitStatus.equals("0"))
                                {
                                    lay_height.setVisibility(View.VISIBLE);
                                    ll_new_height.setVisibility(View.GONE);
                                    int spinpos = height.getPosition(heightString);
                                    sp_height.setSelection(spinpos);
                                }
                                else
                                    {
                                    lay_height.setVisibility(View.GONE);
                                    ll_new_height.setVisibility(View.VISIBLE);
                                    edt_height.setText(heightString);
                                    edt_height.setEnabled(false);
                                }
                            }
                        }


                        String complexingMain = response.body().getData().getComplexion();

                        if (complexingMain.equals(""))
                        {
                            lay_complex.setVisibility(View.VISIBLE);
                            ll_new_complex.setVisibility(View.GONE);
                        }
                        else {

                            if (IdStatus.equals("2"))
                            {
                                lay_complex.setVisibility(View.VISIBLE);
                                ll_new_complex.setVisibility(View.GONE);
                                int spinpos = complexingadapter.getPosition(complexingMain);
                                sp_complexion.setSelection(spinpos);
                            }
                            else {

                                if (IdStatus.equals("1"))
                                {
                                    lay_complex.setVisibility(View.VISIBLE);
                                    ll_new_complex.setVisibility(View.GONE);
                                    int spinpos = complexingadapter.getPosition(complexingMain);
                                    sp_complexion.setSelection(spinpos);
                                }
                                else if (SubmitStatus.equals("0"))
                                {
                                    lay_complex.setVisibility(View.VISIBLE);
                                    ll_new_complex.setVisibility(View.GONE);
                                    int spinpos = complexingadapter.getPosition(complexingMain);
                                    sp_complexion.setSelection(spinpos);
                                }
                                else {
                                    lay_complex.setVisibility(View.GONE);
                                    ll_new_complex.setVisibility(View.VISIBLE);
                                    edt_complex.setText(complexingMain);
                                    edt_complex.setEnabled(false);
                                }
                            }


                        }

                        String identify = response.body().getData().getIdentificationMark();
                        if (identify.equals(""))
                        {
                            layout_identify.setVisibility(View.VISIBLE);
                            ll_new_identify.setVisibility(View.GONE);
                        }
                        else {

                            if (IdStatus.equals("2"))
                            {
                                layout_identify.setVisibility(View.VISIBLE);
                                ll_new_identify.setVisibility(View.GONE);
                                et_identicationmark.setText(identify);

                            }
                            else {
                                if (IdStatus.equals("1"))
                                {
                                    layout_identify.setVisibility(View.VISIBLE);
                                    ll_new_identify.setVisibility(View.GONE);
                                    et_identicationmark.setText(identify);
                                }
                                else if (SubmitStatus.equals("0"))
                                {
                                    layout_identify.setVisibility(View.VISIBLE);
                                    ll_new_identify.setVisibility(View.GONE);
                                    et_identicationmark.setText(identify);
                                }
                                else {
                                    layout_identify.setVisibility(View.GONE);
                                    ll_new_identify.setVisibility(View.VISIBLE);
                                    edt_identify.setText(identify);
                                    edt_identify.setEnabled(false);
                                }

                            }

                        }

                        String empidcardimg = response.body().getData().getEmpImageIdCard();
                        if (empidcardimg.equals(""))
                        {
                            img_idcard.setVisibility(View.GONE);
                            ll_emp_image.setVisibility(View.VISIBLE);
                        }
                        else {

                            if (IdStatus.equals("2"))
                            {
                                img_idcard.setVisibility(View.GONE);
                                ll_emp_image.setVisibility(View.VISIBLE);
                            }
                            else {
                                if (IdStatus.equals("1"))
                                {
                                    img_idcard.setVisibility(View.GONE);
                                    ll_emp_image.setVisibility(View.VISIBLE);
                                }
                               else if (SubmitStatus.equals("0"))
                                {
                                    img_idcard.setVisibility(View.GONE);
                                    ll_emp_image.setVisibility(View.VISIBLE);
                                    encodeimg1 = empidcardimg;
                                    tv_employee_image_name.setText(encodeimg1);

                                }
                                else
                                {
                                    img_idcard.setVisibility(View.VISIBLE);
                                    ll_emp_image.setVisibility(View.GONE);
                                    String s_image = Allurls.ImageURL+empidcardimg;

                                    if (s_image!=null&&!s_image.equalsIgnoreCase("")) {
                                        s_image = s_image.replaceAll("\\\\", "");
                                        ImageLoader.getInstance().displayImage(s_image, img_idcard);
                                    }
                                }
                            }
                        }

                        String empsingimg = response.body().getData().getEmpSignImage();
                        if (empsingimg.equals(""))
                        {
                            img_empsign.setVisibility(View.GONE);
                            ll_employee_sign_image.setVisibility(View.VISIBLE);
                        }
                        else {

                            if (IdStatus.equals("2"))
                            {
                                img_empsign.setVisibility(View.GONE);
                                ll_employee_sign_image.setVisibility(View.VISIBLE);
                            }
                            else {
                                if (IdStatus.equals("1"))
                                {
                                    img_empsign.setVisibility(View.GONE);
                                    ll_employee_sign_image.setVisibility(View.VISIBLE);
                                }
                               else if (SubmitStatus.equals("0"))
                                {
                                    img_empsign.setVisibility(View.GONE);
                                    ll_employee_sign_image.setVisibility(View.VISIBLE);
                                    signimage = empsingimg;
                                    tv_employee_sign_image.setText(empsingimg);
                                }
                                else {
                                    img_empsign.setVisibility(View.VISIBLE);
                                    ll_employee_sign_image.setVisibility(View.GONE);
                                    String s_image = Allurls.ImageURL+empsingimg;

                                    if (s_image!=null&&!s_image.equalsIgnoreCase("")) {
                                        s_image = s_image.replaceAll("\\\\", "");
                                        ImageLoader.getInstance().displayImage(s_image, img_empsign);
                                    }
                                }
                            }
                        }

                        int empidprofimgsize = response.body().getData().getRailwayIdProofArray().size();
                        if (empidprofimgsize<=0)
                        {
                            rec_railwaymultiimg.setVisibility(View.GONE);
                            ll_raiwaymultiimg.setVisibility(View.VISIBLE);
                        }
                        else {

                            if (IdStatus.equals("2"))
                            {
                                rec_railwaymultiimg.setVisibility(View.GONE);
                                ll_raiwaymultiimg.setVisibility(View.VISIBLE);
                            }
                            else {
                                if (IdStatus.equals("1"))
                                {
                                    rec_railwaymultiimg.setVisibility(View.GONE);
                                    ll_raiwaymultiimg.setVisibility(View.VISIBLE);
                                }
                               else if (SubmitStatus.equals("0"))
                                {
                                    rec_railwaymultiimg.setVisibility(View.GONE);
                                    ll_raiwaymultiimg.setVisibility(View.VISIBLE);
//                                    railwayid = response.body().getData().getRailwayIdProofArray().get(0);
                                    tv_railwayidproof.setText(response.body().getData().getRailwayIdProofArray().get(0));
                                }
                                else {
                                    rec_railwaymultiimg.setVisibility(View.VISIBLE);
                                    ll_raiwaymultiimg.setVisibility(View.GONE);
                                    rec_railwaymultiimg.setLayoutManager(new GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false));
                                    rec_railwaymultiimg.setHasFixedSize(true);
                                    imageAdapter = new RailwayProfMultipleImageAdapter(context,response.body().getData().getRailwayIdProofArray());
                                    rec_railwaymultiimg.setNestedScrollingEnabled(false);
                                    rec_railwaymultiimg.setAdapter(imageAdapter);
                                }
                            }
                        }
                    }
                    else
                    {
//                        Toast.makeText(context, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
            @Override
            public void onFailure(Call<IdCardStatusResponse> call, Throwable t) {
//                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }


    private void selectEmployeeImage() {


        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Upload Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
// boolean result = Utility.checkPermission(activity);


                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
/*
if (result)
{
System.out.println("TAKE PHOTO CLICKED");
cameraIntent();
}

*/

                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(IDCardActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CAMERA);
                    } else {
                        cameraIntent();
                    }


                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";

                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(IDCardActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, SELECT_FILE);
                    } else {
                        galleryIntent();
                    }


/*
if (result)
{
System.out.println("GALLERY OPEN");
galleryIntent();
}
*/


                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }
    private void galleryIntent() {

        System.out.println("GALLERY OPEN 22");

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }
    private void cameraIntent() {
        try {

            System.out.println("CAMERA OPEN 22");
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_CAMERA);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        System.out.println("requestCode >>>>>>>>>>"+requestCode);
        System.out.println("resultCode >>>>>>>>>>"+resultCode);

        if (resultCode == Activity.RESULT_OK) {
           /* if (requestCode == SELECT_FILE) {


                try {
                    onSelectFromGalleryResultProfile(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            } else if (requestCode == REQUEST_CAMERA) {

                onCaptureImageResultProfile(data);
            } else if (requestCode == SELECT_FILE1) {
                try {
                    onSelectFromGalleryResultSign(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == REQUEST_CAMERA1) {

                onCaptureImageResultSign(data);
            } else if (requestCode == 4) {
                multipleonCaptureImageResultProfile(data);

            } else if (requestCode == 5 && null != data) {
                if (data.getClipData() != null) {
                    System.out.println("clip data >>>>>>>>>" + data.getClipData());
                    int count = data.getClipData().getItemCount(); //evaluate the count before the for loop --- otherwise, the count is evaluated every loop.
                    System.out.println("Img count >>>>>>>>>" + count);
                    for (int i = 0; i < count; i++) {
                        Uri imageUri = data.getClipData().getItemAt(i).getUri();
                        multiplegetImageFilePath(imageUri);
                    }
                } else {
                    Bitmap bm = null;
                    if (data != null) {
                        try {
                            bm = MediaStore.Images.Media.getBitmap(IDCardActivity.this.getContentResolver(), data.getData());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    BitmapDrawable d = new BitmapDrawable(bm);
//define bounds for your drawable
                    int left = 0;
                    int top = 0;
                    int right = 40;
                    int bottom = 40;

                    Rect r = new Rect(left, top, right, bottom);
//set the new bounds to your drawable
                    d.setBounds(r);
                    Uri tempUri = getImageUri(IDCardActivity.this, bm);
//                getImageFilePath(tempUri);
                    multipleimages = getRealPathFromURI(tempUri);

                    System.out.println("multipleimages >>>>>>>>>>>" + multipleimages);
                    files.add(multipleimages);
                    String filename = "";
                    for (int i = 0; i < files.size(); i++) {
                        filename = filename + files.get(i) + ",";
                    }
                    tv_railwayidproof.setText(filename);
                }

            }
            else*/
            if (requestCode == PICK_FILE_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {

                filePath = data.getData();
                picturename = getRealPathFromURINew(filePath);
                String path = FilePath.getPath(context, filePath);
                System.out.println("path >>>>>>>>>>>>"+path);
              files.clear();
                files.add(path);
                String filename = "";
                for (int i = 0; i < 1; i++) {
                    filename = filename + files.get(i) + ",";
                }
                tv_railwayidproof.setText(filename);
                Log.i(TAG, "onActivityResult: file path : " + picturename);
            }

           /* else if (!chosecode.equals("0"))
            {
                System.out.println("chosecode >>>>>>>>"+chosecode);
                if (chosecode.equals("1"))
                {
                    if (resultCode == RESULT_OK && requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                        CropImage.ActivityResult result = CropImage.getActivityResult(data);
                        encodeimg1 = result.getUri().getPath();
                        System.out.println("signPath1 " + encodeimg1);
                        File file = new File(encodeimg1);
                        tv_employee_image_name.setText(file.getName());

                    }
                }
                else {
                    if (resultCode == RESULT_OK && requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                        CropImage.ActivityResult result = CropImage.getActivityResult(data);
                        signimage = result.getUri().getPath();
                        System.out.println("signPath2 " + signimage);
                        File file = new File(signimage);
                        tv_employee_sign_image.setText(file.getName());
                    }
                }
            }*/

        }
        else {
            if (requestCode == 501)
            {
                if (data!=null)
                {
                    encodeimg1 = data.getStringExtra("data");
                    System.out.println("encodeimg1 >>>>>>>>"+encodeimg1);
                    File file = new File(encodeimg1);
                    tv_employee_image_name.setText(file.getName());
                    System.out.println("encodeimg1 path >>>>>>>>"+file.getPath());
                }
            }
            else if (requestCode == 502) {
                if (data!=null)
                {
                    signimage = data.getStringExtra("data");
                    File file = new File(signimage);
                    tv_employee_sign_image.setText(file.getName());
                }

            }
        }

    }
    private String getRealPathFromURINew(Uri contentURI)
    {
        String thePath = "no-path-found";
        try {
            String[] filePathColumn = {MediaStore.Images.Media.DISPLAY_NAME};
            Cursor cursor = getContentResolver().query(contentURI, filePathColumn, null, null, null);
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
    private void onCaptureImageResultProfile(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Uri tempUri = getImageUri(context, thumbnail);

// CALL THIS METHOD TO GET THE ACTUAL PATH
// File finalFile = new File(getRealPathFromURI(tempUri));

        System.out.println("data.getData() " + data.getData());
        encodeimg1 = getRealPathFromURI(tempUri);
        File file = new File(encodeimg1);
        tv_employee_image_name.setText(file.getName());
        System.out.println("tempUri" + tempUri);
        ;
        //imageButtonProfile.setImageBitmap(thumbnail);

    }
    private void onSelectFromGalleryResultProfile(Intent data) {

        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        BitmapDrawable d = new BitmapDrawable(bm);
//define bounds for your drawable
        int left = 0;
        int top = 0;
        int right = 40;
        int bottom = 40;

        Rect r = new Rect(left, top, right, bottom);
//set the new bounds to your drawable
        d.setBounds(r);
        Uri tempUri = getImageUri(context, bm);

// CALL THIS METHOD TO GET THE ACTUAL PATH
// File finalFile = new File(getRealPathFromURI(tempUri));

        System.out.println("data.getData() " + data.getData());
        encodeimg1 = getRealPathFromURI(tempUri);


// encodeimg1= getRealPathFromURI(data.getData());

        System.out.println("ProfilePicPath " + encodeimg1);
        File file = new File(encodeimg1);
        tv_employee_image_name.setText(file.getName());
        //imageButtonProfile.setImageDrawable(d);

    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {

        try {

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            Long tsLong = System.currentTimeMillis() / 1000;
            path = MediaStore.Images.Media.insertImage(getContentResolver(), inImage, "" + tsLong, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Uri.parse(path);
    }
    private String getRealPathFromURI(Uri contentURI) {
        String result;


        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentURI, projection, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = 0;
            idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }

        return result;

    }
    private void employeesigngalleryIntent() {

        System.out.println("GALLERY OPEN 222");

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE1);
    }
    private void employeesigncameraIntent() {
        try {

            System.out.println("CAMERA OPEN 222");
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_CAMERA1);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void onCaptureImageResultSign(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Uri tempUri = getImageUriSign(context, thumbnail);

// CALL THIS METHOD TO GET THE ACTUAL PATH
// File finalFile = new File(getRealPathFromURI(tempUri));

        System.out.println("data.getData() " + data.getData());
        signimage = getRealPathFromSignURI(tempUri);
        File file = new File(signimage);
        tv_employee_sign_image.setText(file.getName());
        System.out.println("tempUrisign" + tempUri);
        ;
        //imageButtonProfile.setImageBitmap(thumbnail);

    }
    private void onSelectFromGalleryResultSign(Intent data) {

        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        BitmapDrawable d = new BitmapDrawable(bm);
//define bounds for your drawable
        int left = 0;
        int top = 0;
        int right = 40;
        int bottom = 40;

        Rect r = new Rect(left, top, right, bottom);
//set the new bounds to your drawable
        d.setBounds(r);
        Uri tempUri = getImageUriSign(context, bm);

// CALL THIS METHOD TO GET THE ACTUAL PATH
// File finalFile = new File(getRealPathFromURI(tempUri));

        System.out.println("data.getData() " + data.getData());
        signimage = getRealPathFromSignURI(tempUri);


// encodeimg1= getRealPathFromURI(data.getData());

        System.out.println("signPath " + signimage);
        File file = new File(signimage);
        tv_employee_sign_image.setText(file.getName());
        //imageButtonProfile.setImageDrawable(d);

    }
    public Uri getImageUriSign(Context inContext, Bitmap inImage) {

        try {

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            Long tsLong = System.currentTimeMillis() / 1000;
            path = MediaStore.Images.Media.insertImage(getContentResolver(), inImage, "" + tsLong, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Uri.parse(path);
    }
    private String getRealPathFromSignURI(Uri contentURI) {
        String result;


        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentURI, projection, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = 0;
            idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }

        return result;

    }

///multiple image upload
    private void multipleonCaptureImageResultProfile(Intent data) {
        try {

            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

            File destination = new File(Environment.getExternalStorageDirectory(),
                    System.currentTimeMillis() + ".jpg");

            FileOutputStream fo;
            try {
                destination.createNewFile();
                fo = new FileOutputStream(destination);
                fo.write(bytes.toByteArray());
                fo.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Uri tempUri = getImageUri(IDCardActivity.this, thumbnail);

// CALL THIS METHOD TO GET THE ACTUAL PATH
// File finalFile = new File(getRealPathFromURI(tempUri));

            System.out.println("data.getData() " + data.getData());
            multipleimages = getRealPathFromURI(tempUri);
           files.clear();
            files.add(multipleimages);
            String filename = "";
            for (int i = 0; i < 1; i++) {
                filename = filename + files.get(i) + ",";
            }
            tv_railwayidproof.setText(filename);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void multiplegetImageFilePath(Uri uri) {

        File file = new File(uri.getPath());
        String[] filePath = file.getPath().split(":");
        String image_id = filePath[0];
//        String image_id = filePath[filePath.length - 1];
        Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, MediaStore.Images.Media._ID + " = ? ", new String[]{image_id}, null);
        if (cursor != null) {
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
           files.clear();
            files.add(imagePath);
            cursor.close();
            String filename = "";
            for (int i = 0; i < 1; i++) {
                filename = filename + files.get(i) + ",";
            }
            tv_railwayidproof.setText(filename);

        }
    }

    private boolean isValid() {

//        complexion = et_complexion.getText().toString().trim();
        identification_mark = et_identicationmark.getText().toString().trim();
        railwayid = tv_railwayidproof.getText().toString().trim();
        /*encodeimg1 = tv_employee_image_name.getText().toString().trim();
        filename = tv_employee_sign_image.getText().toString().trim();
        filename = tv_railwayidproof.getText().toString().trim();*/
        boolean isValid = true;
        if (TextUtils.isEmpty(height))
        {
            Toast.makeText(context, "Please Select Height", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else if (TextUtils.isEmpty(complexion)) {
            Toast.makeText(context, "Please Enter complexion", Toast.LENGTH_SHORT).show();
//            et_complexion .setError("");
            isValid = false;
        } else if (TextUtils.isEmpty(identification_mark)) {
            et_identicationmark.setError("Please enter identification mark");
            isValid = false;
        } else if (TextUtils.isEmpty(encodeimg1)) {
            Toast.makeText(context, "Please select id card image", Toast.LENGTH_SHORT).show();
            //tv_employee_image_name.setError("Please select id card image");
            isValid = false;
        } else if (TextUtils.isEmpty(signimage)) {
            Toast.makeText(context, "Please select Signature", Toast.LENGTH_SHORT).show();
            //tv_employee_sign_image.setError("Please select Signature");
            isValid = false;
        } else if (TextUtils.isEmpty(railwayid))
        {
            Toast.makeText(context, "Please Select Railway id proof", Toast.LENGTH_SHORT).show();
            isValid = false;
        }
        return isValid;
    }
    private boolean isValidDraft() {

//        complexion = et_complexion.getText().toString().trim();
        identification_mark = et_identicationmark.getText().toString().trim();
        railwayid = tv_railwayidproof.getText().toString().trim();
        /*encodeimg1 = tv_employee_image_name.getText().toString().trim();
        filename = tv_employee_sign_image.getText().toString().trim();
        filename = tv_railwayidproof.getText().toString().trim();*/
        boolean isValid = true;
       /* if (TextUtils.isEmpty(height)) {
            Toast.makeText(context, "Please Select Height", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else if (TextUtils.isEmpty(complexion)) {
            Toast.makeText(context, "Please Enter complexion", Toast.LENGTH_SHORT).show();
//            et_complexion .setError("");
            isValid = false;
        } else if (TextUtils.isEmpty(identification_mark)) {
            et_identicationmark.setError("Please enter identification mark");
            isValid = false;
        } else*/

       /* if (TextUtils.isEmpty(encodeimg1)) {
            Toast.makeText(context, "Please select id card image", Toast.LENGTH_SHORT).show();
            //tv_employee_image_name.setError("Please select id card image");
            isValid = false;
        } else if (TextUtils.isEmpty(signimage)) {
            Toast.makeText(context, "Please select Signature", Toast.LENGTH_SHORT).show();
            //tv_employee_sign_image.setError("Please select Signature");
            isValid = false;
        } else if (TextUtils.isEmpty(railwayid)) {
            Toast.makeText(context, "Please Select Railway id proof", Toast.LENGTH_SHORT).show();

            isValid = false;
        }*/
        return isValid;
    }

    public void uploadIDcard(String submit_status) {
        Customprogress.showPopupProgressSpinner(context, true);
        HashMap<String, RequestBody> data = new HashMap<>();
        data.put("emp_id", createRequestBody(emp_id));
        data.put("emp_name", createRequestBody(emp_name));
        data.put("designation", createRequestBody(designation));
        data.put("father_name", createRequestBody(fathers_name));
        data.put("station_id", createRequestBody(station_id));
        data.put("department_id", createRequestBody(department_id));
        data.put("dob", createRequestBody(dob));
        data.put("height", createRequestBody(height));
        data.put("complexion", createRequestBody(complexion));
        data.put("identification_mark", createRequestBody(identification_mark));
        data.put("paylevel", createRequestBody(paylevel));
//        data.put("card_color_code", createRequestBody("3"));
        data.put("submit_status", createRequestBody(submit_status));
        data.put("retirement_date", createRequestBody(retirement_date));
        MultipartBody.Part image = null;
        if (encodeimg1 != null && !encodeimg1.equals("")){
            System.out.println("api encodeimg1 >>>>>>>>>>>"+encodeimg1);
            if (encodeimg1.contains("uploads/image/"))
            {

            }
            else {
                File file = new File(encodeimg1);
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                image = MultipartBody.Part.createFormData("emp_image_id_card", file.getName(), requestFile);
            }

        }
        MultipartBody.Part signimage1 = null;
        if (signimage != null && !signimage.equals("")) {
            System.out.println("api signimage >>>>>>>>>>>"+signimage);
            if (signimage.contains("uploads/image/"))
            {

            }
            else {
                File signfile = new File(signimage);
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), signfile);
                signimage1 = MultipartBody.Part.createFormData("emp_sign_image", signfile.getName(), requestFile);
            }
        }

        List<MultipartBody.Part> partList = new ArrayList<>();
        System.out.println("api files >>>>>>>>>>>"+files);
        for (int i = 0; i < files.size(); i++) {
            if (files.get(i).contains("uploads/image/"))
            {

            }
            else {
                File file = new File(files.get(i));
                RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part part = MultipartBody.Part.createFormData("railway_id_proof[]", file.getName(), requestBody);
                partList.add(part);
            }
        }

        mAPIService.idcardsubmit(data, image, signimage1, partList).enqueue(new Callback<IdCardResponse>() {
            @Override
            public void onResponse(Call<IdCardResponse> call, Response<IdCardResponse> response) {

                Customprogress.showPopupProgressSpinner(context, false);

                if (response.isSuccessful()) {
                    boolean status = response.body().getStatus();

                    if (status == true) {
                        Intent submit = new Intent(context, IdCardStatusActivity.class);
                       submit.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(submit);
                        finish();
                      /*  Intent intent = new Intent(context, ActivityViewTenants.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);*/
                        if (submit_status.equals("0"))
                        {
                            Toast.makeText(context, "Saved In Draft", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<IdCardResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);
                Log.e("TAG", ""+t.getMessage());
                Toast.makeText(context, "" + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public RequestBody createRequestBody(@NonNull String s) {
        return RequestBody.create(MediaType.parse("multipart/form-data"), s);
    }

}