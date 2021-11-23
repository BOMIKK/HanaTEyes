package com.example.hanateyes;

import android.app.Service;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;  //ADD FirebaseMessagingService

public class MyFirebaseInstanceIdService extends FirebaseMessagingService   {

    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }
    private void sendRegistrationToServer(String token){
        //디바이스 토큰이 생성되거나 재생성 될 시 동작할 코드 작성
    }
}