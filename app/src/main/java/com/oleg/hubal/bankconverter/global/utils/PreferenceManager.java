package com.oleg.hubal.bankconverter.global.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by User on 23.01.2017.
 */

public class PreferenceManager {
    private static final String PREFS_NAME = "com.oleg.hubal.bankconverter.PREFERENCES";

    private static final String KEY_UNCHANGEABLE_DATA = "UNCHANGEABLE_DATA";

    private static SharedPreferences mSharedPreferences;

    private static SharedPreferences getPref(Context context) {
        if (mSharedPreferences != null)
            return mSharedPreferences;
        else
            return context.getSharedPreferences(PREFS_NAME, 0);
    }

    public static boolean isUnchangeableDataLoaded(Context context) {
        mSharedPreferences = getPref(context);
        return mSharedPreferences.getBoolean(KEY_UNCHANGEABLE_DATA, false);
    }

    public static void setUnchangeableDataLoaded(Context context, boolean isLoaded) {
        mSharedPreferences = getPref(context);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(KEY_UNCHANGEABLE_DATA, isLoaded);
        editor.apply();
    }
}
