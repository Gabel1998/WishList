package com.example.wishlist.Model;

public class Item {
    private int itemId;
    private WishList wishlistId;
    private String name;
    private String description;
    private double price;
    private String url;
    private boolean reserved;

    public Item(int itemId, WishList wishlistId, String name, String description, double price, String url){
        this.itemId = itemId;
        this.wishlistId = wishlistId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.url = url;
    }

    public Item(){

    }

    // Getters og Setters
    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
    public String getName() {
        return name;
    }

    public void setWishList(WishList wishList) {
        this.wishlistId = wishList;
    }

    public WishList getWishlistId() {
        return wishlistId;
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

    public boolean getReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }



    @Override
    public String toString(){
        return "User{" +
                "itemId=" + itemId +
                ", wishListId='" + wishlistId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price='" + price + '\'' +
                ", link='" + url + '\'' +
                '}';
    }

}
