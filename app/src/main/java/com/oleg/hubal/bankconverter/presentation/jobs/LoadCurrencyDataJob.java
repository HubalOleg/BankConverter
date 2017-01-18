package com.oleg.hubal.bankconverter.presentation.jobs;

import com.oleg.hubal.bankconverter.global.Constants;
import com.oleg.hubal.bankconverter.global.CurrencyJsonUtils;
import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

import okhttp3.Response;

/**
 * Created by User on 18.01.2017.
 */

public class LoadCurrencyDataJob extends Job {

    private static final String GROUP_ID = "load_currency_data";
    private static final String TAG = "LoadCurrencyDataJob";

    public LoadCurrencyDataJob() {
        super(new Params(Priority.MID).requireNetwork().persist().groupBy(GROUP_ID));
    }

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {
        Response response = CurrencyJsonUtils.getResponseWithRequest(Constants.CURRENCY_JSON_URL);
        String responseBody = response.body().string();


    }

    @Override
    protected void onCancel() {

    }

    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        return false;
    }
}
