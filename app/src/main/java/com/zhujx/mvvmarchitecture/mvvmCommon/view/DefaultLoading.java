package com.zhujx.mvvmarchitecture.mvvmCommon.view;

import android.app.Activity;
import android.content.Context;

import com.qiyukf.desk.base.mvvmCommon.Interface.INetworkScope;

/**
 * @author zhujianxin
 */
public class DefaultLoading implements INetworkScope {
    private String indicator = null;

    private ProgressDialog mProgress = null;

    public DefaultLoading(String indicator) {
        this.indicator = indicator;
    }

    public DefaultLoading() {
    }

    @Override
    public void onBegin(Context context) {
        if (mProgress == null && context != null && !((Activity) context).isFinishing()) {
            try {
                mProgress = new ProgressDialog(context);
                if (indicator != null) {
                    mProgress.setMessage(indicator);
                }
                mProgress.show();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onEnd(Context activity, boolean succes) {
        if (mProgress != null && mProgress.isShowing() && activity != null) {
            mProgress.cancel();
        }
    }
}
