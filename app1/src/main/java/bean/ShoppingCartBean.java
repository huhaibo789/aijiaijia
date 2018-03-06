package bean;

/**
 * Created by misshu on 2017/6/28/028.
 */
public class ShoppingCartBean {
    private int id;
    private String imageUrl; //图片地址
    private String shoppingName; //商品名称
    private String shopnumber;//商品编号
    private String skuname; //sku名称
    private double price; //商品价格
    private int num;//商品数量
    public boolean isChoosed; //商品是否选中
    public boolean isCheck = false;
    private int count;//商品数量
    public ShoppingCartBean() {
    }
    public ShoppingCartBean(int id, String imageUrl, String shoppingName, String shopnumber, String skuname, double price, int num, boolean isChoosed, boolean isCheck, int count) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.shoppingName = shoppingName;
        this.shopnumber = shopnumber;
        this.skuname = skuname;
        this.price = price;
        this.num = num;
        this.isChoosed = isChoosed;
        this.isCheck = isCheck;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getShoppingName() {
        return shoppingName;
    }

    public void setShoppingName(String shoppingName) {
        this.shoppingName = shoppingName;
    }

    public String getShopnumber() {
        return shopnumber;
    }

    public void setShopnumber(String shopnumber) {
        this.shopnumber = shopnumber;
    }

    public String getSkuname() {
        return skuname;
    }

    public void setSkuname(String skuname) {
        this.skuname = skuname;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setChoosed(boolean choosed) {
        isChoosed = choosed;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "ShoppingCartBean{" +
                "id=" + id +
                ", imageUrl='" + imageUrl + '\'' +
                ", shoppingName='" + shoppingName + '\'' +
                ", shopnumber='" + shopnumber + '\'' +
                ", skuname='" + skuname + '\'' +
                ", price=" + price +
                ", num=" + num +
                ", isChoosed=" + isChoosed +
                ", isCheck=" + isCheck +
                ", count=" + count +
                '}';
    }
}
