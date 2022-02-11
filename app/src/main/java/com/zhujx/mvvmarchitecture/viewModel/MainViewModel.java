package com.zhujx.mvvmarchitecture.viewModel;

import android.text.TextUtils;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.lifecycle.MutableLiveData;


import com.zhujx.mvvmarchitecture.model.MainRepository;
import com.zhujx.mvvmarchitecture.mvvmCommon.callback.FetchCallback;
import com.zhujx.mvvmarchitecture.mvvmCommon.utils.ToastUtil;
import com.zhujx.mvvmarchitecture.mvvmCommon.vm.BasicViewModel;

import java.util.List;


public class MainViewModel extends BasicViewModel {
    MainRepository repository;
    Long sessionId;

    private MutableLiveData<Boolean> showfetchErrorView;
    private MutableLiveData<String> description;


    public MainViewModel() {
        super();
        this.showfetchErrorView = new MutableLiveData<>(false);
        this.description = new MutableLiveData<>();
        repository = new MainRepository();
    }


    public MutableLiveData<Boolean> getShowfetchErrorView() {
        return showfetchErrorView;
    }

    public void setDescription(String value) {
        description.setValue(value);
    }


    public MutableLiveData<String> getDescription() {
        return description;
    }


    @Override
    public void fetchData() {
        if (this.repository != null) {
            setLoadingValue(true);
            this.repository.fetchData(sessionId, new FetchCallback<Object>(this) {
                @Override
                public void onResult(Object data) {
                    showfetchErrorView.setValue(false);
                    if (data != null) {
                        setDescription((String) data); //自己实现业务代码，这里表示设置MutableLiveData的值，然后触发页面刷新
                    } else {
                        showfetchErrorView.setValue(true); //该页面的异常布局需要自己处理，因此这里手动设置
                        ToastUtil.showToast("获取会话记录信息失败，请稍后重试");
                    }
                }
            });
        }
    }
}
