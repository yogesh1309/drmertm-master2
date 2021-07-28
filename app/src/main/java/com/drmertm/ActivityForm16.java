package com.drmertm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.webkit.PermissionRequest;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.drmertm.ModelClass.DeptResponse;
import com.drmertm.ModelClass.Form16Response;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityForm16 extends AppCompatActivity {

    ImageView img_back;
    Spinner spiyear;
    ArrayList<String> yearnamelist = new ArrayList<>();
    private JsonPlaceHolderApi mAPIService;
    SessionManager sessionManager;
    String year ;
    TextView txt_view,txt_download;
    WebView wv_pdf;
    String url;
    private int REQUEST_CAMERA = 0;
    public static final int ID_BIG_NOTIFICATION = 234;
    public static final int ID_SMALL_NOTIFICATION = 235;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form16);
        sessionManager = new SessionManager(ActivityForm16.this);
        mAPIService = ApiUtils.getAPIService();
        InitView();
        Click();

        yearnamelist.clear();
        yearnamelist.add(0,"Select Year");
        for (int i=2018;i<=2021;i++)
        {
            yearnamelist.add(String.valueOf(i));
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(ActivityForm16.this, android.R.layout.simple_spinner_item, yearnamelist);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiyear.setAdapter(dataAdapter);

        spiyear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Object item = adapterView.getItemAtPosition(position);


                if (adapterView.getId() == R.id.spiyear) {
                    String gender = adapterView.getItemAtPosition(position).toString();
//                    Toast.makeText(getActivity(), gender, Toast.LENGTH_SHORT).show();

                    wv_pdf.setVisibility(View.GONE);

                     year = yearnamelist.get(position);

                    System.out.println("year >>>>>>>>>>"+year);

                    if (year.equals("Select Year"))
                    {
//                        Toast.makeText(ActivityForm16.this, "Please select year!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        if(isConnectingToInternet()) {

                            sendform16(sessionManager.getSavedEmpno(),year);
                        }
                        else {
                            CustomAlertdialog.createDialog(ActivityForm16.this,getString(R.string.no_internet));
                        }
                    }


                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // TODO Auto-generated method stub

            }
        });

    }

    private void Click() {

        txt_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (year.equals("Select Year"))
                {
                    Toast.makeText(ActivityForm16.this, "Please select year!", Toast.LENGTH_SHORT).show();
                    wv_pdf.setVisibility(View.GONE);
                }
                else
                {

                    Toast.makeText(ActivityForm16.this, "Please Wait...", Toast.LENGTH_SHORT).show();

                    wv_pdf.getSettings().setSupportZoom(true);
                    wv_pdf.getSettings().setJavaScriptEnabled(true);

                    WebViewClient yourWebClient = new WebViewClient() {

                        @Override
                        public void onPageStarted(WebView view, String url, Bitmap favicon) {
                            super.onPageStarted(view, url, favicon);
                            Customprogress.showPopupProgressSpinner(ActivityForm16.this,true);
                        }

                        @Override
                        public boolean shouldOverrideUrlLoading(WebView view, String url) {
                            return false;
                        }

                        @Override
                        public void onPageFinished(WebView view, String url) {
                            Customprogress.showPopupProgressSpinner(ActivityForm16.this,false);
                        }

                    };

                    wv_pdf.setWebViewClient(yourWebClient);
                    wv_pdf.loadUrl("https://docs.google.com/gview?embedded=true&url="+url);
                    wv_pdf.setVisibility(View.VISIBLE);
                }
            }
        });

        txt_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (year.equals("Select Year"))
                {
                    Toast.makeText(ActivityForm16.this, "Please select year!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (isConnectingToInternet()) {
                        if (ContextCompat.checkSelfPermission(ActivityForm16.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(ActivityForm16.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CAMERA);
                        } else {
                            System.out.println("url >>>>>>" + url);
//                            new DownloadTask(ActivityForm16.this, url);

                            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

                            request.setDescription("Download file...");
                            request.setTitle(URLUtil.guessFileName(url, "", ""));
                            request.allowScanningByMediaScanner();
                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); //Notify client once download is completed!
                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(url, "", ""));
                            DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                            dm.enqueue(request);
                            Toast.makeText(getApplicationContext(), "Downloading File", Toast.LENGTH_LONG).show();

                        }
                    }
                    else
                    {
                        Toast.makeText(ActivityForm16.this, "Oops!! There is no internet connection. Please enable internet connection and try again.", Toast.LENGTH_SHORT).show();

                    }

                }
            }
        });


        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

