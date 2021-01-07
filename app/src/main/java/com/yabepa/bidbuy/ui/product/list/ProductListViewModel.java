package com.yabepa.bidbuy.ui.product.list;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.yabepa.bidbuy.data.Product;
import com.yabepa.bidbuy.network.Client;

import java.util.List;

public class ProductListViewModel extends ViewModel {

    MutableLiveData<List<Product>> productList;

    public MutableLiveData<List<Product>> getProductList() {
        if (productList == null) {
            productList = new MutableLiveData<>();
        }

        return productList;
    }

    public void fetchProductList() {
        Client.<List<Product>>sendRequest("getProductList", null, Product.class, true,
                payload -> getProductList().setValue(payload),
                System.out::println);
    }
}
