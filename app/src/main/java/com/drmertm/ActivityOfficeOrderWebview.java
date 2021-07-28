package com.drmertm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

public class ActivityOfficeOrderWebview extends AppCompatActivity {

    String url;
    WebView wv_pdf;
    ImageView img_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_office_order_webview);
        initView();
        Click();
        try {
            Intent intent = getIntent();
            if (intent!=null)
            {
                url = intent.getStringExtra("url");

                System.out.println("url >>>>>>>>"+url);
                wv_pdf.setWebViewClient(new WebViewClient());
                wv_pdf.getSettings().setSupportZoom(true);
                wv_pdf.getSettings().setJavaScriptEnabled(true);
                wv_pdf.loadUrl("https://docs.google.com/gview?embedded=true&url="+url);

            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void Click() {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initView() {
        img_back = findViewById(R.id.img_back);
        wv_pdf = findViewById(R.id.wv_pdf);
    }
}