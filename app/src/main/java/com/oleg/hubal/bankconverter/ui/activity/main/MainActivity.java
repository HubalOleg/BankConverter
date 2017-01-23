package com.oleg.hubal.bankconverter.ui.activity.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;
import com.oleg.hubal.bankconverter.R;
import com.oleg.hubal.bankconverter.global.BankConverterApplication;
import com.oleg.hubal.bankconverter.presentation.presenter.main.MainPresenter;
import com.oleg.hubal.bankconverter.presentation.view.main.MainView;
import com.oleg.hubal.bankconverter.service.LoadCurrencyDataService;
import com.path.android.jobqueue.JobManager;

public class MainActivity extends MvpAppCompatActivity implements MainView {
    public static final String TAG = "MainActivity";

    private JobManager mJobManager;

    @InjectPresenter(type = PresenterType.GLOBAL)
    MainPresenter mMainPresenter;

    public static Intent getIntent(final Context context) {
        Intent intent = new Intent(context, MainActivity.class);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initJobManager();
    }

    private void initJobManager() {
        BankConverterApplication application = (BankConverterApplication) getApplication();
        mJobManager = application.getJobManager();
    }

    @Override
    public void launchLoadBankDataJob() {
        startService(LoadCurrencyDataService.getIntent(getApplicationContext()));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
