package com.goorwl.utils;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

public class Loading {
    private static AlertDialog sSingleTest;

    private Loading() {
    }

    public static AlertDialog getInstance(Context context) {
        if (sSingleTest == null) {
            synchronized (Loading.class) {
                if (sSingleTest == null) {
                    sSingleTest = new AlertDialog.Builder(context)
                            .setView(R.layout.layout_loading).create();
                }
            }
        }
        return sSingleTest;
    }

    // 显示等待对话框
    public static void show(Context context) {
        getInstance(context).show();
    }

    // 取消对话框
    public static void dismiss() {
        if (sSingleTest != null) {
            sSingleTest.dismiss();
            sSingleTest = null;
        }
    }
}
