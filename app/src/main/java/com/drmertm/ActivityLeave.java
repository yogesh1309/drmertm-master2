package com.drmertm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ActivityLeave extends AppCompatActivity {

    LinearLayout layout_applea,line_applea,layout_mylearep,line_mylearep;
    TextView txt_applea,txt_myglearep;
    ImageView img_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave);
        InitView();
        Click();

        // ------------------default fragment set -------------------
        txt_applea.setTextColor(getResources().getColor(R.color.darkbluecolour));
        txt_myglearep.setTextColor(getResources().getColor(R.color.lightgray));
        line_applea.setBackgroundColor(getResources().getColor(R.color.darkbluecolour));
        line_mylearep.setBackgroundColor(getResources().getColor(R.color.lightgray));
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.contentContainer, new FragmentApplyLeave());
        transaction.commit();
    }

    private void Click() {

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        layout_applea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                txt_applea.setTextColor(getResources().getColor(R.color.darkbluecolour));
                txt_myglearep.setTextColor(getResources().getColor(R.color.lightgray));
                line_applea.setBackgroundColor(getResources().getColor(R.color.darkbluecolour));
                line_mylearep.setBackgroundColor(getResources().getColor(R.color.lightgray));
                final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.contentContainer, new FragmentApplyLeave());
                transaction.commit();
            }
        });

        layout_mylearep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_applea.setTextColor(getResources().getColor(R.color.lightgray));
                txt_myglearep.setTextColor(getResources().getColor(R.color.darkbluecolour));
                line_applea.setBackgroundColor(getResources().getColor(R.color.lightgray));
                line_mylearep.setBackgroundColor(getResources().getColor(R.color.darkbluecolour));
                final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.contentContainer, new FragmentMyLeaveReport());
                transaction.commit();
            }
        });
    }

    private void InitView() {

        line_mylearep = (LinearLayout) findViewById(R.id.line_mylearep);
        layout_mylearep = (LinearLayout) findViewById(R.id.layout_mylearep);
        line_applea = (LinearLayout) findViewById(R.id.line_applea);
        layout_applea = (LinearLayout) findViewById(R.id.layout_applea);
        txt_applea = (TextView) findViewById(R.id.txt_applea);
        txt_myglearep = (TextView) findViewById(R.id.txt_myglearep);
        img_back = (ImageView) findViewById(R.id.img_back);
    }
}
