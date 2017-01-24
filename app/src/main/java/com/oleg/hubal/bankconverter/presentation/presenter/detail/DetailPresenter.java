package com.oleg.hubal.bankconverter.presentation.presenter.detail;


import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.oleg.hubal.bankconverter.model.data.Organization;
import com.oleg.hubal.bankconverter.model.data.Organization_Table;
import com.oleg.hubal.bankconverter.presentation.view.detail.DetailView;
import com.raizlabs.android.dbflow.sql.language.SQLite;

@InjectViewState
public class DetailPresenter extends MvpPresenter<DetailView> {

    private Organization mOrganization;

    public void onLoadOrganization(String organizationId) {
        mOrganization = SQLite.select()
                .from(Organization.class)
                .where(Organization_Table.id.is(organizationId))
                .querySingle();

        getViewState().showOrganizationData(mOrganization);
    }

}
