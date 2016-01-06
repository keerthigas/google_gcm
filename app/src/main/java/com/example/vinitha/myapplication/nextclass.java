package com.example.vinitha.myapplication;



/**
 * Created by vinitha on 04-01-2016.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

    public class nextclass extends AppCompatActivity {

        private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
        private static final String TAG = "nextclass";
       private String d;
      public TextView tt;
        private BroadcastReceiver mRegistrationBroadcastReceiver;
      //  private ProgressBar mRegistrationProgressBar;
       private TextView mInformationTextView;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.content_main);

            tt=(TextView)findViewById(R.id.txt2);
            mInformationTextView=(TextView)findViewById(R.id.txt1);
            mRegistrationBroadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    // mRegistrationProgressBar.setVisibility(ProgressBar.GONE);
                    SharedPreferences sharedPreferences =
                            PreferenceManager.getDefaultSharedPreferences(context);

                    boolean sentToken = sharedPreferences
                            .getBoolean(quickstartpreference.SENT_TOKEN_TO_SERVER, false);
                    if (sentToken) {
                        mInformationTextView.setText(getString(R.string.gcm_send_message));
                    } else {
                        mInformationTextView.setText("token recevied wait for our GCM text!!"+d);
                    }
                }
            };

            if (checkPlayServices()) {
                // Start IntentService to register this application with GCM.

                Intent intent = new Intent(this, RegistrationIntentService.class);
                startService(intent);

            }
        }

        public void gettoastRegid(String vtok) {
            d=vtok;
            Log.i(TAG, "GCM Registration Token(nexclas): " + vtok);
            Log.i(TAG, "GCM Registration Token(D VALUE): " + d);
          // Toast.makeText(this,
                //   "REGID" + vtok, Toast.LENGTH_LONG).show();
         //   mInformationTextView.setText(d);
            ShareExternalServer si=new ShareExternalServer();
            si.shareRegIdWithAppServer(vtok);
        }


        //  mInformationTextView = (TextView) findViewById(R.id.informationTextView);




        @Override
        protected void onResume() {
            super.onResume();
            LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                    new IntentFilter(quickstartpreference.REGISTRATION_COMPLETE));
        }


        @Override
        protected void onPause() {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
            super.onPause();
        }

        /**
         * Check the device to make sure it has the Google Play Services APK. If
         * it doesn't, display a dialog that allows users to download the APK from
         * the Google Play Store or enable it in the device's system settings.
         */
        private boolean checkPlayServices() {
            GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
            int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
            if (resultCode != ConnectionResult.SUCCESS) {
                if (apiAvailability.isUserResolvableError(resultCode)) {
                    apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                            .show();
                } else {
                    Log.i(TAG, "This device is not supported.");
                    finish();
                }
                return false;
            }
            return true;
        }

    }