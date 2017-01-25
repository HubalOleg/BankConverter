package com.oleg.hubal.bankconverter.model.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.oleg.hubal.bankconverter.model.CurrencyDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ColumnIgnore;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

/**
 * Created by User on 19.01.2017.
 */

@Table(database = CurrencyDatabase.class)
public class Organization extends BaseModel {

    @Column
    @PrimaryKey
    @SerializedName("id")
    @Expose
    String id;
    @Column
    @SerializedName("title")
    @Expose
    String title;
    @Column
    @SerializedName("regionId")
    @Expose
    String regionId;
    @Column
    @SerializedName("cityId")
    @Expose
    String cityId;
    @Column
    @SerializedName("phone")
    @Expose
    String phone;
    @Column
    @SerializedName("address")
    @Expose
    String address;
    @Column
    @SerializedName("link")
    @Expose
    String link;
    @Column
    String regionName;
    @Column
    String cityName;
    @ColumnIgnore
    transient List<Currency> currency;

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "currency")
    public List<Currency> oneToManyCurrencies() {
        if (currency == null || currency.isEmpty()) {
            currency = SQLite.select()
                    .from(Currency.class)
                    .where(Currency_Table.organizationId.eq(id))
                    .queryList();
        }
        return currency;
    }

    public Organization() {
    }

    public Organization(String id, String title, String regionId, String cityId, String phone, String address, String link, List<Currency> currency) {
        this.id = id;
        this.title = title;
        this.regionId = regionId;
        this.cityId = cityId;
        this.phone = phone;
        this.address = address;
        this.link = link;
        this.currency = currency;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getRegionId() {
        return regionId;
    }

    public String getCityId() {
        return cityId;
    }

    public String getPhone() {
        return phone;
    }

    public String getRegionName() {
        return regionName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAddress() {
        return address;
    }

    public String getLink() {
        return link;
    }

    public List<Currency> getCurrency() {
        return currency;
    }

    public void setCurrency(List<Currency> currency) {
        this.currency = currency;
    }
}
