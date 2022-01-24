package com.zhujx.mvvmarchitecture.mvvmCommon.view;

import android.content.Context;

import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;

import com.zhujx.mvvmarchitecture.mvvmCommon.vm.BasicViewModel;


/**
 * 弃用
 * @param <T>
 * @param <M>
 */
public abstract class BasicVMView<T extends ViewDataBinding, M extends BasicViewModel> {
    private Context mcontext;
    private LifecycleOwner lifecycleOwner;
    private T binding;
    private M vm;
    private ProgressDialog mProgress;

    public BasicVMView(Context mcontext, T binding, M viewModel, LifecycleOwner lifecycleOwner) {
        this.mcontext = mcontext;
        this.lifecycleOwner = lifecycleOwner;
        this.binding = binding;
        this.vm = viewModel;
        this.mProgress = new ProgressDialog(mcontext);
        init();
    }

    protected void init() {
        initView();
        initClick();
        initObserve();
    }

    protected abstract void initView();

    protected abstract void initClick();

    protected void initObserve() {
        vm.getShowLoading().observe(lifecycleOwner, aBoolean -> {
            if (aBoolean) {
                showDialog();
            } else {
                dialogDisMiss();
            }
        });
    }

    public Context getMcontext() {
        return mcontext;
    }

    public LifecycleOwner getLifecycleOwner() {
        return lifecycleOwner;
    }

    public T getBinding() {
        return binding;
    }

    public M getVm() {
        return vm;
    }

    /**
     * 子类可以override自定义
     */
    protected void dialogDisMiss() {
        if (mProgress != null && mProgress.isShowing()) {
            mProgress.cancel();
        }
    }

    /**
     * 子类可以override自定义
     */
    protected void showDialog() {
        mProgress.showProgress(true);
        mProgress.setMessage("正在加载");
        mProgress.show();
    }
}
