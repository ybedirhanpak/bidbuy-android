package com.yabepa.bidbuy.data;

public class Product {
    public String id;
    public String name;
    public String price;

    public Product(String id) {
        this.id = id;
        this.name = "Product " + id;
        this.price = "0";
    }

    public Product(String id, String name, String price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Product(Product product) {
        this.id = product.id;
        this.name = product.name;
        this.price = product.price;
    }
}
