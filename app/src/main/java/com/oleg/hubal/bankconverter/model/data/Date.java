package com.oleg.hubal.bankconverter.model.data;

import com.oleg.hubal.bankconverter.model.CurrencyDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

/**
 * Created by User on 20.01.2017.
 */

@Table(database = CurrencyDatabase.class)
public class Date {

    @Column(defaultValue = "0")
    @PrimaryKey
    public int id;
    @Column
    public String date;

    public Date() {
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }
}
