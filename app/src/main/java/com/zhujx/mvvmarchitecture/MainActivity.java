package com.zhujx.mvvmarchitecture;

import android.app.Activity;
import android.view.View;

import androidx.lifecycle.Observer;

import com.zhujx.mvvmarchitecture.databinding.ActivityMainBinding;
import com.zhujx.mvvmarchitecture.mvvmCommon.view.BaseVmActivity;
import com.zhujx.mvvmarchitecture.viewModel.MainViewModel;


/**
 * 执行顺序 super.OnCreate->super.getDataBindingConfig->getLayoutId->super.initViewModel->initView->initObserve->initClick->onCreate。使用者需要implement如下方法：
 * getLayoutId传入布局配置id
 * initView页面布局以及相关数据初始化
 * inicClick点击事件
 * initObserve监听
 * 当想要自定义loading时，可以overRide showDialog()与loadingDismiss()
 *
 * @author zhujianxin
 */
public class MainActivity extends BaseVmActivity<MainViewModel, ActivityMainBinding> {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        //布局里面的变量名为ViewModel，将bindging与ViewModel绑定，这步需要开发者自己调用，ViewModel可以内有一个方法fetchData，作为页面获取数据-渲染页面的执行入口，在BaseVmActivity中被调用
        getmBinding().setViewModel(mViewModel);
    }

    @Override
    protected void initClick() {
        getmBinding().setProxyClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {

                }
            }
        });
    }


    @Override
    protected void initObserve() {
        super.initObserve();
        mViewModel.getShowfetchErrorView().observe(this, new Observer<Boolean>() { // 做监听，父类有自定义实现，主要是处理的无网络的默认展示, 网络请求的loading消失
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                }
            }
        });
    }
}