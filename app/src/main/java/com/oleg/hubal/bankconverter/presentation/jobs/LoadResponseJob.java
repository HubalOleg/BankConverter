package com.oleg.hubal.bankconverter.presentation.jobs;

import com.oleg.hubal.bankconverter.global.utils.LoadUtils;
import com.oleg.hubal.bankconverter.presentation.events.LoadResponseEvent;
import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

import org.greenrobot.eventbus.EventBus;

import okhttp3.Response;

/**
 * Created by User on 18.01.2017.
 */

public class LoadResponseJob extends Job {

    private static final String GROUP_ID = "load_currency_data";
    private static final String TAG = "LoadResponseJob";

    public LoadResponseJob() {
        super(new Params(Priority.MID).requireNetwork().persist().groupBy(GROUP_ID));
    }

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {
        Response response = LoadUtils.getResponseFromRequest();
        EventBus.getDefault().post(new LoadResponseEvent(response));
    }

    @Override
    protected void onCancel() {

    }

    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        return false;
    }
}
