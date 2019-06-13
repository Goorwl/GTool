package com.goorwl.lomotool.activity;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.widget.Button;

import com.goorwl.lomotool.R;
import com.goorwl.lomotool.bean.TestBean;
import com.goorwl.lomotool.config.ConfigString;
import com.goorwl.utils.CoreActivity;
import com.goorwl.utils.LogUtils;

public class Page2Activity extends CoreActivity implements ConfigString {

    private static final String TAG = "Page2Activity";

    private Activity mActivity;
    private Button   mButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2);
        mActivity = this;

        setTag(CONFIG_PAGE2);

        mButton = findViewById(R.id.button2);
        mButton.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("person", new TestBean("haha", 18));
            jumpActivity(Page3Activity.class, bundle);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.e(TAG, "onDestroy: ");
    }
}
