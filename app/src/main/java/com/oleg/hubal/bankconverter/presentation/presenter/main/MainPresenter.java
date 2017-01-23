package com.oleg.hubal.bankconverter.presentation.presenter.main;


import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.oleg.hubal.bankconverter.global.utils.CurrencyDatabaseUtils;
import com.oleg.hubal.bankconverter.presentation.events.SuccessSynchronizeEvent;
import com.oleg.hubal.bankconverter.presentation.view.main.MainView;
import com.oleg.hubal.bankconverter.service.LoadCurrencyDataService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    private static final String TAG = "MainPresenter";
    public static final String EMPTY_DATE = "";

    public MainPresenter() {
        EventBus.getDefault().register(MainPresenter.this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadBankData(LoadCurrencyDataService.isRunning());
    }

    public void loadBankData(boolean isLoading) {
        if (isLoading) {
            getViewState().showProgressBar();
        } else {
            getViewState().checkInternetConnection();
        }
    }

    public void onDeviceOnline() {
        startLoading();
    }

    public void onDeviceOffline() {
        if (isDataExist()) {
            getViewState().showOrganizationList();
        } else {
            startLoading();
        }
    }

    private boolean isDataExist() {
        return !EMPTY_DATE.equals(CurrencyDatabaseUtils.queryDate().getDate());
    }

    private void startLoading() {
        getViewState().showProgressBar();
        getViewState().launchLoadCurrencyService();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void successSynchronizeEvent(SuccessSynchronizeEvent successSynchronizeEvent) {
        getViewState().dismissProgressBar();
        getViewState().showOrganizationList();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(MainPresenter.this);
    }
}
