package DTO;



public class WishListDTO {
    private int wishListId;
    private String name;
    private String share_token;

    public WishListDTO(int wishListId, String name, String share_token) {
        this.wishListId = wishListId;
        this.name = name;
        this.share_token = share_token;
    }

    public WishListDTO(){}

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

    public void setId(int wishListId) {
        this.wishListId = wishListId;
    }

    public void setShareToken(String shareToken) {
        this.share_token = shareToken;
    }
}
