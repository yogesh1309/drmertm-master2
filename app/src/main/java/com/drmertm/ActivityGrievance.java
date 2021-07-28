package com.drmertm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ActivityGrievance extends AppCompatActivity {

    TextView txt_mygrie,txt_loggrie;
    LinearLayout line_mygrie,layout_mygrie,line_loggrie,layout_loggrie;
    ImageView img_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grievance);

        InitView();
        Click();

        // ------------------default fragment set -------------------
        txt_loggrie.setTextColor(getResources().getColor(R.color.darkbluecolour));
        txt_mygrie.setTextColor(getResources().getColor(R.color.lightgray));
        line_loggrie.setBackgroundColor(getResources().getColor(R.color.darkbluecolour));
        line_mygrie.setBackgroundColor(getResources().getColor(R.color.lightgray));
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.contentContainer, new FragmentLodgeGrievance());
        transaction.commit();
    }

    private void Click() {
        layout_loggrie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                txt_loggrie.setTextColor(getResources().getColor(R.color.darkbluecolour));
                txt_mygrie.setTextColor(getResources().getColor(R.color.lightgray));
                line_loggrie.setBackgroundColor(getResources().getColor(R.color.darkbluecolour));
                line_mygrie.setBackgroundColor(getResources().getColor(R.color.lightgray));
                final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.contentContainer, new FragmentLodgeGrievance());
                transaction.commit();
            }
        });

        layout_mygrie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_loggrie.setTextColor(getResources().getColor(R.color.lightgray));
                txt_mygrie.setTextColor(getResources().getColor(R.color.darkbluecolour));
                line_loggrie.setBackgroundColor(getResources().getColor(R.color.lightgray));
                line_mygrie.setBackgroundColor(getResources().getColor(R.color.darkbluecolour));
                final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.contentContainer, new FragmentMyGrievance());
                transaction.commit();
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
        layout_loggrie = (LinearLayout) findViewById(R.id.layout_loggrie);
        line_loggrie = (LinearLayout) findViewById(R.id.line_loggrie);
        layout_mygrie = (LinearLayout) findViewById(R.id.layout_mygrie);
        line_mygrie = (LinearLayout) findViewById(R.id.line_mygrie);
        txt_loggrie = (TextView) findViewById(R.id.txt_loggrie);
        txt_mygrie = (TextView) findViewById(R.id.txt_mygrie);
        img_back = (ImageView) findViewById(R.id.img_back);
    }
}
