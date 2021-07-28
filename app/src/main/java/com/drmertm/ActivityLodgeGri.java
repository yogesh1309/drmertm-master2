package com.drmertm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ActivityLodgeGri extends AppCompatActivity {

    ImageView img_back;
    LinearLayout layout_yes,layout_no,layout_attach,layout_doty,layout_dotn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lodge_gri);
        InitView();
        Click();

        layout_dotn.setBackground(getResources().getDrawable(R.drawable.blue_fill_dot));
        layout_doty.setBackground(getResources().getDrawable(R.drawable.gray_fill_dot));
        layout_attach.setVisibility(View.GONE);

    }

    private void Click() {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                img_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBackPressed();
                    }
                });
            }
        });

        layout_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layout_dotn.setBackground(getResources().getDrawable(R.drawable.blue_fill_dot));
                layout_doty.setBackground(getResources().getDrawable(R.drawable.gray_fill_dot));
                layout_attach.setVisibility(View.GONE);
            }
        });


        layout_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layout_dotn.setBackground(getResources().getDrawable(R.drawable.gray_fill_dot));
                layout_doty.setBackground(getResources().getDrawable(R.drawable.blue_fill_dot));
                layout_attach.setVisibility(View.VISIBLE);
            }
        });

    }

    private void InitView() {

        img_back = (ImageView) findViewById(R.id.img_back);
        layout_yes = (LinearLayout) findViewById(R.id.layout_yes);
        layout_no = (LinearLayout) findViewById(R.id.layout_no);
        layout_attach = (LinearLayout) findViewById(R.id.layout_attach);
        layout_doty = (LinearLayout) findViewById(R.id.layout_doty);
        layout_dotn = (LinearLayout) findViewById(R.id.layout_dotn);
    }
}
