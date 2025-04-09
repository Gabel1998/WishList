package com.example.wishlist.dto;

import java.util.List;

public class WishListDTO {
    private int wishListId;
    private String name;
    private String share_token;
    private int userId;
    private List<ItemDTO> items;

    public WishListDTO() {}

    public WishListDTO(int wishListId, String name, String share_token) {
        this.wishListId = wishListId;
        this.name = name;
        this.share_token = share_token;
    }

    // --- Getters og Setters ---

    public int getWishListId() {
        return wishListId;
    }

    public void setWishListId(int wishListId) {
        this.wishListId = wishListId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShareToken() {
        return share_token;
    }

    public void setShareToken(String shareToken) {
        this.share_token = shareToken;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<ItemDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemDTO> items) {
        this.items = items;
    }
}
