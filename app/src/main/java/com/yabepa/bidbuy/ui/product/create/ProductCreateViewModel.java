package com.yabepa.bidbuy.ui.product.create;

import androidx.lifecycle.ViewModel;

import com.yabepa.bidbuy.data.Product;
import com.yabepa.bidbuy.dto.ProductCreate;
import com.yabepa.bidbuy.network.Client;

public class ProductCreateViewModel extends ViewModel {

    public void createProduct(String name, double minPrice, String imageURL) {

        ProductCreate productCreate = new ProductCreate(name, minPrice, 1, imageURL);

        Client.<Product>sendRequest("createProduct", productCreate, Product.class, false,
                System.out::println,
                System.out::println);
    }
}