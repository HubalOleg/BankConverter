package com.oleg.hubal.bankconverter.model;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

/**
 * Created by User on 20.01.2017.
 */

@Table(database = CurrencyDatabase.class)
public class CurrencyAbbr {

    @Column
    @PrimaryKey
    public String abbreviation;
    @Column
    public String name;

    public CurrencyAbbr() {
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
