package com.zhujx.mvvmarchitecture.mvvmCommon.callback;


import com.zhujx.mvvmarchitecture.mvvmCommon.network.ApiBase;

import org.json.JSONException;

import retrofit2.Call;
import retrofit2.Response;


/**
 * Created by zhujx on 2022-01-18
 * Describe: http 请求 Activity 中使用的 Callback
 */
public abstract class WrapNetCallback<T> extends BaseCallback<ApiBase<T>> {

    private final int codeSuccess = 200;

    private static final String ACTIVITYCALLBACKTAG = "WrapNetCallback";

    @Override
    public void onResponse(Call<ApiBase<T>> call, Response<ApiBase<T>> response) {
        super.onResponse(call, response);
        if (call.isCanceled()) {
            return;
        }
        if (response == null || !response.isSuccessful()) {
            showServerErrorMsg();
            onFinish(call, true);
            return;
        }
        if (response.body() == null) {
            showJsonParseErrorMsg();
            onFinish(call, true);
        }

        try {
            if (this.judgeResponse(response.body())) {
                onResult(response.body().getResult());
            } else {
                onErrorCode(response.body().getCode(), response.body().getMessage());
            }
        } finally {
            onFinish(call, false);
        }
    }

    @Override
    public void onFailure(Call<ApiBase<T>> call, Throwable t) {
        if (call.isCanceled()) {
            onFinish(call, false);
            return;
        }
        try {
            onFetchError(t);
        } finally {
            onFinish(call, false);
        }
        if (t != null) {
            if (t.getCause() instanceof JSONException) {
                showJsonParseErrorMsg();
                return;
            }
        }
        showNetWorkError();
    }

    public abstract void onErrorCode(int code, String msg);

    public abstract void onResult(T data);


    private boolean judgeResponse(ApiBase<T> response) {
        return response.getCode() == codeSuccess;
    }

    public abstract void onFetchError(Throwable t);

    public void onFinish(Call<ApiBase<T>> call, boolean hasError) {

    }
}
