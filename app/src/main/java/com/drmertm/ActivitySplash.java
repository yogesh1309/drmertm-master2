package com.drmertm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

import com.google.firebase.iid.FirebaseInstanceId;

public class ActivitySplash extends AppCompatActivity {

    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        try {

            sessionManager = new SessionManager(ActivitySplash.this);
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//            getSupportActionBar().hide();


            // Firebase Token
            System.out.println("Firebase token>>>>>>>>>> "+ FirebaseInstanceId.getInstance().getToken());
            sessionManager.setSavedFcmtoken(FirebaseInstanceId.getInstance().getToken());

            //Device Id
            String m_androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
            System.out.println("Device Id >>>>>>>>>> "+ m_androidId);
            sessionManager.setSavedDeviceid(m_androidId);

                   Thread background = new Thread() {
                public void run()
                {
                    try {
                        // Thread will sleep for 5 seconds
                        sleep(3*1000);
                        // After 5 seconds redirect to another intent

                        if(sessionManager.isUserLogin())
                        {
                            Intent in=new Intent(ActivitySplash.this, ActivityDashboard.class);
                            startActivity(in);
                            //Remove activity
                            finish();
                        }
                        else
                        {
                            Intent in=new Intent(ActivitySplash.this, ActivityLogin.class);
                            startActivity(in);
                            //Remove activity
                            finish();
                        }
//                        Intent in=new Intent(ActivitySplash.this, ActivityLogin.class);
//                        startActivity(in);
                        //Remove activity
//                        finish();
                    }
                    catch (Exception e) {
                    }
                }
            };
            // start thread
            background.start();

        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
