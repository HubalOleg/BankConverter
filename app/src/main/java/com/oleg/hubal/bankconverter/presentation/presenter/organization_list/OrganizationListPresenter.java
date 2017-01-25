package com.oleg.hubal.bankconverter.presentation.presenter.organization_list;


import android.text.TextUtils;
import android.webkit.URLUtil;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.oleg.hubal.bankconverter.global.constants.ErrorConstants;
import com.oleg.hubal.bankconverter.model.data.Organization;
import com.oleg.hubal.bankconverter.presentation.view.organization_list.OrganizationListView;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
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

    public void onRefresh() {
        getViewState().refreshData();
    }

    public void loadOrganizationList() {
        mOrganizationList = SQLite.select().from(Organization.class).queryList();

        getViewState().showOrganizationList(mOrganizationList);
    }

    public void queryOrganizationList(String queryKey) {
        if (TextUtils.isEmpty(queryKey)) {
            getViewState().showOrganizationList(mOrganizationList);
            return;
        }

        List<Organization> mQueryList = new ArrayList<>();

        queryKey = queryKey.toLowerCase();

        for (Organization organization : mOrganizationList) {
            if (isOrganizationContainKey(organization, queryKey)) {
                mQueryList.add(organization);
            }
        }

        getViewState().showOrganizationList(mQueryList);
    }

    private boolean isOrganizationContainKey(Organization organization, String key) {
        return (organization.getTitle().toLowerCase().contains(key)
                || organization.getCityName().toLowerCase().contains(key)
                || organization.getRegionName().toLowerCase().contains(key));
    }

    public void onSearchClosed() {
        getViewState().showOrganizationList(mOrganizationList);
    }

    public void onLinkClicked(String url) {
        if (URLUtil.isValidUrl(url)) {
            getViewState().showSite(url);
        }
    }

    public void onLocationClicked(String address, String city, String region) {
        String location = address + COMA + CITY + city + COMA + region;
        getViewState().showMap(location);
    }

    public void onPhoneClicked(String phone) {
        if (phone == null) {
            getViewState().showError(ErrorConstants.PHONE_NOT_EXIST);
        } else {
            getViewState().makeCall(phone);
        }
    }

    public void onDetailClicked(String organizationId) {
        getViewState().showDetail(organizationId);
    }
}
