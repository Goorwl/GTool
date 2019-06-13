package com.goorwl.lomotool.activity;

import android.os.Bundle;

import com.goorwl.lomotool.R;
import com.goorwl.lomotool.config.ConfigString;
import com.goorwl.utils.CoreActivity;
import com.goorwl.utils.LiveEventBus;
import com.goorwl.utils.ThreadUtils;

public class SplashActivity extends CoreActivity implements ConfigString {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        LiveEventBus.get().with(CONFIG_SPLASH).observe1(this, s -> {
            jumpActivity(MainActivity.class);
            finish();
        });

        ThreadUtils.runOnUiThread(() -> {
            LiveEventBus.get().with(CONFIG_SPLASH).postValueDelay("xxx", 2000);
        });
    }
}
