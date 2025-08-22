package com.jakir.permissions;


import static com.jakir.permissions.PermissionAccess_helper.REQUEST_CODE_MANAGESTORAGE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;

public class PermissionAccess {


    public static boolean isManageStorageGranted(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        }
        return true;
    }

    public static void requestManageStorage(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                try {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                    intent.addCategory("android.intent.category.DEFAULT");
                    intent.setData(Uri.parse(String.format("package:%s", context.getPackageName())));
                    ((Activity) context).startActivityIfNeeded(intent, REQUEST_CODE_MANAGESTORAGE);
                } catch (Exception exception) {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                    ((Activity) context).startActivityIfNeeded(intent, REQUEST_CODE_MANAGESTORAGE);
                }
            }
        }
    }
}
