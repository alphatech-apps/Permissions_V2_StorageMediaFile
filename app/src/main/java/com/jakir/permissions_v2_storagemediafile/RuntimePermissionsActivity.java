package com.jakir.permissions_v2_storagemediafile;

import static com.jakir.permissions.PermissionsRuntime_helper.REQUEST_CODE_FILESTORAGE;
import static com.jakir.permissions.PermissionsRuntime_helper.REQUEST_CODE_MEDIASTORAGE;
import static com.jakir.permissions.PermissionsRuntime_helper.REQUEST_CODE_MEDIASTORAGE_AUDIO;
import static com.jakir.permissions.PermissionsRuntime_helper.REQUEST_CODE_MEDIASTORAGE_IMAGE;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.materialswitch.MaterialSwitch;
import com.jakir.permissions.PermissionsRuntime;
import com.jakir.permissions.PermissionsRuntime_helper;

public class RuntimePermissionsActivity extends AppCompatActivity {
    MaterialSwitch sw_mediastorage_audio_allow, sw_mediastorage_image_allow, sw_mediastorage_allow, sw_filestorage_allow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initViews();
        setListeners();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setSwitchToggle(sw_mediastorage_audio_allow, sw_mediastorage_image_allow, sw_mediastorage_allow, sw_filestorage_allow);
    }

    private void setSwitchToggle(MaterialSwitch sw_mediastorage_audio_allow, MaterialSwitch sw_mediastorage_image_allow, MaterialSwitch sw_mediastorage_allow, MaterialSwitch sw_filestorage_allow) {
        if (sw_mediastorage_audio_allow != null)
            sw_mediastorage_audio_allow.setChecked(PermissionsRuntime.checkMediaAudioPermission(this));
        if (sw_mediastorage_image_allow != null)
            sw_mediastorage_image_allow.setChecked(PermissionsRuntime.checkMediaImagePermission(this));
        if (sw_mediastorage_allow != null)
            sw_mediastorage_allow.setChecked(PermissionsRuntime.checkMediaStoragePermission(this));
        if (sw_filestorage_allow != null)
            sw_filestorage_allow.setChecked(PermissionsRuntime.checkFileStoragePermission(this));
    }

    private void initViews() {
        sw_mediastorage_audio_allow = findViewById(R.id.sw_mediastorage_audio_allow);
        sw_mediastorage_image_allow = findViewById(R.id.sw_mediastorage_image_allow);
        sw_mediastorage_allow = findViewById(R.id.sw_mediastorage_allow);

        sw_filestorage_allow = findViewById(R.id.sw_filestorage_allow);
    }

    private void setListeners() {

        sw_mediastorage_audio_allow.setOnClickListener(v -> {
            if (!PermissionsRuntime.checkMediaAudioPermission(this)) {
                PermissionsRuntime.requestPermission(this, PermissionsRuntime_helper.MediaAudioPermission, REQUEST_CODE_MEDIASTORAGE_AUDIO, "", true, null);
            }
        });
        sw_mediastorage_image_allow.setOnClickListener(v -> {
            if (!PermissionsRuntime.checkMediaImagePermission(this)) {
                PermissionsRuntime.requestPermission(this, PermissionsRuntime_helper.MediaImagePermission, REQUEST_CODE_MEDIASTORAGE_IMAGE, "", true, null);
            }
        });
        sw_mediastorage_allow.setOnClickListener(v -> {
            if (!PermissionsRuntime.checkMediaStoragePermission(this)) {
                PermissionsRuntime.requestMediaStoragePermission(this, "", true, null);
            }
        });

        sw_filestorage_allow.setOnClickListener(v -> {
            if (!PermissionsRuntime.checkFileStoragePermission(this)) {
                PermissionsRuntime.requestFileStoragePermission(this, "", true, null);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_MEDIASTORAGE_AUDIO) {
            boolean sw_mediastorage_state = PermissionsRuntime.checkMediaAudioPermission(this);
            sw_mediastorage_audio_allow.setChecked(sw_mediastorage_state);
            Toast.makeText(this, sw_mediastorage_state ? "✅ Audio Permission Granted" : "❌ Audio Permission Denied", Toast.LENGTH_SHORT).show();
        } else if (requestCode == REQUEST_CODE_MEDIASTORAGE_IMAGE) {
            boolean sw_mediastorage_state = PermissionsRuntime.checkMediaImagePermission(this);
            sw_mediastorage_image_allow.setChecked(sw_mediastorage_state);
            Toast.makeText(this, sw_mediastorage_state ? "✅ Image Permission Granted" : "❌ Image Permission Denied", Toast.LENGTH_SHORT).show();
        } else if (requestCode == REQUEST_CODE_MEDIASTORAGE) {
            boolean sw_mediastorage_state = PermissionsRuntime.checkMediaStoragePermission(this);
            sw_mediastorage_allow.setChecked(sw_mediastorage_state);
            Toast.makeText(this, sw_mediastorage_state ? "✅ MEDIASTORAGE Permission Granted" : "❌ MEDIASTORAGE Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE_FILESTORAGE: {
                boolean fileStorage = Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && PermissionsRuntime.checkFileStoragePermission(this);
                sw_filestorage_allow.setChecked(fileStorage);
                Toast.makeText(this, fileStorage ? "✅ File storage access granted!" : "❌ File storage access not granted", Toast.LENGTH_SHORT).show();
                break;
            }

        }

    }
}