package com.yabepa.bidbuy.ui.bid;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yabepa.bidbuy.R;
import com.yabepa.bidbuy.data.Bid;

import java.util.List;

public class BidListAdapter extends RecyclerView.Adapter<BidListAdapter.ViewHolder> {

    private final List<Bid> mValues;

    public BidListAdapter(List<Bid> items) {
        mValues = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_bid_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.bind(mValues.get(position));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView bidOwner;
        public final TextView bidPrice;
        public final TextView bidDate;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            bidOwner = view.findViewById(R.id.bid_owner);
            bidPrice = view.findViewById(R.id.bid_price);
            bidDate = view.findViewById(R.id.bid_date);
        }

        public void bind(Bid item) {
            String ownerId = "" + item.fromUserId;
            bidOwner.setText(ownerId);
            String price = "₺" + item.price;
            bidPrice.setText(price);
            bidDate.setText(item.createDate);
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + bidPrice.getText() + "'";
        }
    }
}