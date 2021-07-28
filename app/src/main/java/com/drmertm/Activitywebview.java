package com.drmertm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Activitywebview extends AppCompatActivity {

    ImageView img_back;
    WebView wv_pdf;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activitywebview);
        InitView();
        Click();
        try {
            Intent intent = getIntent();
            if (intent!=null)
            {
                String url = intent.getStringExtra("url");
                progressBar = findViewById(R.id.progressBar);
               /* wv_pdf.setWebViewClient(new WebViewClient());
                Log.i("url >>>",url);
                wv_pdf.loadUrl(url);*/

                wv_pdf.setWebChromeClient( new MyWebChromeClient());
                wv_pdf.setWebViewClient( new webClient());
                wv_pdf.getSettings().setLoadWithOverviewMode(true);
                wv_pdf.getSettings().setSupportZoom(true);
                wv_pdf.getSettings().setJavaScriptEnabled(true);
                Log.i("url >>>",url);
                wv_pdf.loadUrl(url);

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

    private void InitView() {
        img_back = findViewById(R.id.img_back);
        wv_pdf = (WebView) findViewById(R.id.webView);
    }
    public class MyWebChromeClient extends WebChromeClient {
        public void onProgressChanged(WebView view, int newProgress) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(newProgress);
        }
    }

    public class webClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        if (wv_pdf.canGoBack()) {
            wv_pdf.goBack();
            progressBar.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
        }
    }
}