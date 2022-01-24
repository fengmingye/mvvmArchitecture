package com.zhujx.mvvmarchitecture.mvvmCommon.utils;


import com.zhujx.mvvmarchitecture.mvvmCommon.callback.FetchCallback;
import com.zhujx.mvvmarchitecture.mvvmCommon.callback.WrapNetCallback;

import retrofit2.Call;

public class Utils {
    public static <T> WrapNetCallback<T> transferFetchCbToWrapNetCb(FetchCallback<T> originCb) {
        return new WrapNetCallback<T>() {
            @Override
            public void onErrorCode(int code, String msg) {
                if (originCb != null) {
                    originCb.onErrorCode(code, msg);
                }
            }

            @Override
            public void onResult(T data) {
                if (originCb != null) {
                    originCb.onResult(data);
                }
            }

            @Override
            public void onFetchError(Throwable t) {
                if (originCb != null) {
                    originCb.onFetchError(t);
                }
            }

            @Override
            public void onFinish(Call<QiyukfApiBase<T>> call, boolean hasError) {
                super.onFinish(call, hasError);
                if (originCb != null) {
                    originCb.onFinish();
                }
            }
        };
    }
}
