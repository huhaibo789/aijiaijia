package bean;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;



/**
 * Created by Administrator on 2016/6/29 0029.
 */
@DatabaseTable(tableName = "user")
public class UserBean {
    @DatabaseField(columnName = "_id",dataType = DataType.INTEGER,generatedId =true)
    private int _id;
    @DatabaseField(columnName = "name",dataType = DataType.STRING)
    private String name;
    @DatabaseField(columnName = "pwd",dataType = DataType.STRING)
    private String pwd;

    public UserBean() {
    }

    public UserBean( String name, String pwd) {
        this.name = name;
        this.pwd = pwd;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "id=" + _id +
                ", name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}
