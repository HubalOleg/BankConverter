package com.oleg.hubal.bankconverter.organization_list;

import com.oleg.hubal.bankconverter.global.DBFlow;
import com.oleg.hubal.bankconverter.model.data.Organization;
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

import java.util.List;

import static org.mockito.Matchers.anyListOf;
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
    @Mock
    private List<Organization> mOrganizationList;

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
    public void loadOrganizationList_ShowList() {
        mOrganizationListPresenter.loadOrganizationList();

        verify(mOrganizationListView, times(2)).showOrganizationList(anyListOf(Organization.class));
    }
}
