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
}
