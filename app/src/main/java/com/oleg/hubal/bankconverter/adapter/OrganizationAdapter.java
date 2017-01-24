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

import static com.oleg.hubal.bankconverter.ui.activity.main.MainActivity.TAG;

/**
 * Created by User on 23.01.2017.
 */

public class OrganizationAdapter extends RecyclerView.Adapter<OrganizationAdapter.OrganizationViewHolder> {

    private Context mContext;
    private OnOrganizationClickListener mOnOrganizationClickListener;
    private List<Organization> mOrganizationList;
    private List<Organization> mListCopy;

    public OrganizationAdapter(Context context, OnOrganizationClickListener onOrganizationClickListener) {
        mContext = context;
        mOnOrganizationClickListener = onOrganizationClickListener;
        mOrganizationList = new ArrayList<>();
    }

    @Override
    public OrganizationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_organization, parent, false);

        return new OrganizationViewHolder(view);
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
        mOrganizationList = organizationList;
        mListCopy = organizationList;
        notifyDataSetChanged();
    }

    public void filter(String queryKey) {
        Log.d(TAG, "filter: ");
        mOrganizationList.clear();
        if(queryKey.isEmpty()){
            mOrganizationList.addAll(mListCopy);
        } else{
            queryKey = queryKey.toLowerCase();
            for(Organization item: mListCopy){
                if(item.getTitle().toLowerCase().contains(queryKey)
                        ) {
                    mOrganizationList.add(item);
                }
            }
        }
        notifyDataSetChanged();
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

        public OrganizationViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(OrganizationViewHolder.this, itemView);
            mLinkButton.setOnClickListener(mOnLinkClickListener);
            mLocationButton.setOnClickListener(mOnLocationClickListener);
            mPhoneButton.setOnClickListener(mOnPhoneClickListener);
        }

        public void onBind(int position, Organization organization) {
            mPosition = position;
            mOrganization = organization;

            mOrganizationNameTextView.setText(organization.getTitle());
            mRegionTextView.setText(organization.getRegionName());
            mCityTextView.setText(organization.getCityName());
            mPhoneTextView.setText(String.format(mContext.getString(R.string.card_phone), organization.getPhone()));
            mAddressTextView.setText(String.format(mContext.getString(R.string.card_address), organization.getAddress()));
        }
    }

    public interface OnOrganizationClickListener {
        void onLinkClick(String url);
        void onLocationClick(String address, String city, String region);
        void onPhoneClick(String phone);
        void onDetailClick(String organizationId);
    }
}
