package com.yabepa.bidbuy.data;


public class Product {
    public int id;
    public String name;
    public double price;
    public boolean isSold;
    public String imageURL;
    public Bid lastBid;
    public User owner;


    public Product(int id) {
        this.id = id;
        this.name = "Product " + id;
    }

    public String getIdString() {
        return Integer.toString(this.id);
    }

    public String getPriceString() {
        return "₺ " + this.price;
    }

    public String getOwnerString() {
        if(this.owner != null && this.owner.username != null) {
            return this.owner.username;
        }
        return "";
    }

    public String getLastBidString() {
        return Integer.toString(this.lastBid.id);
    }

    public String getIsSoldString() {
        return Boolean.toString(this.isSold);
    }
}
