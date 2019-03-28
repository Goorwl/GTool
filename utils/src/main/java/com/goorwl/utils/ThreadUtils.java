package com.goorwl.utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadUtils {

    //获取主线程的handler对象
    private static Handler         mHandler         = new Handler(Looper.getMainLooper());
    //创建一个线程池，里面维护一个线程
    private static ExecutorService sExecutorService = Executors.newSingleThreadExecutor();

    //运行在主线程
    public static void runOnUiThread(Runnable r) {
        //如果本身就是主线程，直接运行任务中的run方法
        if (Looper.myLooper() == Looper.getMainLooper()) {
            r.run();
        } else {
            //通过handler给主线程发送消息
            mHandler.post(r);
        }
    }

    //运行在子线程
    public static void runOnSubThread(final Runnable r) {
        //        new Thread(r.run()).start();
        //提交任务
        sExecutorService.submit(r);
    }
}
