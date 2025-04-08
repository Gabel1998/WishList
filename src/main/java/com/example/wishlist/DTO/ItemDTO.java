package com.example.wishlist.DTO;

public class ItemDTO {

    private int itemId;
    private String name;
    private String description;
    private double price;
    private String link;
    private boolean reserved;

    public ItemDTO() {}

    public ItemDTO(int itemId, String name, String description, double price, String link, boolean reserved) {
        this.itemId = itemId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.link = link;
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
}
