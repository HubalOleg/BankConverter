package com.oleg.hubal.bankconverter.organization_list;

import com.oleg.hubal.bankconverter.DBFlow;
import com.oleg.hubal.bankconverter.global.utils.CurrencyDatabaseUtils;
import com.oleg.hubal.bankconverter.model.data.City;
import com.oleg.hubal.bankconverter.model.data.Organization;
import com.oleg.hubal.bankconverter.model.data.Region;
import com.oleg.hubal.bankconverter.presentation.events.SuccessSynchronizeEvent;
import com.oleg.hubal.bankconverter.presentation.presenter.organization_list.OrganizationListPresenter;
import com.oleg.hubal.bankconverter.presentation.view.organization_list.OrganizationListView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by User on 23.01.2017.
 */

@RunWith(RobolectricTestRunner.class)
@Config(manifest = "src/main/AndroidManifest.xml")
public class OrganizationListTest {

    @Mock
    private OrganizationListView mOrganizationListView;

    private OrganizationListPresenter mOrganizationListPresenter;

    @Rule
    public final DBFlow mDBFlow = DBFlow.create();

    @Before
    public void setupPresenter() {
        MockitoAnnotations.initMocks(OrganizationListTest.this);

        mOrganizationListPresenter = new OrganizationListPresenter();
        mOrganizationListPresenter.attachView(mOrganizationListView);
    }

    @Test
    public void onMenuCreated_SetSearchQuery() {
        mOrganizationListPresenter.filterOrganizationList("query");
        mOrganizationListPresenter.onMenuCreated();

        verify(mOrganizationListView).setSearchQuery("query");
    }

    @Test
    public void loadOrganizationList_ShowList() {
        mOrganizationListPresenter.loadOrganizationList();

        verify(mOrganizationListView, times(2)).showOrganizationList(anyListOf(Organization.class));
    }

    @Test
    public void onLinkClicked_ShowSite() {
        mOrganizationListPresenter.onLinkClicked("https://github.com");

        verify(mOrganizationListView).showSite("https://github.com");
    }

    @Test
    public void onLinkClicked_InvalidUrl() {
        mOrganizationListPresenter.onLinkClicked("invalid_url");

        verify(mOrganizationListView, times(0)).showSite("https://github.com");
    }

    @Test
    public void onLocationClicked() {
        mOrganizationListPresenter.onLocationClicked("address", "city", "region");

        verify(mOrganizationListView).showMap("address, город city, region");
    }

    @Test
    public void onSearchClosed_ShowList() {
        mOrganizationListPresenter.onSearchClosed();

        verify(mOrganizationListView, times(2)).showOrganizationList(anyList());
    }

    @Test
    public void onPhoneClicked_MakeCall() {
        mOrganizationListPresenter.onPhoneClicked("number");

        verify(mOrganizationListView).makeCall("number");
    }

    @Test
    public void onPhoneClicked_EmptyNumber() {
        mOrganizationListPresenter.onPhoneClicked(null);

        verify(mOrganizationListView).showError(anyString());
    }

    @Test
    public void onDetailClicked_ShowDetail() {
        mOrganizationListPresenter.onDetailClicked("organizationId", 0);

        verify(mOrganizationListView).showDetail("organizationId", 0);
    }

    @Test
    public void onRefresh_RefreshData() {
        mOrganizationListPresenter.onRefresh(false);

        verify(mOrganizationListView).launchLoadCurrencyService();
    }

    @Test
    public void onRefresh_AlreadyRunning() {
        mOrganizationListPresenter.onRefresh(true);

        verify(mOrganizationListView).stopRefreshing();
    }

    @Test
    public void queryOrganizationList_WithKey() {
        Region region = new Region("", "");
        region.save();
        City city = new City("", "");
        city.save();
        List<Organization> organizationList = new ArrayList<>();
        Organization organization = new Organization("", "key", "", "", "", "", "", null);
        organizationList.add(organization);
        CurrencyDatabaseUtils.saveOrganizationList(organizationList);
        mOrganizationListPresenter.loadOrganizationList();
        mOrganizationListPresenter.filterOrganizationList("ke");

        verify(mOrganizationListView, times(3)).showOrganizationList(anyList());
    }

    @Test
    public void queryOrganizationList_EmptyKey() {
        mOrganizationListPresenter.filterOrganizationList("");

        verify(mOrganizationListView, times(2)).showOrganizationList(anyList());
    }

    @Test
    public void successSynchronizeEvent_LoadEvent() {
        mOrganizationListPresenter.successSynchronizeEvent(new SuccessSynchronizeEvent());

        verify(mOrganizationListView, times(2)).showOrganizationList(anyList());
        verify(mOrganizationListView).stopRefreshing();
    }
}
