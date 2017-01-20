package com.oleg.hubal.bankconverter.presentation.jobs;

import android.util.Log;

import com.oleg.hubal.bankconverter.global.utils.LoadUtils;
import com.oleg.hubal.bankconverter.global.utils.ResponseParseManager;
import com.oleg.hubal.bankconverter.model.data.Organization;
import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

import java.util.List;

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
        Response response = LoadUtils.getResponseFromRequest();
        ResponseParseManager responseParseManager = new ResponseParseManager(response);

        List<Organization> organizationList = responseParseManager.getOrganizationList();

        for (Organization organization : organizationList) {
            Log.d(TAG, "onRun: " + organization.getTitle());
        }
    }

    @Override
    protected void onCancel() {

    }

    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        return false;
    }
}
