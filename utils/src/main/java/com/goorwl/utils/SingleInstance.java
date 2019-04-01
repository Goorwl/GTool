package com.goorwl.utils;

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
