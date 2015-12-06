package com.way.tunnelvision.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by pc on 2015/12/6.
 */
public class ToastUtil {
    public static void show(Context context, String info) {
        Toast.makeText(context, info, Toast.LENGTH_LONG).show();
    }

    public static void show(Context context, int info) {
        Toast.makeText(context, info, Toast.LENGTH_LONG).show();
    }

}
