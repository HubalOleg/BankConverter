package com.oleg.hubal.bankconverter.detail;

import android.net.Uri;

import com.oleg.hubal.bankconverter.DBFlow;
import com.oleg.hubal.bankconverter.global.utils.CurrencyDatabaseUtils;
import com.oleg.hubal.bankconverter.model.data.Currency;
import com.oleg.hubal.bankconverter.model.data.CurrencyUI;
import com.oleg.hubal.bankconverter.model.data.Organization;
import com.oleg.hubal.bankconverter.presentation.events.CreateImageEvent;
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
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
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
    @Mock
    private Uri mUri;

    @Rule
    public final DBFlow mDBFlow = DBFlow.create();

    @Before
    public void setupPresenter() throws IOException, JSONException {
        MockitoAnnotations.initMocks(DetailUnitTest.this);

        mDetailPresenter = new DetailPresenter();
        mDetailPresenter.attachView(mDetailView);

        Organization organization = new Organization("any_id", "", "", "", "number", "address", "https://github.com", null);
        organization.setCityName("cityName");
        organization.setRegionName("regionName");
        organization.save();
        mDetailPresenter.onLoadOrganization("any_id");
    }

    @Test
    public void loadOrganization_ShowOrganization() {
        mDetailPresenter.onLoadOrganization("");

        verify(mDetailView, times(2)).showOrganizationData(any(Organization.class));
    }

    @Test
    public void loadCurrency_ShowCurrencyUIList() {
        List<Currency> currencyList = new ArrayList<>();
        Currency currency = new Currency("id", "eur", "1.1", "1.1");
        currency.setCurrent(true);
        Currency previousCurrency = new Currency("id", "eur", "1.1", "1.1");
        previousCurrency.setCurrent(false);
        currencyList.add(currency);
        currencyList.add(previousCurrency);
        List<Organization> organizationList = new ArrayList<>();
        Organization organization = new Organization("id", "", "", "", "", "", "", currencyList);
        organizationList.add(organization);
        CurrencyDatabaseUtils.saveOrganizationList(organizationList);

        mDetailPresenter.onLoadOrganization("id");
        mDetailPresenter.onLoadCurrency();

        verify(mDetailView).showCurrencyData(anyListOf(CurrencyUI.class));
    }

    @Test
    public void onFloatingSiteClick_ShowSite() {
        mDetailPresenter.onFloatingSiteClick();

        verify(mDetailView).showSite("https://github.com");
    }

    @Test
    public void onFloatingMapClick() {
        mDetailPresenter.onFloatingMapClick();

        verify(mDetailView).showMap("address, город cityName, regionName");
    }

    @Test
    public void onFloatingPhoneClick_MakeCall() {
        mDetailPresenter.onFloatingPhoneClick();

        verify(mDetailView).makeCall("number");
    }


    @Test
    public void onFloatingPhoneClick_NullNumber() {
        Organization organization = new Organization("null", null, null, null, null, null, null, null);
        organization.save();
        mDetailPresenter.onLoadOrganization("null");
        mDetailPresenter.onFloatingPhoneClick();

        verify(mDetailView).showError(anyString());
    }

    @Test
    public void onCreateImageEvent() {
        mDetailPresenter.onCreateImageEvent(new CreateImageEvent(mUri));

        verify(mDetailView).showShareDialog(mUri);
    }
}
