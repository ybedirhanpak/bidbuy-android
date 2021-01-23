package com.yabepa.bidbuy.ui.product.create;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yabepa.bidbuy.R;
import com.yabepa.bidbuy.databinding.ProductCreateFragmentBinding;

public class ProductCreateFragment extends Fragment {

    private ProductCreateViewModel viewModel;
    private ProductCreateFragmentBinding binding;
    private SharedPreferences sharedPref;

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
        sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE);

        // If user is not logged in, navigate back
        String username = sharedPref.getString(getString(R.string.sp_username), "");
        int userId = sharedPref.getInt(getString(R.string.sp_userId), -1);

        if (username.equals("") && userId == -1) {
            // There is no user logged in
            Navigation.findNavController(view).navigateUp();
            Toast.makeText(requireActivity(), "Please log in first" + username, Toast.LENGTH_SHORT).show();
        }

        binding.buttonProductCreate.setOnClickListener(buttonView -> {
            String name = binding.productName.getText().toString();
            double minPrice = Double.parseDouble(binding.productMinPrice.getText().toString());
            String image = "https://productimages.hepsiburada.net/s/49/1100/10986386784306.jpg";

            viewModel.createProduct(name, minPrice, userId, image);
        });

        // Observe to created product and navigate back
        viewModel.createdProduct.observe(requireActivity(), product -> {
            if(product != null) {
                Navigation.findNavController(view).navigateUp();
                Toast.makeText(requireActivity(), "Product is created successfully: " + product.name, Toast.LENGTH_SHORT).show();
            }
        });
    }
}