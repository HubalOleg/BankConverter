package com.oleg.hubal.bankconverter.presentation.view.organization_list;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.oleg.hubal.bankconverter.model.data.Organization;

import java.util.List;

public interface OrganizationListView extends MvpView {

//    @StateStrategyType(SkipStrategy.class)
    void showOrganizationList(List<Organization> organizationList);
    @StateStrategyType(SkipStrategy.class)
    void showSite(String url);
    @StateStrategyType(SkipStrategy.class)
    void showMap(String location);
    @StateStrategyType(SkipStrategy.class)
    void showError(String error);
    @StateStrategyType(SkipStrategy.class)
    void makeCall(String number);
    @StateStrategyType(SkipStrategy.class)
    void refreshData();
    @StateStrategyType(SkipStrategy.class)
    void showDetail(String organizationId, int position);
    @StateStrategyType(SkipStrategy.class)
    void launchLoadCurrencyService();
    @StateStrategyType(SkipStrategy.class)
    void stopRefreshing();
    @StateStrategyType(SkipStrategy.class)
    void setSearchQuery(String query);
}
