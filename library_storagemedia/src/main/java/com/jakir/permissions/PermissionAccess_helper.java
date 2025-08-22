package com.jakir.permissions;

import java.util.HashMap;

//
// Created by JAKIR HOSSAIN on 8/16/2025.
//
public class PermissionAccess_helper {
    // 🔢 All request codes
    public static final int REQUEST_CODE_MANAGESTORAGE = 1111013;
    private static final HashMap<Integer, String> specialPermissionNameMap = new HashMap<>();
    private static final HashMap<Integer, Integer> specialPermissionAnimMap = new HashMap<>();
    private static final HashMap<Integer, String> specialPermissionMessageMap = new HashMap<>();

    static {
        specialPermissionNameMap.put(REQUEST_CODE_MANAGESTORAGE, "All Files Access");
    }

    static {
        specialPermissionAnimMap.put(REQUEST_CODE_MANAGESTORAGE, R.raw.folder01);
    }

    static {
        specialPermissionMessageMap.put(REQUEST_CODE_MANAGESTORAGE, "Files Storage Access is needed to read and manage all files on your device.");
    }


    // Get name by code
    public static String getAccessPermissionName(int code) {
        String name = specialPermissionNameMap.get(code);
        return (name != null) ? name : "Unknown Permission";
    }

    // Method to get animation by code
    public static int getAccessPermissionAnim(int code) {
        Integer animRes = specialPermissionAnimMap.get(code);
        return (animRes != null) ? animRes : R.raw.permissionrequried; // default fallback
    }

    // Method to get message by code
    public static String getAccessPermissionMessage(int code) {
        String msg = specialPermissionMessageMap.get(code);
        return (msg != null) ? msg : "This permission is required for app functionality.";
    }
}
