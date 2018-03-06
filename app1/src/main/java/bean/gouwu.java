package bean;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;



/**
 * Created by Administrator on 2016/6/27 0027.
 */
@DatabaseTable(tableName = "vip1")
public class gouwu {
    @DatabaseField(columnName = "_id",dataType = DataType.INTEGER,generatedId= true)
    private int _id;
    @DatabaseField(columnName = "picture",dataType = DataType.STRING)
    private String picture;
    @DatabaseField(columnName = "bigtitle",dataType = DataType.STRING)
    private String bigtitle;
    @DatabaseField(columnName = "nowprice",dataType = DataType.STRING)
    private String nowprice;
    @DatabaseField(columnName = "previousprice",dataType = DataType.STRING)
    private String previousprice;
    @DatabaseField(columnName = "num",dataType = DataType.STRING)
    private  String num;
    @DatabaseField(columnName = "productid",dataType = DataType.STRING)
    private  String productid;
    @DatabaseField(columnName = "skuid",dataType = DataType.STRING)
    private  String skuid;
    @DatabaseField(columnName = "skuisok",dataType = DataType.STRING)
    private  String skuisok;
    @DatabaseField(columnName = "skutext",dataType = DataType.STRING)
    private  String skutext;
    //必须有无参构造


    public String getSkuid() {
        return skuid;
    }

    public void setSkuid(String skuid) {
        this.skuid = skuid;
    }

    public String getSkuisok() {
        return skuisok;
    }

    public void setSkuisok(String skuisok) {
        this.skuisok = skuisok;
    }

    public String getSkutext() {
        return skutext;
    }

    public void setSkutext(String skutext) {
        this.skutext = skutext;
    }

    private boolean checked = true;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    public gouwu() {
    }

    public gouwu(String productid,String picture,String num, String bigtitle, String nowprice,String previousprice,String skuid,String skuisok,String skutext) {
      this.picture=picture;
        this.bigtitle=bigtitle;
        this.skuid=skuid;
        this.skuisok=skuisok;
        this.skutext=skutext;
        this.nowprice=nowprice;
        this.previousprice=previousprice;
        this.num=num;
        this.productid=productid;

    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getBigtitle() {
        return bigtitle;
    }

    public void setBigtitle(String bigtitle) {
        this.bigtitle = bigtitle;
    }

    public String getNowprice() {
        return nowprice;
    }

    public void setNowprice(String nowprice) {
        this.nowprice = nowprice;
    }

    public String getPreviousprice() {
        return previousprice;
    }

    public void setPreviousprice(String previousprice) {
        this.previousprice = previousprice;
    }

    @Override
    public String toString() {
        return "gouwu{" +
                "_id=" + _id +
                ", picture='" + picture + '\'' +
                ", bigtitle='" + bigtitle + '\'' +
                ", nowprice='" + nowprice + '\'' +
                ", previousprice='" + previousprice + '\'' +
                ", num='" + num + '\'' +
                ", productid='" + productid + '\'' +
                ", skuid='" + skuid + '\'' +
                ", skuisok='" + skuisok + '\'' +
                ", skutext='" + skutext + '\'' +
                ", checked=" + checked +
                '}';
    }
}
