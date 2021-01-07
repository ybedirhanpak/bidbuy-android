package com.yabepa.bidbuy.data;

public class Product {
    public int id;
    public String name;
    public double price;

    public Product(int id) {
        this.id = id;
        this.name = "Product " + id;
        this.price = 0.0;
    }

    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Product(Product product) {
        this.id = product.id;
        this.name = product.name;
        this.price = product.price;
    }

    public String getIdString() {
        return Integer.toString(this.id);
    }

    public String getPriceString() {
        return Double.toString(this.price);
    }

}
