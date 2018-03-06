package utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.util.List;

import bean.Food;
import bean.UserBean;

/**
 * Created by Administrator on 2016/6/29 0029.
 */
public class DBHelper1 extends OrmLiteSqliteOpenHelper {
    public DBHelper1(Context context) {
        super(context, "vip.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        //创建表

        try {
            TableUtils.createTableIfNotExists(connectionSource, Food.class);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Food.class, true);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        onCreate(database, connectionSource);
    }
    //增加
    public void insert(Food food) {

        try {
            getDao(Food.class).create(food);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

    }
    //删除
    public void delete(int id) {

        try {
            DeleteBuilder<Food, Integer> builder = (DeleteBuilder<Food, Integer>) getDao(Food.class).deleteBuilder();
            builder.where().eq("_id", id);
            builder.delete();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }
    //更新
    public void updata(Food food, int id) {

        //更新指定列中的值

        try {
            UpdateBuilder<UserBean, Integer> builder = (UpdateBuilder<UserBean, Integer>) getDao(UserBean.class)
                    .updateBuilder();
            builder.updateColumnValue("biaoti", food.getBiaoti());
            builder.updateColumnValue("name", food.getName());
            builder.updateColumnValue("information", food.getInformation());
            builder.where().eq("_id", id);
            builder.update();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }



    }
    //查询
    public List<Food> query(String name) {
        List<Food> list = null;
        try {
            QueryBuilder<Food, Integer> builder = (QueryBuilder<Food, Integer>) getDao(Food.class).queryBuilder();
            list = builder.where().like("name", name).query();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
  //查询所有
    public List<Food> queryAll() {
        List<Food> list = null;

        try {
            list = getDao(Food.class).queryForAll();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
