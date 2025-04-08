package com.example.wishlist.dto;

public class ItemDTO {

    private int itemId;
    private String name;
    private String description;
    private double price;
    private String url;
    private boolean reserved;

    public Long getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(Long wishlistId) {
        this.wishlistId = wishlistId;
    }

    private Long wishlistId;

    public ItemDTO() {}

    public ItemDTO(int itemId, String name, String description, double price, String url, boolean reserved) {
        this.itemId = itemId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.url = url;
        this.reserved = reserved;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
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

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

}
