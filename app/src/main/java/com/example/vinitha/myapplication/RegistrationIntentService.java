package com.example.vinitha.myapplication;


import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

/**
 * Created by vinitha on 04-01-2016.
 */

public class RegistrationIntentService extends IntentService {
private static final String TAG = "RegIntentService";
private static final String[] TOPICS = {"global"};

    public RegistrationIntentService() {
        super(TAG);
    }

        @Override
        public void onHandleIntent(Intent intent) {




            try {
                InstanceID instanceID = InstanceID.getInstance(this);
                String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                        GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

                Log.i(TAG, "GCM Registration Token: " + token);
                nextclass v=new nextclass();
                v.gettoastRegid(token);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            Intent registrationComplete = new Intent(quickstartpreference.REGISTRATION_COMPLETE);
             LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);


        }


}