package com.oleg.hubal.bankconverter.presentation.view.organization_list;

import com.arellomobile.mvp.MvpView;
import com.oleg.hubal.bankconverter.model.data.Organization;

import java.util.List;

public interface OrganizationListView extends MvpView {

    void showOrganizationList(List<Organization> organizationList);
}
