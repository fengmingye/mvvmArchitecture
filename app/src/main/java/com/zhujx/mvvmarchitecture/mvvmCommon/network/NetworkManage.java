package com.qiyukf.desk.base.mvvmCommon.network;
import android.content.Context;

import com.zhujx.mvvmarchitecture.mvvmCommon.Interface.INetworkScope;
import com.zhujx.mvvmarchitecture.mvvmCommon.callback.WrapNetCallback;
import com.zhujx.mvvmarchitecture.mvvmCommon.view.DefaultLoading;


/**
 * 代码留着，先弃用
 * @param <T>
 */
public class NetworkManage<T> {
    private Context mcontext;
    private boolean showLoading = false;
    private QiyukfApi apiInstance;
    private Class<? extends INetworkScope> scopeConfig;
    private WrapNetCallback<T> callback;
    private INetworkScope networkLoadingScope;

    public NetworkManage(Context mcontext) {
        this.mcontext = mcontext;
        this.scopeConfig = DefaultLoading.class;
    }

    public QiyukfApi getApiInstance() {
        if (apiInstance == null) {
            synchronized (NetworkManage.class) {
                if (apiInstance == null) {
                    apiInstance = RPCApiFactory.create(QiyukfApi.class);
                }
            }
        }
        return apiInstance;
    }

    /**
     * 设置是否显示loading
     *
     * @param loading
     * @return
     */
    public NetworkManage setShowLoading(boolean loading) {
        this.showLoading = loading;
        return this;
    }

    /**
     * 设置loading的样式
     *
     * @param scope
     * @return
     */
    public NetworkManage setLoadingScope(Class<? extends INetworkScope> scope) { //设置自定义loading
        if (scope != null) {
            this.scopeConfig = scope;
        }
        return this;
    }

    public WrapNetCallback<T> wrapAndSetCallBack(IFetchDataCb mfetchDataCb) {
//        callback = new WrapNetCallback<T>((Activity) mcontext) {
//            @Override
//            public void onErrorCode(int code, String msg) {
//                mfetchDataCb.onErrorCode(code, msg);
//            }
//
//            @Override
//            public void onResult(T data) {
//                mfetchDataCb.onResult(data);
//            }
//
//            @Override
//            public void onFetchError(Throwable t) {
//                mfetchDataCb.onFetchError(t);
//            }
//
//            @Override
//            public void onFinish(Call<QiyukfApiBase<T>> call, boolean hasError) {
//                super.onFinish(call, hasError);
//                if (networkLoadingScope != null) {
//                    networkLoadingScope.onEnd(mcontext, !hasError);
//                }
//            }
//        };
        return callback;
    }

    public QiyukfApi build() {
        if (showLoading && this.scopeConfig != null) {
            try {
                networkLoadingScope = this.scopeConfig.newInstance();
                if (networkLoadingScope != null) {
                    networkLoadingScope.onBegin(mcontext);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
        return getApiInstance();
    }
}