//                    new DownloadTask(ActivityForm16.this, url);
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

                    request.setDescription("Download file...");
                    request.setTitle(URLUtil.guessFileName(url, "", ""));
                    request.allowScanningByMediaScanner();
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); //Notify client once download is completed!
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(url, "", ""));
                    DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                    dm.enqueue(request);
                    Toast.makeText(getApplicationContext(), "Downloading File", Toast.LENGTH_LONG).show();

                } else {
//                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }


    private void InitView() {

        img_back = (ImageView) findViewById(R.id.img_back);
        spiyear = (Spinner) findViewById(R.id.spiyear);
        txt_view = (TextView) findViewById(R.id.txt_view);
        txt_download = (TextView) findViewById(R.id.txt_download);
        wv_pdf = findViewById(R.id.wv_pdf);

    }

    public void sendform16(String empid, String year) {
        Customprogress.showPopupProgressSpinner(ActivityForm16.this,true);
        mAPIService.form16(empid,year).enqueue(new Callback<Form16Response>() {
            @Override
            public void onResponse(Call<Form16Response> call, Response<Form16Response> response) {

                Customprogress.showPopupProgressSpinner(ActivityForm16.this,false);

                if(response.isSuccessful()) {
//                    Log.i(TAG, "post submitted to API." + response.body().toString());
//                    response.body().getMessage();
//                    response.body().getStatus();
//                    response.body().getData();
                    System.out.println("Status >>>>>>>>>>>>"+response.body().getStatus());
                    System.out.println("Message >>>>>>>>>>>>"+response.body().getMessage());

                    boolean status = response.body().getStatus();

                    if (status==true)
                    {
                        System.out.println("Data >>>>>>>>>>>>"+response.body().getData());
                        url = response.body().getData();
                    }
                    else
                    {
//                        Toast.makeText(ActivityLogin.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<Form16Response> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(ActivityForm16.this,false);
//                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }

    //Check if internet is present or not
    private boolean isConnectingToInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    //Open downloaded folder
    private void openDownloadedFolder() {
        //First check if SD Card is present or not
        if (new CheckForSDCard().isSDCardPresent()) {

            //Get Download Directory File
            File apkStorage = new File(
                    Environment.getExternalStorageDirectory() + "/"
                            + Utils.downloadDirectory);

            //If file is not present then display Toast
            if (!apkStorage.exists())
                Toast.makeText(ActivityForm16.this, "Right now there is no directory. Please download some file first.", Toast.LENGTH_SHORT).show();

            else {

                //If directory is present Open Folder

                /** Note: Directory will open only if there is a app to open directory like File Manager, etc.  **/

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()
                        + "/" + Utils.downloadDirectory);
                intent.setDataAndType(uri, "file/*");
                startActivity(Intent.createChooser(intent, "Open Download Folder"));
            }

        } else
            Toast.makeText(ActivityForm16.this, "Oops!! There is no SD Card.", Toast.LENGTH_SHORT).show();

    }


    public class CheckForSDCard {
        //Check If SD Card is present or not method
        public boolean isSDCardPresent() {
            if (Environment.getExternalStorageState().equals(

                    Environment.MEDIA_MOUNTED)) {
                return true;
            }
            return false;
        }
    }

    public class Utils {

        //String Values to be Used in App
        public static final String downloadDirectory = "Download";

//        public static final String mainUrl = "https://cybraze.com/drmertm/uploads/form16";
//        public static final String mainUrl = "http://103.82.146.90/uploads/form16";
        public static final String mainUrl = "https://drmertm.in/uploads/form16/";
//        public static final String mainUrl = "http://103.82.146.90/uploads/form16/";

    }

    public class DownloadTask {

        private static final String TAG = "Download Task";
        private Context context;
        private String downloadUrl = "", downloadFileName = "";

        public DownloadTask(Context context, String downloadUrl) {
            this.context = context;
            this.downloadUrl = downloadUrl;

            downloadFileName = downloadUrl.replace(Utils.mainUrl, "");//Create file name by picking download file name from URL
           System.out.println("downloadFileName >>>>>>>>>>"+downloadFileName);
            Log.e(TAG, downloadFileName);

            //Start Downloading Task
            new DownloadingTask().execute();
        }

        private class DownloadingTask extends AsyncTask<Void, Void, Void> {

            File apkStorage = null;
            File outputFile = null;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
//                buttonText.setEnabled(false);
//                buttonText.setText(R.string.downloadStarted);//Set Button Text when download started
                Toast.makeText(context, "Download Started…", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected void onPostExecute(Void result) {
                try {
                    if (outputFile != null) {
//                        buttonText.setEnabled(true);
//                        buttonText.setText(R.string.downloadCompleted);//If Download completed then change button text
//                        Toast.makeText(context, "Download Completed…", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(context, "See your Download folder..", Toast.LENGTH_LONG).show();
                        ShowNotification(downloadFileName);

                    } else {
//                        buttonText.setText(R.string.downloadFailed);//If download failed change button text
                        Toast.makeText(context, "Download Failed…", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
//                                buttonText.setEnabled(true);
//                                buttonText.setText(R.string.downloadAgain);//Change button text again after 3sec
                                Toast.makeText(context, "Download Again", Toast.LENGTH_SHORT).show();
                            }
                        }, 3000);

                        Log.e(TAG, "Download Failed");

                    }
                } catch (Exception e) {
                    e.printStackTrace();

                    //Change button text if exception occurs
//                    buttonText.setText(R.string.downloadFailed);
                    Toast.makeText(context, "Download Failed…", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
//                            buttonText.setEnabled(true);
//                            buttonText.setText(R.string.downloadAgain);
                            Toast.makeText(context, "Download Again", Toast.LENGTH_SHORT).show();
                        }
                    }, 3000);
                    Log.e(TAG, "Download Failed with Exception - " + e.getLocalizedMessage());

                }


                super.onPostExecute(result);
            }

            @Override
            protected Void doInBackground(Void... arg0) {
                try {
                    URL url = new URL(downloadUrl);//Create Download URl
                    HttpURLConnection c = (HttpURLConnection) url.openConnection();//Open Url Connection
                    c.setRequestMethod("GET");//Set Request Method to "GET" since we are grtting data
                    c.connect();//connect the URL Connection

                    //If Connection response is not OK then show Logs
                    if (c.getResponseCode() != HttpURLConnection.HTTP_OK) {
                        Log.e(TAG, "Server returned HTTP " + c.getResponseCode() + " " + c.getResponseMessage());

                    }
                    //Get File if SD card is present
                    if (new CheckForSDCard().isSDCardPresent()) {

                        apkStorage = new File(Environment.getExternalStorageDirectory() + "/" + Utils.downloadDirectory);
                    } else
                        Toast.makeText(context, "Oops!! There is no SD Card.", Toast.LENGTH_SHORT).show();

                    //If File is not present create directory
                    if (!apkStorage.exists()) {
                        apkStorage.mkdir();
                        Log.e(TAG, "Directory Created.");
                    }


                    outputFile = new File(apkStorage, downloadFileName);//Create Output file in Main File

                    //Create New File if not present
                    if (!outputFile.exists()) {
                        System.out.println("outputFile >>>>>>>>>"+outputFile);
                        outputFile.createNewFile();
                        Log.e(TAG, "File Created");
                    }

                    FileOutputStream fos = new FileOutputStream(outputFile);//Get OutputStream for NewFile Location

                    InputStream is = c.getInputStream();//Get InputStream for connection

                    byte[] buffer = new byte[1024];//Set buffer type
                    int len1 = 0;//init length
                    while ((len1 = is.read(buffer)) != -1) {
                        fos.write(buffer, 0, len1);//Write new file
                    }

                    //Close all connection after doing task
                    fos.close();
                    is.close();

                } catch (Exception e) {

                    //Read exception if something went wrong
                    e.printStackTrace();
                    outputFile = null;
                    Log.e(TAG, "Download Error Exception " + e.getMessage());
                }

                return null;
            }
        }
    }

    public void ShowNotification (String name)
    {
        Intent intent = new Intent(ActivityForm16.this,ActivityWebViewOffline.class);
        intent.putExtra("data","Form 16");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);


        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(ActivityForm16.this, Constants.CHANNEL_ID);
        Notification notification;
        notification = mBuilder.setSmallIcon(R.mipmap.ic_launcher).setTicker("Download").setWhen(0)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setContentTitle(Html.fromHtml("Download"))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentText(Html.fromHtml(name))
                .setVibrate(new long[]{1000, 1000})
                .setSound(defaultSoundUri)
                .build();

        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager = (NotificationManager) ActivityForm16.this.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(Constants.CHANNEL_ID, Constants.CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(Constants.CHANNEL_DESCRIPTION);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(ID_SMALL_NOTIFICATION, notification);
    }
}
