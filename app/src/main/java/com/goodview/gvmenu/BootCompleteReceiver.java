package com.goodview.gvmenu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by goodview on 2019/6/25.
 */

public class BootCompleteReceiver extends BroadcastReceiver {

    public static final String BOOT_COMPLETE = "android.intent.action.BOOT_COMPLETED";
    public static final String GV_TEST_ACTION = "com.goodview.menu.ACTION_TEST";
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        LogUtils.D("action-----"+action);

        if(BOOT_COMPLETE.equals(action) || GV_TEST_ACTION.equals(action)) {
            Intent menuintent = new Intent(context,MenuService.class);
            context.startService(menuintent);
        }

    }
}
