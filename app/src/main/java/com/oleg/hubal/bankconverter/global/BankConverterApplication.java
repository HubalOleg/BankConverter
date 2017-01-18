package com.oleg.hubal.bankconverter.global;

import android.app.Application;
import android.util.Log;

import com.path.android.jobqueue.JobManager;
import com.path.android.jobqueue.config.Configuration;
import com.path.android.jobqueue.log.CustomLogger;

/**
 * Created by User on 18.01.2017.
 */

public class BankConverterApplication extends Application {

    private JobManager mJobManager;

    @Override
    public void onCreate() {
        super.onCreate();

        configureJobManager();
    }

    private void configureJobManager() {
        Configuration configuration = new Configuration.Builder(BankConverterApplication.this)
                .customLogger(new CustomLogger() {
                    private static final String TAG = "JOBS";

                    @Override
                    public boolean isDebugEnabled() {
                        return true;
                    }

                    @Override
                    public void d(String text, Object... args) {
                        Log.d(TAG, String.format(text, args));
                    }

                    @Override
                    public void e(Throwable t, String text, Object... args) {
                        Log.e(TAG, String.format(text, args), t);
                    }

                    @Override
                    public void e(String text, Object... args) {
                        Log.e(TAG, String.format(text, args));
                    }
                })
                .minConsumerCount(1)
                .maxConsumerCount(20)
                .loadFactor(3)
                .consumerKeepAlive(120)
                .build();
        mJobManager = new JobManager(BankConverterApplication.this, configuration);
    }

    public JobManager getJobManager() {
        return mJobManager;
    }
}
