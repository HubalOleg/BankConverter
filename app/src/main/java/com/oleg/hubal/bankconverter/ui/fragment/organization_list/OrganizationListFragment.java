package com.oleg.hubal.bankconverter.ui.fragment.organization_list;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.oleg.hubal.bankconverter.R;
import com.oleg.hubal.bankconverter.adapter.OrganizationAdapter;
import com.oleg.hubal.bankconverter.model.data.Organization;
import com.oleg.hubal.bankconverter.presentation.presenter.organization_list.OrganizationListPresenter;
import com.oleg.hubal.bankconverter.presentation.view.organization_list.OrganizationListView;
import com.oleg.hubal.bankconverter.ui.activity.main.MainActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrganizationListFragment extends MvpAppCompatFragment implements OrganizationListView {
    public static final String TAG = "OrganizationListFragment";

    private OrganizationAdapter mOrganizationAdapter;
    private OrganizationTransitionListener mOrganizationTransitionListener;

    @BindView(R.id.rv_organization_list)
    RecyclerView mOrganizationListRecycler;

    private OrganizationAdapter.OnOrganizationClickListener mOnOrganizationClickListener =
            new OrganizationAdapter.OnOrganizationClickListener() {
                @Override
                public void onLinkClick(String url) {
                    mOrganizationListPresenter.onLinkClicked(url);
                }

                @Override
                public void onLocationClick(Organization organization) {
                    mOrganizationListPresenter.onLocationClicked(organization);
                }

                @Override
                public void onPhoneClick(String phone) {

                }

                @Override
                public void onDetailClick(String organizationId) {

                }
            };

    @InjectPresenter
    OrganizationListPresenter mOrganizationListPresenter;

    public static OrganizationListFragment newInstance() {
        OrganizationListFragment fragment = new OrganizationListFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mOrganizationTransitionListener = (MainActivity) context;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bank_list, container, false);
        ButterKnife.bind(OrganizationListFragment.this, view);

        mOrganizationListRecycler.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mOrganizationListRecycler.setLayoutManager(layoutManager);

        mOrganizationAdapter = new OrganizationAdapter(getContext(), mOnOrganizationClickListener);
        mOrganizationListRecycler.setAdapter(mOrganizationAdapter);

        return view;
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void showOrganizationList(List<Organization> organizationList) {
        mOrganizationAdapter.setOrganizationList(organizationList);
    }

    @Override
    public void showSite(String url) {
        mOrganizationTransitionListener.showSiteTransition(url);
    }

    @Override
    public void showMap(String location) {
        mOrganizationTransitionListener.showMapTransition(location);
    }

    public interface OrganizationTransitionListener {
        void showMapTransition(String location);
        void showSiteTransition(String url);
    }
}
