package com.oleg.hubal.bankconverter.model;

import com.oleg.hubal.bankconverter.model.data.City;
import com.oleg.hubal.bankconverter.model.data.Currency;
import com.oleg.hubal.bankconverter.model.data.CurrencyAbbr;
import com.oleg.hubal.bankconverter.model.data.Currency_Table;
import com.oleg.hubal.bankconverter.model.data.Date;
import com.oleg.hubal.bankconverter.model.data.Organization;
import com.oleg.hubal.bankconverter.model.data.Organization_Table;
import com.oleg.hubal.bankconverter.model.data.Region;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

/**
 * Created by User on 20.01.2017.
 */

public class CurrencyDatabaseUtils {

    public static void saveOrganizationList(List<Organization> organizationList) {
        for (Organization organization : organizationList) {
            String organizationId = organization.getId();

            if (isOrganizationExist(organizationId)) {
                updateCurrency(organizationId);
            }

            organization.save();
        }
    }

    private static boolean isOrganizationExist(String organizationId) {
        Organization organization = SQLite.select()
                .from(Organization.class)
                .where(Organization_Table.id.is(organizationId))
                .querySingle();

        if (organization == null) {
            return false;
        } else {
            return true;
        }
    }

    private static void updateCurrency(String organizationId) {
        List<Currency> currencyList = SQLite.select()
                .from(Currency.class)
                .where(Currency_Table.organizationId.is(organizationId))
                .and(Currency_Table.isCurrent.is(true))
                .queryList();

        for (Currency currency : currencyList) {
            currency.setCurrent(false);
            currency.save();
        }
    }

    public static void saveList(List<? extends BaseModel> baseModelList) {
        for (BaseModel baseModel : baseModelList) {
            baseModel.save();
        }
    }

    public static List<Organization> queryOrganizationList() {
        return SQLite.select().from(Organization.class).queryList();
    }

    public static List<CurrencyAbbr> queryCurrencyAbbrList() {
        return SQLite.select().from(CurrencyAbbr.class).queryList();
    }

    public static List<City> queryCityList() {
        return SQLite.select().from(City.class).queryList();
    }

    public static List<Region> queryRegionList() {
        return SQLite.select().from(Region.class).queryList();
    }

    public static Date queryDate() {
        return SQLite.select().from(Date.class).querySingle();
    }
}
