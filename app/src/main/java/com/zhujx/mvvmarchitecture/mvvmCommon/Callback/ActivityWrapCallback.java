package com.zhujx.mvvmarchitecture.mvvmCommon.Callback;

import android.app.Activity;

import androidx.annotation.NonNull;


/**
 * Created by zhujx on 2022-01-18
 * Describe: http 请求 Activity 中使用的 Callback
 */
public abstract class ActivityWrapCallback<T> extends ActivityCallback<QiyukfApiBase<T>> {

    private int CODE_SUCCESS = 200;

    public ActivityWrapCallback(Activity activity) {
        super(activity);
    }

    @Override
    public void onResponse(@NonNull QiyukfApiBase<T> response) {
        if (this.judgeResponse(response)) {
            onResult(response.getResult());
        } else {
            onErrorCode(response.getCode(), response.getMessage());
        }
    }

    @Override
    public void onFail(Call<QiyukfApiBase<T>> call, Throwable t) {
        onFetchError(t);
    }

    public abstract void onErrorCode(int code, String msg);

    public abstract void onResult(T data);


    private boolean judgeResponse(QiyukfApiBase<T> response) {
        return response.getCode() == CODE_SUCCESS;
    }

    public abstract void onFetchError(Throwable t);
}
