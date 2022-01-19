package com.zhujx.mvvmarchitecture.mvvmCommon.VM;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


/**
 * 主要是封装通用事件， 如loading出现/消失
 */
public class BasicViewModel extends ViewModel {

    private MutableLiveData<Boolean> showLoading;

    private MutableLiveData<Boolean> netWorkError;

    public BasicViewModel() {
        this.showLoading = new MutableLiveData<>();
        this.netWorkError = new MutableLiveData<>();
    }

    public void setLoadingValue(boolean showLoading) {
        this.showLoading.setValue(showLoading);
    }

    public MutableLiveData<Boolean> getShowLoading() {
        return showLoading;
    }

    public void showNetworkError(boolean show) {
        this.netWorkError.setValue(show);
    }

    public MutableLiveData<Boolean> getNetWorkError() {
        return netWorkError;
    }
}
