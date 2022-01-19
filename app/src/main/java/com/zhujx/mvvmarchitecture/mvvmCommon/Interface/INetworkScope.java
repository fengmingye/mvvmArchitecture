package com.zhujx.mvvmarchitecture.mvvmCommon.Interface;

import android.content.Context;

/**
 * Created by zhujx on 2022/1/18.
 * 灰屏旋转, 白屏等loading样式
 */
public interface INetworkScope {

    //网络请求开始
    void onBegin(final Context activity);

    //网络请求结束
    //viewTag主要用于标识二级投资列表下拉刷新控件
    void onEnd(final Context activity, boolean succes);

}
