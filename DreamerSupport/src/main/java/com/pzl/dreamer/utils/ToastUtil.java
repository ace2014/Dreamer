package com.pzl.dreamer.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * @author zl.peng
 * @version [1.0, 2016-10-28]
 */


public class ToastUtil {
    private static Toast toast = null;

    public static void showSimpleToast(Context context, String msg) {
        if (toast != null)
            toast.cancel();
        toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.show();
    }


}

