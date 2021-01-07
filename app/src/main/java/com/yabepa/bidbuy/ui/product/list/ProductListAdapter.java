package com.yabepa.bidbuy.ui.product.list;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yabepa.bidbuy.R;
import com.yabepa.bidbuy.data.Product;

import java.util.List;


public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {

    private final List<Product> productList;

    public ProductListAdapter(List<Product> items) {
        productList = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_product_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView textViewId;
        public final TextView textViewName;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            textViewId = view.findViewById(R.id.product_number);
            textViewName = view.findViewById(R.id.product_name);
        }

        public void bind(Product product) {
            textViewId.setText(product.id);
            textViewName.setText(product.name);
            mView.setOnClickListener(view -> {
                Bundle bundle = new Bundle();
                bundle.putString("productID", product.id);
                Navigation.findNavController(view).navigate(R.id.action_productListFragment_to_productFragment, bundle);
            });
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + textViewName.getText() + "'";
        }
    }
}