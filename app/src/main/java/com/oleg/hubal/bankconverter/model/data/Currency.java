package com.oleg.hubal.bankconverter.model.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.oleg.hubal.bankconverter.model.CurrencyDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by User on 19.01.2017.
 */

@Table(database = CurrencyDatabase.class)
public class Currency extends BaseModel {

    @Column
    @PrimaryKey
    String organizationId;
    @Column
    @PrimaryKey
    String nameAbbreviation;
    @Column
    @PrimaryKey
    transient boolean isCurrent;
    @Column
    @SerializedName("ask")
    @Expose
    String ask;
    @Column
    @SerializedName("bid")
    @Expose
    String bid;

    public Currency() {
    }

    public Currency(String organizationId, String nameAbbreviation, String ask, String bid) {
        this.organizationId = organizationId;
        this.nameAbbreviation = nameAbbreviation;
        this.ask = ask;
        this.bid = bid;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getNameAbbreviation() {
        return nameAbbreviation;
    }

    public void setNameAbbreviation(String nameAbbreviation) {
        this.nameAbbreviation = nameAbbreviation;
    }

    public String getAsk() {
        return ask;
    }

    public String getBid() {
        return bid;
    }

    public boolean isCurrent() {
        return isCurrent;
    }

    public void setCurrent(boolean current) {
        isCurrent = current;
    }
}
