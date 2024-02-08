package com.digid.eddokenaCM.Utils;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


public class PhoneCall {
    Context context;

    private static final int CALL_REQUEST_CODE = 1;

    public PhoneCall(Context context) {
        this.context = context;
    }


    public void makePhoneCall(String num) {
        if (num.trim().length() > 0) {

            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((AppCompatActivity) context,
                        new String[]{Manifest.permission.CALL_PHONE}, CALL_REQUEST_CODE);
            } else {
                String dial = "tel:" + num;
                context.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }

        } else {
            Toast.makeText(context, "Ce client n'a pas de numéro ", Toast.LENGTH_LONG).show();
        }
    }

}
