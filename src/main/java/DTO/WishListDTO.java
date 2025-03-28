package DTO;

public class WishListDTO {
    private int wishListId;
    private String name;

    public WishListDTO(int wishListId, String name){
        this.wishListId = wishListId;
        this.name = name;
    }

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

}
