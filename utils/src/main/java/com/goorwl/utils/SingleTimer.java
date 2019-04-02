package com.goorwl.utils;

import android.os.CountDownTimer;
import android.util.Log;

/**
 * FileName: SingleTimer
 * Author: Goorwl
 * Create Date: 2019/4/2 14:55
 * Github: https://github.com/Goorwl
 * Blog: https://xiaozhuanlan.com/goorwl
 */

public class SingleTimer {
    private static final String         TAG      = "SingleTimer";
    private static       SingleTimer    instance = null;
    private static       int            lastTime = 0;
    private              CountDownTimer mCountDownTimer;

    private SingleTimer() {
    }

    public static SingleTimer getInstance() {
        //避免不必要的同步
        if (instance == null) {
            synchronized (SingleTimer.class) {
                //在null的情况下创建实例
                if (instance == null) {
                    instance = new SingleTimer();
                }
            }
        }
        return instance;
    }

    public void startTime(int time) {
        if (lastTime == 0) {
            mCountDownTimer = new CountDownTimer(time * 1000, 1000) {
                public void onTick(long millisUntilFinished) {
                    lastTime = (int) (millisUntilFinished / 1000);
                    Log.i(TAG, "seconds remaining: " + millisUntilFinished / 1000);
                }

                public void onFinish() {
                }
            };
            mCountDownTimer.start();
        }
    }

    public void stopTime() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
            lastTime = 0;
        }
    }

    public int getTime() {
        return lastTime;
    }

    public void setTime(int time) {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
        mCountDownTimer = new CountDownTimer(time * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                lastTime = (int) (millisUntilFinished / 1000);
                Log.i(TAG, "seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
            }
        };
        mCountDownTimer.start();
        lastTime = time;
    }
}
