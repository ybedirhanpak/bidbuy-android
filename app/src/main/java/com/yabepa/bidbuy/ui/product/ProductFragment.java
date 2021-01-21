package com.yabepa.bidbuy.ui.product;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.yabepa.bidbuy.data.IPreviewItem;
import com.yabepa.bidbuy.databinding.FragmentProductBinding;
import com.yabepa.bidbuy.ui.common.PreviewListAdapter;

import java.util.ArrayList;

public class ProductFragment extends Fragment {

    private static final String ARG_PRODUCT_ID = "productID";
    private int productID = 0;

    private FragmentProductBinding binding;

    ArrayList<IPreviewItem> previewList = new ArrayList<>();
    PreviewListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            productID = getArguments().getInt(ARG_PRODUCT_ID);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProductBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        int mColumnCount = 2;
        Context context = view.getContext();
        RecyclerView recyclerView = binding.productPreviewList;

        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        adapter = new PreviewListAdapter(previewList);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ProductViewModel viewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        viewModel.getProduct().observe(requireActivity(), product -> {
            // Update product
            binding.setProduct(product);
            // Update preview list
            this.previewList.clear();
            this.previewList.addAll(product.generatePreviewList());
            this.adapter.notifyDataSetChanged();
            // Update product image
            if (product.imageURL != null && !product.imageURL.equals("")) {
                Picasso.get().load(product.imageURL).into(binding.productImage);
            }
        });
        viewModel.fetchProduct(productID);
    }
}