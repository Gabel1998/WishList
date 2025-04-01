package Model;
//int wishListId burde også stemme overens med database
public class WishList {
    private int wishListId;
    private User userId;
    private String name;
    private String share_token;

    public WishList(int wishListId, User userId, String name,  String share_token) {
        this.wishListId = wishListId;
        this.userId = userId;
        this.name = name;
        this.share_token = share_token;
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

    public String getShare_token() {
        return share_token;
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