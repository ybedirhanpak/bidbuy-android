package com.yabepa.bidbuy.ui.bid.list;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yabepa.bidbuy.R;
import com.yabepa.bidbuy.data.Bid;
import com.yabepa.bidbuy.databinding.FragmentBidListBinding;
import com.yabepa.bidbuy.databinding.FragmentProductBinding;
import com.yabepa.bidbuy.ui.bid.BidListAdapter;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 */
public class BidListFragment extends Fragment {

    private static final String ARG_PRODUCT_ID = "productID";
    private int productID = 0;

    private FragmentBidListBinding binding;
    private BidListViewModel viewModel;

    ArrayList<Bid> bidList = new ArrayList<>();
    BidListAdapter adapter;

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
        binding = FragmentBidListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        // Set the adapter
        Context context = view.getContext();
        adapter = new BidListAdapter(bidList);
        binding.recyclerViewBidList.setLayoutManager(new LinearLayoutManager(context));
        binding.recyclerViewBidList.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(BidListViewModel.class);

        viewModel.getBidList().observe(requireActivity(), bids -> {
            bidList.clear();
            bidList.addAll(bids);
            adapter.notifyDataSetChanged();
        });

        viewModel.fetchBidList(productID);
    }
}