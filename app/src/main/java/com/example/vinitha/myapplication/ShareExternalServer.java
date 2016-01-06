package com.example.vinitha.myapplication;

/**
 * Created by vinitha on 05-01-2016.
 */
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class ShareExternalServer {

    public String shareRegIdWithAppServer(final String regId) {

        String result = "";
        String TAG="server connection";
        Map paramsMap = new HashMap();
        paramsMap.put("regId", regId);
        try {
            URL serverUrl = null;
            try {
                serverUrl = new URL(Config.APP_SERVER_URL);
                Log.i(TAG,"DONE WITH CALLING SERVER!");
            } catch (MalformedURLException e) {
                Log.e("AppUtil", "URL Connection Error: "
                        + Config.APP_SERVER_URL, e);
                result = "Invalid URL: " + Config.APP_SERVER_URL;
            }

            StringBuilder postBody = new StringBuilder();
            Iterator iterator = paramsMap.entrySet()
                    .iterator();

            while (iterator.hasNext()) {
                Entry param = (Entry) iterator.next();
                postBody.append(param.getKey()).append('=')
                        .append(param.getValue());
                if (iterator.hasNext()) {
                    postBody.append('&');
                }
            }
            String body = postBody.toString();
            byte[] bytes = body.getBytes();
            HttpURLConnection httpCon = null;
            try {
                httpCon = (HttpURLConnection) serverUrl.openConnection();
                httpCon.setDoOutput(true);
                httpCon.setUseCaches(false);
                httpCon.setFixedLengthStreamingMode(bytes.length);
                httpCon.setRequestMethod("POST");
                httpCon.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded;charset=UTF-8");
                OutputStream out = httpCon.getOutputStream();
                Log.i(TAG,"DONE WITH CALLING SERVER2!");
                out.write(bytes);
                out.close();
                Log.i(TAG, "DONE WITH CALLING SERVER3!");
                int status = httpCon.getResponseCode();
                if (status == 200) {
                    result = "RegId shared with Application Server. RegId: "
                            + regId;
                } else {
                    result = "Post Failure." + " Status: " + status;
                }
            } finally {
                if (httpCon != null) {
                    httpCon.disconnect();
                }
            }

        } catch (IOException e) {
            result = "Post Failure. Error in sharing with App Server.";
            Log.e("AppUtil", "Error in sharing with App Server: " + e);
        }
        return result;
    }
}