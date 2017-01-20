package com.oleg.hubal.bankconverter.global;

import com.oleg.hubal.bankconverter.global.utils.CurrencyDatabaseUtils;
import com.oleg.hubal.bankconverter.model.data.Currency;
import com.oleg.hubal.bankconverter.model.data.Organization;
import com.raizlabs.android.dbflow.sql.language.SQLite;

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
    public void saveOrganizationList_CheckFields() {
        CurrencyDatabaseUtils.saveOrganizationList(mOrganizationList);
        List<Organization> organizationListDB = SQLite.select().from(Organization.class).queryList();

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
    public void saveOrganizationList_CheckCurrencyFields() {
        CurrencyDatabaseUtils.saveOrganizationList(mOrganizationList);
        List<Organization> organizationListDB = SQLite.select().from(Organization.class).queryList();

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
}
