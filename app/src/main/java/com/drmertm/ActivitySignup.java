package com.drmertm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.drmertm.ModelClass.OtpSendResponse;
import com.drmertm.ModelClass.OtpVerifyResponse;
import com.drmertm.ModelClass.Signup2Response;
import com.drmertm.ModelClass.SignupResponse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivitySignup extends AppCompatActivity {

    ImageView img_back;
    TextView txt_request,txt_signup;
    EditText edt_empid,edt_panno;
    String s_emno,s_panno;
    Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
    private JsonPlaceHolderApi mAPIService;
    SessionManager sessionManager;
    String mobile,OTP,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAPIService = ApiUtils.getAPIService();
        sessionManager  = new SessionManager(ActivitySignup.this);
        InitView();
        Click();

        edt_panno.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
    }

    private void Click() {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        txt_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(ActivitySignup.this, ActivityLogin.class);
//                startActivity(intent);
                finish();
            }
        });
        txt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try {

                    s_emno = edt_empid.getText().toString().trim();
                    s_panno = edt_panno.getText().toString().trim();

                    Matcher matcher = pattern.matcher(s_panno);

                    if (s_emno.isEmpty())
                    {
                        edt_empid.setError("Please enter employee id!");
                    }
                    else if (s_emno.length()!=11)
                    {
                        edt_empid.setError("Please enter valid employee id!");
                    }
                    else if (s_panno.isEmpty())
                    {
                        edt_panno.setError("Please enter pan number!");
                    }
                    else if (!matcher.matches())
                    {
                        edt_panno.setError("Please enter valid pan number!");
                    }
                    else
                    {
                        if(Utils.isInternetConnected(ActivitySignup.this)) {
                            sendPost(s_emno,s_panno);
                        }
                        else {
            CustomAlertdialog.createDialog(ActivitySignup.this,getString(R.string.no_internet));
                        }


                    }


                }catch (Exception e)
                {
                    e.printStackTrace();
                }




