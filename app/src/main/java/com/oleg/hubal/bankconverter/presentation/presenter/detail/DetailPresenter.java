package com.oleg.hubal.bankconverter.presentation.presenter.detail;


import android.webkit.URLUtil;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.oleg.hubal.bankconverter.global.constants.ErrorConstants;
import com.oleg.hubal.bankconverter.model.data.Currency;
import com.oleg.hubal.bankconverter.model.data.CurrencyAbbr;
import com.oleg.hubal.bankconverter.model.data.CurrencyAbbr_Table;
import com.oleg.hubal.bankconverter.model.data.CurrencyUI;
import com.oleg.hubal.bankconverter.model.data.Currency_Table;
import com.oleg.hubal.bankconverter.model.data.Organization;
import com.oleg.hubal.bankconverter.model.data.Organization_Table;
import com.oleg.hubal.bankconverter.presentation.view.detail.DetailView;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

@InjectViewState
public class DetailPresenter extends MvpPresenter<DetailView> {

    private static final String COMA = ", ";
    private static final String CITY = "город ";

    private Organization mOrganization;

    public void onLoadOrganization(String organizationId) {
        mOrganization = SQLite.select()
                .from(Organization.class)
                .where(Organization_Table.id.is(organizationId))
                .querySingle();

        getViewState().showOrganizationData(mOrganization);
    }

    public void onLoadCurrency(String organizationId) {
        parseCurrencyData(organizationId);
    }

    private void parseCurrencyData(String organizationId) {
        List<Currency> currentCurrencyList = SQLite.select()
                .from(Currency.class)
                .where(Currency_Table.isCurrent.is(true))
                .and(Currency_Table.organizationId.is(organizationId))
                .queryList();

        List<CurrencyUI> currencyUIList = getCurrencyUIList(currentCurrencyList);

        getViewState().showCurrencyData(currencyUIList);
    }

    private List<CurrencyUI> getCurrencyUIList(List<Currency> currencyList) {
        List<CurrencyUI> currencyUIList = new ArrayList<>();

        for (Currency currency : currencyList) {
            CurrencyUI currencyUI = new CurrencyUI();
            String currencyName = loadCurrencyName(currency.getNameAbbreviation());
            float ask = Float.parseFloat(currency.getAsk());
            float bid = Float.parseFloat(currency.getBid());

            currencyUI.setCurrencyName(currencyName);
            currencyUI.setCurrentAsk(currency.getAsk());
            currencyUI.setCurrentBid(currency.getBid());
            currencyUI.setAskIncreased(false);
            currencyUI.setBidIncreased(false);

            Currency previousCurrency = loadPreviousCurrency(currency);

            if (previousCurrency != null) {
                float previousAsk = Float.parseFloat(previousCurrency.getAsk());
                float previousBid = Float.parseFloat(previousCurrency.getBid());

                currencyUI.setAskIncreased(ask > previousAsk);
                currencyUI.setBidIncreased(bid > previousBid);
            }

            currencyUIList.add(currencyUI);
        }

        return currencyUIList;
    }

    private String loadCurrencyName(String abbreviation) {
        CurrencyAbbr currencyAbbr = SQLite.select()
                .from(CurrencyAbbr.class)
                .where(CurrencyAbbr_Table.abbreviation.is(abbreviation))
                .querySingle();

        return (currencyAbbr == null) ? "" : currencyAbbr.getName();
    }

    private Currency loadPreviousCurrency(Currency currency) {
        return SQLite.select()
                .from(Currency.class)
                .where(Currency_Table.organizationId.is(currency.getOrganizationId()))
                .and(Currency_Table.nameAbbreviation.is(currency.getNameAbbreviation()))
                .and(Currency_Table.isCurrent.is(false))
                .querySingle();
    }

    public void onFloatingMapClick() {
        String location = mOrganization.getAddress()+ COMA + CITY + mOrganization.getCityName() + COMA + mOrganization.getRegionName();
        getViewState().showMap(location);
    }

    public void onFloatingSiteClick() {
        String url = mOrganization.getLink();
        if (URLUtil.isValidUrl(url)) {
            getViewState().showSite(url);
        }
    }

    public void onFloatingPhoneClick() {
        if (mOrganization.getPhone() == null) {
            getViewState().showError(ErrorConstants.PHONE_NOT_EXIST);
        } else {
            getViewState().makeCall(mOrganization.getPhone());
        }
    }

}
