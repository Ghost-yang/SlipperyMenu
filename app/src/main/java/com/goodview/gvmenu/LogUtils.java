package com.goodview.gvmenu;

import android.util.Log;

/**
 * Created by goodview on 2019/6/25.
 */

public class LogUtils {
    public static boolean isDebug = true;

    public static final String mTag = "Channel";

    public static void D(String paramString) { Log.d(mTag, paramString); }

    public static void E(String paramString) { Log.e(mTag, paramString); }
}
