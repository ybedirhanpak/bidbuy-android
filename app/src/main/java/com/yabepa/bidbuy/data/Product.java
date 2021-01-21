package com.yabepa.bidbuy.data;

import java.util.ArrayList;
import java.util.Date;

public class Product {
    public int id;
    public String name;
    public double price;
    public Date deadline;
    public int ownerId;
    public int lastBidId;
    public boolean isSold;

    public Product(int id) {
        this.id = id;
        this.name = "Product " + id;
    }

    public Product(int id, String name, double price, Date deadline, int ownerId, int lastBidId, boolean isSold) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.deadline = deadline;
        this.ownerId = ownerId;
        this.lastBidId = lastBidId;
        this.isSold = isSold;
    }

    public Product(Product product) {
        this.id = product.id;
        this.name = product.name;
        this.price = product.price;
        this.deadline = product.deadline;
        this.ownerId = product.ownerId;
        this.lastBidId = product.lastBidId;
        this.isSold = product.isSold;
    }

    public String getIdString() {
        return Integer.toString(this.id);
    }

    public String getPriceString() {
        return Double.toString(this.price);
    }

    public String getDeadlineString() {
        if(this.deadline != null) {
            return this.deadline.toString();
        }
        return "-";
    }

    public String getOwnerString() {
        return Integer.toString(this.ownerId);
    }

    public String getLastBidString() {
        return Integer.toString(this.lastBidId);
    }

    public String getIsSoldString() {
        return Boolean.toString(this.isSold);
    }

    public ArrayList<PreviewItem> generatePreviewList() {
        ArrayList<PreviewItem> previewList = new ArrayList<>();

        previewList.add(new PreviewItem("Id", getIdString()));
        previewList.add(new PreviewItem("Name", name));
        previewList.add(new PreviewItem("Price", getPriceString()));
        previewList.add(new PreviewItem("Deadline", getDeadlineString()));
        previewList.add(new PreviewItem("Owner Id", getOwnerString()));
        previewList.add(new PreviewItem("Last Bid Id", getLastBidString()));
        previewList.add(new PreviewItem("Is Sold ?", getIsSoldString()));

        return previewList;
    }
}
