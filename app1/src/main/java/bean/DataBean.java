package bean;

public class DataBean {

	int id;
	int carNum;
	String shopName;
	String skuid;
	String isok;
	String skutext;
	String content;
    String picture;
	String status;
	String stringtips;
	String shop_discount;
    String ide;
	double price;
	double previousprice;
	boolean isChoose;

	public String getShop_discount() {
		return shop_discount;
	}

	public void setShop_discount(String shop_discount) {
		this.shop_discount = shop_discount;
	}

	public String getStringtips() {
		return stringtips;
	}

	public void setStringtips(String stringtips) {
		this.stringtips = stringtips;
	}

	public String getSkuid() {
		return skuid;
	}

	public String getSkutext() {
		return skutext;
	}

	public void setSkutext(String skutext) {
		this.skutext = skutext;
	}

	public void setSkuid(String skuid) {
		this.skuid = skuid;
	}

	public String getIsok() {
		return isok;
	}

	public void setIsok(String isok) {
		this.isok = isok;
	}

	public String getIde() {
		return ide;
	}

	public void setIde(String ide) {
		this.ide = ide;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public double getPreviousprice() {
		return previousprice;
	}

	public void setPreviousprice(double previousprice) {
		this.previousprice = previousprice;
	}

	public boolean isChoose() {
		return isChoose;
	}

	public void setChoose(boolean isChoose) {
		this.isChoose = isChoose;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCarNum() {
		return carNum;
	}

	public void setCarNum(int carNum) {
		this.carNum = carNum;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "DataBean{" +
				"id=" + id +
				", carNum=" + carNum +
				", shopName='" + shopName + '\'' +
				", skuid='" + skuid + '\'' +
				", isok='" + isok + '\'' +
				", skutext='" + skutext + '\'' +
				", content='" + content + '\'' +
				", picture='" + picture + '\'' +
				", status='" + status + '\'' +
				", stringtips='" + stringtips + '\'' +
				", shop_discount='" + shop_discount + '\'' +
				", ide='" + ide + '\'' +
				", price=" + price +
				", previousprice=" + previousprice +
				", isChoose=" + isChoose +
				'}';
	}
}