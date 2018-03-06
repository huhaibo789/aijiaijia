package bean;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by misshu on 2017/5/10/010.
 */
@DatabaseTable(tableName = "vip5")
public class h5gouwu {
    @DatabaseField(columnName = "_id",dataType = DataType.INTEGER,generatedId= true)
    private int _id;
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





    public h5gouwu() {
    }
    public h5gouwu(String productid,String num,String skuid,String isok ){
        this.skuid=skuid;
        this.isok=isok;
        this.num=num;
        this.productid=productid;
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



    @Override
    public String toString() {
        return "gouwu3{" +
                "_id=" + _id +
                ", num='" + num + '\'' +
                ", productid='" + productid + '\'' +
                '}';
    }
}
