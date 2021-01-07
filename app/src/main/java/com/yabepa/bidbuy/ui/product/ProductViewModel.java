package com.yabepa.bidbuy.ui.product;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.yabepa.bidbuy.data.Product;
import com.yabepa.bidbuy.network.Client;

public class ProductViewModel extends ViewModel {

    private MutableLiveData<Product> currentProduct;

    public MutableLiveData<Product> getCurrentProduct() {
        if (currentProduct == null) {
            currentProduct = new MutableLiveData<>(new Product("0"));
        }

        return currentProduct;
    }

    public void increasePrice() {
        Client.sendRequest("increaseValue", null, value -> {
            Product updatedProduct = new Product(getCurrentProduct().getValue());
            updatedProduct.price = value.toString();
            getCurrentProduct().setValue(updatedProduct);
        }, System.out::println);
    }
}
