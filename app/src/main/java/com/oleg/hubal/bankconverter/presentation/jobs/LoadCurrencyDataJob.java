package com.oleg.hubal.bankconverter.presentation.jobs;

import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

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

    }

    @Override
    protected void onCancel() {

    }

    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        return false;
    }
}
