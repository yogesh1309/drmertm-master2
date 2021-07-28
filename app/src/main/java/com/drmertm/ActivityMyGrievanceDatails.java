package com.drmertm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivityMyGrievanceDatails extends AppCompatActivity {

    ImageView img_back;
    TextView txt_refno,txt_mobile,txt_pincode,txt_date,txt_typegri,txt_desc,txt_dept,txt_section,txt_resaddress,txt_inchasec,txt_offaddress,txt_comstat,txt_penretrep,txt_pfno,txt_comname,txt_comdesi,txt_comdept;
    Date d;
    TextView txt_attachment,txt_reply;
    LinearLayout layout_reply,layout_replyr,layout_attachr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_grievance_datails);

        InitView();
        Click();
        try {

            Intent intent = getIntent();
            if (intent!=null)
            {

               String refno = intent.getStringExtra("refno");
               String date = intent.getStringExtra("date");
               String typegri = intent.getStringExtra("typegri");
               String desc = intent.getStringExtra("desc");
               String dept = intent.getStringExtra("dept");
               String section = intent.getStringExtra("section");
               String inchsec = intent.getStringExtra("inchsec");
               String penretrep = intent.getStringExtra("penretrep");
               String pfno = intent.getStringExtra("pfno");
               String comname = intent.getStringExtra("comname");
               String comdesi = intent.getStringExtra("comdesi");
               String comdept = intent.getStringExtra("comdept");
               String comstation = intent.getStringExtra("comstation");
               String comoffadd = intent.getStringExtra("comoffadd");
               String comresadd = intent.getStringExtra("comresadd");
               String compincode = intent.getStringExtra("compincode");
               String commobile = intent.getStringExtra("commobile");
               String remark = intent.getStringExtra("remark");
               String replyattachment = intent.getStringExtra("replyattachment");


               //date from date string.....
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    System.out.println("date >>>>>>>>>>"+date);
                     d = sdf.parse(date);
                    SimpleDateFormat targetFormat = new SimpleDateFormat("dd-MM-yyyy");
//                    SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
                    date = targetFormat.format(d);
                } catch (ParseException ex) {
                    Log.v("Exception", ex.getLocalizedMessage());
                }

               txt_refno.setText(refno);
               txt_date.setText(date);
               txt_typegri.setText(typegri);
               txt_desc.setText(desc);
               txt_dept.setText(comdept);
               txt_section.setText(section);
               txt_inchasec.setText(inchsec);
               txt_penretrep.setText(penretrep);
               txt_pfno.setText(pfno);
               txt_comname.setText(comname);
               txt_comdesi.setText(comdesi);
               txt_comdept.setText(dept);
               txt_comstat.setText(comstation);
               txt_offaddress.setText(comoffadd);
               txt_resaddress.setText(comresadd);
                txt_pincode.setText(compincode);
                txt_mobile.setText(commobile);


                System.out.println("remark >>>>>>>>>"+remark);
                System.out.println("replyattachment >>>>>>>>>"+replyattachment);
                if (!remark.isEmpty()||!replyattachment.isEmpty())
                {
                    layout_reply.setVisibility(View.VISIBLE);
                    if (!remark.isEmpty())
                    {
                        layout_replyr.setVisibility(View.VISIBLE);
                        txt_reply.setText(remark);
                    }
                    else {
                        layout_replyr.setVisibility(View.GONE);
                    }

                    if (!replyattachment.isEmpty())
                    {
                        layout_attachr.setVisibility(View.VISIBLE);
                        txt_attachment.setText(replyattachment);
                    }
                    else {
                        layout_attachr.setVisibility(View.GONE);
                    }
                }
                else
                {
                    layout_reply.setVisibility(View.GONE);
                }

                layout_attachr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent1 = new Intent(ActivityMyGrievanceDatails.this,ActivityReplyAttachmentView.class);
                        System.out.println("replyattachment >>>>>>>>>"+replyattachment);
                        intent1.putExtra("image",replyattachment);
                        startActivity(intent1);
                    }
                });

            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private void Click() {

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void InitView() {

        img_back = (ImageView) findViewById(R.id.img_back);
        txt_date = (TextView) findViewById(R.id.txt_date);
        txt_refno = (TextView) findViewById(R.id.txt_refno);
        txt_typegri = (TextView) findViewById(R.id.txt_typegri);
        txt_desc = (TextView) findViewById(R.id.txt_desc);
        txt_dept = (TextView) findViewById(R.id.txt_dept);
        txt_section = (TextView) findViewById(R.id.txt_section);
        txt_inchasec = (TextView) findViewById(R.id.txt_inchasec);
        txt_penretrep = (TextView) findViewById(R.id.txt_penretrep);
        txt_pfno = (TextView) findViewById(R.id.txt_pfno);
        txt_comname = (TextView) findViewById(R.id.txt_comname);
        txt_comdesi = (TextView) findViewById(R.id.txt_comdesi);
        txt_comdept = (TextView) findViewById(R.id.txt_comdept);
        txt_comstat = (TextView) findViewById(R.id.txt_comstat);
        txt_offaddress = (TextView) findViewById(R.id.txt_offaddress);
        txt_resaddress = (TextView) findViewById(R.id.txt_resaddress);
        txt_pincode = (TextView) findViewById(R.id.txt_pincode);
        txt_mobile = (TextView) findViewById(R.id.txt_mobile);
        txt_attachment = findViewById(R.id.txt_attachment);
        txt_reply = findViewById(R.id.txt_reply);
        layout_reply = findViewById(R.id.layout_reply);
        layout_replyr = findViewById(R.id.layout_replyr);
        layout_attachr = findViewById(R.id.layout_attachr);
    }
}
