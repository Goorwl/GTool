package com.goorwl.utils;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

/**
 * FileName: CoreActivity
 * Author: Goorwl
 * Create Date: 2019/4/2 15:04
 * Github: https://github.com/Goorwl
 * Blog: https://xiaozhuanlan.com/goorwl
 */

public abstract class CoreActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AppManager.getAppManager().addActivity(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AppManager.getAppManager().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().removeActivity(this);
    }

    public void jumpActivity(Class clazz) {
        jumpActivity(clazz, null);
    }

    /**
     * 快捷跳转函数
     *
     * @param clazz
     * @param bundle
     */
    public void jumpActivity(Class clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void onResult(Object data) {
    }

    //页面标记 方便跳转
    String acTag = "";

    public void setTag(String tag) {
        this.acTag = tag;
    }

    public String getTag() {
        return acTag;
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }

    @Override
    public void startActivity(Intent intent, @Nullable Bundle options) {
        super.startActivity(intent, options);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }

    public void jumpActivity(String activityUrl) {
        if (activityUrl == null || activityUrl.equals("")) {
            Toast.makeText(this, "功能暂未开放", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            Class clz = Class.forName(activityUrl);
            startActivity(new Intent(this, clz));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.d("error", e.toString());
        }
    }
}
