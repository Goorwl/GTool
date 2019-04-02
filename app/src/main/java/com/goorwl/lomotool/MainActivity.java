package com.goorwl.lomotool;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import com.goorwl.utils.CoreActivity;
import com.goorwl.utils.LogUtils;
import com.goorwl.utils.SingleInstance;

public class MainActivity extends CoreActivity implements ConfigString {
    private static final String TAG = "MainActivity";

    private Activity mActivity;

    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity = this;

        mButton=findViewById(R.id.button);

        SingleInstance<TestBean> singleInstance = new SingleInstance<TestBean>() {
            @Override
            protected TestBean create() {
                return new TestBean("Tom", 23);
            }
        };

        setTag(CONFIG_HOME);

        int age = singleInstance.get().getAge();
        LogUtils.e(TAG, "onCreate: " + age);

        mButton.setOnClickListener(v -> jumpActivity(Page2Activity.class));

    }
}
