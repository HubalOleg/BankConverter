package com.oleg.hubal.bankconverter.presentation.presenter.organization_list;


import android.webkit.URLUtil;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.oleg.hubal.bankconverter.model.data.Organization;
import com.oleg.hubal.bankconverter.presentation.view.organization_list.OrganizationListView;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

@InjectViewState
public class OrganizationListPresenter extends MvpPresenter<OrganizationListView> {

    private static final String COMA = ", ";
    private static final String CITY = "город ";

    private List<Organization> mOrganizationList;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        loadOrganizationList();
    }

    public void loadOrganizationList() {
        mOrganizationList = SQLite.select().from(Organization.class).queryList();

        getViewState().showOrganizationList(mOrganizationList);
    }

    public void onLinkClicked(String url) {
        if (URLUtil.isValidUrl(url)) {
            getViewState().showSite(url);
        }
    }

    public void onLocationClicked(Organization organization) {
        String locationBuilder = organization.getAddress() + COMA +
                CITY + organization.getCityName() + COMA +
                organization.getRegionName();
        getViewState().showMap(locationBuilder);
    }
}
