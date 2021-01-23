package com.yabepa.bidbuy.data;

public class Bid {
    public int id;
    public int fromUserId;
    public int toProductId;
    public double price;

    public Bid(int id, int fromUserId, int toProductId, double price) {
        this.id = id;
        this.fromUserId = fromUserId;
        this.toProductId = toProductId;
        this.price = price;
    }
}
