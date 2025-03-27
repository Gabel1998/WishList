package Model;
//int wishListId burde også stemme overens med database
public class WishList {
    private int wishListId;
    private User userId;
    private String name;

    public WishList(int wishListId, User userId, String name){
        this.wishListId = wishListId;
        this.userId = userId;
        this.name = name;
    }

    public WishList(){

    }

    public int getWishListId() {
        return wishListId;
    }

    public void setWishListId(int wishListId) {
        this.wishListId = wishListId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return "User{" +
                "wishListId=" + wishListId +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}