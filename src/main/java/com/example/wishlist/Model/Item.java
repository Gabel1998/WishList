package com.example.wishlist.Model;

public class Item {
    private int itemId;
    private WishList wishlistId;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private String link;
    private boolean reserved;

    public Item(int itemId, WishList wishlistId, String name, String description, double price, int quantity, String link){
        this.itemId = itemId;
        this.wishlistId = wishlistId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.link = link;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public boolean isReserved() {
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
                ", quantity='" + quantity + '\'' +
                ", link='" + link + '\'' +
                '}';
    }

}
