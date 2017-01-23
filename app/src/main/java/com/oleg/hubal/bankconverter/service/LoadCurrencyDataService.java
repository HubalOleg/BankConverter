package com.oleg.hubal.bankconverter.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.oleg.hubal.bankconverter.R;
import com.oleg.hubal.bankconverter.global.BankConverterApplication;
import com.oleg.hubal.bankconverter.global.utils.CurrencyDatabaseUtils;
import com.oleg.hubal.bankconverter.global.utils.LoadUtils;
import com.oleg.hubal.bankconverter.global.utils.PreferenceManager;
import com.oleg.hubal.bankconverter.global.utils.ResponseParseManager;
import com.oleg.hubal.bankconverter.model.data.City;
import com.oleg.hubal.bankconverter.model.data.CurrencyAbbr;
import com.oleg.hubal.bankconverter.model.data.Date;
import com.oleg.hubal.bankconverter.model.data.Organization;
import com.oleg.hubal.bankconverter.model.data.Region;
import com.oleg.hubal.bankconverter.presentation.events.LoadResponseEvent;
import com.oleg.hubal.bankconverter.presentation.jobs.LoadResponseJob;
import com.path.android.jobqueue.JobManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * Created by User on 18.01.2017.
 */

public class LoadCurrencyDataService extends Service {

    private static final String TAG = "LoadCurrencyDataService";
    private static final int NOTIFY_ID = 0;

    public static boolean isRunning = false;
    private ResponseParseManager mResponseParseManager;
    private Notification.Builder mNotifyBuilder;
    private NotificationManager mNotificationManager;
    private JobManager mJobManager;

    public static Intent getIntent(final Context context) {
        Intent intent = new Intent(context, LoadCurrencyDataService.class);

        return intent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        BankConverterApplication application = (BankConverterApplication) getApplication();
        mJobManager = application.getJobManager();

        createNotification();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(NOTIFY_ID, mNotifyBuilder.build());
        EventBus.getDefault().register(LoadCurrencyDataService.this);
        isRunning = true;

        try {
            loadResponse();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            stopForeground(true);
        }

        return START_STICKY;
    }

    private void createNotification() {
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        mNotifyBuilder = new Notification.Builder(getBaseContext())
                .setSmallIcon(android.R.drawable.ic_input_get)
                .setContentTitle(getString(R.string.notification_load_title))
                .setContentText(getString(R.string.notification_connection))
                .setAutoCancel(false)
                .setOngoing(true);

        mNotificationManager.notify(NOTIFY_ID, mNotifyBuilder.build());
    }

    private void changeNotificationMessage(String message) {
        mNotifyBuilder.setContentText(message);

        mNotificationManager.notify(NOTIFY_ID, mNotifyBuilder.build());
    }

    private void loadResponse() throws IOException, JSONException {
        mJobManager.addJobInBackground(new LoadResponseJob());
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onLoadResponseEvent(LoadResponseEvent loadResponseEvent) {
        try {
            mResponseParseManager = new ResponseParseManager(loadResponseEvent.response);
            checkAndSaveData();
            dataSuccessfulSynchronized();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void checkAndSaveData() throws JSONException {
        Date currentDate = CurrencyDatabaseUtils.queryDate();
        Date responseDate = mResponseParseManager.getDate();

        boolean isUnchangeableDataLoaded = PreferenceManager.isUnchangeableDataLoaded(getApplicationContext());

        if (!isUnchangeableDataLoaded) {
            saveUnchangeableData();
        }

        if (LoadUtils.isDataUpdated(currentDate, responseDate)) {
            saveOrganizationData();
            responseDate.save();
        }
    }


    private void saveUnchangeableData() throws JSONException {
        changeNotificationMessage(getString(R.string.notification_saving_unchangeable));
        List<CurrencyAbbr> currencyAbbrList = mResponseParseManager.getCurrencyAbbrList();
        List<Region> regionList = mResponseParseManager.getRegionList();
        List<City> cityList = mResponseParseManager.getCityList();

        CurrencyDatabaseUtils.saveList(currencyAbbrList);
        CurrencyDatabaseUtils.saveList(regionList);
        CurrencyDatabaseUtils.saveList(cityList);

        PreferenceManager.setUnchangeableDataLoaded(getApplicationContext(), true);
    }

    private void saveOrganizationData() throws JSONException {
        changeNotificationMessage(getString(R.string.notification_saving_organization));
        List<Organization> organizationList = mResponseParseManager.getOrganizationList();

        if (organizationList.size() > 0) {
            CurrencyDatabaseUtils.saveOrganizationList(organizationList);

        }
    }

    private void dataSuccessfulSynchronized() {
        mNotifyBuilder.setContentText("Data successful synchronized")
                .setOngoing(false);
        mNotificationManager.notify(NOTIFY_ID, mNotifyBuilder.build());
        stopSelf();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
        isRunning = false;
        EventBus.getDefault().unregister(LoadCurrencyDataService.this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
