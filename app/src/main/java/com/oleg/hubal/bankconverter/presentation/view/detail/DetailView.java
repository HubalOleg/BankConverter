package com.oleg.hubal.bankconverter.presentation.view.detail;

import android.net.Uri;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.oleg.hubal.bankconverter.model.data.CurrencyUI;
import com.oleg.hubal.bankconverter.model.data.Organization;

import java.util.List;

public interface DetailView extends MvpView {
    void showOrganizationData(Organization organization);
    void showCurrencyData(List<CurrencyUI> currencyUIList);
    @StateStrategyType(SkipStrategy.class)
    void makeCall(String number);
    @StateStrategyType(SkipStrategy.class)
    void showSite(String url);
    @StateStrategyType(SkipStrategy.class)
    void showMap(String location);
    @StateStrategyType(SkipStrategy.class)
    void showError(String error);
    void showShareDialog(Uri imageUri);
}
