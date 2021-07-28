package com.drmertm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.drmertm.ModelClass.ViewOfficeOrderData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActivityOfficeOrderDetails extends AppCompatActivity {

    ImageView img_back;
    TextView txt_refno,txt_subject,txt_deptname,txt_headname,txt_view,txt_download,txt_refdate,txt_issdate,txt_letterdate;
    ViewOfficeOrderData list;
    private int REQUEST_CAMERA = 0;
    public static final int ID_BIG_NOTIFICATION = 234;
    public static final int ID_SMALL_NOTIFICATION = 235;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_office_order_details);
        InitView();
        Click();

        try {
            Intent intent = getIntent();
            if (intent!=null)
            {


                list = (ViewOfficeOrderData)intent.getSerializableExtra("data");
                   txt_refno.setText(list.getReferenceNo());
                   txt_deptname.setText(list.getDepartmentName());
                   txt_headname.setText(list.getHeadName());
                   txt_subject.setText(list.getSubject());
                txt_letterdate.setText(list.getLetterDate());
                   txt_refdate.setText(list.getReferenceDate());

                   String issdate = list.getIssueDate();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date date = format.parse(issdate);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String dateTime = dateFormat.format(date);
                    txt_issdate.setText(dateTime);

                } catch (ParseException e) {
                    e.printStackTrace();
                }


                   url = Allurls.ImageURL+list.getDocument();

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

        txt_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityOfficeOrderDetails.this,ActivityOfficeOrderWebview.class);
                intent.putExtra("url",url);
                startActivity(intent);
            }
        });

        txt_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnectingToInternet()) {
                    if (ContextCompat.checkSelfPermission(ActivityOfficeOrderDetails.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(ActivityOfficeOrderDetails.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CAMERA);
                    } else {

                        new DownloadTask(ActivityOfficeOrderDetails.this, url);
                    }
                }
                else
                {
                    Toast.makeText(ActivityOfficeOrderDetails.this, "Oops!! There is no internet connection. Please enable internet connection and try again.", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void InitView() {
        img_back = findViewById(R.id.img_back);
        txt_refno = findViewById(R.id.txt_refno);
        txt_subject = findViewById(R.id.txt_subject);
        txt_deptname = findViewById(R.id.txt_deptname);
        txt_headname = findViewById(R.id.txt_headname);
        txt_view = findViewById(R.id.txt_view);
        txt_download = findViewById(R.id.txt_download);
        txt_refdate = findViewById(R.id.txt_refdate);
        txt_issdate = findViewById(R.id.txt_issdate);
        txt_letterdate = findViewById(R.id.txt_letterdate);
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
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        System.out.println("Request Code >>>>>>>"+requestCode);
        switch (requestCode) {
            case 0: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    new DownloadTask(ActivityOfficeOrderDetails.this, url);

                } else {
//                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
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
                Toast.makeText(ActivityOfficeOrderDetails.this, "Right now there is no directory. Please download some file first.", Toast.LENGTH_SHORT).show();

            else {

                //If directory is present Open Folder

                /** Note: Directory will open only if there is a app to open directory like File Manager, etc.  **/

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()
                        + "/" + ActivityForm16.Utils.downloadDirectory);
                intent.setDataAndType(uri, "file/*");
                startActivity(Intent.createChooser(intent, "Open Download Folder"));
            }

        } else
            Toast.makeText(ActivityOfficeOrderDetails.this, "Oops!! There is no SD Card.", Toast.LENGTH_SHORT).show();

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
        public static final String mainUrl = "https://drmertm.in/uploads/officeorder/";

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
            new DownloadTask.DownloadingTask().execute();
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
                        Toast.makeText(context, "See your Download folder..", Toast.LENGTH_LONG).show();
                        ShowNotification(downloadFileName);
                        System.out.println("Download file >>>>>>>>>>>"+outputFile);
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
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(ActivityOfficeOrderDetails.this, Constants.CHANNEL_ID);
        Notification notification;
        notification = mBuilder.setSmallIcon(R.mipmap.ic_launcher).setTicker("Download").setWhen(0)
                .setAutoCancel(true)

                .setContentTitle(Html.fromHtml("Download"))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentText(Html.fromHtml(name))
                .setVibrate(new long[]{1000, 1000})
                .setSound(defaultSoundUri)
                .build();

        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager = (NotificationManager) ActivityOfficeOrderDetails.this.getSystemService(Context.NOTIFICATION_SERVICE);
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