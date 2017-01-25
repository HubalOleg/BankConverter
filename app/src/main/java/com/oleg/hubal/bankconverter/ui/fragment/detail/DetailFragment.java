package com.oleg.hubal.bankconverter.ui.fragment.detail;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.github.clans.fab.FloatingActionButton;
import com.oleg.hubal.bankconverter.R;
import com.oleg.hubal.bankconverter.adapter.CurrencyAdapter;
import com.oleg.hubal.bankconverter.global.constants.Constants;
import com.oleg.hubal.bankconverter.global.listener.OrganizationTransitionListener;
import com.oleg.hubal.bankconverter.model.data.CurrencyUI;
import com.oleg.hubal.bankconverter.model.data.Organization;
import com.oleg.hubal.bankconverter.presentation.presenter.detail.DetailPresenter;
import com.oleg.hubal.bankconverter.presentation.view.detail.DetailView;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailFragment extends MvpAppCompatFragment implements DetailView {
    public static final String TAG = "DetailFragment";

    private OrganizationTransitionListener mOrganizationTransitionListener;
    private CurrencyAdapter mCurrencyAdapter;

    @BindView(R.id.tv_title_detail)
    TextView mTitleTextView;
    @BindView(R.id.tv_site_detail)
    TextView mSiteTextView;
    @BindView(R.id.tv_address_detail)
    TextView mAddressTextView;
    @BindView(R.id.tv_city_detail)
    TextView mCityTextView;
    @BindView(R.id.tv_region_detail)
    TextView mRegionTextView;
    @BindView(R.id.tv_phone_detail)
    TextView mPhoneTextView;
    @BindView(R.id.rv_currency_recycler)
    RecyclerView mCurrencyRecyclerView;
    @BindView(R.id.fab_map)
    FloatingActionButton mMapFloatingButton;
    @BindView(R.id.fab_site)
    FloatingActionButton mSiteFloatingButton;
    @BindView(R.id.fab_phone)
    FloatingActionButton mPhoneFloatingButton;
    @BindView(R.id.share_view)
    ImageView mShareImageView;

    @InjectPresenter
    DetailPresenter mDetailPresenter;

    private View.OnClickListener mOnMapClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mDetailPresenter.onFloatingMapClick();
        }
    };

    private View.OnClickListener mOnSiteClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mDetailPresenter.onFloatingSiteClick();
        }
    };

    private View.OnClickListener mOnPhoneClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mDetailPresenter.onFloatingPhoneClick();
        }
    };

    public static DetailFragment newInstance(String organizationId) {
        DetailFragment fragment = new DetailFragment();

        Bundle args = new Bundle();
        args.putString(Constants.BUNDLE_ORGANIZATION_ID, organizationId);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mOrganizationTransitionListener = (OrganizationTransitionListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(DetailFragment.this, view);

        setHasOptionsMenu(true);

        mMapFloatingButton.setOnClickListener(mOnMapClickListener);
        mSiteFloatingButton.setOnClickListener(mOnSiteClickListener);
        mPhoneFloatingButton.setOnClickListener(mOnPhoneClickListener);

        mCurrencyRecyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mCurrencyRecyclerView.setLayoutManager(layoutManager);

        mCurrencyAdapter = new CurrencyAdapter();
        mCurrencyRecyclerView.setAdapter(mCurrencyAdapter);

        String organizationId = getArguments().getString(Constants.BUNDLE_ORGANIZATION_ID);
        mDetailPresenter.onLoadOrganization(organizationId);
        mDetailPresenter.onLoadCurrency();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.share, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                mDetailPresenter.onShareClick(getContext().getCacheDir().toString());
                return true;
        }
        return false;
    }

    @Override
    public void showOrganizationData(Organization organization) {
        mTitleTextView.setText(organization.getTitle());
        mSiteTextView.setText(organization.getLink());
        mAddressTextView.setText(String.format(getString(R.string.detail_address), organization.getAddress()));
        mCityTextView.setText(String.format(getString(R.string.detail_city), organization.getCityName()));
        mRegionTextView.setText(String.format(getString(R.string.detail_region), organization.getRegionName()));
        mPhoneTextView.setText(String.format(getString(R.string.detail_phone), organization.getPhone()));
    }

    @Override
    public void showCurrencyData(List<CurrencyUI> currencyUIList) {
        mCurrencyAdapter.setCurrencyUIList(currencyUIList);
    }

    @Override
    public void makeCall(String number) {
        mOrganizationTransitionListener.showCallTransition(number);
    }

    @Override
    public void showSite(String url) {
        mOrganizationTransitionListener.showSiteTransition(url);
    }

    @Override
    public void showMap(String location) {
        mOrganizationTransitionListener.showMapTransition(location);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showShareDialog(Uri imageUri) {
        Picasso.with(getContext()).load(imageUri).into(mShareImageView);
    }
}
