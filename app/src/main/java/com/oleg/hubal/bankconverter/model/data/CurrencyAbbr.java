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
public class CurrencyAbbr extends BaseModel {

    @Column
    @PrimaryKey
    String abbreviation;
    @Column
    String name;

    public CurrencyAbbr() {
    }

    public CurrencyAbbr(String abbreviation, String name) {
        this.abbreviation = abbreviation;
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
