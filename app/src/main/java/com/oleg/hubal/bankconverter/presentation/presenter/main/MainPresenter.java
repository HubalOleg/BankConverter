package com.oleg.hubal.bankconverter.presentation.presenter.main;


import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.oleg.hubal.bankconverter.presentation.view.main.MainView;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    private static final String TAG = "MainPresenter";

    public MainPresenter() {

    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadBankData();
    }

    public void loadBankData() {
        getViewState().launchLoadBankDataJob();
    }
}
