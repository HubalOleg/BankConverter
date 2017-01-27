package com.oleg.hubal.bankconverter.global.utils;

import com.oleg.hubal.bankconverter.model.CurrencyDatabase;
import com.oleg.hubal.bankconverter.model.data.City;
import com.oleg.hubal.bankconverter.model.data.City_Table;
import com.oleg.hubal.bankconverter.model.data.Currency;
import com.oleg.hubal.bankconverter.model.data.CurrencyAbbr;
import com.oleg.hubal.bankconverter.model.data.Currency_Table;
import com.oleg.hubal.bankconverter.model.data.Date;
import com.oleg.hubal.bankconverter.model.data.Organization;
import com.oleg.hubal.bankconverter.model.data.Organization_Table;
import com.oleg.hubal.bankconverter.model.data.Region;
import com.oleg.hubal.bankconverter.model.data.Region_Table;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;

import java.util.List;

/**
 * Created by User on 20.01.2017.
 */

public class CurrencyDatabaseUtils {

    public static void saveOrganizationList(List<Organization> organizationList) {
        FlowManager.getDatabase(CurrencyDatabase.class)
                .beginTransactionAsync(new ProcessModelTransaction.Builder<>(
                        new ProcessModelTransaction.ProcessModel<Organization>() {
                            @Override
                            public void processModel(Organization organization, DatabaseWrapper wrapper) {
                                String organizationId = organization.getId();
                                if (isOrganizationExist(organizationId)) {
                                    updateCurrency(organizationId);
                                    saveCurrency(organization.getCurrency());
                                } else {
                                    setDataAndSave(organization);
                                }
                            }
                        }).addAll(organizationList).build())
                .build()
                .executeSync();
    }

    private static void setDataAndSave(Organization organization) {
        Region region = SQLite.select().from(Region.class)
                .where(Region_Table.regionId.is(organization.getRegionId()))
                .querySingle();

        City city = SQLite.select().from(City.class)
                .where(City_Table.cityId.is(organization.getCityId()))
                .querySingle();

        String regionName = (region == null) ? "" : region.getName();
        String cityName = (city == null) ? "" : city.getName();

        organization.setRegionName(regionName);
        organization.setCityName(cityName);

        organization.save();
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

    public static void updateCurrency(String organizationId) {
        List<Currency> currencyList = SQLite.select()
                .from(Currency.class)
                .where(Currency_Table.organizationId.is(organizationId))
                .and(Currency_Table.isCurrent.is(true))
                .queryList();

        FlowManager.getDatabase(CurrencyDatabase.class)
                .beginTransactionAsync(new ProcessModelTransaction.Builder<>(
                        new ProcessModelTransaction.ProcessModel<Currency>() {
                            @Override
                            public void processModel(Currency currency, DatabaseWrapper wrapper) {
                                currency.setCurrent(false);
                                currency.save();
                            }
                        }).addAll(currencyList).build())
                .build()
                .executeSync();
    }

    public static void saveCurrency(List<Currency> currencyList) {
        FlowManager.getDatabase(CurrencyDatabase.class)
                .beginTransactionAsync(new ProcessModelTransaction.Builder<>(
                        new ProcessModelTransaction.ProcessModel<Currency>() {
                            @Override
                            public void processModel(Currency currency, DatabaseWrapper wrapper) {
                                currency.save();
                            }
                        }).addAll(currencyList).build())
                .build()
                .executeSync();
    }

    public static void saveList(List<? extends BaseModel> baseModelList) {
        for (BaseModel baseModel : baseModelList) {
            baseModel.save();
        }
    }

    public static void saveRegionList(List<Region> regionList) {
        FlowManager.getDatabase(CurrencyDatabase.class)
                .beginTransactionAsync(new ProcessModelTransaction.Builder<>(
                        new ProcessModelTransaction.ProcessModel<Region>() {
                            @Override
                            public void processModel(Region region, DatabaseWrapper wrapper) {
                                region.save();
                            }
                        }).addAll(regionList).build())
                .build()
                .executeSync();
    }

    public static void saveCityList(List<City> cityList) {
        FlowManager.getDatabase(CurrencyDatabase.class)
                .beginTransactionAsync(new ProcessModelTransaction.Builder<>(
                        new ProcessModelTransaction.ProcessModel<City>() {
                            @Override
                            public void processModel(City city, DatabaseWrapper wrapper) {
                                city.save();
                            }
                        }).addAll(cityList).build())
                .build()
                .executeSync();
    }

    public static void saveCurrencyAbbrList(List<CurrencyAbbr> currencyAbbrList) {
        FlowManager.getDatabase(CurrencyDatabase.class)
                .beginTransactionAsync(new ProcessModelTransaction.Builder<>(
                        new ProcessModelTransaction.ProcessModel<CurrencyAbbr>() {
                            @Override
                            public void processModel(CurrencyAbbr currencyAbbr, DatabaseWrapper wrapper) {
                                currencyAbbr.save();
                            }
                        }).addAll(currencyAbbrList).build())
                .build()
                .executeSync();
    }

    public static Date queryDate() {
        Date date = SQLite.select().from(Date.class).querySingle();
        if (date == null) {
            date = new Date("");
        }
        return date;
    }
}
