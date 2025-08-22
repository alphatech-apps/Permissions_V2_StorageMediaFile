package com.jakir.permissions.dialogs;

import static android.view.View.VISIBLE;
import static com.jakir.permissions.PermissionsRuntime.checkRationale_RequestPermission;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.jakir.permissions.PermissionsRuntime_helper;
import com.jakir.permissions.R;
import com.jakir.permissions.extraclass.Pref;

//
// Created by JAKIR HOSSAIN on 8/6/2025.
//
public class Bottomsheet_dialog_Single {
    LottieAnimationView lottie;
    TextView tittle_tv, tittle_tvi, message_tv, allow_tv;
    ImageView close_iv, Primage;
    Context context;
    Button allow_btn;

    public Bottomsheet_dialog_Single(Context context) {
        this.context = context;
    }

    public void show(String permissionName, int allow_code, String message, Drawable permissionImage) {
        BottomSheetDialog dialog;
        View view = ((Activity) context).getLayoutInflater().inflate(R.layout.permission_bottomsheet_layout, null);
        dialog = new BottomSheetDialog(context, R.style.AppBottomSheetDialogTheme); // Style here
        dialog.setContentView(view);
        dialog.show();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setNavigationBarColor(ContextCompat.getColor(context, R.color.DayLight_NightDark)); //✅ NavigationBar color fix
        }

        String pDisplayName = PermissionsRuntime_helper.getPermissionName(permissionName);

        if (permissionImage != null) {
            tittle_tvi = dialog.findViewById(R.id.tittle_tvi);
            Primage = dialog.findViewById(R.id.Primage);

            Primage.setImageDrawable(permissionImage);
            tittle_tvi.setVisibility(VISIBLE);
            tittle_tvi.setText(pDisplayName + " Permission");
            Primage.setVisibility(VISIBLE);
        } else {
            tittle_tv = dialog.findViewById(R.id.tittle_tv);
            lottie = dialog.findViewById(R.id.lottie);

            lottie.setAnimation(PermissionsRuntime_helper.getPermissionAnim(pDisplayName));
            lottie.playAnimation();
            tittle_tv.setVisibility(VISIBLE);
            tittle_tv.setText(pDisplayName + " Permission");
            lottie.setVisibility(VISIBLE);
        }

        message_tv = dialog.findViewById(R.id.message_tv);
        message_tv.setText(message.isEmpty() ? PermissionsRuntime_helper.getPermissionMessage(pDisplayName) : message);

        allow_btn = dialog.findViewById(R.id.allow_btn);
        allow_btn.setOnClickListener(view1 -> {
            dialog.dismiss();
            if (Pref.getState(permissionName, context)) {
                checkRationale_RequestPermission((Activity) context, permissionName, allow_code);
            } else {
                Pref.setState(true, permissionName, context);
                ActivityCompat.requestPermissions((Activity) context, new String[]{permissionName}, allow_code);
            }
        });
        close_iv = dialog.findViewById(R.id.close_iv);
        close_iv.setOnClickListener(v -> dialog.dismiss());
    }

    public void Notshow(String permissionName, int allow_code) {
        if (Pref.getState(permissionName, context)) {
            checkRationale_RequestPermission((Activity) context, permissionName, allow_code);
        } else {
            Pref.setState(true, permissionName, context);
            ActivityCompat.requestPermissions((Activity) context, new String[]{permissionName}, allow_code);
        }
    }
}
