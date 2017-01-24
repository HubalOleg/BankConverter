package com.oleg.hubal.bankconverter.ui.activity.detail_portrait;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.oleg.hubal.bankconverter.R;
import com.oleg.hubal.bankconverter.global.constants.Constants;
import com.oleg.hubal.bankconverter.ui.fragment.detail.DetailFragment;

public class DetailActivity extends AppCompatActivity {
    public static final String TAG = "DetailActivity";

    public static Intent getIntent(final Context context) {
        Intent intent = new Intent(context, DetailActivity.class);

        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String organizationId = getIntent().getStringExtra(Constants.BUNDLE_ORGANIZATION_ID);
        showDetailFragment(organizationId);
    }

    private void showDetailFragment(String organizationId) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_detail_container, DetailFragment.newInstance(organizationId))
                .commit();
    }
}
