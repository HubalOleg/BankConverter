package com.oleg.hubal.bankconverter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.oleg.hubal.bankconverter.R;
import com.oleg.hubal.bankconverter.model.data.Organization;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by User on 23.01.2017.
 */

public class OrganizationAdapter extends RecyclerView.Adapter<OrganizationAdapter.OrganizationViewHolder> {

    private static final String TAG = "OrganizationAdapter";

    private Context mContext;
    private OnOrganizationClickListener mOnOrganizationClickListener;
    private List<Organization> mOrganizationList;
    private List<OrganizationViewHolder> mOrganizationHolderList;
    private OrganizationViewHolder mOrganizationViewHolder;
    private int mActivePosition = -1;


    public OrganizationAdapter(Context context, OnOrganizationClickListener onOrganizationClickListener) {
        mContext = context;
        mOnOrganizationClickListener = onOrganizationClickListener;
        mOrganizationList = new ArrayList<>();
        mOrganizationHolderList = new ArrayList<>();
    }

    @Override
    public OrganizationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_organization, parent, false);

        OrganizationViewHolder organizationViewHolder = new OrganizationViewHolder(view);
        mOrganizationHolderList.add(organizationViewHolder);

        return organizationViewHolder;
    }

    @Override
    public void onBindViewHolder(OrganizationViewHolder holder, int position) {
        holder.onBind(position, mOrganizationList.get(position));
    }

    @Override
    public int getItemCount() {
        return mOrganizationList.size();
    }

    public void setOrganizationList(List<Organization> organizationList) {
        mActivePosition = - 1;
        mOrganizationList = organizationList;
        notifyDataSetChanged();
    }

    public void setOrganizationSelected(int position) {
        if (mOrganizationViewHolder != null) {
            mOrganizationViewHolder.changeSelection(false);
        }

        Log.d(TAG, "setOrganizationSelected: " + position + " " + mActivePosition);
        mActivePosition = position;

        mOrganizationViewHolder = getOrganizationViewHolder(position);
        if (mOrganizationViewHolder != null) {
            mOrganizationViewHolder.changeSelection(true);
        }
    }

    public void deselectItems() {
        mActivePosition = -1;
        if (mOrganizationViewHolder != null) {
            mOrganizationViewHolder.changeSelection(false);
        }
    }

    private OrganizationViewHolder getOrganizationViewHolder(int position) {
        for (OrganizationViewHolder organizationViewHolder : mOrganizationHolderList) {
            if (organizationViewHolder.getBoundPosition() == position) {
                return organizationViewHolder;
            }
        }
        return null;
    }

    class OrganizationViewHolder extends RecyclerView.ViewHolder {

        private Organization mOrganization;
        private int mPosition;

        @BindView(R.id.tv_organization_name)
        TextView mOrganizationNameTextView;
        @BindView(R.id.tv_region)
        TextView mRegionTextView;
        @BindView(R.id.tv_city)
        TextView mCityTextView;
        @BindView(R.id.tv_phone)
        TextView mPhoneTextView;
        @BindView(R.id.tv_address)
        TextView mAddressTextView;
        @BindView(R.id.btn_link)
        ImageButton mLinkButton;
        @BindView(R.id.btn_location)
        ImageButton mLocationButton;
        @BindView(R.id.btn_phone)
        ImageButton mPhoneButton;
        @BindView(R.id.btn_detail)
        ImageButton mDetailButton;

        private View.OnClickListener mOnLinkClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnOrganizationClickListener.onLinkClick(mOrganization.getLink());
            }
        };

        private View.OnClickListener mOnLocationClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnOrganizationClickListener.onLocationClick(mOrganization.getAddress(),
                        mOrganization.getCityName(), mOrganization.getRegionName());
            }
        };

        private View.OnClickListener mOnPhoneClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnOrganizationClickListener.onPhoneClick(mOrganization.getPhone());
            }
        };

        private View.OnClickListener mOnDetailClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnOrganizationClickListener.onDetailClick(mOrganization.getId(), mPosition);
            }
        };

        public OrganizationViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(OrganizationViewHolder.this, itemView);
            mLinkButton.setOnClickListener(mOnLinkClickListener);
            mLocationButton.setOnClickListener(mOnLocationClickListener);
            mPhoneButton.setOnClickListener(mOnPhoneClickListener);
            mDetailButton.setOnClickListener(mOnDetailClickListener);
        }

        public void onBind(int position, Organization organization) {
            mPosition = position;
            mOrganization = organization;

            mOrganizationNameTextView.setText(organization.getTitle());
            mRegionTextView.setText(organization.getRegionName());
            mCityTextView.setText(organization.getCityName());
            mPhoneTextView.setText(String.format(mContext.getString(R.string.card_phone), organization.getPhone()));
            mAddressTextView.setText(String.format(mContext.getString(R.string.card_address), organization.getAddress()));
            changeSelection(mPosition == mActivePosition);
        }

        public int getBoundPosition() {
            return mPosition;
        }

        public void changeSelection(boolean isSelected) {
            mDetailButton.setSelected(isSelected);
        }
    }

    public interface OnOrganizationClickListener {
        void onLinkClick(String url);

        void onLocationClick(String address, String city, String region);

        void onPhoneClick(String phone);

        void onDetailClick(String organizationId, int position);
    }
}
