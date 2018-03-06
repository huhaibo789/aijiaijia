package bean;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;



/**
 * Created by Administrator on 2016/6/27 0027.
 */
@DatabaseTable(tableName = "vip4")
public class gouwu3 {
    @DatabaseField(columnName = "_id",dataType = DataType.INTEGER,generatedId= true)
    private int _id;
    @DatabaseField(columnName = "picture",dataType = DataType.STRING)
    private String picture;
    @DatabaseField(columnName = "num",dataType = DataType.STRING)
    private  String num;
    @DatabaseField(columnName = "productid",dataType = DataType.STRING)
    private  String productid;
    @DatabaseField(columnName = "skuid",dataType = DataType.STRING)
    private  String skuid;
    @DatabaseField(columnName = "isok",dataType = DataType.STRING)
    private  String isok;
    //必须有无参构造

    //public boolean isCheck;

    private boolean checked = true;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public gouwu3() {
    }
   public gouwu3(String picture,String productid,String num,String skuid,String isok){
       this.picture=picture;
       this.num=num;
       this.productid=productid;
       this.skuid=skuid;
       this.isok=isok;
   }

    public String getSkuid() {
        return skuid;
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

    @Override
    public String toString() {
        return "gouwu3{" +
                "_id=" + _id +
                ", picture='" + picture + '\'' +
                ", num='" + num + '\'' +
                ", productid='" + productid + '\'' +
                ", skuid='" + skuid + '\'' +
                ", isok='" + isok + '\'' +
                ", checked=" + checked +
                '}';
    }
}
