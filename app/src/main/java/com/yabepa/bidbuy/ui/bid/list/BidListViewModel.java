package com.yabepa.bidbuy.ui.bid.list;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.yabepa.bidbuy.data.Bid;
import com.yabepa.bidbuy.dto.ProductIdHolder;
import com.yabepa.bidbuy.network.Client;

import java.util.List;

public class BidListViewModel extends ViewModel {

    MutableLiveData<List<Bid>> bidList;

    public MutableLiveData<List<Bid>> getBidList() {
        if (bidList == null) {
            bidList = new MutableLiveData<>();
        }

        return bidList;
    }

    public void fetchBidList(int toProductId) {
        ProductIdHolder idHolder = new ProductIdHolder(toProductId);
        Client.<List<Bid>>sendRequest("getBidListOfProduct", idHolder, Bid.class, true,
                payload -> getBidList().setValue(payload),
                System.out::println);
    }
}
