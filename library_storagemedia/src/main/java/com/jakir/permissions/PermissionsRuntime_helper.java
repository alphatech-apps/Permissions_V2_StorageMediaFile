package com.jakir.permissions;

import android.Manifest;
import android.os.Build;

import java.util.HashMap;

public class PermissionsRuntime_helper {
    // 🔢 All request codes
    public static final int REQUEST_CODE_MEDIASTORAGE_AUDIO = 1010101010, REQUEST_CODE_MEDIASTORAGE_IMAGE = 10101010,  REQUEST_CODE_MEDIASTORAGE = 8888, REQUEST_CODE_FILESTORAGE = 1111013, REQUEST_CODE_APPDETAILS = 101101110;


    private static final HashMap<String, String> permissionNameMap = new HashMap<>();
    private static final HashMap<String, Integer> permissionAnimMap = new HashMap<>();
    private static final HashMap<String, String> permissionMessageMap = new HashMap<>();

    public static String[] MediaStoragePermissions = getMediaStoragePermissions();
    public static String MediaImagePermission = getMediaImagePermissionNameFromApi();
    public static String MediaAudioPermission = getMediaAudioPermissionNameFromApi();
    public static String READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;


    static {
        permissionNameMap.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, "Storage");
        permissionNameMap.put(Manifest.permission.READ_EXTERNAL_STORAGE, "Storage");
        permissionNameMap.put(Manifest.permission.READ_MEDIA_AUDIO, "Music & Audio");
        permissionNameMap.put(Manifest.permission.READ_MEDIA_IMAGES, "Photos & Videos");
        permissionNameMap.put(Manifest.permission.READ_MEDIA_VIDEO, "Photos & Videos");
    }

    static {
        permissionAnimMap.put("Multiple", R.raw.permissionrequried);
//        permissionAnimMap.put("Media Storage", R.raw.folder01);
        permissionAnimMap.put("Storage", R.raw.folder01);
        permissionAnimMap.put("Music & Audio", R.raw.audio01);
        permissionAnimMap.put("Photos & Videos", R.raw.imagefile);
    }

    static {
        permissionMessageMap.put("Multiple", "All permissions are required for the full functionality of the app.");
        permissionMessageMap.put("Media Storage", "Media Storage permission is required to access media files (photos and videos) on your device.");
        permissionMessageMap.put("Storage", "Storage permission is required to access photos, videos, audios and manage all files on your device.");
        permissionMessageMap.put("Music & Audio", "Music & Audio access is required to read your audio files.");
        permissionMessageMap.put("Photos & Videos", "Photos & Videos permission is required to access media files (photos and videos) on your device.");
    }


    private static String[] getMediaStoragePermissions() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            return new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        } else {
            return new String[]{Manifest.permission.READ_MEDIA_AUDIO, Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.READ_MEDIA_VIDEO};
        }
    }

    public static String getMediaImagePermissionNameFromApi() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            return Manifest.permission.READ_EXTERNAL_STORAGE;
        } else {
            return Manifest.permission.READ_MEDIA_IMAGES;
        }
    }

    public static String getMediaAudioPermissionNameFromApi() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            return Manifest.permission.READ_EXTERNAL_STORAGE;
        } else {
            return Manifest.permission.READ_MEDIA_AUDIO;
        }
    }


    public static String getPermissionName(String permissionName) {
        String readableName = permissionNameMap.get(permissionName);
        return (readableName != null) ? readableName : "Unknown Permission";

    }

    public static int getPermissionAnim(String permissionName) {
        Integer animRes = permissionAnimMap.get(permissionName);
        return (animRes != null) ? animRes : R.raw.permissionrequried; // default fallback
    }

    public static String getPermissionMessage(String permissionName) {
        String msg = permissionMessageMap.get(permissionName);
        return (msg != null) ? msg : "This permission is required for app functionality.";
    }
}

