package com.zhujx.mvvmarchitecture.mvvmCommon.vm;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


/**
 * 主要是封装通用事件， 如loading出现/消失
 * @author zhujianxin
 */
public abstract class BasicViewModel extends ViewModel {

    private MutableLiveData<Boolean> showLoading;

    private MutableLiveData<Boolean> netWorkError;

    public BasicViewModel() {
        this.showLoading = new MutableLiveData<>();
        this.netWorkError = new MutableLiveData<>();
    }

    public void setLoadingValue(boolean showLoading) {
        /**
         * 增加边界条件处理,应对多个网络请求设置多次loading的情况
         */
        if (this.showLoading.getValue() == null || showLoading != this.showLoading.getValue()) {
            this.showLoading.setValue(showLoading);
        }
    }

    public MutableLiveData<Boolean> getShowLoading() {
        return showLoading;
    }

    public void showNetworkError(boolean show) {
        if (this.netWorkError.getValue() == null || show != this.netWorkError.getValue()) {
            this.netWorkError.setValue(show);
        }
    }

    public MutableLiveData<Boolean> getNetWorkError() {
        return netWorkError;
    }

    public abstract void fetchData();
}
