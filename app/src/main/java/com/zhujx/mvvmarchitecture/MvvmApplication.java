package com.zhujx.mvvmarchitecture;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

import com.zhujx.mvvmarchitecture.mvvmCommon.utils.ToastUtil;


/**
 *
 * @author zhujx
 * @date 2022/2/11
 */
public class MvvmApplication extends Application implements ViewModelStoreOwner {
    private ViewModelStore mAppViewModelStore;
    private static MvvmApplication instance;

    public static MvvmApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mAppViewModelStore = new ViewModelStore();
        ToastUtil.init();
    }

    @NonNull
    @Override
    public ViewModelStore getViewModelStore() {
        return mAppViewModelStore;
    }

}
