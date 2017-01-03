package com.br.partiufutebol.services;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Suporte01 on 25/11/2016.
 */
public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {
 public final String TAG = "NOTICIAS";
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String token = FirebaseInstanceId.getInstance().getToken();

        Log.d(TAG, "Token"+ token);


    }
}
