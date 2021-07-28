package com.drmertm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.drmertm.ModelClass.ForgotPasswordEmpResponse;
import com.drmertm.ModelClass.ForgotpasswordOtpResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityForgotPasswordOTP extends AppCompatActivity {

    ImageView img_back;
    EditText edt_otp,edt_newpassword,edt_conpassword;
    String s_otp,s_newpassword,s_confpassword,empid;
    TextView txt_submit;
    private JsonPlaceHolderApi mAPIService;
    ImageView img_newpassword,img_confpassword;
    boolean is_click = false;
    boolean is_click2 = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_o_t_p);
        mAPIService = ApiUtils.getAPIService();
        InitView();
        Click();

        try {

            Intent intent = getIntent();
            if (intent!=null)
            {

                 empid = intent.getStringExtra("empid");

            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private void Click() {

        img_newpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!is_click)
                {
                    is_click=true;
                    edt_newpassword.setTransformationMethod(null);
                    img_newpassword.setImageDrawable(getResources().getDrawable(R.drawable.icopenpassword));

                }
                else
                {
                    is_click=false;
                    edt_newpassword.setTransformationMethod(new PasswordTransformationMethod());
                    img_newpassword.setImageDrawable(getResources().getDrawable(R.drawable.ic_closepassword));
                }
            }
        });

        img_confpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!is_click2)
                {
                    is_click2=true;
                    edt_conpassword.setTransformationMethod(null);
                    img_confpassword.setImageDrawable(getResources().getDrawable(R.drawable.icopenpassword));

                }
                else
                {
                    is_click2=false;
                    edt_conpassword.setTransformationMethod(new PasswordTransformationMethod());
                    img_confpassword.setImageDrawable(getResources().getDrawable(R.drawable.ic_closepassword));
                }

            }
        });

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        txt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s_otp = edt_otp.getText().toString().trim();
                s_newpassword = edt_newpassword.getText().toString().trim();
                s_confpassword = edt_conpassword.getText().toString().trim();

                if (s_otp.isEmpty())
                {
                    edt_otp.setError("Please enter OTP!");
                }
                else if (s_newpassword.isEmpty())
                {
                    edt_newpassword.setError("Please enter password!");
                }
                else if (s_newpassword.length()<6)
                {
                    edt_newpassword.setError("Password size is small, at least 6 characters minimum!");
                }
                else if (s_confpassword.isEmpty())
                {
                    edt_conpassword.setError("Please enter password!");
                }
                else if (s_confpassword.length()<6)
                {
                    edt_conpassword.setError("Password size is small, at least 6 characters minimum!");
                }
                else if (!s_newpassword.equals(s_confpassword))
                {
                    edt_newpassword.setError("New password and Confirm password are not match!");
                    edt_newpassword.setText("");
                    edt_conpassword.setText("");
                }
                else
                {
                    if(Utils.isInternetConnected(ActivityForgotPasswordOTP.this)) {

                        sendforgotpasswordotp(empid,s_otp,s_newpassword);
                    }
                    else {
                        CustomAlertdialog.createDialog(ActivityForgotPasswordOTP.this,getString(R.string.no_internet));
                    }
                }
            }
        });
    }

    private void InitView() {

        img_back = (ImageView) findViewById(R.id.img_back);
        edt_otp = (EditText) findViewById(R.id.edt_otp);
        edt_newpassword = (EditText) findViewById(R.id.edt_newpassword);
        edt_conpassword = (EditText) findViewById(R.id.edt_conpassword);
        txt_submit = (TextView) findViewById(R.id.txt_submit);
        img_newpassword = (ImageView) findViewById(R.id.img_newpassword);
        img_confpassword = (ImageView) findViewById(R.id.img_confpassword);
    }

    public void sendforgotpasswordotp(String emp_id, String otp, String password) {
        Customprogress.showPopupProgressSpinner(ActivityForgotPasswordOTP.this,true);
        mAPIService.forgotpasswordotp(emp_id,otp,password).enqueue(new Callback<ForgotpasswordOtpResponse>() {
            @Override
            public void onResponse(Call<ForgotpasswordOtpResponse> call, Response<ForgotpasswordOtpResponse> response) {

                Customprogress.showPopupProgressSpinner(ActivityForgotPasswordOTP.this,false);

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

                        Intent intent = new Intent(ActivityForgotPasswordOTP.this,ActivityLogin.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                    }
                    else
                    {
                        Toast.makeText(ActivityForgotPasswordOTP.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<ForgotpasswordOtpResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(ActivityForgotPasswordOTP.this,false);
//                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }

}