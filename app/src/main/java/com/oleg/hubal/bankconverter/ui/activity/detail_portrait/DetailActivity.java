package com.oleg.hubal.bankconverter.ui.activity.detail_portrait;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.oleg.hubal.bankconverter.R;
import com.oleg.hubal.bankconverter.global.constants.Constants;
import com.oleg.hubal.bankconverter.global.listener.OrganizationTransitionListener;
import com.oleg.hubal.bankconverter.ui.fragment.detail.DetailFragment;

import static com.oleg.hubal.bankconverter.global.constants.Constants.TEL_PREFIX;

public class DetailActivity extends AppCompatActivity implements OrganizationTransitionListener {
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

    @Override
    public void onRefreshData() {

    }

    @Override
    public void showMapTransition(String location) {
        Intent searchAddress = new  Intent(Intent.ACTION_VIEW,Uri.parse(Constants.GEO_PREFIX + location));
        startActivity(searchAddress);
    }

    @Override
    public void showSiteTransition(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    @Override
    public void showCallTransition(String number) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(Constants.TEL_PREFIX + number));
        startActivity(callIntent);
    }

    @Override
    public void showDetailTransition(String organizationId) {

    }
}
