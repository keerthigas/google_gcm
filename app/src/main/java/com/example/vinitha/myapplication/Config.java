package com.example.vinitha.myapplication;

/**
 * Created by vinitha on 05-01-2016.
 */
public interface Config {

    // used to share GCM regId with application server - using php app server
    static final String APP_SERVER_URL = "http://172.16.10.30:8080/WebApplication2/?shareRegId=1";

    // GCM server using java
    // static final String APP_SERVER_URL =
    // "http://192.168.1.17:8080/GCM-App-Server/GCMNotification?shareRegId=1";

    // Google Project Number
   // static final String GOOGLE_PROJECT_ID = "1176";
    static final String MESSAGE_KEY = "message";

}