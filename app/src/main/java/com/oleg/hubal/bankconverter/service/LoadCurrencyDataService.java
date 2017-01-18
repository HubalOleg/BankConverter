package com.oleg.hubal.bankconverter.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

/**
 * Created by User on 18.01.2017.
 */

public class LoadCurrencyDataService extends IntentService {

    private static final String TAG = "LoadCurrencyDataService";
    private static final String SERVICE_NAME = "load_bank_data_service";

    public static Intent getIntent(final Context context) {
        Intent intent = new Intent(context, LoadCurrencyDataService.class);

        return intent;
    }

    public LoadCurrencyDataService() {
        super(SERVICE_NAME);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
