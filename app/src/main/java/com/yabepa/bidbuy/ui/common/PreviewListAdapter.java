package com.yabepa.bidbuy.ui.common;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yabepa.bidbuy.R;
import com.yabepa.bidbuy.data.IPreviewItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link IPreviewItem}.
 */
public class PreviewListAdapter extends RecyclerView.Adapter<PreviewListAdapter.ViewHolder> {

    private final List<IPreviewItem> mValues;

    public PreviewListAdapter(List<IPreviewItem> items) {
        mValues = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_preview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        IPreviewItem item = mValues.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView textViewKey;
        public final TextView textViewValue;

        public ViewHolder(View view) {
            super(view);
            textViewKey = (TextView) view.findViewById(R.id.preview_item_key);
            textViewValue = (TextView) view.findViewById(R.id.preview_item_value);
        }

        public void bind(IPreviewItem item) {
            textViewKey.setText(item.getKey());
            textViewValue.setText(item.getValue());
        }
    }
}