package com.oleg.hubal.bankconverter.presentation.view.detail;

import com.arellomobile.mvp.MvpView;
import com.oleg.hubal.bankconverter.model.data.CurrencyUI;
import com.oleg.hubal.bankconverter.model.data.Organization;

import java.util.List;

public interface DetailView extends MvpView {
    void showOrganizationData(Organization organization);
    void showCurrencyData(List<CurrencyUI> currencyUIList);
}