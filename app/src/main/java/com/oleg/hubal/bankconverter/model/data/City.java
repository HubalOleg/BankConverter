package com.oleg.hubal.bankconverter.model.data;

import com.oleg.hubal.bankconverter.model.CurrencyDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by User on 20.01.2017.
 */

@Table(database = CurrencyDatabase.class)
public class City extends BaseModel {

    @Column
    @PrimaryKey
    String cityId;
    @Column
    String name;

    public City() {
    }

    public City(String cityId, String name) {
        this.cityId = cityId;
        this.name = name;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
