package com.oleg.hubal.bankconverter.presentation.presenter.organization_list;


import android.webkit.URLUtil;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.oleg.hubal.bankconverter.global.constants.ErrorConstants;
import com.oleg.hubal.bankconverter.model.data.Organization;
import com.oleg.hubal.bankconverter.presentation.events.SuccessSynchronizeEvent;
import com.oleg.hubal.bankconverter.presentation.view.organization_list.OrganizationListView;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

@InjectViewState
public class OrganizationListPresenter extends MvpPresenter<OrganizationListView> {

    private static final String TAG = "OrganizationListPresent";

    private static final String COMA = ", ";
    private static final String CITY = "город ";

    private List<Organization> mOrganizationList;
    private String mQueryKey;

    public OrganizationListPresenter() {
        EventBus.getDefault().register(OrganizationListPresenter.this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        loadOrganizationList();
    }

    public void onMenuCreated() {
        if (mQueryKey != null && !mQueryKey.isEmpty()) {
            getViewState().setSearchQuery(mQueryKey);
        }
    }

    public void onRefresh(boolean isServiceRunning) {
        if (!isServiceRunning) {
            getViewState().launchLoadCurrencyService();
        } else {
            getViewState().stopRefreshing();
        }
    }

    public void loadOrganizationList() {
        mOrganizationList = SQLite.select().from(Organization.class).queryList();

        getViewState().showOrganizationList(mOrganizationList);
//        SQLite.select()
//                .from(Organization.class)
//                .async()
//                .queryListResultCallback(new QueryTransaction.QueryResultListCallback<Organization>() {
//                    @Override
//                    public void onListQueryResult(QueryTransaction transaction, @NonNull List<Organization> resultList) {
//                        mOrganizationList = resultList;
//                        getViewState().showOrganizationList(resultList);
//                    }
//                }).execute();
    }

    public void filterOrganizationList(String queryKey) {
        mQueryKey = queryKey;

        List<Organization> queryList = new ArrayList<>();

        queryKey = queryKey.toLowerCase();

        for (Organization organization : mOrganizationList) {
            if (isOrganizationContainKey(organization, queryKey)) {
                queryList.add(organization);
            }
        }

        getViewState().showOrganizationList(queryList);
    }

    private boolean isOrganizationContainKey(Organization organization, String key) {
        return (organization.getTitle().toLowerCase().contains(key)
                || organization.getCityName().toLowerCase().contains(key)
                || organization.getRegionName().toLowerCase().contains(key));
    }

    public void onSearchClosed() {
        mQueryKey = "";
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

    public void onDetailClicked(String organizationId, int position) {
        getViewState().showDetail(organizationId, position);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void successSynchronizeEvent(SuccessSynchronizeEvent successSynchronizeEvent) {
        loadOrganizationList();
        getViewState().stopRefreshing();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(OrganizationListPresenter.this);
    }
}
