package com.drmertm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.URLUtil;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

public class WVIdCardActivity extends AppCompatActivity {

    String url;
    ImageView img_back;
    WebView wv_pdf;
    Context context;
    private int REQUEST_CAMERA = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_w_v_id_card);
        InitView();
        Click();
    try {
            Intent intent = getIntent();
            if (intent!=null)
            {
               url = intent.getStringExtra("url");
                //-----------------webview code ----------------------
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(WVIdCardActivity.this, new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CAMERA);
                } else {
                    wv_pdf.setWebViewClient(new WebViewClient() {

                        public boolean shouldOverrideUrlLoading(WebView view, String url) {
                            return false;
                        }

                        @Override
                        public void onPageFinished(WebView view, String url)
                        {
//                            createWebPagePrint(view);
                            super.onPageFinished(view, url);
                        }
                    });
                    wv_pdf.getSettings().setSupportZoom(true);
                    wv_pdf.getSettings().setJavaScriptEnabled(true);
                    WebSettings ws = wv_pdf.getSettings();
                    wv_pdf.addJavascriptInterface(new Object()
                    {
                        @JavascriptInterface
                        public void printDiv()
                        {
                            Toast.makeText (context, "", Toast.LENGTH_SHORT).show();
                            createWebPagePrint(wv_pdf);
                        }
                    }, "dowloadbtn");
                    wv_pdf.loadUrl(url);
//                    createWebPagePrint(wv_pdf);

                   /* wv_pdf.setDownloadListener(new DownloadListener() {
                        @Override
                        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

                            request.setDescription("Download file...");
                            request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimetype));
                            request.allowScanningByMediaScanner();
                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); //Notify client once download is completed!
                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(url, contentDisposition, mimetype));
                            DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                            dm.enqueue(request);
                            Toast.makeText(getApplicationContext(), "Downloading File", Toast.LENGTH_LONG).show();
                        }
                    });
                   */

                }

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

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        System.out.println("Request Code >>>>>>>"+requestCode);
        switch (requestCode) {
            case 0: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    wv_pdf.setWebViewClient(new WebViewClient());
                    wv_pdf.getSettings().setSupportZoom(true);
                    wv_pdf.getSettings().setJavaScriptEnabled(true);
                    wv_pdf.setDownloadListener(new DownloadListener() {
                        @Override
                        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

                            request.setDescription("Download file...");
                            request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimetype));
                            request.allowScanningByMediaScanner();
                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); //Notify client once download is completed!
                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(url, contentDisposition, mimetype));
                            DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                            dm.enqueue(request);
                            Toast.makeText(getApplicationContext(), "Downloading File", Toast.LENGTH_LONG).show();
                        }
                    });
                    wv_pdf.loadUrl(url);

                } else {
//                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    private void InitView() {
        context = WVIdCardActivity.this;
        img_back = findViewById(R.id.img_back);
        wv_pdf = findViewById(R.id.wv_pdf);
    }

    public  void createWebPagePrint(WebView webView) {
		/*if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
            return;*/
        PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
        PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter();
        String jobName = getString(R.string.app_name) + " Document";
        PrintAttributes.Builder builder = new PrintAttributes.Builder();
        builder.setMediaSize(PrintAttributes.MediaSize.ISO_A5);
        PrintJob printJob = printManager.print(jobName, printAdapter, builder.build());

        if(printJob.isCompleted()){
            Toast.makeText(getApplicationContext(), "print complete.", Toast.LENGTH_LONG).show();
        }
        else if(printJob.isFailed()){
            Toast.makeText(getApplicationContext(), "print_failed", Toast.LENGTH_LONG).show();
        }
        // Save the job object for later status checking
    }
}