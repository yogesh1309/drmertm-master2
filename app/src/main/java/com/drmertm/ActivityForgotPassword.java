package com.drmertm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.drmertm.ModelClass.ForgotPasswordEmpResponse;
import com.drmertm.ModelClass.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityForgotPassword extends AppCompatActivity {

    ImageView img_back;
    EditText edt_empid;
    TextView txt_submit;
    String s_emp_id;
    private JsonPlaceHolderApi mAPIService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
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
                s_emp_id = edt_empid.getText().toString().trim();

                if (s_emp_id.isEmpty())
                {
                    edt_empid.setError("Please enter employee id!");
                }
                else if (s_emp_id.length()!=11)
                {
                    edt_empid.setError("Please enter valid employee id!");
                }
                else
                {
                    if(Utils.isInternetConnected(ActivityForgotPassword.this)) {

                        sendforgotpassword(s_emp_id);
                    }
                    else {
                        CustomAlertdialog.createDialog(ActivityForgotPassword.this,getString(R.string.no_internet));
                    }
                }
            }
        });
    }

    private void InitView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        edt_empid = (EditText) findViewById(R.id.edt_empid);
        txt_submit = (TextView) findViewById(R.id.txt_submit);
    }

    public void sendforgotpassword(String emp_id) {
        Customprogress.showPopupProgressSpinner(ActivityForgotPassword.this,true);
        mAPIService.forgotpassword(emp_id).enqueue(new Callback<ForgotPasswordEmpResponse>() {
            @Override
            public void onResponse(Call<ForgotPasswordEmpResponse> call, Response<ForgotPasswordEmpResponse> response) {

                Customprogress.showPopupProgressSpinner(ActivityForgotPassword.this,false);

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

                        edt_empid.setText("");
                        Intent intent = new Intent(ActivityForgotPassword.this,ActivityForgotPasswordOTP.class);
                        intent.putExtra("empid",s_emp_id);
                        startActivity(intent);

                    }
                    else
                    {
                        Toast.makeText(ActivityForgotPassword.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<ForgotPasswordEmpResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(ActivityForgotPassword.this,false);
//                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }

}