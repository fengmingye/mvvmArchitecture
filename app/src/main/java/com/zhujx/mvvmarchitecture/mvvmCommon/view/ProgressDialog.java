package com.zhujx.mvvmarchitecture.mvvmCommon.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zhujx.mvvmarchitecture.R;


/**
 */
public class ProgressDialog extends Dialog {
    private ProgressBar pb;
    private TextView tvMessage;

    private Handler handler;

    public ProgressDialog(Context context) {
        super(context, R.style.dialog_default_style);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.default_progress_dialog, null);
        setContentView(view);
        setCancelable(false);
    }

    public void showProgress(boolean show) {
        pb.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public void setMessage(String message) {
        tvMessage.setText(message);
    }

    public void show(long time) {
        if (!isShowing()) {
            show();
        }
        getHandler().postDelayed(() -> {
            if (isShowing()) {
                cancel();
            }
        }, time);
    }

    private Handler getHandler() {
        if (handler == null) {
            handler = new Handler(Looper.getMainLooper());
        }
        return handler;
    }
}
