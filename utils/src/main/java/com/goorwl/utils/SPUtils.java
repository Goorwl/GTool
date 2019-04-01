package com.goorwl.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SPUtils {
    private static SharedPreferences sSharedPreferences;

    private SPUtils() {
    }

    public static void init(Context context) {
        if (sSharedPreferences == null) {
            synchronized (SPUtils.class) {
                if (sSharedPreferences == null) {
                    sSharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
                }
            }
        }
    }

    public static void putBoolean(String key, boolean value) {
        if (sSharedPreferences == null) {
            return;
        }
        sSharedPreferences.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(String key, boolean defValue) {
        if (sSharedPreferences == null) {
            return false;
        }
        return sSharedPreferences.getBoolean(key, defValue);
    }

    public static void putString(String key, String value) {
        if (sSharedPreferences == null) {
            return;
        }
        sSharedPreferences.edit().putString(key, value).commit();
    }

    public static String getString(String key, String defValue) {
        if (sSharedPreferences == null) {
            return "";
        }
        return sSharedPreferences.getString(key, defValue);
    }

    public static void putInt(String key, int value) {
        if (sSharedPreferences == null) {
            return;
        }
        sSharedPreferences.edit().putInt(key, value).commit();
    }

    public static int getInt(String key, int defValue) {
        if (sSharedPreferences == null) {
            return 0;
        }
        return sSharedPreferences.getInt(key, defValue);
    }

    public static void putFloat(String key, float value) {
        if (sSharedPreferences == null) {
            return;
        }
        sSharedPreferences.edit().putFloat(key, value).commit();
    }

    public static Float getFloat(String key, Float defValue) {
        if (sSharedPreferences == null) {
            return 0f;
        }
        return sSharedPreferences.getFloat(key, defValue);
    }


    public static void putLong(String key, long value) {
        if (sSharedPreferences == null) {
            return;
        }
        sSharedPreferences.edit().putLong(key, value).commit();
    }

    public static long getLong(String key, long defValue) {
        if (sSharedPreferences == null) {
            return 0l;
        }
        return sSharedPreferences.getLong(key, defValue);
    }
}
