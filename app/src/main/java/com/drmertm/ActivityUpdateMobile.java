package com.drmertm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.drmertm.ModelClass.ForgotPasswordEmpResponse;
import com.drmertm.ModelClass.OtpSendResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityUpdateMobile extends AppCompatActivity {

    ImageView img_back;
    EditText edt_mob,edt_otp;
    LinearLayout layout_otp;
    TextView txt_submit;
    String s_mob,s_otp;
    private JsonPlaceHolderApi mAPIService;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_mobile);
        sessionManager = new SessionManager(ActivityUpdateMobile.this);
        mAPIService = ApiUtils.getAPIService();
        InitView();
        Click();
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

                s_mob = edt_mob.getText().toString().trim();

                if (s_mob.isEmpty())
                {
                    edt_mob.setError("Please enter phone number!");
                }
                else if (s_mob.length()!=8 && s_mob.length()!=10)
                {
                    edt_mob.setError("Please enter valid phone number!");
                }
                else
                {
                    if(Utils.isInternetConnected(ActivityUpdateMobile.this)) {

                        sendotp(sessionManager.getSavedEmpno(),s_mob);
                    }
                    else {
                        CustomAlertdialog.createDialog(ActivityUpdateMobile.this,getString(R.string.no_internet));
                    }
                }
            }
        });
    }

    private void InitView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        edt_mob = (EditText) findViewById(R.id.edt_mob);
        edt_otp = (EditText) findViewById(R.id.edt_otp);
        layout_otp = (LinearLayout) findViewById(R.id.layout_otp);
        txt_submit = (TextView) findViewById(R.id.txt_submit);
    }

    public void sendotp(String emp_id ,String mobno) {
        Customprogress.showPopupProgressSpinner(ActivityUpdateMobile.this,true);
        mAPIService.otpsend2(emp_id,mobno).enqueue(new Callback<OtpSendResponse>() {
            @Override
            public void onResponse(Call<OtpSendResponse> call, Response<OtpSendResponse> response) {

                Customprogress.showPopupProgressSpinner(ActivityUpdateMobile.this,false);

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


                        layout_otp.setVisibility(View.VISIBLE);
//                        edt_mob.setClickable(true);
                        txt_submit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String otp = edt_otp.getText().toString().trim();
                                if (otp.isEmpty())
                                {
                                    edt_otp.setError("Please enter OTP!");
                                }
                                else
                                {
                                    if(Utils.isInternetConnected(ActivityUpdateMobile.this)) {

                                        sendotpverify(sessionManager.getSavedEmpno(),s_mob,otp);
                                    }
                                    else {
                                        CustomAlertdialog.createDialog(ActivityUpdateMobile.this,getString(R.string.no_internet));
                                    }
                                }
                            }
                        });



                    }
                    else
                    {
                        Toast.makeText(ActivityUpdateMobile.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<OtpSendResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(ActivityUpdateMobile.this,false);
//                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }

    public void sendotpverify(String emp_id ,String mobno,String otp) {
        Customprogress.showPopupProgressSpinner(ActivityUpdateMobile.this,true);
        mAPIService.otpverify2(emp_id,mobno,otp).enqueue(new Callback<OtpSendResponse>() {
            @Override
            public void onResponse(Call<OtpSendResponse> call, Response<OtpSendResponse> response) {

                Customprogress.showPopupProgressSpinner(ActivityUpdateMobile.this,false);

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
                        createDialog(ActivityUpdateMobile.this,response.body().getMessage());
                    }
                    else
                    {
                        Toast.makeText(ActivityUpdateMobile.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<OtpSendResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(ActivityUpdateMobile.this,false);
//                Log.e(TAG, "Unable to submit post to API.");
            }
        });
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

                dialog.dismiss();
                finish();

            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

}