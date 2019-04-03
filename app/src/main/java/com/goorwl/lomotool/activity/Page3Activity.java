package com.goorwl.lomotool.activity;

import android.os.Bundle;
import android.widget.Button;

import com.goorwl.lomotool.R;
import com.goorwl.lomotool.bean.TestBean;
import com.goorwl.lomotool.config.ConfigString;
import com.goorwl.utils.AppManager;
import com.goorwl.utils.CoreActivity;
import com.goorwl.utils.LogUtils;

public class Page3Activity extends CoreActivity implements ConfigString {
    private static final String TAG = "Page3Activity";

    private CoreActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page3);
        mActivity = this;

        Bundle   key  = getIntent().getExtras();
        TestBean kakk = (TestBean) key.getSerializable("person");

        LogUtils.e(TAG, "onCreate: " + kakk.getName());

        Button btn3 = findViewById(R.id.button3);
        Button btn4 = findViewById(R.id.button4);
        Button btn5 = findViewById(R.id.button5);
        Button btn6 = findViewById(R.id.button6);
        Button btn7 = findViewById(R.id.button7);

        btn3.setOnClickListener(v -> {
            AppManager.getAppManager().backToHome(false);
        });
        btn4.setOnClickListener(v -> {
            AppManager.getAppManager().backToTag(CONFIG_HOME);

        });
        btn5.setOnClickListener(v -> {
            AppManager.getAppManager().backToTagFront(CONFIG_PAGE2);
        });
        btn6.setOnClickListener(v -> {
            CoreActivity topActivity = AppManager.getAppManager().getTopActivity();
            LogUtils.e(TAG, "onCreate: " + topActivity.getLocalClassName());
        });
        btn7.setOnClickListener(v -> {
            CoreActivity topActivity = AppManager.getAppManager().getPreActivity(mActivity);
            LogUtils.e(TAG, "onCreate: " + topActivity.getLocalClassName());
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.e(TAG, "onDestroy: ");
    }
}
