package com.zhujx.mvvmarchitecture.mvvmCommon.utils;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.zhujx.mvvmarchitecture.MvvmApplication;
import com.zhujx.mvvmarchitecture.mvvmCommon.callback.FetchCallback;
import com.zhujx.mvvmarchitecture.mvvmCommon.callback.WrapNetCallback;
import com.zhujx.mvvmarchitecture.mvvmCommon.network.ApiBase;

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
            public void onFinish(Call<ApiBase<T>> call, boolean hasError) {
                super.onFinish(call, hasError);
                if (originCb != null) {
                    originCb.onFinish();
                }
            }
        };
    }

    public static boolean isNetAvailable() {
        return isNetworkConnected(MvvmApplication.getInstance());
    }

    /**
     * 此判断不可靠
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        NetworkInfo networkInfo = getActiveNetworkInfo(context);
        if (networkInfo != null) {
            boolean a = networkInfo.isConnected();
            return a;
        } else {
            return false;
        }
    }

    /**
     * 获取可用的网络信息
     *
     * @param context
     * @return
     */
    private static NetworkInfo getActiveNetworkInfo(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            return cm.getActiveNetworkInfo();
        } catch (Exception e) {
            return null;
        }
    }
}
