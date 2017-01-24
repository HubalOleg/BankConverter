package com.oleg.hubal.bankconverter.presentation.view.main;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface MainView extends MvpView {
    @StateStrategyType(SkipStrategy.class)
    void launchLoadCurrencyService();
    @StateStrategyType(SkipStrategy.class)
    void checkInternetConnection();
//    @StateStrategyType(SkipStrategy.class)
    void showOrganizationList();
    @StateStrategyType(SkipStrategy.class)
    void showDetailFragment(String organizationId);
    void showProgressBar();
    void dismissProgressBar();
}
