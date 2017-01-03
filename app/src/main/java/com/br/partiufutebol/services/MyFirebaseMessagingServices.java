package com.br.partiufutebol.services;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Suporte01 on 25/11/2016.
 */
public class MyFirebaseMessagingServices extends FirebaseMessagingService {
    public final String TAG = "NOTICIAS";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String messagem = remoteMessage.getFrom();
        if(remoteMessage.getNotification()!= null){
            Log.d(TAG, "Notificação"+ remoteMessage.getNotification().getBody());


        }

    }


}
