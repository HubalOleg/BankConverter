package com.oleg.hubal.bankconverter.ui.activity.main;

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
import com.oleg.hubal.bankconverter.global.utils.LoadUtils;
import com.oleg.hubal.bankconverter.presentation.presenter.main.MainPresenter;
import com.oleg.hubal.bankconverter.presentation.view.main.MainView;
import com.oleg.hubal.bankconverter.service.LoadCurrencyDataService;
import com.oleg.hubal.bankconverter.ui.fragment.organization_list.OrganizationListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends MvpAppCompatActivity implements MainView, OrganizationListFragment.OrganizationTransitionListener {
    public static final String TAG = "MainActivity";
    public static final String GEO_PREFIX = "geo:0,0?q=";
    public static final String TEL_PREFIX = "tel:";

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
        Intent searchAddress = new  Intent(Intent.ACTION_VIEW,Uri.parse(GEO_PREFIX + location));
        startActivity(searchAddress);
    }

    @Override
    public void showSiteTransition(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    @Override
    public void showCallTransition(String number) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(TEL_PREFIX + number));
        startActivity(callIntent);
    }
}
