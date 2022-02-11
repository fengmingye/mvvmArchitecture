package com.zhujx.mvvmarchitecture.model;

import com.zhujx.mvvmarchitecture.mvvmCommon.callback.FetchCallback;

/**
 * 获取数据
 * mvvm中的model层
 */
public class MainRepository {

    public void fetchData(long sessionId, FetchCallback<Object> fetchDataCb) {
//        RPCApiFactory.create(QiyukfApi.class).selectSessionRecordDetailApi(sessionId, DeskPreferences.getToken()).enqueue(Utils.transferFetchCbToWrapNetCb(fetchDataCb));
//        获取数据并且返回
    }
}
