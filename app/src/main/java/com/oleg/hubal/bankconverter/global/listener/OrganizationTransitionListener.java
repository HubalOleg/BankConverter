package com.oleg.hubal.bankconverter.global.listener;

/**
 * Created by User on 24.01.2017.
 */

public interface OrganizationTransitionListener {
    void onRefreshData();
    void showMapTransition(String location);
    void showSiteTransition(String url);
    void showCallTransition(String number);
    void showDetailTransition(String organizationId);
}
