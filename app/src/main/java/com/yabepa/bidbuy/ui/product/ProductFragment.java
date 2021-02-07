package com.yabepa.bidbuy.ui.product;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import com.yabepa.bidbuy.R;
import com.yabepa.bidbuy.data.Bid;
import com.yabepa.bidbuy.data.Product;
import com.yabepa.bidbuy.databinding.FragmentProductBinding;
import com.yabepa.bidbuy.ui.bid.BidListAdapter;

import java.util.ArrayList;

public class ProductFragment extends Fragment {

    private static final String ARG_PRODUCT_ID = "productID";
    private int productID = 0;

    private FragmentProductBinding binding;
    private ProductViewModel viewModel;

    ArrayList<Bid> bidList = new ArrayList<>();
    BidListAdapter bidListAdapter;

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

        // Configure Last Bids
        Context context = view.getContext();
        RecyclerView recyclerView = binding.recyclerViewLastBids;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        bidListAdapter = new BidListAdapter(bidList);
        recyclerView.setAdapter(bidListAdapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        SharedPreferences sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE);

        // Get logged in user
        String username = sharedPref.getString(getString(R.string.sp_username), "");
        int userId = sharedPref.getInt(getString(R.string.sp_userId), -1);

        // Update fragment content when product is changed
        viewModel.getProduct().observe(requireActivity(), product -> {
            // Update product
            binding.setProduct(product);

            // Update product image
            if (product.imageURL != null && !product.imageURL.equals("")) {
                Picasso.get().load(product.imageURL).into(binding.productImage);
            }
        });

        // Fetch product from server
        viewModel.fetchProduct(productID,
                product -> {
                    viewModel.getProduct().setValue(product);
                    if (product.lastBid != null) {
                        addBidToList(product.lastBid);
                    }
                },
                error -> {
                    Navigation.findNavController(requireView()).navigateUp();
                    Snackbar.make(view, error.message, Snackbar.LENGTH_SHORT).show();
                });

        binding.buttonGiveBid.setOnClickListener(buttonView -> {
            Editable bidEditText = binding.editTextBid.getText();

            if (bidEditText == null) {
                Snackbar.make(view, "Please enter a bid.", Snackbar.LENGTH_SHORT).show();
                return;
            }

            String bidText = bidEditText.toString();

            if (username.equals("") && userId == -1) {
                // There is already a user logged in
                Snackbar.make(view, "You need to log in first." + username, Snackbar.LENGTH_SHORT).show();
            } else if (bidText.equals("")) {
                // There is already a user logged in
                Snackbar.make(view, "Please enter a bid.", Snackbar.LENGTH_SHORT).show();
            } else {
                double bid = Double.parseDouble(binding.editTextBid.getText().toString());
                bid = Math.round(bid * 100) / 100.0;
                viewModel.giveBid(userId, productID, bid,
                        bidCreated -> {
                            Snackbar.make(view, "Bid is created: ₺" + bidCreated.price, Snackbar.LENGTH_SHORT).show();
                            // Reset editText
                            binding.editTextBid.setText("");
                        }, error -> Snackbar.make(view, error.message, Snackbar.LENGTH_SHORT).show());
            }
        });

        binding.buttonPlusOne.setOnClickListener(buttonView -> increaseBidText(1));

        binding.buttonPlusFive.setOnClickListener(buttonView -> increaseBidText(5));

        binding.buttonPlusTen.setOnClickListener(buttonView -> increaseBidText(10));

        binding.textViewSeeAll.setOnClickListener(textView -> {
            Bundle bundle = new Bundle();
            bundle.putInt("productID", productID);
            Navigation.findNavController(requireView()).navigate(R.id.action_productFragment_to_bidFragment, bundle);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bidList.clear();
        bidListAdapter.notifyDataSetChanged();
        viewModel.stopFetchProduct(productID);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel.stopFetchProduct(productID);
    }

    private void addBidToList(Bid bid) {
        if (bidList.size() > 0 && bidList.get(0).price == bid.price) {
            return;
        }
        bidList.add(0, bid);
        bidListAdapter.notifyDataSetChanged();
    }

    private void increaseBidText(double increaseAmount) {
        Product currentProduct = viewModel.getProduct().getValue();
        if (currentProduct != null) {
            double newBid = currentProduct.price + increaseAmount;
            newBid = Math.round(newBid * 100) / 100.0;
            String price = Double.toString(newBid);
            binding.editTextBid.setText(price);
        }
    }
}