package com.goorwl.utils;

/**
 * FileName: SingleInstance
 * Author: Goorwl
 * Create Date: 2019/4/2 14:55
 * Github: https://github.com/Goorwl
 * Blog: https://xiaozhuanlan.com/goorwl
 */

public abstract class SingleInstance<T> {
    private T mType;

    protected abstract T create();

    public final T get() {
        if (mType == null) {
            synchronized (SingleInstance.class) {
                if (mType == null) {
                    mType = create();
                }
            }
        }
        return mType;
    }
}
