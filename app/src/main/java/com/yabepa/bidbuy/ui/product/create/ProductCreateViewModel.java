package com.yabepa.bidbuy.ui.product.create;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.yabepa.bidbuy.data.Product;
import com.yabepa.bidbuy.dto.ProductCreate;
import com.yabepa.bidbuy.network.Client;

public class ProductCreateViewModel extends ViewModel {

    public MutableLiveData<Product> createdProduct = new MutableLiveData<>(null);

    public void createProduct(String name, double minPrice, int ownerId, String imageURL) {

        ProductCreate productCreate = new ProductCreate(name, minPrice, ownerId, imageURL);

        Client.<Product>sendRequest("createProduct", productCreate, Product.class, false,
                product -> createdProduct.setValue(product),
                System.out::println);
    }
}