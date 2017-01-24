package com.oleg.hubal.bankconverter.ui.activity.map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.oleg.hubal.bankconverter.R;
import com.oleg.hubal.bankconverter.presentation.presenter.map.MapPresenter;
import com.oleg.hubal.bankconverter.presentation.view.map.MapView;

public class MapActivity extends MvpAppCompatActivity implements MapView {
    public static final String TAG = "MapActivity";
    @InjectPresenter
    MapPresenter mMapPresenter;

    public static Intent getIntent(final Context context) {
        Intent intent = new Intent(context, MapActivity.class);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);


    }
}
