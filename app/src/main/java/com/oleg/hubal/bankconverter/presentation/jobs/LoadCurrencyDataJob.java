package com.oleg.hubal.bankconverter.presentation.jobs;

import android.util.Log;

import com.oleg.hubal.bankconverter.global.utils.LoadUtils;
import com.oleg.hubal.bankconverter.model.Currency;
import com.oleg.hubal.bankconverter.model.Organization;
import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.json.JSONObject;

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
        JSONObject responseJSON = new JSONObject(response.body().string());
        List<Organization> organizationList = LoadUtils.getOrganizationList(responseJSON);
        for (Organization organization : organizationList) {
            organization.save();
        }
        List<Organization> organizationList1 = SQLite.select().from(Organization.class).queryList();
        for (Organization organization : organizationList1) {

            for(Currency currenct : organization.getCurrency()) {
                Log.d(TAG, "onRun: " + organization.getTitle());
                Log.d(TAG, "onRun: " + currenct.getNameAbbreviation());
            }
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
