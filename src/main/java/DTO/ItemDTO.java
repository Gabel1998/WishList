package DTO;

import Model.WishList;

public class ItemDTO {

    private String name;
    private String description;
    private double price;
    private int quantity;
    private String link;

    public ItemDTO(String name, String description, double price, int quantity, String link){
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.link = link;
    }

    public ItemDTO(){

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
}
