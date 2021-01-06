package com.yabepa.bidbuy.ui.product;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yabepa.bidbuy.data.Product;
import com.yabepa.bidbuy.databinding.FragmentProductBinding;
import com.yabepa.bidbuy.network.Client;

public class ProductFragment extends Fragment {

    private static final String ARG_PRODUCT_ID = "productID";
    private String productID = "Empty product";

    private FragmentProductBinding binding;

    private Client client = new Client();

    public ProductFragment() {
        // Required empty public constructor
    }

    public static ProductFragment newInstance(String param1) {
        ProductFragment fragment = new ProductFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PRODUCT_ID, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            productID = getArguments().getString(ARG_PRODUCT_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProductBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.textViewProduct.setText(productID);

        binding.buttonIncreasePrice.setOnClickListener(buttonView -> {
            client.sendRequest("increaseValue", null,
                    response -> {
                        binding.setProduct(new Product(response.body.toString()));
                    },
                    error -> {
                        new Handler(Looper.getMainLooper()).post(() -> {
                            Toast.makeText(requireActivity(), error, Toast.LENGTH_SHORT).show();
                        });
                    });
        });
    }
}