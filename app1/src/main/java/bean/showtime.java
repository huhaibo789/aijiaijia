package bean;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by misshu on 2017/7/12/012.
 */
@DatabaseTable(tableName = "vip5")
public class showtime {
    @DatabaseField(columnName = "_id",dataType = DataType.INTEGER,generatedId= true)
    private int _id;
    @DatabaseField(columnName = "showcount",dataType = DataType.STRING)
    private  String showcount;
    @DatabaseField(columnName = "showshijian",dataType = DataType.STRING)
    private  String showshijian;
    //必须有无参构造
    //public boolean isCheck;
    public showtime() {
    }
    public showtime( String showcount,String showshijian ){
        this.showcount=showcount;
        this.showshijian=showshijian;
    }

    public String getShowshijian() {
        return showshijian;
    }

    public void setShowshijian(String showshijian) {
        this.showshijian = showshijian;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getShowcount() {
        return showcount;
    }

    public void setShowcount(String showcount) {
        this.showcount = showcount;
    }

    @Override
    public String toString() {
        return "showtime{" +
                "_id=" + _id +
                ", showcount='" + showcount + '\'' +
                ", showshijian='" + showshijian + '\'' +
                '}';
    }
}
