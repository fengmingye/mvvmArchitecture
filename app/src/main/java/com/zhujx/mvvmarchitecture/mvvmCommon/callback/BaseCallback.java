package com.zhujx.mvvmarchitecture.mvvmCommon.callback;

import android.app.Activity;
import android.app.Fragment;
import android.os.Build;


import com.zhujx.mvvmarchitecture.R;
import com.zhujx.mvvmarchitecture.mvvmCommon.utils.ToastUtil;
import com.zhujx.mvvmarchitecture.mvvmCommon.utils.Utils;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by andya on 2019-06-12
 * Describe:
 */
public class BaseCallback<T> implements Callback<T> {

    private static final String BASECALLBACKTAG = "BaseCallback";
    public final String AUTHORITY_FAILURE_MSG = "authorityFailure";
    public static final String AUTHORITY_FAILURE_CODE = "99000099";

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
//        checkAuthorityFailure(response);
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {

    }


    protected void showJsonParseErrorMsg() {
        ToastUtil.showToast(R.string.error_jsonparse);
    }

    protected void showServerErrorMsg() {
        if (!Utils.isNetAvailable()) {
            ToastUtil.showToast(R.string.error_no_network);
            return;
        }
        ToastUtil.showToast(R.string.error_server);
    }

    public void showNetWorkError() {
        if (!Utils.isNetAvailable()) {
            ToastUtil.showToast(R.string.error_no_network);
            return;
        }
        ToastUtil.showToast(R.string.error_server);
    }

    public boolean canContinue(Activity activity) {
        if (activity == null) {
            return false;
        }

        if (activity.isFinishing()) {
            return false;
        }

        if (Build.VERSION_CODES.JELLY_BEAN_MR1 <= Build.VERSION.SDK_INT
                && activity.isDestroyed()) {

            return false;
        }
        return true;
    }

    public boolean canContinue(Fragment fragment) {
        if (fragment == null) {
            return false;
        }

        if (fragment.isDetached() || fragment.isRemoving()) {
            return false;
        }
        Activity activity = fragment.getActivity();
        if (activity == null || activity.isFinishing()) {
            return false;
        }
        if (Build.VERSION_CODES.JELLY_BEAN_MR1 <= Build.VERSION.SDK_INT
                && activity.isDestroyed()) {
            return false;
        }
        return true;
    }

}
