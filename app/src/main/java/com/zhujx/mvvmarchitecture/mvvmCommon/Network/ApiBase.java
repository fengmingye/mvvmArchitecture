package com.zhujx.mvvmarchitecture.mvvmCommon.Network;


import java.io.Serializable;

/**
 * Created by zhanyage on 2019-06-13
 * Describe: qiyukfapi base Json 包
 */
public class ApiBase<T> implements Serializable {

    private int code;
    private T result;
    private String message;

    public int getCode() {
        return code;
    }

    public T getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

}
