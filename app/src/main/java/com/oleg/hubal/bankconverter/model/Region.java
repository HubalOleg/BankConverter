package com.oleg.hubal.bankconverter.model;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

/**
 * Created by User on 20.01.2017.
 */

@Table(database = CurrencyDatabase.class)
public class Region {

    @Column
    @PrimaryKey
    public String regionId;
}
