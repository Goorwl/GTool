package com.goorwl.lomotool.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.goorwl.lomotool.BuildConfig;
import com.goorwl.lomotool.R;
import com.goorwl.lomotool.config.ConfigString;
import com.goorwl.utils.AppManager;
import com.goorwl.utils.CoreActivity;
import com.goorwl.utils.ImageUtils;
import com.goorwl.utils.LogUtils;
import com.goorwl.utils.SPUtils;
import com.goorwl.utils.SingleTimer;
import com.goorwl.utils.ThreadUtils;

public class MainActivity extends CoreActivity implements ConfigString {
    private static final String TAG = "MainActivity";

    private Activity mActivity;

    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity = this;
        setTag(CONFIG_HOME);
        String tag = getTag();


        mButton = findViewById(R.id.button);
        mButton.setOnClickListener(v -> jumpActivity(Page2Activity.class));

        LogUtils.setEnable(BuildConfig.DEBUG);
        LogUtils.setLevel(1);

        LogUtils.v(TAG, "onCreate: ");
        LogUtils.i(TAG, "onCreate: ");
        LogUtils.d(TAG, "onCreate: ");
        LogUtils.w(TAG, "onCreate: ");
        LogUtils.e(TAG, "onCreate: ");

        ThreadUtils.runOnSubThread(() -> {
            // do something on sub thread
        });

        ThreadUtils.runOnUiThread(() -> {
            // do something on main thread
        });

        boolean resPutInt = SPUtils.putInt(CONFIG_HOME, 1);
        int     resGetInt = SPUtils.getInt(CONFIG_HOME, 0);

        SingleTimer.getInstance().setTime(3);
        int time = SingleTimer.getInstance().getTime();
        SingleTimer.getInstance().startTime(5);
        SingleTimer.getInstance().stopTime();

        CoreActivity topActivity = AppManager.getAppManager().getTopActivity();
        CoreActivity preActivity = AppManager.getAppManager().getPreActivity(this);
//
//        AppManager.getAppManager().backToTagFront("xxx");
//        AppManager.getAppManager().backToTag("xxx");
//        AppManager.getAppManager().backToHome();

//        Bitmap xxx = ImageUtils.stringToBitmap("xxx");
//        String res   = ImageUtils.bitmapToString(xxx);
//        Drawable drawable = ImageUtils.bitmapToDrawable(mActivity, xxx);
//        Bitmap bitmap = ImageUtils.drawableToBitmap(drawable);
    }
}
