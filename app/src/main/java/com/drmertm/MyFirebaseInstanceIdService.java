package com.drmertm;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;

/**
 * Created by Belal on 12/8/2017.
 */

//the class extending FirebaseInstanceIdService
public class MyFirebaseInstanceIdService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if(!task.isSuccessful())
                {
                    Log.w(TAG, "getInstanceId failed", task.getException());
                    return;
                }
                //String token = task.getResult().getToken();
                String token=task.getResult().getToken();
                Log.d(TAG, token);
                Log.d(TAG, "Refreshed token: " + token);
                System.out.println("device Token >>>>>>>>>"+token);

            }
        });


    }




}
