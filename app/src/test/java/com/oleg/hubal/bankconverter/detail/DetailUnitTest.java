package com.oleg.hubal.bankconverter.detail;

import com.oleg.hubal.bankconverter.DBFlow;
import com.oleg.hubal.bankconverter.model.data.CurrencyUI;
import com.oleg.hubal.bankconverter.model.data.Organization;
import com.oleg.hubal.bankconverter.presentation.presenter.detail.DetailPresenter;
import com.oleg.hubal.bankconverter.presentation.view.detail.DetailView;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.IOException;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.verify;

/**
 * Created by User on 24.01.2017.
 */

@RunWith(RobolectricTestRunner.class)
@Config(manifest = "src/main/AndroidManifest.xml")
public class DetailUnitTest {

    private DetailPresenter mDetailPresenter;

    @Mock
    private DetailView mDetailView;

    @Rule
    public final DBFlow mDBFlow = DBFlow.create();

    @Before
    public void setupPresenter() throws IOException, JSONException {
        MockitoAnnotations.initMocks(DetailUnitTest.this);

        mDetailPresenter = new DetailPresenter();
        mDetailPresenter.attachView(mDetailView);
    }

    @Test
    public void loadOrganization_ShowOrganization() {
        mDetailPresenter.onLoadOrganization("");

        verify(mDetailView).showOrganizationData(any(Organization.class));
    }

    @Test
    public void loadCurrency_ShowCurrencyUIList() {
        mDetailPresenter.onLoadCurrency("");

        verify(mDetailView).showCurrencyData(anyListOf(CurrencyUI.class));
    }
}
