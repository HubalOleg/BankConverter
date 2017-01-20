package com.oleg.hubal.bankconverter.model;

import com.oleg.hubal.bankconverter.global.DBFlow;
import com.oleg.hubal.bankconverter.model.data.City;
import com.oleg.hubal.bankconverter.model.data.Currency;
import com.oleg.hubal.bankconverter.model.data.CurrencyAbbr;
import com.oleg.hubal.bankconverter.model.data.Date;
import com.oleg.hubal.bankconverter.model.data.Organization;
import com.oleg.hubal.bankconverter.model.data.Region;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by User on 20.01.2017.
 */

@RunWith(RobolectricTestRunner.class)
@Config(manifest = "src/main/AndroidManifest.xml")
public class CurrencyDatabaseUtilsTest {

    private List<Organization> mOrganizationList;
    private List<Currency> mCurrencyList;

    @Rule
    public final DBFlow mDBFlow = DBFlow.create();


    @Before
    public void initCurrencyData() throws Exception {
        mOrganizationList = new ArrayList<>();
        mCurrencyList = new ArrayList<>();

        Currency currency = new Currency("organizationId", "nameAbbreviation", "ask", "bid");
        mCurrencyList.add(currency);

        Organization organization = new Organization("organizationId", "title", "regionId", "cityId", "phone", "address", "link", mCurrencyList);
        mOrganizationList.add(organization);
    }

    @Test
    public void queryOrganizationList_CheckFields() {
        CurrencyDatabaseUtils.saveOrganizationList(mOrganizationList);
        List<Organization> organizationListDB = CurrencyDatabaseUtils.queryOrganizationList();

        Organization organization = mOrganizationList.get(0);
        Organization organizationDB = organizationListDB.get(0);

        assertEquals(organization.getId(), organizationDB.getId());
        assertEquals(organization.getLink(), organizationDB.getLink());
        assertEquals(organization.getAddress(), organizationDB.getAddress());
        assertEquals(organization.getCityId(), organizationDB.getCityId());
        assertEquals(organization.getRegionId(), organizationDB.getRegionId());
        assertEquals(organization.getTitle(), organizationDB.getTitle());
        assertEquals(organization.getPhone(), organizationDB.getPhone());
    }

    @Test
    public void queryOrganizationList_CheckCurrencyFields() {
        CurrencyDatabaseUtils.saveOrganizationList(mOrganizationList);
        List<Organization> organizationListDB = CurrencyDatabaseUtils.queryOrganizationList();

        Organization organization = mOrganizationList.get(0);
        Organization organizationDB = organizationListDB.get(0);

        Currency currency = organization.getCurrency().get(0);
        Currency currencyDB = organizationDB.getCurrency().get(0);

        assertEquals(currency.getOrganizationId(), currencyDB.getOrganizationId());
        assertEquals(currency.getNameAbbreviation(), currencyDB.getNameAbbreviation());
        assertEquals(currency.getAsk(), currencyDB.getAsk());
        assertEquals(currency.getBid(), currencyDB.getBid());
        assertEquals(currency.isCurrent(), currencyDB.isCurrent());
    }

    @Test
    public void queryCurrencyAbbrList_CheckFields() {
        List<CurrencyAbbr> currencyAbbrList = new ArrayList<>();
        CurrencyAbbr currencyAbbr = new CurrencyAbbr("abbreviation", "name");
        currencyAbbrList.add(currencyAbbr);
        CurrencyDatabaseUtils.saveList(currencyAbbrList);

        List<CurrencyAbbr> currencyAbbrListDB = CurrencyDatabaseUtils.queryCurrencyAbbrList();
        CurrencyAbbr currencyAbbrDB = currencyAbbrListDB.get(0);

        assertEquals(currencyAbbr.getAbbreviation(), currencyAbbrDB.getAbbreviation());
        assertEquals(currencyAbbr.getName(), currencyAbbrDB.getName());
    }

    @Test
    public void queryCityList_CheckFields() {
        List<City> cityList = new ArrayList<>();
        City city = new City("cityId", "name");
        cityList.add(city);
        CurrencyDatabaseUtils.saveList(cityList);

        List<City> cityListDB = CurrencyDatabaseUtils.queryCityList();
        City cityDB = cityListDB.get(0);

        assertEquals(city.getName(), cityDB.getName());
        assertEquals(city.getCityId(), cityDB.getCityId());
    }

    @Test
    public void queryRegionList_CheckFields() {
        List<Region> regionList = new ArrayList<>();
        Region region = new Region("regionId", "name");
        regionList.add(region);
        CurrencyDatabaseUtils.saveList(regionList);

        List<Region> regionListDB = CurrencyDatabaseUtils.queryRegionList();
        Region regionDB = regionListDB.get(0);

        assertEquals(region.getName(), regionDB.getName());
        assertEquals(region.getRegionId(), regionDB.getRegionId());
    }

    @Test
    public void queryDate_CheckDate() {
        Date date = new Date();
        date.save();

        Date dateDB = CurrencyDatabaseUtils.queryDate();

        assertEquals(date.getId(), dateDB.getId());
        assertEquals(date.getDate(), dateDB.getDate());
    }
}
