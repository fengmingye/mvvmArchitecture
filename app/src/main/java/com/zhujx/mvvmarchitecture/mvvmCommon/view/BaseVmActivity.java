package com.zhujx.mvvmarchitecture.mvvmCommon.view;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


import com.zhujx.mvvmarchitecture.mvvmCommon.config.DataBindingConfig;
import com.zhujx.mvvmarchitecture.mvvmCommon.utils.ScreenUtils;
import com.zhujx.mvvmarchitecture.mvvmCommon.vm.BasicViewModel;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 执行顺序 super.OnCreate->getDataBindingConfig->initViewModel->initView->initObserve->initClick
 * getDataBindingConfig传入布局配置
 * initViewModel初始化ViewModel，以及Model，返回ViewModel实例
 * initView页面布局以及相关数据初始化，数据获取
 * inicClick点击事件
 * initObserve监听
 *
 * @author zhujianxin on 2022/1/22
 */
public abstract class BaseVmActivity<M extends BasicViewModel, B extends ViewDataBinding> extends AppCompatActivity {
    ViewModelProvider mApplicationProvider;
    ViewModelProvider mActivityProvider;
    protected B mBinding;
    protected M mViewModel;
    private ProgressDialog mProgress;
    private View netWorkErrorView;
    private String TAG = "BaseVMActivity";


    /**
     * 获取Activity级别的VM
     *
     * @param modelClass
     * @param <T>
     * @return
     */
    protected <T extends ViewModel> T getActivityViewModel(@NonNull Class<T> modelClass) {
        if (mActivityProvider == null) {
            mActivityProvider = new ViewModelProvider(this);
        }
        return mActivityProvider.get(modelClass);
    }

    /**
     * 获取全局共享Application级别的VM
     *
     * @param modelClass
     * @param <T>
     * @return
     */
    protected <T extends ViewModel> T getApplicationScopeViewModel(@NonNull Class<T> modelClass) {
        if (mApplicationProvider == null) {
            mApplicationProvider = new ViewModelProvider(this.getApplicationContext());
        }
        return mApplicationProvider.get(modelClass);
    }


    public B getmBinding() {
        return mBinding;
    }

    protected M initViewModel() {
        if (mViewModel == null) {
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
            } else {
                //如果沒有指定泛型參數，則默認使用BaseViewModel
                modelClass = BasicViewModel.class;
            }
            return (M) getActivityViewModel(modelClass);
        }
        return mViewModel;
    }

    protected abstract int getLayoutId();

    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(getLayoutId());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DataBindingConfig dataBindingConfig = this.getDataBindingConfig();
//        mBinding = DataBindingUtil.setContentView(this,  dataBindingConfig.getLayout()); //奇怪，使用这个setContentView方法会闪退，报错：“View must have a tag error in android data binding”，但是其他版本的databinding是OK的

        mBinding = DataBindingUtil.inflate(getLayoutInflater(), dataBindingConfig.getLayout(), null, false);
        setContentView(mBinding.getRoot());
        mBinding.setLifecycleOwner(this);

        initBaseView();

        mViewModel = this.initViewModel();

        this.initView();

        this.initObserve();

        this.initClick();

        if (mViewModel != null) {
            // 页面获取数据 - 渲染页面 的入口
            mViewModel.fetchData();
        }
    }

    protected abstract void initView();

    protected abstract void initClick();

    private void initBaseView() {
        this.mProgress = new ProgressDialog(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mBinding.unbind();
        this.mBinding = null;
    }

    /**
     * 子类可以override自定义
     */
    protected void loadingDisMiss() {
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

    /**
     * 生成无网络布局，子类可以overRide
     */
    protected View generateNetworkErrorView() {
        View networkErrorView = View.inflate(this, R.layout.frag_no_network, null);
        networkErrorView.findViewById(R.id.iv_title_bar_back_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        return networkErrorView;
    }

    /**
     * 无网络布局展示
     */
    private void showNetworkErrorView() {
        if (netWorkErrorView == null) {
            netWorkErrorView = generateNetworkErrorView();
        }
        if (netWorkErrorView != null && netWorkErrorView.getParent() == null) {
            netWorkErrorView.setFitsSystemWindows(true);
            ViewGroup contentParent = getWindow().getDecorView().findViewById(android.R.id.content);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.gravity = Gravity.CENTER;
            params.topMargin = ScreenUtils.getStatusBarHeight(); // 解决无网络页面被statusBar遮挡的问题
            netWorkErrorView.setLayoutParams(params);
            contentParent.addView(netWorkErrorView);
        }

    }

    /**
     * 无网络布局消失
     */
    private void networkErrorViewDismiss() {
        try {
            if (netWorkErrorView != null && netWorkErrorView.getParent() != null) {
                ((ViewGroup) netWorkErrorView.getParent()).removeView(netWorkErrorView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void initObserve() {
        if (mViewModel != null) {
            mViewModel.getShowLoading().observe(this, aBoolean -> {
                if (aBoolean) {
                    showDialog();
                } else {
                    loadingDisMiss();
                }
            });
            mViewModel.getNetWorkError().observe(this, show -> {
                if (show) {
                    showNetworkErrorView();
                } else {
                    networkErrorViewDismiss();
                }
            });
        }
    }

    public M getViewModel() {
        return (M) mViewModel;
    }
}
