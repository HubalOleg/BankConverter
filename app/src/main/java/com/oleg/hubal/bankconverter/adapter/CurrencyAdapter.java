package com.oleg.hubal.bankconverter.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.oleg.hubal.bankconverter.R;
import com.oleg.hubal.bankconverter.model.data.CurrencyUI;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by User on 25.01.2017.
 */

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder> {

    private List<CurrencyUI> mCurrencyUIList;

    public CurrencyAdapter() {
        mCurrencyUIList = new ArrayList<>();
    }

    @Override
    public CurrencyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_currency, parent, false);

        return new CurrencyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CurrencyViewHolder holder, int position) {
        holder.onBind(mCurrencyUIList.get(position));
    }

    public void setCurrencyUIList(List<CurrencyUI> currencyUIList) {
        mCurrencyUIList = currencyUIList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mCurrencyUIList.size();
    }

    class CurrencyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name_currency)
        TextView mNameTextView;
        @BindView(R.id.tv_ask_currency)
        TextView mAskTextView;
        @BindView(R.id.tv_bid_currency)
        TextView mBidTextView;
        @BindView(R.id.iv_ask_arrow)
        ImageView mAskArrowImageView;
        @BindView(R.id.iv_bid_arrow)
        ImageView mBidArrowImageView;


        public CurrencyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(CurrencyViewHolder.this, itemView);
        }

        public void onBind(CurrencyUI currencyUI) {
            mNameTextView.setText(currencyUI.getCurrencyName());
            mAskTextView.setText(String.valueOf(currencyUI.getCurrentAsk()));
            mBidTextView.setText(String.valueOf(currencyUI.getCurrentBid()));

            setAskSelector(currencyUI.isAskIncreased());
            setBidSelector(currencyUI.isBidIncreased());
        }

        private void setAskSelector(boolean isIncreased) {
            mAskTextView.setSelected(isIncreased);
            mAskArrowImageView.setSelected(isIncreased);
        }

        private void setBidSelector(boolean isIncreased) {
            mBidTextView.setSelected(isIncreased);
            mBidArrowImageView.setSelected(isIncreased);
        }
    }

}
