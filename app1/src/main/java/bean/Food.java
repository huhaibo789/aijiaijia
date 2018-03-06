package bean;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;



/**
 * Created by Administrator on 2016/6/27 0027.
 */
@DatabaseTable(tableName = "vip")
public class Food {
    @DatabaseField(columnName = "_id",dataType = DataType.INTEGER,generatedId= true)
    private int _id;
    @DatabaseField(columnName = "biaoti",dataType = DataType.STRING)
    private String biaoti;
    @DatabaseField(columnName = "name",dataType = DataType.STRING)
    private String name;
    @DatabaseField(columnName = "information",dataType = DataType.STRING)
    private String information;
    @DatabaseField(columnName = "num",dataType = DataType.STRING)
    private String num;

    //必须有无参构造


    public Food() {
    }

    public Food(String biaoti, String name, String information,String num) {
        this.biaoti = biaoti;
        this.name = name;
        this.information = information;
        this.num=num;

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

    public String getBiaoti() {
        return biaoti;
    }

    public void setBiaoti(String biaoti) {
        this.biaoti = biaoti;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    @Override
    public String toString() {
        return "Food{" +
                "_id=" + _id +
                ", biaoti='" + biaoti + '\'' +
                ", name='" + name + '\'' +
                ", information='" + information + '\'' +
                ", num='" + num + '\'' +
                '}';
    }
}
