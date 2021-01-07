package com.yabepa.bidbuy.ui.product.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yabepa.bidbuy.data.Product;


public class DummyContent {

    public static final List<Product> ITEMS = new ArrayList<>();

    public static final Map<String, Product> ITEM_MAP = new HashMap<String, Product>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createProduct(i));
        }
    }

    private static void addItem(Product item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static Product createProduct(int position) {
        return new Product(String.valueOf(position), "Product " + position, generateName(position));
    }

    private static String generateName(int position) {
        return "Name of product: " + position;
    }
}