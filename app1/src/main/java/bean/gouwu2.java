package bean;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;



/**
 * Created by Administrator on 2016/6/27 0027.
 */
@DatabaseTable(tableName = "vip3")
public class gouwu2 {
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
    @DatabaseField(columnName = "id",dataType = DataType.STRING)
    private String id;
    //必须有无参构造

    //public boolean isCheck;

    private boolean checked = true;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public gouwu2() {
    }

    public gouwu2(String picture, String bigtitle, String nowprice,String previousprice,String id) {
        this.picture=picture;
        this.bigtitle=bigtitle;
        this.nowprice=nowprice;
        this.previousprice=previousprice;
        this.id=id;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "gouwu2{" +
                "_id=" + _id +
                ", picture='" + picture + '\'' +
                ", bigtitle='" + bigtitle + '\'' +
                ", nowprice='" + nowprice + '\'' +
                ", previousprice='" + previousprice + '\'' +
                ", id='" + id + '\'' +
                ", checked=" + checked +
                '}';
    }
}