//                Intent intent = new Intent(ActivitySignup.this,ActivityDashboard.class);
//                startActivity(intent);
//                finish();
            }
        });
    }

    private void InitView() {
        img_back =(ImageView) findViewById(R.id.img_back);
        txt_request = (TextView) findViewById(R.id.txt_request);
        txt_signup = (TextView) findViewById(R.id.txt_signup);
        edt_empid = (EditText) findViewById(R.id.edt_empid);
        edt_panno = (EditText) findViewById(R.id.edt_panno);
    }

    public void sendPost(String empno, String panno) {

        Customprogress.showPopupProgressSpinner(ActivitySignup.this,true);

        mAPIService.signup(empno, panno).enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {

                Customprogress.showPopupProgressSpinner(ActivitySignup.this,false);

                if(response.isSuccessful()) {
                   System.out.println("Status >>>>>>>>>>>>"+response.body().getStatus());
                    System.out.println("Message >>>>>>>>>>>>"+response.body().getMessage());

                    boolean status = response.body().getStatus();

                    if (status==true)
                    {
                        alertdialogbox();

                    }
                    else
                    {
                        Toast.makeText(ActivitySignup.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(ActivitySignup.this,false);
//                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }

    public void sendOtp(String empno, String mobile) {

        Customprogress.showPopupProgressSpinner(ActivitySignup.this,true);

        mAPIService.otpsend(empno, mobile).enqueue(new Callback<OtpSendResponse>() {
            @Override
            public void onResponse(Call<OtpSendResponse> call, Response<OtpSendResponse> response) {

                Customprogress.showPopupProgressSpinner(ActivitySignup.this,false);

                if(response.isSuccessful()) {
                    System.out.println("Status >>>>>>>>>>>>"+response.body().getStatus());
                    System.out.println("Message >>>>>>>>>>>>"+response.body().getMessage());

                    boolean status = response.body().getStatus();

                    if (status==true)
                    {
                        alertdialogboxverifyotp();

                    }
                    else
                    {
                        Toast.makeText(ActivitySignup.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<OtpSendResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(ActivitySignup.this,false);
//                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }

    public void sendOtpVerify(String empno, String mobile, String OtpVerify) {

        Customprogress.showPopupProgressSpinner(ActivitySignup.this,true);

        mAPIService.otpverify(empno, mobile, OtpVerify).enqueue(new Callback<OtpVerifyResponse>() {
            @Override
            public void onResponse(Call<OtpVerifyResponse> call, Response<OtpVerifyResponse> response) {

                Customprogress.showPopupProgressSpinner(ActivitySignup.this,false);

                if(response.isSuccessful()) {
                    System.out.println("Status >>>>>>>>>>>>"+response.body().getStatus());
                    System.out.println("Message >>>>>>>>>>>>"+response.body().getMessage());

                    boolean status = response.body().getStatus();

                    if (status==true)
                    {
                        alertdialogboxpassword();

                    }
                    else
                    {
                        Toast.makeText(ActivitySignup.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<OtpVerifyResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(ActivitySignup.this,false);
//                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }

    public void sendSignup2(String empno, String mobile, String panno, String password) {

        Customprogress.showPopupProgressSpinner(ActivitySignup.this,true);

        mAPIService.signup2(empno, panno,mobile,password).enqueue(new Callback<Signup2Response>() {
            @Override
            public void onResponse(Call<Signup2Response> call, Response<Signup2Response> response) {

                Customprogress.showPopupProgressSpinner(ActivitySignup.this,false);

                if(response.isSuccessful()) {
                    System.out.println("Status >>>>>>>>>>>>"+response.body().getStatus());
                    System.out.println("Message >>>>>>>>>>>>"+response.body().getMessage());

                    boolean status = response.body().getStatus();

                    if (status==true)
                    {

                        createDialog(ActivitySignup.this,response.body().getMessage());
//                        alertdialogboxsuccess(response.body().getMessage());

                    }
                    else
                    {
                        Toast.makeText(ActivitySignup.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<Signup2Response> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(ActivitySignup.this,false);
//                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }

    public void alertdialogbox()
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(ActivitySignup.this);
        LayoutInflater inflater = getLayoutInflater();

        View dialogView=inflater.inflate(R.layout.alertdialog_custom_view_mobile,null);
        builder.setCancelable(false);
        builder.setView(dialogView);
        Button btn_positive =  dialogView.findViewById(R.id.dialog_positive_btn);
        Button btn_negative =  dialogView.findViewById(R.id.dialog_negative_btn);
        final EditText et_name = dialogView.findViewById(R.id.et_mob);
        final Dialog dialog = builder.create();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        btn_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                OTPalertdialogbox();

                mobile = et_name.getText().toString().trim();
                if (mobile.isEmpty())
                {
                    et_name.setError("Please enter mobile number!");
                }
                else if (mobile.length()!=8 && mobile.length()!=10)
                {
                    et_name.setError("Please enter valid phone number!");
                }
                else {

                    if(Utils.isInternetConnected(ActivitySignup.this)) {
                        dialog.cancel();
                        sendOtp(s_emno,mobile);
                    }
                    else {
                        CustomAlertdialog.createDialog(ActivitySignup.this,getString(R.string.no_internet));
                    }

                }



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
    public void alertdialogboxverifyotp()
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(ActivitySignup.this);
        LayoutInflater inflater = getLayoutInflater();

        View dialogView=inflater.inflate(R.layout.alertdialog_custom_view_otp,null);
        builder.setCancelable(false);
        builder.setView(dialogView);
        Button btn_positive =  dialogView.findViewById(R.id.dialog_positive_btn);
        Button btn_negative =  dialogView.findViewById(R.id.dialog_negative_btn);
        final EditText et_name = dialogView.findViewById(R.id.et_otp);
        final Dialog dialog = builder.create();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        btn_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                OTPalertdialogbox();

                OTP = et_name.getText().toString().trim();
                if (OTP.isEmpty())
                {
                    et_name.setError("Please enter OTP number!");
                }
                else {
                    if(Utils.isInternetConnected(ActivitySignup.this)) {
                        dialog.cancel();
                        sendOtpVerify(s_emno,mobile,OTP);
                    }
                    else {
                        CustomAlertdialog.createDialog(ActivitySignup.this,getString(R.string.no_internet));
                    }


                }



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

    public void alertdialogboxpassword()
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(ActivitySignup.this);
        LayoutInflater inflater = getLayoutInflater();

        View dialogView=inflater.inflate(R.layout.alertdialog_custom_view_password,null);
        builder.setCancelable(false);
        builder.setView(dialogView);
        Button btn_positive =  dialogView.findViewById(R.id.dialog_positive_btn);
        Button btn_negative =  dialogView.findViewById(R.id.dialog_negative_btn);
        final EditText et_newpass = dialogView.findViewById(R.id.et_newpass);
        final EditText et_conpass = dialogView.findViewById(R.id.et_conpass);
        ImageView img_newpassword,img_confpassword;
        final boolean[] is_click = {false};
        final boolean[] is_click2 = {false};
        img_newpassword = dialogView.findViewById(R.id.img_newpassword);
        img_confpassword = dialogView.findViewById(R.id.img_confpassword);
        final Dialog dialog = builder.create();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        img_newpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!is_click[0])
                {
                    is_click[0] =true;
                    et_newpass.setTransformationMethod(null);
                    img_newpassword.setImageDrawable(getResources().getDrawable(R.drawable.icopenpassword));

                }
                else
                {
                    is_click[0] =false;
                    et_newpass.setTransformationMethod(new PasswordTransformationMethod());
                    img_newpassword.setImageDrawable(getResources().getDrawable(R.drawable.ic_closepassword));
                }
            }
        });

        img_confpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!is_click2[0])
                {
                    is_click2[0] =true;
                    et_conpass.setTransformationMethod(null);
                    img_confpassword.setImageDrawable(getResources().getDrawable(R.drawable.icopenpassword));

                }
                else
                {
                    is_click2[0] =false;
                    et_conpass.setTransformationMethod(new PasswordTransformationMethod());
                    img_confpassword.setImageDrawable(getResources().getDrawable(R.drawable.ic_closepassword));
                }

            }
        });


        btn_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                OTPalertdialogbox();

                password = et_newpass.getText().toString().trim();
              String  conpassword = et_conpass.getText().toString().trim();
                if (password.isEmpty())
                {
                    et_newpass.setError("Please enter password!");
                }
                else if (password.length()<6)
                {
                    et_newpass.setError("Password size is small, at least 6 characters minimum!");
                }
                else if (conpassword.isEmpty())
                {
                    et_conpass.setError("Please enter password!");
                }
                else if (conpassword.length()<6)
                {
                    et_conpass.setError("Password size is small, at least 6 characters minimum!");
                }
                else if (!password.equals(conpassword))
                {
                    et_newpass.setError("Password and Confirm Password not match!");
                    et_newpass.setText("");
                    et_conpass.setText("");
                }
                else {
                    if(Utils.isInternetConnected(ActivitySignup.this)) {
                        dialog.cancel();
                        sendSignup2(s_emno,mobile,s_panno,password);
                    }
                    else {
                        CustomAlertdialog.createDialog(ActivitySignup.this,getString(R.string.no_internet));
                    }
                }
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

    public void createDialog(final Context context, final String message)
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

                Intent intent = new Intent(context, ActivityLogin.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
                finish();

            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
}
