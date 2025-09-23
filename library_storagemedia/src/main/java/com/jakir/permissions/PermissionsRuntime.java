package com.jakir.permissions;


import static com.jakir.permissions.PermissionsRuntime_helper.REQUEST_CODE_APPDETAILS;
import static com.jakir.permissions.PermissionsRuntime_helper.REQUEST_CODE_FILESTORAGE;
import static com.jakir.permissions.PermissionsRuntime_helper.REQUEST_CODE_MEDIASTORAGE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.jakir.permissions.dialogs.Bottomsheet_dialog_Access;
import com.jakir.permissions.dialogs.Bottomsheet_dialog_Multiple;
import com.jakir.permissions.dialogs.Bottomsheet_dialog_Single;

public class PermissionsRuntime {

    // 🔐 Check single permission
    public static boolean isPermissionGranted(Context context, String permissionName) {
        return ContextCompat.checkSelfPermission(context, permissionName) == PackageManager.PERMISSION_GRANTED;
    }


    // 📥 Request single permission using dialog
    public static void requestPermission(Context context, String permission, int requestCode, String message, boolean showDialog, Drawable image) {
        Bottomsheet_dialog_Single dialog = new Bottomsheet_dialog_Single(context);
        if (showDialog) {
            dialog.show(permission, requestCode, message, image);
        } else {
            dialog.Notshow(permission, requestCode);
        }
    }

    // 📥 Request multiple permissions using dialog
    public static void requestMultiplePermission(Context context, String[] permissions, int requestCode, String message, boolean showDialog, Drawable image) {
        Bottomsheet_dialog_Multiple dialog = new Bottomsheet_dialog_Multiple(context);
        if (showDialog) {
            dialog.show(permissions, requestCode, message, image);
        } else {
            dialog.Notshow(permissions, requestCode);
        }
    }


    // 🎵 Media Storage  audio
    public static boolean checkMediaAudioPermission(Context context) {
        return isPermissionGranted(context, PermissionsRuntime_helper.MediaAudioPermission);
    }

    // 🎵 Media Storage images
    public static boolean checkMediaImagePermission(Context context) {
        return isPermissionGranted(context, PermissionsRuntime_helper.MediaImagePermission);
    }

    // 🎵 Media Storage (images + audio)
    public static boolean checkMediaStoragePermission(Context context) {
        return isPermissionGranted(context, PermissionsRuntime_helper.MediaImagePermission) && isPermissionGranted(context, PermissionsRuntime_helper.MediaAudioPermission);
    }

    public static void requestMediaStoragePermission(Context context, String message, boolean showDialog, Drawable image) {
        requestMultiplePermission(context, PermissionsRuntime_helper.MediaStoragePermissions, REQUEST_CODE_MEDIASTORAGE, message, showDialog, image);
    }

    // 📁 File storage (scoped or legacy)
    public static boolean checkFileStoragePermission(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        } else {
            return isPermissionGranted(context, PermissionsRuntime_helper.READ_EXTERNAL_STORAGE);
        }
    }

    public static void requestFileStoragePermission(Context context, String message, boolean showDialog, Drawable image) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Bottomsheet_dialog_Access dialog = new Bottomsheet_dialog_Access(context);
            if (showDialog) {
                dialog.show(REQUEST_CODE_FILESTORAGE, message, image, null);
            } else {
                dialog.Notshow(REQUEST_CODE_FILESTORAGE, null);
            }
        } else {
            requestMultiplePermission(context, PermissionsRuntime_helper.MediaStoragePermissions, REQUEST_CODE_FILESTORAGE, message, showDialog, image);
        }
    }


    public static void checkRationale_RequestPermission(Activity activity, String permissionName, int allow_code) {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, permissionName)) {
            BottomSheetDialog dialog;
            View view = activity.getLayoutInflater().inflate(R.layout.bottomsheet_permission_settings, null);
            dialog = new BottomSheetDialog(activity); // Style here
            dialog.setContentView(view);
            dialog.show();
            if (dialog.getWindow() != null)
                dialog.getWindow().setNavigationBarColor(Color.TRANSPARENT); //✅ NavigationBar color fix

            Button allow_settings = dialog.findViewById(R.id.allow_settings_btn);
            allow_settings.setOnClickListener(view1 -> {
                dialog.dismiss();
                goAppDetailsActivity(activity);
            });
            ActivityCompat.requestPermissions(activity, new String[]{permissionName}, allow_code);
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{permissionName}, allow_code);
        }
    }

    public static void checkRationale_RequestMultiplePermission(Activity activity, String[] permissions, int allow_code) {
        boolean rtnl = false;
        for (String permission : permissions) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                rtnl = true;
                break;
            }
        }
        if (rtnl) {
            BottomSheetDialog dialog;
            View view = activity.getLayoutInflater().inflate(R.layout.bottomsheet_permission_settings, null);
            dialog = new BottomSheetDialog(activity); // Style here
            dialog.setContentView(view);
            dialog.show();
            if (dialog.getWindow() != null)
                dialog.getWindow().setNavigationBarColor(Color.TRANSPARENT); //✅ NavigationBar color fix

            Button allow_settings = dialog.findViewById(R.id.allow_settings_btn);
            allow_settings.setOnClickListener(view1 -> {
                dialog.dismiss();
                goAppDetailsActivity(activity);
            });
        }
        ActivityCompat.requestPermissions(activity, permissions, allow_code);
    }
    private static void goAppDetailsActivity(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        activity.startActivityIfNeeded(intent, REQUEST_CODE_APPDETAILS);
    }
}
