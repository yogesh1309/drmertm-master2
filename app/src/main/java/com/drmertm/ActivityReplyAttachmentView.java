package com.drmertm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class ActivityReplyAttachmentView extends AppCompatActivity {

    ImageView img_reply;
    WebView wv_reply;
    ImageView img_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply_attachment_view);
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(ActivityReplyAttachmentView.this));
        InitView();
        Click();
        try {
            Intent  intent = getIntent();
            if (intent!=null)
            {
                String image = intent.getStringExtra("image");
                System.out.println("image >>>>>"+image);

                String extension = image.substring(image.lastIndexOf("."));

                System.out.println("extension >>>>>"+extension);

                if (extension.contains("png")||extension.contains("jpg")||extension.contains("jpeg"))
                {
                   img_reply.setVisibility(View.VISIBLE);
                   wv_reply.setVisibility(View.GONE);

                   String s_image = Allurls.ImageURL+image;
                    if (s_image!=null&&!s_image.equalsIgnoreCase("")) {
                        s_image = s_image.replaceAll("\\\\", "");

                        System.out.println("imageUrlimageUrlimageUrl"+s_image);
                        ImageLoader.getInstance().displayImage(s_image, img_reply);
                    }

                }
                else {

                    img_reply.setVisibility(View.GONE);
                    wv_reply.setVisibility(View.VISIBLE);

                    wv_reply.setWebViewClient(new WebViewClient());
                    wv_reply.getSettings().setSupportZoom(true);
                    wv_reply.getSettings().setJavaScriptEnabled(true);
                    System.out.println("url >>>>>>>>>>>>"+Allurls.ImageURL+image);
                    wv_reply.loadUrl("https://docs.google.com/gview?embedded=true&url="+Allurls.ImageURL+image);
                    wv_reply.setWebChromeClient(new WebChromeClient() {
                        public void onProgressChanged(WebView view, int progress) {
                           ActivityReplyAttachmentView.this.setTitle("Loading...");
                            ActivityReplyAttachmentView.this.setProgress(progress * 100);

                            if(progress == 100)
                                ActivityReplyAttachmentView.this.setTitle("Waiting...");
                        }


                    });

                    wv_reply.setWebViewClient(new WebViewClient(){

                        @Override public void onReceivedError(WebView view, WebResourceRequest request,
                                                              WebResourceError error) {
                            super.onReceivedError(view, request, error);

                            Toast.makeText(ActivityReplyAttachmentView.this, "Web view error.", Toast.LENGTH_SHORT).show();
                        }
                    });



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

    private void InitView() {
        img_reply = findViewById(R.id.img_reply);
        wv_reply = findViewById(R.id.wv_reply);
        img_back = findViewById(R.id.img_back);
    }
}