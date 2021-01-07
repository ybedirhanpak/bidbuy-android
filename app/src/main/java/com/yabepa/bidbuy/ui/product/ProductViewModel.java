package com.yabepa.bidbuy.ui.product;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.yabepa.bidbuy.data.Product;
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
}
