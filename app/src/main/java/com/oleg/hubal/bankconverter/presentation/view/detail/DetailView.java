package com.oleg.hubal.bankconverter.presentation.view.detail;

import com.arellomobile.mvp.MvpView;
import com.oleg.hubal.bankconverter.model.data.Organization;

public interface DetailView extends MvpView {
    void showOrganizationData(Organization organization);
}
