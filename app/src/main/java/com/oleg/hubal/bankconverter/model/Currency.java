package com.oleg.hubal.bankconverter.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by User on 19.01.2017.
 */

@Table(database = FlowDatabase.class)
public class Currency extends BaseModel {

    @Column
    @PrimaryKey
    public String organizationId;
    @Column
    @PrimaryKey
    public String nameAbbreviation;
    @Column(defaultValue = "true")
    @PrimaryKey
    public transient boolean isCurrent;
    @Column
    @SerializedName("ask")
    @Expose
    public String ask;
    @Column
    @SerializedName("bid")
    @Expose
    public String bid;

    public Currency() {
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
