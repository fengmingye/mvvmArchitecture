package com.zhujx.mvvmarchitecture.mvvmCommon.callback;

import com.qiyukf.common.utils.system.NetworkUtil;
import com.qiyukf.common.utils.system.ToastUtil;
import com.qiyukf.desk.base.mvvmCommon.Interface.IFetchDataCb;
import com.qiyukf.desk.base.mvvmCommon.vm.BasicViewModel;
import com.zhujx.mvvmarchitecture.mvvmCommon.vm.BasicViewModel;

/**
 * Created by zhujx on 2022-1-18
 * Describe: viewmodel调用model方法的回调接口默认实现
 */
public abstract class FetchCallback<T> implements IFetchDataCb<T> {

    private BasicViewModel vm;


    public FetchCallback(BasicViewModel vm) {
        this.vm = vm;
    }

    @Override
    public void onErrorCode(int code, String msg) { // code不等于200，网络请求成功但是业务不对如接口报错，默认toast处理，可以overRide
        ToastUtil.showToast(msg);
    }

    public abstract void onResult(T data);


    @Override
    public void onFetchError(Throwable t) { // 请求异常，无网络状态下，展示通用的无网络页面布局
        if (!NetworkUtil.isNetAvailable() && this.vm != null) {
            this.vm.getNetWorkError().setValue(true);
        }
    }

    @Override
    public void onFinish() { // 统一处理loading消失
        if (vm.getShowLoading().getValue() == true) {
            vm.getShowLoading().setValue(false);
        }
    }
}
