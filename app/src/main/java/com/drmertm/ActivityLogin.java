package com.drmertm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.drmertm.ModelClass.LoginResponse;
import com.google.firebase.iid.FirebaseInstanceId;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityLogin extends AppCompatActivity {

    TextView txt_request,txt_login,txt_reset;
    EditText edt_pass,edt_empid;
    String s_password,s_empid;
    private JsonPlaceHolderApi mAPIService;
    SessionManager sessionManager;
    ImageView img_password;
    boolean is_click = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sessionManager = new SessionManager(ActivityLogin.this);
        mAPIService = ApiUtils.getAPIService();

        // Firebase Token
        System.out.println("Firebase token>>>>>>>>>> "+ FirebaseInstanceId.getInstance().getToken());
        sessionManager.setSavedFcmtoken(FirebaseInstanceId.getInstance().getToken());

        //Device Id
        String m_androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        System.out.println("Device Id >>>>>>>>>> "+ m_androidId);
        sessionManager.setSavedDeviceid(m_androidId);

        InitView();
        Click();
    }

    private void Click() {


        img_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (!is_click)
               {
                   is_click=true;
                   edt_pass.setTransformationMethod(null);
                   img_password.setImageDrawable(getResources().getDrawable(R.drawable.icopenpassword));

               }
               else
               {
                   is_click=false;
                   edt_pass.setTransformationMethod(new PasswordTransformationMethod());
                   img_password.setImageDrawable(getResources().getDrawable(R.drawable.ic_closepassword));
               }
            }
        });

        txt_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityLogin.this, ActivitySignup.class);
                startActivity(intent);
            }
        });

        txt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                s_empid = edt_empid.getText().toString().trim();
                s_password = edt_pass.getText().toString().trim();

                if (s_empid.isEmpty())
                {
                    edt_empid.setError("Please enter employee id!");
                }
                else if (s_empid.length()!=11)
                {
                    edt_empid.setError("Please enter valid employee id!");
                }
                else if (s_password.isEmpty())
                {
                    edt_pass.setError("Please enter password!");
                }
                else if (s_password.length()<6)
                {
                    edt_pass.setError("Password size is small, at least 6 characters minimum!");
                }
                else
                {
                    if(Utils.isInternetConnected(ActivityLogin.this)) {

                        sendlogin(s_empid,s_password);
                    }
                    else {
                        CustomAlertdialog.createDialog(ActivityLogin.this,getString(R.string.no_internet));
                    }

                }
//                Intent intent = new Intent(ActivityLogin.this,ActivityDashboard.class);
//                startActivity(intent);
//                finish();
            }
        });

        txt_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                alertdialogbox();
                Intent intent = new Intent(ActivityLogin.this,ActivityForgotPassword.class);
                startActivity(intent);
            }
        });
    }

    private void InitView() {

        txt_request= (TextView)findViewById(R.id.txt_request);
        txt_login= (TextView)findViewById(R.id.txt_login);
        txt_reset= (TextView)findViewById(R.id.txt_reset);
        edt_pass= (EditText) findViewById(R.id.edt_pass);
        edt_empid= (EditText) findViewById(R.id.edt_empid);
        img_password= (ImageView) findViewById(R.id.img_password);
    }

    public void alertdialogbox()
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityLogin.this);
        LayoutInflater inflater = getLayoutInflater();

        View dialogView=inflater.inflate(R.layout.alertdialog_custom_view,null);
        builder.setCancelable(false);
        builder.setView(dialogView);
        Button btn_positive =  dialogView.findViewById(R.id.dialog_positive_btn);
        Button btn_negative =  dialogView.findViewById(R.id.dialog_negative_btn);
        final EditText et_name = dialogView.findViewById(R.id.et_empid);
        final Dialog dialog = builder.create();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        btn_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.cancel();
                OTPalertdialogbox();
                /*
                emails = et_name.getText().toString();
                if (emails.isEmpty())
                {
                    et_name.setError("Please Enter Email Address.");
                }
                else if (!emails.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")) {

                    et_name.setError("Please Enter valid Email Address.");
                }
                else {

                    if(Utils.isInternetConnected(Login_Activity.this)) {
                        new EmailAs().execute();
                        dialog.cancel();
                    }
                    else {
                        CustomAlertdialog.createDialog(Login_Activity.this,getString(R.string.no_internet));
                        dialog.cancel();
                    }
                }

                 */

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
    public void OTPalertdialogbox()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityLogin.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.otp_infom_layout,null);
        builder.setCancelable(false);
        builder.setView(dialogView);
        Button btn_positive = dialogView.findViewById(R.id.btnsubmitOTP);
        Button btn_negative =  dialogView.findViewById(R.id.btncancelOTP);
        final EditText edit_otp =  dialogView.findViewById(R.id.edit_otp);
        final EditText edit_password=dialogView.findViewById(R.id.edit_otp_pass);
        final EditText edit_otp_password_conf=dialogView.findViewById(R.id.edit_otp_pass_conf);
        final AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        btn_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                otp = edit_otp.getText().toString();
                OTPpassword=edit_password.getText().toString();
                OTPconpassword=edit_otp_password_conf.getText().toString();

                if (otp.isEmpty())
                {
                    edit_otp.setError("Please Enter OTP !");
                }
                else if(OTPpassword.isEmpty())
                {
                    edit_password.setError("Please Enter New Password !");
                }
                else if(OTPpassword.length() < 6)
                {
                    edit_password.setError("You must have 6 characters in your password !");
                }
                else if(OTPconpassword.isEmpty())
                {
                    edit_otp_password_conf.setError("Please Enter New Password !");
                }
                else if(OTPconpassword.length() < 6)
                {
                    edit_otp_password_conf.setError("You must have 6 characters in your password !");
                }
                else if(OTPpassword.equals(OTPconpassword))
                {

                    if(Utils.isInternetConnected(Login_Activity.this)) {
                        new OTPAs().execute();

                    }
                    else {
                        CustomAlertdialog.createDialog(Login_Activity.this,getString(R.string.no_internet));
                        dialog.dismiss();
                    }
                }
                else {

                    edit_password.setError("New and Confirm Passsword is not Match.");
                    edit_password.setText("");
                    edit_otp_password_conf.setText("");

                }

                 */

                dialog.dismiss();
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


    public void sendlogin(String email, String password) {
        Customprogress.showPopupProgressSpinner(ActivityLogin.this,true);
        mAPIService.login(email, password,sessionManager.getSavedStringFcmtoken(),sessionManager.getSavedStringDeviceid(),"Android").enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                Customprogress.showPopupProgressSpinner(ActivityLogin.this,false);

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
                        String emp_no = response.body().getData().getEMPNO();
                        String name = response.body().getData().getEMPNAME();
//                        String designation = response.body().getData().getDESIGCODE();
                        Integer FcmTokenId = response.body().getData().getFcmTokenId();


                        sessionManager.setSavedEmpNo(emp_no);
                        sessionManager.setSavedUserName(name);
                        sessionManager.setUserLoggedIn(true);
                        sessionManager.setSavedFcmTokenId(FcmTokenId);

                        Intent intent = new Intent(ActivityLogin.this, ActivityDashboard.class);
                        startActivity(intent);
                        finish();

                    }
                    else
                    {
                        Toast.makeText(ActivityLogin.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
//                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }

}
