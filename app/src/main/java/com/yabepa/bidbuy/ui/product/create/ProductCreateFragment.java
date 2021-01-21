package com.yabepa.bidbuy.ui.product.create;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yabepa.bidbuy.databinding.ProductCreateFragmentBinding;

public class ProductCreateFragment extends Fragment {

    private ProductCreateViewModel viewModel;
    private ProductCreateFragmentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ProductCreateFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ProductCreateViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonProductCreate.setOnClickListener(buttonView -> {
            String name = binding.productName.getText().toString();
            double minPrice = Double.parseDouble(binding.productMinPrice.getText().toString());
            String image = "https://productimages.hepsiburada.net/s/49/1100/10986386784306.jpg";

            viewModel.createProduct(name, minPrice, image);
        });
    }
}