package com.zhujx.mvvmarchitecture.mvvmCommon.utils;

import android.content.Context;
import android.content.res.Resources;

public class ScreenUtils {
    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }
}
