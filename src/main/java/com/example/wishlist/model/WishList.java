package com.example.wishlist.model;

public class WishList {
    private int wishListId;
    private User user;
    private String name;
    private String share_token;

    public WishList(int wishListId, User user, String name, String share_token) {
        this.wishListId = wishListId;
        this.user = user;
        this.name = name;
        this.share_token = share_token;
    }

    public WishList(int wishListId) {
        this.wishListId = wishListId;
    }


    public WishList() {}

    public int getWishListId() {
        return wishListId;
    }

    public void setWishListId(int wishListId) {
        this.wishListId = wishListId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShare_token() {
        return share_token;
    }

    public void setShareToken(String share_token) {
        this.share_token = share_token;
    }

    @Override
    public String toString() {
        return "WishList{" +
                "wishListId=" + wishListId +
                ", user=" + user +
                ", name='" + name + '\'' +
                ", share_token='" + share_token + '\'' +
                '}';
    }
}
