package com.oleg.hubal.bankconverter.ui.fragment.organization_list;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;
import com.oleg.hubal.bankconverter.R;
import com.oleg.hubal.bankconverter.adapter.OrganizationAdapter;
import com.oleg.hubal.bankconverter.global.listener.OrganizationTransitionListener;
import com.oleg.hubal.bankconverter.model.data.Organization;
import com.oleg.hubal.bankconverter.presentation.presenter.organization_list.OrganizationListPresenter;
import com.oleg.hubal.bankconverter.presentation.view.organization_list.OrganizationListView;
import com.oleg.hubal.bankconverter.service.LoadCurrencyDataService;
import com.oleg.hubal.bankconverter.ui.activity.main.MainActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrganizationListFragment extends MvpAppCompatFragment implements OrganizationListView {

    private OrganizationAdapter mOrganizationAdapter;

    private OrganizationTransitionListener mOrganizationTransitionListener;
    @BindView(R.id.srl_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.rv_organization_list)
    RecyclerView mOrganizationListRecycler;

    private OrganizationAdapter.OnOrganizationClickListener mOnOrganizationClickListener =
            new OrganizationAdapter.OnOrganizationClickListener() {
                @Override
                public void onLinkClick(String url) {
                    mOrganizationListPresenter.onLinkClicked(url);
                }

                @Override
                public void onLocationClick(String address, String city, String region) {
                    mOrganizationListPresenter.onLocationClicked(address, city, region);
                }

                @Override
                public void onPhoneClick(String phone) {
                    mOrganizationListPresenter.onPhoneClicked(phone);
                }

                @Override
                public void onDetailClick(String organizationId, int position) {
                    mOrganizationListPresenter.onDetailClicked(organizationId, position);
                }
            };

    private SearchView.OnQueryTextListener mOnQueryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            mOrganizationListPresenter.filterOrganizationList(query);
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    };
    private SearchView.OnCloseListener mOnCloseListener = new SearchView.OnCloseListener() {
        @Override
        public boolean onClose() {
            mOrganizationListPresenter.onSearchClosed();
            return false;
        }
    };

    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mOrganizationListPresenter.onRefresh(LoadCurrencyDataService.isRunning());
        }
    };

    @InjectPresenter(type = PresenterType.GLOBAL)
    OrganizationListPresenter mOrganizationListPresenter;
    private MenuItem mSearchMenuItem;
    private SearchView mSearchView;

    public static OrganizationListFragment newInstance() {
        OrganizationListFragment fragment = new OrganizationListFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mOrganizationTransitionListener = (OrganizationTransitionListener) context;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bank_list, container, false);
        ButterKnife.bind(OrganizationListFragment.this, view);

        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);

        mOrganizationListRecycler.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mOrganizationListRecycler.setLayoutManager(layoutManager);

        mOrganizationAdapter = new OrganizationAdapter(getContext(), mOnOrganizationClickListener);
        mOrganizationListRecycler.setAdapter(mOrganizationAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mOrganizationAdapter.deselectItems();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.search, menu);
        mSearchMenuItem = menu.findItem(R.id.action_search);
        mSearchView = new SearchView(((MainActivity) getContext()).getSupportActionBar().getThemedContext());
        MenuItemCompat.setActionView(mSearchMenuItem, mSearchView);
        mSearchView.setOnQueryTextListener(mOnQueryTextListener);
        mSearchView.setOnCloseListener(mOnCloseListener);
        mOrganizationListPresenter.onMenuCreated();
    }

    @Override
    public void setSearchQuery(String query) {
        mSearchMenuItem.expandActionView();
        mSearchView.setQuery(query, false);
        mSearchView.clearFocus();
    }

    @Override
    public void refreshData() {
        mOrganizationTransitionListener.onRefreshData();
    }

    @Override
    public void launchLoadCurrencyService() {
        getContext().startService(LoadCurrencyDataService.getIntent(getContext()));
    }

    @Override
    public void stopRefreshing() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showDetail(String organizationId, int position) {
        mOrganizationAdapter.setOrganizationSelected(position);
        mOrganizationTransitionListener.showDetailTransition(organizationId);
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

    @Override
    public void makeCall(String number) {
        mOrganizationTransitionListener.showCallTransition(number);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStop() {
        super.onStop();
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
