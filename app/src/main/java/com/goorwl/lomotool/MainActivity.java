package com.goorwl.lomotool;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.goorwl.utils.LogUtils;
import com.goorwl.utils.SingleInstance;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SingleInstance<TestBean> singleInstance = new SingleInstance<TestBean>() {
            @Override
            protected TestBean create() {
                return new TestBean("Tom", 23);
            }
        };

        int age = singleInstance.get().getAge();
        LogUtils.e(TAG, "onCreate: " + age);

    }
}
