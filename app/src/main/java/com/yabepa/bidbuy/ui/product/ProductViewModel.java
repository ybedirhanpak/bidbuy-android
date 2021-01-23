package com.yabepa.bidbuy.ui.product;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.yabepa.bidbuy.common.Callback;
import com.yabepa.bidbuy.data.Bid;
import com.yabepa.bidbuy.data.Product;
import com.yabepa.bidbuy.dto.BidCreate;
import com.yabepa.bidbuy.dto.Message;
import com.yabepa.bidbuy.dto.Retrieve;
import com.yabepa.bidbuy.network.Client;

public class ProductViewModel extends ViewModel {

    private MutableLiveData<Product> product;

    public MutableLiveData<Product> getProduct() {
        if (product == null) {
            product = new MutableLiveData<>(new Product(0));
        }
        return product;
    }

    public void fetchProduct(int productId) {
        Retrieve retrieveBody = new Retrieve(productId);
        Client.<Product>sendRequest("getProduct", retrieveBody, Product.class, false,
                payload -> getProduct().setValue(payload),
                System.out::println);
    }

    public void giveBid(int productId, int userId, double bid,
                        Callback.Success<Bid> success, Callback.Error<Message> error) {
        BidCreate bidCreate = new BidCreate(productId, userId, bid);
        Client.sendRequest("createBid", bidCreate, Bid.class, false,
                success, error);
    }
}
