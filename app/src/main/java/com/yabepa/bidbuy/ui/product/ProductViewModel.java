package com.yabepa.bidbuy.ui.product;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.yabepa.bidbuy.data.Product;

public class ProductViewModel extends ViewModel {

    private MutableLiveData<Product> currentProduct;

    public MutableLiveData<Product> getCurrentProduct() {
        if (currentProduct == null) {
            currentProduct = new MutableLiveData<>(new Product("0"));
        }

        return currentProduct;
    }
}
