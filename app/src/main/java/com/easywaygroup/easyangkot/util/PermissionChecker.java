package com.easywaygroup.easyangkot.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

public class PermissionChecker {

    public static String fineLocationPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    public static boolean checkPermissionIsGranted(Context context, String permission) {
        if(ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            return true;
        }
    }

}
