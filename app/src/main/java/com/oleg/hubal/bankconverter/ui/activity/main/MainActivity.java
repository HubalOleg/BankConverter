package com.oleg.hubal.bankconverter.ui.activity.main;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;
import com.oleg.hubal.bankconverter.R;
import com.oleg.hubal.bankconverter.global.constants.Constants;
import com.oleg.hubal.bankconverter.global.listener.OrganizationTransitionListener;
import com.oleg.hubal.bankconverter.global.utils.LoadUtils;
import com.oleg.hubal.bankconverter.presentation.presenter.main.MainPresenter;
import com.oleg.hubal.bankconverter.presentation.view.main.MainView;
import com.oleg.hubal.bankconverter.service.LoadCurrencyDataService;
import com.oleg.hubal.bankconverter.service.receiver.CurrencyAlarmReceiver;
import com.oleg.hubal.bankconverter.ui.activity.detail_portrait.DetailActivity;
import com.oleg.hubal.bankconverter.ui.fragment.detail.DetailFragment;
import com.oleg.hubal.bankconverter.ui.fragment.organization_list.OrganizationListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.oleg.hubal.bankconverter.R.id.fl_container_land;

public class MainActivity extends MvpAppCompatActivity implements MainView, OrganizationTransitionListener {
    public static final String TAG = "MainActivity";

    @BindView(R.id.pb_load_progress)
    ProgressBar mLoadProgressBar;

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
        cancelAlarm();
        ButterKnife.bind(MainActivity.this);
    }

    @Override
    public void launchLoadCurrencyService() {
        startService(LoadCurrencyDataService.getIntent(getApplicationContext()));
    }

    @Override
    public void showProgressBar() {
        mLoadProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissProgressBar() {
        mLoadProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showOrganizationList() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_container, OrganizationListFragment.newInstance())
                .commit();
    }

    @Override
    public void checkInternetConnection() {
        if (LoadUtils.isOnline(MainActivity.this)) {
            mMainPresenter.onDeviceOnline();
        } else {
            mMainPresenter.onDeviceOffline();
        }
    }

    @Override
    public void onRefreshData() {
        if (LoadUtils.isOnline(MainActivity.this)) {
            mMainPresenter.onDeviceOnline();
        }
    }

    @Override
    public void showMapTransition(String location) {
        Intent searchAddress = new  Intent(Intent.ACTION_VIEW,Uri.parse(Constants.GEO_PREFIX + location));
        startActivity(searchAddress);
    }

    @Override
    public void showSiteTransition(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    @Override
    public void showCallTransition(String number) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(Constants.TEL_PREFIX + number));
        startActivity(callIntent);
    }

    @Override
    public void showDetailTransition(String organizationId) {
        mMainPresenter.onShowDetail(organizationId);
    }

    @Override
    public void showDetailFragment(String organizationId) {
        if (findViewById(fl_container_land) == null) {
            showPortraitDetail(organizationId);
        } else {
            showLandscapeDetail(organizationId);
        }
    }

    private void showPortraitDetail(String organizationId) {
        Intent intent = DetailActivity.getIntent(MainActivity.this);
        intent.putExtra(Constants.BUNDLE_ORGANIZATION_ID, organizationId);
        startActivity(intent);
    }

    private void showLandscapeDetail(String organizationId) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(fl_container_land, DetailFragment.newInstance(organizationId))
                .addToBackStack("")
                .commit();
    }

    private void scheduleAlarm() {
        Intent intent = new Intent(getApplicationContext(), CurrencyAlarmReceiver.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, CurrencyAlarmReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        long firstMillis = System.currentTimeMillis();
        AlarmManager alarm = (AlarmManager) MainActivity.this.getSystemService(Context.ALARM_SERVICE);
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis,
                AlarmManager.INTERVAL_FIFTEEN_MINUTES, pIntent);
    }

    private void cancelAlarm() {
        Intent intent = new Intent(getApplicationContext(), CurrencyAlarmReceiver.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, CurrencyAlarmReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarm = (AlarmManager) MainActivity.this.getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(pIntent);
    }

    @Override
    protected void onStop() {
        scheduleAlarm();
        super.onStop();
    }
}
