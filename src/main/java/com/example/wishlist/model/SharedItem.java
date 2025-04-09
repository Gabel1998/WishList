package com.example.wishlist.model;

public class SharedItem {
    private long id;
    private long originalItemId;
    private long sharedWishlistId;
    private String name;
    private String description;
    private double price;
    private String url;
    private boolean reserved;


    // Getters og Setters

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOriginalItemId() {
        return originalItemId;
    }

    public void setOriginalItemId(long originalItemId) {
        this.originalItemId = originalItemId;
    }

    public long getSharedWishlistId() {
        return sharedWishlistId;
    }

    public void setSharedWishlistId(long sharedWishlistId) {
        this.sharedWishlistId = sharedWishlistId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
