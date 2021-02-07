package com.yabepa.bidbuy.ui.product.create;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.yabepa.bidbuy.R;
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(ProductCreateViewModel.class);
        SharedPreferences sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE);

        // If user is not logged in, navigate back
        String username = sharedPref.getString(getString(R.string.sp_username), "");
        int userId = sharedPref.getInt(getString(R.string.sp_userId), -1);

        if (username.equals("") && userId == -1) {
            // There is no user logged in
            Navigation.findNavController(view).navigateUp();
            Snackbar.make(view, "Please log in first" + username, Snackbar.LENGTH_SHORT).show();
        }

        binding.buttonProductCreate.setOnClickListener(buttonView -> {
            Editable nameEditable = binding.productName.getText();
            Editable minPriceEditable = binding.productMinPrice.getText();

            if (nameEditable == null) {
                Snackbar.make(view, "Please enter name", Snackbar.LENGTH_SHORT).show();
                return;
            }

            String name = nameEditable.toString();

            if (name.equals("")) {
                Snackbar.make(view, "Please enter name", Snackbar.LENGTH_SHORT).show();
                return;
            }

            if (minPriceEditable == null) {
                Snackbar.make(view, "Please enter name", Snackbar.LENGTH_SHORT).show();
                return;
            }

            String priceInput = minPriceEditable.toString();

            if (priceInput.equals("")) {
                Snackbar.make(view, "Please enter name", Snackbar.LENGTH_SHORT).show();
                return;
            }

            double minPrice = Double.parseDouble(priceInput);
            String image = "https://productimages.hepsiburada.net/s/49/1100/10986386784306.jpg";

            viewModel.createProduct(name, minPrice, userId, image);
        });

        // Observe to created product and navigate back
        viewModel.createdProduct.observe(requireActivity(), product -> {
            if (product != null) {
                Navigation.findNavController(view).navigateUp();
                Snackbar.make(view, "Product is created successfully: " + product.name, Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}