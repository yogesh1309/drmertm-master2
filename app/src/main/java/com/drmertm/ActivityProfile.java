package com.drmertm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

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
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.drmertm.ModelClass.UpdateProfileResponse;
import com.drmertm.ModelClass.UserDetailResponse;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityProfile extends AppCompatActivity {

    ImageView img_back;
    TextView txt_submit,txt_edit,txt_mob,txt_name,txt_empno;
    CircleImageView imageButtonProfile;
    private JsonPlaceHolderApi mAPIService;
    SessionManager sessionManager;
    FragmentActivity activity;
    //------------------------file code====================================
    String encodeimg1="", cartkey;
    String path = "";
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    File uploadFileI;
    private static final int GALLERY_REQUEST = 100;
    //------------------------file code====================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        sessionManager = new SessionManager(ActivityProfile.this);
        mAPIService = ApiUtils.getAPIService();
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(ActivityProfile.this));
        activity = this;
        InitView();
        Click();

        if(Utils.isInternetConnected(ActivityProfile.this)) {
            sendUserDetail(sessionManager.getSavedEmpno()); // Last step. Logout function
        }
        else {
            CustomAlertdialog.createDialog(ActivityProfile.this,getString(R.string.no_internet));
        }
    }

    private void Click() {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        txt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadMultiFile(encodeimg1);
            }
        });

        imageButtonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectImage();
            }
        });

        txt_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityProfile.this,ActivityUpdateMobile.class);
                startActivity(intent);
            }
        });
    }

    private void InitView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        imageButtonProfile = (CircleImageView) findViewById(R.id.imageButtonProfile);
        txt_submit = (TextView) findViewById(R.id.txt_submit);
        txt_edit = (TextView) findViewById(R.id.txt_edit);
        txt_mob = (TextView) findViewById(R.id.txt_mob);
        txt_name = (TextView) findViewById(R.id.txt_name);
        txt_empno = (TextView) findViewById(R.id.txt_empno);
    }

    public void sendUserDetail(String fcmtokenid) {
        mAPIService.userdetail(fcmtokenid).enqueue(new Callback<UserDetailResponse>() {
            @Override
            public void onResponse(Call<UserDetailResponse> call, Response<UserDetailResponse> response) {

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

                        String empname =  response.body().getData().getEMPNAME();
                        String empno =  response.body().getData().getEMPNO();
                        String image = response.body().getData().getImage();
                        String mob_no = response.body().getData().getMobile();

                        txt_name.setText(empname);
                        txt_empno.setText(empno);
                        txt_mob.setText(mob_no);
                        String s_image = Allurls.ImageURL+image;

                        if (s_image!=null&&!s_image.equalsIgnoreCase("")) {
                            s_image = s_image.replaceAll("\\\\", "");

                            System.out.println("imageUrlimageUrlimageUrl"+s_image);
                            ImageLoader.getInstance().displayImage(s_image, imageButtonProfile);
                        }
                    }
                    else
                    {
//                        Toast.makeText(ActivityDashboard.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<UserDetailResponse> call, Throwable t) {
//                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }

    private void selectImage() {


        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Upload Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
//                boolean result = Utility.checkPermission(activity);


                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                  /*
                    if (result)
                    {
                        System.out.println("TAKE PHOTO CLICKED");
                        cameraIntent();
                    }

                   */

                    if (ContextCompat.checkSelfPermission(ActivityProfile.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(ActivityProfile.this, new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CAMERA);
                    } else {
                        cameraIntent();
                    }


                }
                else if (items[item].equals("Choose from Library"))
                {
                    userChoosenTask = "Choose from Library";

                    if (ContextCompat.checkSelfPermission(ActivityProfile.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(ActivityProfile.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, SELECT_FILE);
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

    private void galleryIntent()
    {

        System.out.println("GALLERY OPEN 22");

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent()
    {
        try{

            System.out.println("CAMERA OPEN 22");
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_CAMERA);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {


                try{
                    onSelectFromGalleryResultProfile(data);
                }catch (Exception e){
                    e.printStackTrace();
                }


            }else if (requestCode == REQUEST_CAMERA) {

                onCaptureImageResultProfile(data);
            }
        }
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
        Uri tempUri = getImageUri(activity,thumbnail);

        // CALL THIS METHOD TO GET THE ACTUAL PATH
        //   File finalFile = new File(getRealPathFromURI(tempUri));

        System.out.println("data.getData() " + data.getData());
        encodeimg1= getRealPathFromURI(tempUri);

        System.out.println("tempUri" + tempUri);;
        imageButtonProfile.setImageBitmap(thumbnail);

    }

    private void onSelectFromGalleryResultProfile(Intent data) {

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        BitmapDrawable d= new BitmapDrawable(bm);
//define bounds for your drawable
        int left =0;
        int top = 0;
        int right=40;
        int bottom=40;

        Rect r = new Rect(left,top,right,bottom);
//set the new bounds to your drawable
        d.setBounds(r);
        Uri tempUri = getImageUri(activity,bm);

        // CALL THIS METHOD TO GET THE ACTUAL PATH
        //  File finalFile = new File(getRealPathFromURI(tempUri));

        System.out.println("data.getData() " + data.getData());
        encodeimg1 = getRealPathFromURI(tempUri);



//        encodeimg1= getRealPathFromURI(data.getData());

        System.out.println("ProfilePicPath  " + encodeimg1);

        imageButtonProfile.setImageDrawable(d);

    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {

        try {

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return Uri.parse(path);
    }

    private String getRealPathFromURI(Uri contentURI)
    {
        String result;


        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor =activity.getContentResolver().query(contentURI, projection, null, null, null);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        System.out.println("Request Code >>>>>>>"+requestCode);
        switch (requestCode) {
            case 0: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    galleryIntent();
                    cameraIntent();

                } else {
//                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            case 1:
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    cameraIntent();
                    galleryIntent();

                } else {
//                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    private void uploadMultiFile( String img) {

        try {

            Customprogress.showPopupProgressSpinner(ActivityProfile.this,true);

            MultipartBody.Builder builder = new MultipartBody.Builder();
            builder.setType(MultipartBody.FORM);

            builder.addFormDataPart("emp_id", sessionManager.getSavedEmpno());

            File file = new File(img);
            builder.addFormDataPart("image", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));

            MultipartBody requestBody = builder.build();
            Call<UpdateProfileResponse> call = mAPIService.editprofile(requestBody);
            call.enqueue(new Callback<UpdateProfileResponse>() {
                @Override
                public void onResponse(Call<UpdateProfileResponse> call, Response<UpdateProfileResponse> response) {

                    Customprogress.showPopupProgressSpinner(ActivityProfile.this,false);

                    Toast.makeText(ActivityProfile.this, "Profile Updated.", Toast.LENGTH_LONG).show();

                    sendUserDetail(sessionManager.getSavedEmpno());

                }

                @Override
                public void onFailure(Call<UpdateProfileResponse> call, Throwable t) {

                    Customprogress.showPopupProgressSpinner(ActivityProfile.this,false);

                    Toast.makeText(ActivityProfile.this, "Profile Updated.", Toast.LENGTH_LONG).show();

//                    Toast.makeText(ActivityProfile.this, t.getMessage(), Toast.LENGTH_LONG).show();

                    Log.d("TAG >>>>>>>>>>>>", "Error " + t.getMessage());
                }
            });


        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Utils.isInternetConnected(ActivityProfile.this)) {
//            sendUserDetail(sessionManager.getSavedEmpno()); // Last step. Logout function
        }
        else {
            CustomAlertdialog.createDialog(ActivityProfile.this,getString(R.string.no_internet));
        }
    }
}