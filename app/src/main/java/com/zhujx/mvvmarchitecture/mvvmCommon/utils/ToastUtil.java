package com.zhujx.mvvmarchitecture.mvvmCommon.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.zhujx.mvvmarchitecture.MvvmApplication;

public class ToastUtil {
    /**
     * handler to show toasts safely
     */
    private static Handler handler = null;
    private static Context appContext;
    private static Toast toast = null;

    public static void init() {
        appContext = MvvmApplication.getInstance();
    }

    public static void showToast(int resId) {
        final int finalId = resId;
        getHandler().post(new Runnable() {

            @Override
            public void run() {
                if (toast != null) {
                    toast.setText(finalId);
                    toast.setDuration(Toast.LENGTH_SHORT);
                } else {
                    toast = Toast.makeText(appContext, finalId, Toast.LENGTH_SHORT);
                }

                toast.show();
            }
        });
    }

    public static void showToast(String text) {
        final String msg = text;
        getHandler().post(new Runnable() {

            @Override
            public void run() {
                if (toast != null) {
                    toast.setText(msg);
                    toast.setDuration(Toast.LENGTH_SHORT);
                } else {
                    toast = Toast.makeText(appContext, msg, Toast.LENGTH_SHORT);
                }
                toast.show();
            }
        });
    }

    private static Handler getHandler() {
        if (handler == null) {
            handler = new Handler(Looper.getMainLooper());
        }
        return handler;
    }
}
