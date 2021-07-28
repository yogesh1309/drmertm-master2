package com.drmertm;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class FragmentApplyLeave extends Fragment {
    TextView txt_sendotp;
    LinearLayout layout_otp;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
   View v = inflater.inflate(R.layout.fragment_fragment_apply_leave, container, false);

        InitView(v);
        Click();

        layout_otp.setVisibility(View.GONE);
   return v;
    }

    private void InitView(View v) {

        txt_sendotp = (TextView) v.findViewById(R.id.txt_sendotp);
        layout_otp = (LinearLayout) v.findViewById(R.id.layout_otp);

    }

    private void Click() {

        txt_sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layout_otp.setVisibility(View.VISIBLE);
            }
        });


    }
}
