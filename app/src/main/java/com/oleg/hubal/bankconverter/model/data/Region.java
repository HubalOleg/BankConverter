package com.oleg.hubal.bankconverter.model.data;

import com.oleg.hubal.bankconverter.model.CurrencyDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by User on 20.01.2017.
 */

@Table(database = CurrencyDatabase.class)
public class Region extends BaseModel {

    @Column
    @PrimaryKey
    @ForeignKey(tableClass = Organization.class)
    String regionId;
    @Column
    String name;

    public Region() {
    }

    public Region(String regionId, String name) {
        this.regionId = regionId;
        this.name = name;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
