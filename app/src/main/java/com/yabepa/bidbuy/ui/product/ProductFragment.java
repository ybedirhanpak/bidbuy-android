package com.yabepa.bidbuy.ui.product;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.yabepa.bidbuy.R;
import com.yabepa.bidbuy.data.IPreviewItem;
import com.yabepa.bidbuy.databinding.FragmentProductBinding;
import com.yabepa.bidbuy.ui.common.PreviewListAdapter;

import java.util.ArrayList;

public class ProductFragment extends Fragment {

    private static final String ARG_PRODUCT_ID = "productID";
    private int productID = 0;

    private FragmentProductBinding binding;
    private SharedPreferences sharedPref;

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ProductViewModel viewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE);

        // Get logged in user
        String username = sharedPref.getString(getString(R.string.sp_username), "");
        int userId = sharedPref.getInt(getString(R.string.sp_userId), -1);

        // Update fragment content when product is changed
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

        // Fetch product from server
        viewModel.fetchProduct(productID);

        binding.buttonGiveBid.setOnClickListener(buttonView -> {
            if (username.equals("") && userId == -1) {
                // There is already a user logged in
                Toast.makeText(requireActivity(), "You need to log in first", Toast.LENGTH_SHORT).show();
            } else {
                double bid = Double.parseDouble(binding.editTextBid.getText().toString());
                viewModel.giveBid(productID, userId, bid,
                        bidCreated -> {
                            Toast.makeText(requireActivity(), "Bid is created: " + bidCreated.price, Toast.LENGTH_SHORT).show();
                        }, error -> {
                            Toast.makeText(requireActivity(), error.message, Toast.LENGTH_SHORT).show();
                        });
            }
        });
    }
}