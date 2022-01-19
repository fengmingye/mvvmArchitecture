package com.zhujx.mvvmarchitecture.mvvmCommon.View;

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

import com.zhujx.mvvmarchitecture.MVVMApplication;
import com.zhujx.mvvmarchitecture.R;
import com.zhujx.mvvmarchitecture.mvvmCommon.VM.BasicViewModel;
import com.zhujx.mvvmarchitecture.mvvmCommon.config.DataBindingConfig;
import com.zhujx.mvvmarchitecture.mvvmCommon.utils.ScreenUtils;


/**
 * * created by zhujx on 2022/1/22
 */

/**
 * 执行顺序 super.OnCreate->getDataBindingConfig->initViewModel->initView->initObserve->initClick
 * getDataBindingConfig传入布局配置
 * initViewModel初始化ViewModel，以及Model，返回ViewModel实例
 * initView页面布局以及相关数据初始化，数据获取
 * inicClick点击事件
 * initObserve监听
 */
public abstract class BaseVMActivity<M extends BasicViewModel> extends AppCompatActivity {
    ViewModelProvider mApplicationProvider;
    ViewModelProvider mActivityProvider;
    private ViewDataBinding mBinding;
    private M mviewModel;
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
            mApplicationProvider = new ViewModelProvider((MVVMApplication) this.getApplicationContext());
        }
        return mApplicationProvider.get(modelClass);
    }


    public ViewDataBinding getmBinding() {
        return mBinding;
    }

    protected abstract M initViewModel();

    protected abstract DataBindingConfig getDataBindingConfig();

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DataBindingConfig dataBindingConfig = this.getDataBindingConfig();
//        mBinding = DataBindingUtil.setContentView(this,  dataBindingConfig.getLayout()); //奇怪，使用这个setContentView方法会闪退，报错：“View must have a tag error in android data binding”，但是其他版本的databinding是OK的

        mBinding = DataBindingUtil.inflate(getLayoutInflater(), dataBindingConfig.getLayout(), null, false);
        setContentView(mBinding.getRoot());
        mBinding.setLifecycleOwner(this);

        initBaseView();

        mviewModel = this.initViewModel();

        this.initView();

        this.initObserve();

        this.initClick();

    }


    protected abstract void initView();

    protected abstract void initClick();

    private void initBaseView() {
        this.mProgress = new ProgressDialog(this);
    }

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
            params.topMargin = ScreenUtils.getStatusBarHeight(this); // 解决无网络页面被statusBar遮挡的问题
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
//            Log.i(TAG, "networkErrorViewDismiss error");
            e.printStackTrace();
        }
    }

    protected void initObserve() {
        if (mviewModel != null) {
            mviewModel.getShowLoading().observe(this, aBoolean -> {
                if (aBoolean) {
                    showDialog();
                } else {
                    loadingDisMiss();
                }
            });
            mviewModel.getNetWorkError().observe(this, show -> {
                if (show) {
                    showNetworkErrorView();
                } else {
                    networkErrorViewDismiss();
                }
            });
        }
    }

}
