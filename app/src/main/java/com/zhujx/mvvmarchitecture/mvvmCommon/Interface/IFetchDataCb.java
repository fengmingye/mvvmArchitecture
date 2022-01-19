package com.zhujx.mvvmarchitecture.mvvmCommon.Interface;

public interface IFetchDataCb<T> {
    void onResult(T data);//code=200，接口请求正常

    void onErrorCode(int code, String msg); //网络请求成功，但是code!=200，即接口业务异常

    void onFetchError(Throwable t); // 网络请求异常
}