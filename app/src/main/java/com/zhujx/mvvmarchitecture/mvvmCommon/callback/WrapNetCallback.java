package com.zhujx.mvvmarchitecture.mvvmCommon.callback;


import org.json.JSONException;


/**
 * Created by zhujx on 2022-01-18
 * Describe: http 请求 Activity 中使用的 Callback
 */
public abstract class WrapNetCallback<T> extends BaseCallback<QiyukfApiBase<T>> {

    private final int codeSuccess = 200;

    private static final String ACTIVITYCALLBACKTAG = "WrapNetCallback";

    @Override
    public void onResponse(Call<QiyukfApiBase<T>> call, Response<QiyukfApiBase<T>> response) {
        super.onResponse(call, response);
        NimLog.d("ActivityCallback onResponse, call => {}" + call, "response => {}" + response);
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
    public void onFailure(Call<QiyukfApiBase<T>> call, Throwable t) {
        NimLog.w("WrapNetCallback onFailure", t);
        if (call.isCanceled()) {
            NimLog.i(ACTIVITYCALLBACKTAG + "call isCanceled, stop do more,url={}", call.request().url().toString());
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
                NimLog.d("showJsonParseErrorMsg=>{}", call.request().url().toString());
                showJsonParseErrorMsg();
                return;
            }
        }
        NimLog.d("showNetworkErrorMsg=>{}", call.request().url().toString());
        showNetWorkError();
    }

    public abstract void onErrorCode(int code, String msg);

    public abstract void onResult(T data);


    private boolean judgeResponse(QiyukfApiBase<T> response) {
        return response.getCode() == codeSuccess;
    }

    public abstract void onFetchError(Throwable t);

    public void onFinish(Call<QiyukfApiBase<T>> call, boolean hasError) {

    }
}
