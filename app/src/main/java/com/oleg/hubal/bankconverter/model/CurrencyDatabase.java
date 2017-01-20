package com.oleg.hubal.bankconverter.model;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by User on 19.01.2017.
 */

@Database(name = CurrencyDatabase.NAME, version = CurrencyDatabase.VERSION)
public class CurrencyDatabase {
    public static final String NAME = "TopFourDatabase";
    public static final int VERSION = 2;
}
