package com.oleg.hubal.bankconverter.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
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

@Table(database = FlowDatabase.class)
public class Organization extends BaseModel {

    @Column
    @PrimaryKey
    @SerializedName("id")
    @Expose
    public String id;
    @Column
    @SerializedName("title")
    @Expose
    public String title;
    @Column
    @SerializedName("regionId")
    @Expose
    public String regionId;
    @Column
    @SerializedName("cityId")
    @Expose
    public String cityId;
    @Column
    @SerializedName("phone")
    @Expose
    public String phone;
    @Column
    @SerializedName("address")
    @Expose
    public String address;
    @Column
    @SerializedName("link")
    @Expose
    public  String link;
    @ColumnIgnore
    public transient List<Currency> currency;


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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
