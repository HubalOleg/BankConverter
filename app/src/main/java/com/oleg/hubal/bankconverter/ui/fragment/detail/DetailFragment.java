package com.oleg.hubal.bankconverter.ui.fragment.detail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.oleg.hubal.bankconverter.R;
import com.oleg.hubal.bankconverter.adapter.CurrencyAdapter;
import com.oleg.hubal.bankconverter.global.constants.Constants;
import com.oleg.hubal.bankconverter.global.listener.OrganizationTransitionListener;
import com.oleg.hubal.bankconverter.model.data.CurrencyUI;
import com.oleg.hubal.bankconverter.model.data.Organization;
import com.oleg.hubal.bankconverter.presentation.presenter.detail.DetailPresenter;
import com.oleg.hubal.bankconverter.presentation.view.detail.DetailView;

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

    @InjectPresenter
    DetailPresenter mDetailPresenter;

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

        mCurrencyRecyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mCurrencyRecyclerView.setLayoutManager(layoutManager);

        mCurrencyAdapter = new CurrencyAdapter();
        mCurrencyRecyclerView.setAdapter(mCurrencyAdapter);

        String organizationId = getArguments().getString(Constants.BUNDLE_ORGANIZATION_ID);
        mDetailPresenter.onLoadOrganization(organizationId);
        mDetailPresenter.onLoadCurrency(organizationId);
        return view;
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
}