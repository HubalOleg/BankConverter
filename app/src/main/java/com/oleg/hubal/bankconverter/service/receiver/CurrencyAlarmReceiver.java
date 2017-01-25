package com.oleg.hubal.bankconverter.service.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.oleg.hubal.bankconverter.global.utils.LoadUtils;
import com.oleg.hubal.bankconverter.service.LoadCurrencyDataService;

/**
 * Created by User on 25.01.2017.
 */

public class CurrencyAlarmReceiver extends BroadcastReceiver {
    public static final int REQUEST_CODE = 9;
    public static final String ACTION = "com.oleg.hubal.bankconverter.alarm";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!LoadCurrencyDataService.isRunning && LoadUtils.isOnline(context)) {
            context.startService(LoadCurrencyDataService.getIntent(context));
        }
    }
}
