package com.oleg.hubal.bankconverter.ui.fragment.detail;

import android.os.Bundle;
import android.view.View;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.oleg.hubal.bankconverter.global.constants.Constants;
import com.oleg.hubal.bankconverter.presentation.presenter.detail.DetailPresenter;
import com.oleg.hubal.bankconverter.presentation.view.detail.DetailView;

public class DetailFragment extends MvpAppCompatFragment implements DetailView {
    public static final String TAG = "DetailFragment";


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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String organizationId = getArguments().getString(Constants.BUNDLE_ORGANIZATION_ID);
        mDetailPresenter.onLoadOrganization(organizationId);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
