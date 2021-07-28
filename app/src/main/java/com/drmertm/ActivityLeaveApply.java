package com.drmertm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ActivityLeaveApply extends AppCompatActivity {

    TextView txt_sendotp;
    LinearLayout layout_otp;
    ImageView img_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_apply);
        InitView();
        Click();

        layout_otp.setVisibility(View.GONE);
    }

    private void Click() {

        txt_sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layout_otp.setVisibility(View.VISIBLE);
            }
        });

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void InitView() {

        txt_sendotp = (TextView) findViewById(R.id.txt_sendotp);
        layout_otp = (LinearLayout) findViewById(R.id.layout_otp);
        img_back = (ImageView) findViewById(R.id.img_back);
    }
}
